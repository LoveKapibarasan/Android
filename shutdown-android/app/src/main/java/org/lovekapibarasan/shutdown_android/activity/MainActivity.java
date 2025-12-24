package org.lovekapibarasan.shutdown_android.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.lovekapibarasan.shutdown_android.MyApplication;
import org.lovekapibarasan.shutdown_android.R;
import org.lovekapibarasan.shutdown_android.config.AppConstants;
import org.lovekapibarasan.shutdown_android.config.LockConfig;
import org.lovekapibarasan.shutdown_android.config.RepeatingTriggerConfig;
import org.lovekapibarasan.shutdown_android.notification.NotificationHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity {

    private LinearLayout listContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listContainer = findViewById(R.id.listContainer);
        // Alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (!alarmManager.canScheduleExactAlarms()) {
            // Setting
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            startActivity(intent);
        }
        // Overlay
        if (!Settings.canDrawOverlays(this)) {
            Log.d(AppConstants.TAG, "canDrawOverlays: " + Settings.canDrawOverlays(this));
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 100);
        }
        // Notification
        if (NotificationHelper.hasNotificationPermission(this)) {
            // 許可されていれば通知を出す
            NotificationHelper.showNotification(
                    this,
                    AppConstants.NOTIFICATION_TITLE,
                    "Notification Test!"
            );
        } else {
            NotificationHelper.showPermissionDeniedMessage(this);
            // Android 13+
            requestPermissions(
                    new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                    101
            );
        }

        MyApplication app = (MyApplication) getApplication();
        LockConfig lockConfig = app.getLockConfig();
        displayTriggers(lockConfig.getRepeatingTriggers());
        // Add Config Button
        Button addSetButton = findViewById(R.id.addSetButton);
        addSetButton.setOnClickListener(v -> addEmptySetConfig());
        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> saveCurrentConfigs());

    }
    public void onTestNotificationButtonClick(View view) {
        Toast.makeText(this, "Check Notification！", Toast.LENGTH_SHORT).show();
        NotificationHelper.showNotification(
                MainActivity.this,
                AppConstants.NOTIFICATION_TITLE,
                "Notification Message"
        );
    }
    public void onTestOverlayButtonClick(View view){
        List<RepeatingTriggerConfig> triggers = new ArrayList<>();
        Toast.makeText(this, "Start in 1 minute！", Toast.LENGTH_SHORT).show();
        RepeatingTriggerConfig oneMinuteTrigger = createTestOneMinuteTrigger();
        triggers.add(oneMinuteTrigger);

        triggers.add(oneMinuteTrigger);
        MyApplication app = (MyApplication) getApplication();
        LockConfig currentConfig = app.getLockConfig();
        List<RepeatingTriggerConfig> currentTriggers = currentConfig.getRepeatingTriggers();
        if (currentTriggers != null) {
            triggers.addAll(currentTriggers);
        }
        app.updateConfig(triggers);
    }
    private RepeatingTriggerConfig createTestOneMinuteTrigger() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 1);
        Integer startHour = now.get(Calendar.HOUR_OF_DAY);
        Integer startMinute = now.get(Calendar.MINUTE);

        Calendar end = (Calendar) now.clone();
        end.add(Calendar.MINUTE, 1);
        Integer endHour = end.get(Calendar.HOUR_OF_DAY);
        Integer endMinute = end.get(Calendar.MINUTE);

        return new RepeatingTriggerConfig(
                startHour, startMinute, null, endHour, endMinute, 1
        );
    }

    private void addEmptySetConfig() {
        LayoutInflater inflater = LayoutInflater.from(this);
        // set_config.xml  inflate（Parent listContainer）
        View setView = inflater.inflate(R.layout.set_config, listContainer, false);

        // 各 EditText を空に設定
        ((EditText)setView.findViewById(R.id.text1)).setText("");
        ((EditText)setView.findViewById(R.id.text2)).setText("");
        ((EditText)setView.findViewById(R.id.text3)).setText("");
        ((EditText)setView.findViewById(R.id.text4)).setText("");
        ((EditText)setView.findViewById(R.id.text5)).setText("");
        ((EditText)setView.findViewById(R.id.text6)).setText("");

        Button deleteButton = setView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            Log.d(AppConstants.TAG, "Delete attempt at MainActivity");
            // listContainer からこの setView を削除
            listContainer.removeView(setView);
        });

        // listContainer に追加
        listContainer.addView(setView);
    }

    private void displayTriggers(List<RepeatingTriggerConfig> triggers) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (RepeatingTriggerConfig config : triggers) {
            // set_item.xml  inflate
            View setView = inflater.inflate(R.layout.set_config, listContainer, false);

            ((EditText)setView.findViewById(R.id.text1)).setText(String.valueOf(config.startHour));
            ((EditText)setView.findViewById(R.id.text2)).setText(String.valueOf(config.startMinute));
            ((EditText)setView.findViewById(R.id.text3)).setText(String.valueOf(config.intervalMinutes));
            ((EditText)setView.findViewById(R.id.text4)).setText(String.valueOf(config.endHour));
            ((EditText)setView.findViewById(R.id.text5)).setText(String.valueOf(config.endMinute));
            ((EditText)setView.findViewById(R.id.text6)).setText(String.valueOf(config.durationMinutes));

            Button deleteButton = setView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(v -> {
                Log.d(AppConstants.TAG, "Delete attempt at MainActivity");
                listContainer.removeView(setView);
            });
            // LinearLayoutに追加
            listContainer.addView(setView);
        }
    }

    private void saveCurrentConfigs() {
        List<RepeatingTriggerConfig> triggers = new ArrayList<>();

        // listContainer children view
        int count = listContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View setView = listContainer.getChildAt(i);
            try {
                Integer startHour = parseOrNull(((EditText)setView.findViewById(R.id.text1)).getText().toString());
                Integer startMinute = parseOrNull(((EditText)setView.findViewById(R.id.text2)).getText().toString());
                Integer intervalMinutes = parseOrNull(((EditText)setView.findViewById(R.id.text3)).getText().toString());
                Integer endHour = parseOrNull(((EditText)setView.findViewById(R.id.text4)).getText().toString());
                Integer endMinute = parseOrNull(((EditText)setView.findViewById(R.id.text5)).getText().toString());
                Integer durationMinutes = parseOrNull(((EditText)setView.findViewById(R.id.text6)).getText().toString());

                triggers.add(new RepeatingTriggerConfig(
                        startHour, startMinute, intervalMinutes,
                        endHour, endMinute, durationMinutes
                ));
            } catch (NumberFormatException e) {
                // 空欄や不正入力は無視
                Log.d(AppConstants.TAG, "Invalid input in setView " + i + ": " + e.getMessage());
            }
        }

        // save to LockConfig
        MyApplication app = (MyApplication) getApplication();
        app.updateConfig(triggers);

        Toast.makeText(this, "Configurations saved!", Toast.LENGTH_SHORT).show();
    }

    private Integer parseOrNull(String str) {
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
