package org.lovekapibarasan.shutdown_android.triggers;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.RequiresPermission;

import org.lovekapibarasan.shutdown_android.broadcast.AlarmReceiver;
import org.lovekapibarasan.shutdown_android.config.AppConstants;
import org.lovekapibarasan.shutdown_android.config.RepeatingTriggerConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimeTriggerManager {

    private final Context context;
    private final AlarmManager alarmManager;
    private final List<PendingIntent> registeredStartIntents = new ArrayList<>();
    private final List<PendingIntent> registeredStopIntents = new ArrayList<>();
    private int index = 0;

    public TimeTriggerManager(Context context) {
        this.context = context;
        this.alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    public void setRepeatingTrigger(RepeatingTriggerConfig repeatingTriggerConfig) {
        int requestCodeStart = index * 2;
        int requestCodeStop = index * 2 + 1;
        index++;
        Intent intentStart = new Intent(context, AlarmReceiver.class);
        intentStart.putExtra("ACTION", "START");
        Intent intentStop = new Intent(context, AlarmReceiver.class);
        intentStop.putExtra("ACTION", "STOP");

        PendingIntent startPendingIntent = PendingIntent.getBroadcast(
                context, requestCodeStart, intentStart, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(
                context, requestCodeStop, intentStop, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        Log.d(AppConstants.TAG, "PendingIntent is created with index: " + index);


        Integer startHour = repeatingTriggerConfig.startHour;
        Integer startMinute = repeatingTriggerConfig.startMinute;
        Integer endHour = repeatingTriggerConfig.endHour;
        Integer endMinute = repeatingTriggerConfig.endMinute;
        Integer intervalMinutes = repeatingTriggerConfig.intervalMinutes;
        Integer durationMinutes = repeatingTriggerConfig.durationMinutes;
        registeredStartIntents.add(startPendingIntent);
        registeredStopIntents.add(stopPendingIntent);

        if (!alarmManager.canScheduleExactAlarms()) {
            throw new SecurityException("SCHEDULE_EXACT_ALARM permission not granted");
        }

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(startCalendar.getTime());

        Log.d(AppConstants.TAG, "Start Calendar is " + formattedTime);
        long now = System.currentTimeMillis();

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, endHour);
        endCalendar.set(Calendar.MINUTE, endMinute);
        endCalendar.set(Calendar.SECOND, 0);
        formattedTime = sdf.format(endCalendar.getTime());
        Log.d(AppConstants.TAG, "End Calendar is " + formattedTime);

        if (intervalMinutes == null || durationMinutes == null) {

            if (endCalendar.getTimeInMillis() <= now) {
                Log.d(AppConstants.TAG, "End time is in the past, skipping alarm.");
                return;
            }

            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, //Even if sleeping
                    startCalendar.getTimeInMillis(),
                    startPendingIntent
            );

            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    endCalendar.getTimeInMillis(),
                    stopPendingIntent
            );
        } else {
            long currentTime = startCalendar.getTimeInMillis();
            long endTime = endCalendar.getTimeInMillis();
            long cycleMillis = (long) (intervalMinutes + durationMinutes) * 60 * 1000;

            while (currentTime < endTime) {
                // ループごとにユニークな PendingIntent を作る
                int requestCodeStartLoop = index * 2;
                int requestCodeStopLoop = index * 2 + 1;

                Intent intentStartLoop = new Intent(context, AlarmReceiver.class);
                intentStartLoop.putExtra("ACTION", "START");
                intentStartLoop.putExtra("TRIGGER_INDEX", index);
                intentStartLoop.putExtra("LOOP_INDEX", index);

                Intent intentStopLoop = new Intent(context, AlarmReceiver.class);
                intentStopLoop.putExtra("ACTION", "STOP");
                intentStopLoop.putExtra("TRIGGER_INDEX", index);
                intentStopLoop.putExtra("LOOP_INDEX", index);


                PendingIntent startLoopPendingIntent = PendingIntent.getBroadcast(
                        context, requestCodeStartLoop, intentStartLoop,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                PendingIntent stopLoopPendingIntent = PendingIntent.getBroadcast(
                        context, requestCodeStopLoop, intentStopLoop,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                );
                registeredStartIntents.add(startLoopPendingIntent);
                registeredStopIntents.add(stopLoopPendingIntent);

                // Alarm
                long stopTime = currentTime + (durationMinutes * 60 * 1000);
                if (stopTime <= now) {
                    Log.d(AppConstants.TAG, "End time is in the past, skipping alarm in loop.");
                    currentTime += cycleMillis;
                    index++;
                    continue;
                }
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        currentTime,
                        startLoopPendingIntent
                );

                if (stopTime <= endTime) {
                    alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            stopTime,
                            stopLoopPendingIntent
                    );

                }

                currentTime += cycleMillis;
                index++;
            }
        }
    }
    public void clearAll() {
        for (PendingIntent pi : registeredStartIntents) {
            alarmManager.cancel(pi);
        }
        for (PendingIntent pi : registeredStopIntents) {
            alarmManager.cancel(pi);
        }
        registeredStartIntents.clear();
        registeredStopIntents.clear();
        Log.d(AppConstants.TAG, "TimeTriggerManager: All alarms cleared");
    }
}