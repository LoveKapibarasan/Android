package org.lovekapibarasan.shutdown_android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.lovekapibarasan.shutdown_android.config.AppConstants;
import org.lovekapibarasan.shutdown_android.service.FullScreenService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d(AppConstants.TAG, "onReceive: intent is null");
            return;
        }

        String action = intent.getStringExtra("ACTION");
        Log.d(AppConstants.TAG, "onReceive called! ACTION=" + action);

        if ("START".equals(action)) {
            Log.d(AppConstants.TAG, "Starting FullScreenService...");
            Intent serviceIntent = new Intent(context, FullScreenService.class);
            context.startForegroundService(serviceIntent); // Android 8.0+
        } else if ("STOP".equals(action)) {
            Log.d(AppConstants.TAG, "Stopping FullScreenService...");
            Intent serviceIntent = new Intent(context, FullScreenService.class);
            context.stopService(serviceIntent);
        } else {
            Log.d(AppConstants.TAG, "Unknown action: " + action);
        }
    }
}

