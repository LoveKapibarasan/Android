package org.lovekapibarasan.shutdown_android.triggers;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresPermission;
import java.util.Calendar;

// Do not need to write in manifest
public class TimeTriggerManager {

    private final Context context;
    private final AlarmManager alarmManager;

    public TimeTriggerManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    public void setRepeatingTrigger(Integer startHour, Integer startMinute,
                                    Long intervalMinutes, Long durationMinutes,
                                    Integer endHour, Integer endMinute,
                                    PendingIntent startPendingIntent,
                                    PendingIntent stopPendingIntent) {

        // 権限チェック
        if (!alarmManager.canScheduleExactAlarms()) {
            throw new SecurityException("SCHEDULE_EXACT_ALARM permission not granted");
        }

        // デフォルト値を設定
        if (startHour == null || startMinute == null) {
            startHour = 0;
            startMinute = 0;
        }

        if (endHour == null || endMinute == null) {
            endHour = 23;
            endMinute = 59;
        }
        // start > end のチェック
        int startTotalMinutes = startHour * 60 + startMinute;
        int endTotalMinutes = endHour * 60 + endMinute;

        if (startTotalMinutes >= endTotalMinutes) {
            throw new IllegalArgumentException("Start time must be before end time");
        }


        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.HOUR_OF_DAY, startHour);
        startCalendar.set(Calendar.MINUTE, startMinute);
        startCalendar.set(Calendar.SECOND, 0);


        // ずっとブロック
        if (intervalMinutes == null || durationMinutes == null) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, //Even if sleeping
                    startCalendar.getTimeInMillis(),
                    startPendingIntent
            );

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
            endCalendar.set(Calendar.MINUTE, endMinute);
            endCalendar.set(Calendar.SECOND, 0);

            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    endCalendar.getTimeInMillis(),
                    stopPendingIntent
            );
            return;
        }

        // 休憩とブロックを繰り返し
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
        endCalendar.set(Calendar.MINUTE, endMinute);
        endCalendar.set(Calendar.SECOND, 0);

        long currentTime = startCalendar.getTimeInMillis();
        long endTime = endCalendar.getTimeInMillis();
        long cycleMillis = (intervalMinutes + durationMinutes) * 60 * 1000;

        while (currentTime < endTime) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    currentTime,
                    startPendingIntent
            );

            long stopTime = currentTime + (durationMinutes * 60 * 1000);
            if (stopTime <= endTime) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        stopTime,
                        stopPendingIntent
                );
            }

            currentTime += cycleMillis;
        }
    }
}