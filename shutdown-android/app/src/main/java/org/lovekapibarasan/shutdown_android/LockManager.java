package org.lovekapibarasan.shutdown_android;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.lovekapibarasan.shutdown_android.activity.FullScreenActivity;
import org.lovekapibarasan.shutdown_android.config.LockConfig;
import org.lovekapibarasan.shutdown_android.config.RepeatingTriggerConfig;
import org.lovekapibarasan.shutdown_android.triggers.TimeTriggerManager;
import java.util.List;

public class LockManager {

    private final Context context;
    private final LockConfig config;
    private final TimeTriggerManager timeTrigger;
    private final PendingIntent startLockPendingIntent;
    private PendingIntent stopLockPendingIntent;

    private boolean isRunning = false;


    public LockManager(Context context, String configPath) {
        Log.d(AppConstants.TAG, "LockManager: Constructor started");
        Log.d(AppConstants.TAG, "LockManager: configPath = " + configPath);

        this.context = context;
        this.config = new LockConfig(context, configPath);
        this.timeTrigger = new TimeTriggerManager(context);

        Log.d(AppConstants.TAG, "LockManager: LockConfig and TimeTriggerManager initialized");

        // FullScreenActivity起動用 PendingIntent
        Intent startIntent = new Intent(context, FullScreenActivity.class);
        startIntent.putExtra("ACTION", "START");
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        this.startLockPendingIntent = PendingIntent.getActivity(
                context,
                0,
                startIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent stopIntent = new Intent(context, FullScreenActivity.class);
        stopIntent.putExtra("ACTION", "STOP");
        this.stopLockPendingIntent = PendingIntent.getActivity(
                context,
                1,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        Log.d(AppConstants.TAG, "LockManager: PendingIntent created");
    }

    public LockConfig getConfig() {
        return config;
    }

    private void loadAndSetTriggers() {
        Log.d(AppConstants.TAG, "loadAndSetTriggers: Started");

        // パッケージリストを読み込み
        List<String> packages = config.loadPackageList();
        Log.d(AppConstants.TAG, "loadAndSetTriggers: Loaded " + packages.size() + " packages");

        // アプリ起動トリガーを設定
        if (!packages.isEmpty()) {
            Log.d(AppConstants.TAG, "loadAndSetTriggers: Setting app trigger for packages: " + packages);

            Intent intent = new Intent("ACTION_SET_APP_TRIGGER_CONFIG");
            intent.putExtra("target_packages", packages.toArray(new String[0]));
            intent.putExtra("pending_intent", startLockPendingIntent);
            context.sendBroadcast(intent);

            Log.d(AppConstants.TAG, "loadAndSetTriggers: Broadcast sent for app triggers");
        } else {
            Log.w(AppConstants.TAG, "loadAndSetTriggers: No packages to monitor");
        }

        // 時間トリガーを読み込み
        List<RepeatingTriggerConfig> triggers = config.getRepeatingTriggers();
        Log.d(AppConstants.TAG, "loadAndSetTriggers: Loaded " + triggers.size() + " time triggers");

        // 各トリガーを設定
        for (int i = 0; i < triggers.size(); i++) {
            RepeatingTriggerConfig trigger = triggers.get(i);
            Log.d(AppConstants.TAG, "loadAndSetTriggers: Setting trigger #" + (i+1) +
                    " - Start: " + trigger.startHour + ":" + trigger.startMinute +
                    ", Interval: " + trigger.intervalMinutes + "min" +
                    ", Duration: " + trigger.durationMinutes + "min" +
                    ", End: " + trigger.endHour + ":" + trigger.endMinute);

            try {
                timeTrigger.setRepeatingTrigger(
                        trigger.startHour,
                        trigger.startMinute,
                        trigger.intervalMinutes,
                        trigger.durationMinutes,
                        trigger.endHour,
                        trigger.endMinute,
                        startLockPendingIntent,
                        stopLockPendingIntent
                );
                Log.d(AppConstants.TAG, "loadAndSetTriggers: Trigger #" + (i+1) + " set successfully");
            } catch (SecurityException e) {
                // 権限がない場合の処理
                Log.e(AppConstants.TAG, "loadAndSetTriggers: SCHEDULE_EXACT_ALARM permission not granted for trigger #" + (i+1), e);
            } catch (Exception e) {
                Log.e(AppConstants.TAG, "loadAndSetTriggers: Failed to set trigger #" + (i+1), e);
            }
        }

        Log.d(AppConstants.TAG, "loadAndSetTriggers: Completed");
    }
    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        if (!isRunning) {
            try {
                loadAndSetTriggers();
                isRunning = true;
                Log.d(AppConstants.TAG, "LockManager started");
            } catch (Exception e) {
                Log.e(AppConstants.TAG, "Error starting LockManager", e);
            }
        }
    }
}
