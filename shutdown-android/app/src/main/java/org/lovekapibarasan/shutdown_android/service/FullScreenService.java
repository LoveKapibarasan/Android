package org.lovekapibarasan.shutdown_android.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import org.lovekapibarasan.shutdown_android.R;
import org.lovekapibarasan.shutdown_android.notification.NotificationHelper;
import org.lovekapibarasan.shutdown_android.config.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class FullScreenService extends Service {
    private final List<View> overlays = new ArrayList<>();
    private WindowManager windowManager; // ← class-level variable

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createChannel(this);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // ① Foreground （in 5 seconds）
        Notification notification =
                NotificationHelper.createForegroundNotification(
                        this,
                        AppConstants.NOTIFICATION_TITLE,
                        "Notification from FullScreenService"
                );

        startForeground(
                AppConstants.NOTIFICATION_ID_GENERAL,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST
        );
        // ② FullScreen View
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View overlayView = inflater.inflate(R.layout.overlay_warning, null);
        overlays.add(overlayView);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        windowManager.addView(overlayView, params);
        Button btnClose = overlayView.findViewById(R.id.btn_close_overlay);
        btnClose.setOnClickListener(v -> {
            Log.d(AppConstants.TAG, "Close Attempt at FullScreenService");
            removeOverlay(overlayView);

            if (overlays.isEmpty()) {
                stopSelf(); // Service ends
            }
        });


        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void removeOverlay(View overlayView) {
        if (overlayView != null && overlayView.getParent() != null) {
            windowManager.removeViewImmediate(overlayView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (View v : overlays) {
            removeOverlay(v);
        }
        overlays.clear();
    }

}
