package org.lovekapibarasan.shutdown_android.triggers;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresPermission;

// service.xxxYYY(...);  // Error
@SuppressLint("AccessibilityPolicy")
public class AppLaunchTrigger extends AccessibilityService {
    @RequiresPermission(android.Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
    private String[] targetPackages;
    private BroadcastReceiver configReceiver;
    private PendingIntent pendingIntent;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        // 設定を受け取るレシーバー
        configReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                targetPackages = intent.getStringArrayExtra("target_packages");
                pendingIntent = intent.getParcelableExtra("pending_intent", PendingIntent.class);
            }
        };

        IntentFilter filter = new IntentFilter("ACTION_SET_APP_TRIGGER_CONFIG");
        registerReceiver(configReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

        @Override
        public void onAccessibilityEvent(AccessibilityEvent event) {
            if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                String packageName = event.getPackageName().toString();

                if (targetPackages != null) {
                    for (String target : targetPackages) {
                        if (packageName.equals(target)) {
                            trigger();
                            break;
                        }
                    }
                }
            }
        }

    private void trigger() {
        if (pendingIntent != null) {
            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onInterrupt() {
        // Do Nothing
    }
}