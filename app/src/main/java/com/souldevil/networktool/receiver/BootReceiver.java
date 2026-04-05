package com.souldevil.networktool.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.souldevil.networktool.service.CaptureService;

/**
 * Receiver to start capture service on device boot
 */
public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "Boot completed, checking auto-start preference");
            
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            boolean autoStart = prefs.getBoolean("start_on_boot", false);
            
            if (autoStart) {
                Intent serviceIntent = new Intent(context, CaptureService.class);
                serviceIntent.setAction(CaptureService.ACTION_START);
                context.startForegroundService(serviceIntent);
                
                Log.d(TAG, "Started capture service on boot");
            }
        }
    }
}
