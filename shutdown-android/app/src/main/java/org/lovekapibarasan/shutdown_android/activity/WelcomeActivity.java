package org.lovekapibarasan.shutdown_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.lovekapibarasan.shutdown_android.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        findViewById(R.id.startButton).setOnClickListener(v -> {
            //Flag
            SharedPreferences prefs =
                    getSharedPreferences("app_prefs", MODE_PRIVATE);
            prefs.edit()
                    .putBoolean("isFirstLaunch", false)
                    .apply();

            // Main
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
