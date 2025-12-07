package org.lovekapibarasan.shutdown_android;

import android.app.Application;
import android.util.Log;

import java.io.File;


public class MyApplication extends Application {
    private static LockManager lockManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(AppConstants.TAG, "Application onCreate started");

        String configPath = getFilesDir() + "/" + AppConstants.CONFIG_FILE_NAME;
        Log.d(AppConstants.TAG, "Config path: " + configPath);

        try {
            lockManager = new LockManager(this, configPath);
            Log.d(AppConstants.TAG, "LockManager created successfully");

            // Config ファイルが存在するか確認
            File configFile = new File(configPath);
            if (configFile.exists()) {
                Log.d(AppConstants.TAG, "Config file exists, starting background execution");

                // バックグラウンド実行が開始されていなければ開始
                if (!lockManager.isRunning()) {
                    lockManager.start();
                    Log.d(AppConstants.TAG, "Background execution started");
                } else {
                    Log.d(AppConstants.TAG, "Background execution already running");
                }
            } else {
                Log.d(AppConstants.TAG, "Config file not found, waiting for import");
            }

        } catch (Exception e) {
            Log.e(AppConstants.TAG, "Error initializing LockManager", e);
        }
    }

    public static LockManager getLockManager() {
        return lockManager;
    }
}