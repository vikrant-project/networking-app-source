package com.souldevil.networktool;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import com.souldevil.networktool.data.db.AppDatabase;

/**
 * Application class for Soul Devil Network Tool
 */
public class SoulDevilApp extends Application {
    private static final String TAG = "SoulDevilApp";
    private static SoulDevilApp instance;
    public static final String CHANNEL_ID = "soul_devil_capture";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        
        // Create notification channel (MUST be done before any notification is posted)
        createNotificationChannel();
        
        // Initialize Room database lazily (not on main thread)
        // Database will be initialized when first accessed
        Log.d(TAG, "Soul Devil Network Tool initialized");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Packet Capture Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Network packet capture and analysis service");
            
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            Log.d(TAG, "Notification channel created");
        }
    }

    public static SoulDevilApp getInstance() {
        return instance;
    }
}
