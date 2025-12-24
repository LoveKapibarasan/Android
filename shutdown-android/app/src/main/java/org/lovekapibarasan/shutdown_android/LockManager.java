package org.lovekapibarasan.shutdown_android;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.lovekapibarasan.shutdown_android.broadcast.AlarmReceiver;
import org.lovekapibarasan.shutdown_android.config.LockConfig;
import org.lovekapibarasan.shutdown_android.config.RepeatingTriggerConfig;
import org.lovekapibarasan.shutdown_android.triggers.TimeTriggerManager;
import org.lovekapibarasan.shutdown_android.config.AppConstants;
import java.util.List;

public class LockManager {

    private final Context context;
    private LockConfig config;
    private final TimeTriggerManager timeTrigger;

    private boolean isRunning = false;


    public LockManager(Context context, LockConfig config) {
        Log.d(AppConstants.TAG, "LockManager: Constructor started");

        this.context = context;
        this.config = config;
        this.timeTrigger = new TimeTriggerManager(context);

        Log.d(AppConstants.TAG, "LockManager: LockConfig and TimeTriggerManager initialized");
    }

    private void loadAndSetTriggers() {
        Log.d(AppConstants.TAG, "loadAndSetTriggers: Started");
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
                        trigger
                );
                Log.d(AppConstants.TAG, "loadAndSetTriggers: Trigger #" + (i+1) + " set successfully");
            } catch (Exception e) {
                Log.e(AppConstants.TAG, "loadAndSetTriggers: Failed to set trigger #" + (i+1), e);
            }
        }

        Log.d(AppConstants.TAG, "loadAndSetTriggers: Completed");
    }

    public void updateConfig(LockConfig newConfig) {
        this.config = newConfig;
        if (isRunning) {
            restartTriggers();
        }
    }

    private void restartTriggers() {
        Log.d(AppConstants.TAG, "LockManager: Restarting triggers with new config");
        timeTrigger.clearAll();
        loadAndSetTriggers();
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
