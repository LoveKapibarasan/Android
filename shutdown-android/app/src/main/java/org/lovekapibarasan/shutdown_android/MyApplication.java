package org.lovekapibarasan.shutdown_android;

import android.app.Application;
import android.util.Log;
import org.lovekapibarasan.shutdown_android.config.AppConstants;
import org.lovekapibarasan.shutdown_android.config.LockConfig;
import org.lovekapibarasan.shutdown_android.config.RepeatingTriggerConfig;

import java.util.List;


public class MyApplication extends Application {
    private static LockManager lockManager;
    private static LockConfig lockConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        lockConfig = new LockConfig(this);
        Log.d(AppConstants.TAG, "Application onCreate started");

        try {
            lockManager = new LockManager(this, lockConfig);
            Log.d(AppConstants.TAG, "LockManager created successfully");

                Log.d(AppConstants.TAG, "Config file exists, starting background execution");
                // バックグラウンド実行が開始されていなければ開始
                if (!lockManager.isRunning()) {
                    lockManager.start();
                    Log.d(AppConstants.TAG, "Background execution started");
                } else {
                    Log.d(AppConstants.TAG, "Background execution already running");
                }

        } catch (Exception e) {
            Log.e(AppConstants.TAG, "Error initializing LockManager", e);
        }
    }

    public LockConfig getLockConfig() {
        return lockConfig;
    }

    public void updateConfig(List<RepeatingTriggerConfig> triggers) {
        lockConfig.saveRepeatingTriggers(triggers);
        lockManager.updateConfig(lockConfig);
    }
}