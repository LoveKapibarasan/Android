package org.lovekapibarasan.shutdown_android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.lovekapibarasan.shutdown_android.service.FullScreenService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent service = new Intent(context, FullScreenService.class);
            context.startForegroundService(service);
        }
    }
}
