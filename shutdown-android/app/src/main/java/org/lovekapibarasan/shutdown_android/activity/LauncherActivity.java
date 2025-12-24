package org.lovekapibarasan.shutdown_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import org.lovekapibarasan.shutdown_android.R;
import android.os.Handler;
import android.os.Looper;

public class LauncherActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash Screen
        setContentView(R.layout.activity_splash);

        // 2s
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences prefs =
                    getSharedPreferences("app_prefs", MODE_PRIVATE);
            boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);

            if (isFirstLaunch) {
                startActivity(new Intent(LauncherActivity.this, WelcomeActivity.class));
            } else {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            }
            finish();
        }, SPLASH_DELAY);
    }
}

