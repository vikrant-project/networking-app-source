package com.souldevil.networktool.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.VpnService;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.souldevil.networktool.R;
import com.souldevil.networktool.SoulDevilApp;
import com.souldevil.networktool.data.model.ConnectionRecord;
import com.souldevil.networktool.data.model.PacketInfo;
import com.souldevil.networktool.data.repository.ConnectionRepository;
import com.souldevil.networktool.data.repository.PcapRepository;
import com.souldevil.networktool.ui.main.MainActivity;
import com.souldevil.networktool.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * VPN Service for capturing network packets
 */
public class CaptureService extends VpnService {
    private static final String TAG = "CaptureService";
    private static final int NOTIFICATION_ID = 1001;

    private ParcelFileDescriptor vpnInterface;
    private Thread captureThread;
    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private PcapWriter pcapWriter;
    private ConnectionRepository connectionRepository;
    private PcapRepository pcapRepository;

    private AtomicLong totalBytesSent = new AtomicLong(0);
    private AtomicLong totalBytesReceived = new AtomicLong(0);
    private AtomicLong packetCount = new AtomicLong(0);

    public static final String ACTION_START = "com.souldevil.networktool.START";
    public static final String ACTION_STOP = "com.souldevil.networktool.STOP";

    @Override
    public void onCreate() {
        super.onCreate();
        connectionRepository = new ConnectionRepository(getApplication());
        pcapRepository = new PcapRepository(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                startCapture();
            } else if (ACTION_STOP.equals(action)) {
                stopCapture();
            }
        }
        return START_STICKY;
    }

    private void startCapture() {
        if (isRunning.get()) {
            Log.d(TAG, "Capture already running");
            return;
        }

        try {
            // Create VPN interface
            Builder builder = new Builder();
            builder.setSession("Soul Devil Network Tool")
                    .addAddress("10.215.173.1", 32)
                    .addRoute("0.0.0.0", 0)
                    .setMtu(1500)
                    .setBlocking(false);

            // Allow all apps by default
            // You can add builder.addAllowedApplication() or addDisallowedApplication() here

            vpnInterface = builder.establish();

            if (vpnInterface == null) {
                Log.e(TAG, "Failed to establish VPN interface");
                stopSelf();
                return;
            }

            // Initialize PCAP writer
            File pcapFile = new File(pcapRepository.getPcapDirectory(), FileUtils.generatePcapFilename());
            pcapWriter = new PcapWriter(pcapFile);

            isRunning.set(true);

            // Start foreground service with notification
            // CRITICAL: Must call startForeground with proper service type for Android 14+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                startForeground(NOTIFICATION_ID, createNotification(),
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(NOTIFICATION_ID, createNotification(),
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST);
            } else {
                startForeground(NOTIFICATION_ID, createNotification());
            }

            // Start packet capture thread
            captureThread = new Thread(this::capturePackets);
            captureThread.start();

            Log.d(TAG, "Capture started");

        } catch (Exception e) {
            Log.e(TAG, "Error starting capture", e);
            stopCapture();
        }
    }

    private void capturePackets() {
        FileInputStream inputStream = new FileInputStream(vpnInterface.getFileDescriptor());
        FileOutputStream outputStream = new FileOutputStream(vpnInterface.getFileDescriptor());
        
        ByteBuffer buffer = ByteBuffer.allocate(32767);
        
        while (isRunning.get()) {
            try {
                buffer.clear();
                int length = inputStream.read(buffer.array());
                
                if (length > 0) {
                    buffer.limit(length);
                    byte[] packet = new byte[length];
                    buffer.get(packet);
                    
                    // Process packet
                    processPacket(packet);
                    
                    // Write to PCAP file
                    if (pcapWriter != null) {
                        pcapWriter.writePacket(packet);
                    }
                    
                    // Forward packet to actual network
                    buffer.position(0);
                    outputStream.write(packet);
                    
                    packetCount.incrementAndGet();
                    
                    // Update notification every 100 packets
                    if (packetCount.get() % 100 == 0) {
                        updateNotification();
                    }
                }
                
            } catch (IOException e) {
                if (isRunning.get()) {
                    Log.e(TAG, "Error reading packet", e);
                }
            }
        }
        
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Error closing streams", e);
        }
    }

    private void processPacket(byte[] packet) {
        try {
            PacketInfo packetInfo = PacketParser.parse(packet);
            
            if (packetInfo != null) {
                // Update connection record in database
                ConnectionRecord connection = new ConnectionRecord();
                connection.setSourceIp(packetInfo.getSourceIp());
                connection.setDestIp(packetInfo.getDestIp());
                connection.setSourcePort(packetInfo.getSourcePort());
                connection.setDestPort(packetInfo.getDestPort());
                connection.setProtocol(packetInfo.getProtocol());
                connection.setDataSent(packetInfo.getPayloadSize());
                connection.setActive(true);
                
                // Try to get app info
                String appName = getAppNameFromPacket(packetInfo);
                connection.setAppName(appName);
                connection.setPackageName(appName);
                
                connectionRepository.insert(connection);
                
                totalBytesSent.addAndGet(packetInfo.getPayloadSize());
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error processing packet", e);
        }
    }

    private String getAppNameFromPacket(PacketInfo packetInfo) {
        // This is a simplified version. In reality, you would need to map
        // the connection to the app using Android's NetworkStatsManager
        // or by analyzing /proc/net/tcp and /proc/net/udp files
        return "Unknown";
    }

    private void stopCapture() {
        if (!isRunning.get()) {
            return;
        }

        isRunning.set(false);

        // Stop capture thread
        if (captureThread != null) {
            try {
                captureThread.join(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error stopping capture thread", e);
            }
        }

        // Close PCAP writer
        if (pcapWriter != null) {
            try {
                pcapWriter.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing PCAP writer", e);
            }
            pcapWriter = null;
        }

        // Close VPN interface
        closeVpnInterface();

        stopForeground(true);
        stopSelf();

        Log.d(TAG, "Capture stopped");
    }

    private void closeVpnInterface() {
        if (vpnInterface != null) {
            try {
                vpnInterface.close();
            } catch (IOException e) {
                Log.e(TAG, "Error closing VPN interface", e);
            }
            vpnInterface = null;
        }
    }

    @Override
    public void onRevoke() {
        // Called when VPN permission is revoked by user
        Log.d(TAG, "VPN permission revoked");
        stopCapture();
        super.onRevoke();
    }

    private Notification createNotification() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent stopIntent = new Intent(this, CaptureService.class);
        stopIntent.setAction(ACTION_STOP);
        PendingIntent stopPendingIntent = PendingIntent.getService(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        return new NotificationCompat.Builder(this, SoulDevilApp.CHANNEL_ID)
                .setContentTitle("Soul Devil - Capturing")
                .setContentText("0 connections | ↑ 0 KB/s ↓ 0 KB/s")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_stop, "STOP", stopPendingIntent)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void updateNotification() {
        String text = String.format("%d packets | %s sent",
                packetCount.get(),
                FileUtils.formatFileSize(totalBytesSent.get()));

        Notification notification = new NotificationCompat.Builder(this, SoulDevilApp.CHANNEL_ID)
                .setContentTitle("Soul Devil - Capturing")
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notification)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    @Override
    public void onDestroy() {
        stopCapture();
        super.onDestroy();
    }
}
