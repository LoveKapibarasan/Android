# AlarmManager
* System Service


```java
// Get System Instance
AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

long triggerAtMillis = System.currentTimeMillis() + 60000; // 60 seconds after
alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

long intervalMillis = 1000 * 60 * 60; // 1h
alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);

Calendar calendar = Calendar.getInstance();
calendar.set(Calendar.HOUR_OF_DAY, 9); // 09:00
calendar.set(Calendar.MINUTE, 0);
calendar.set(Calendar.SECOND, 0);
long triggerAtMillis = calendar.getTimeInMillis();
// AlarmManager.INTERVAL_DAY = 86,400,000 ms = 1 day
long intervalMillis = AlarmManager.INTERVAL_DAY; 

AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
Intent intent = new Intent(this, MyReceiver.class);
PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);

```



### Types

* `RTC_WAKEUP`: System Time. Even sleep state.
* `RTC`
* `ELAPSED_REALTIME_WAKEUP`: Passed time. Even sleep state.
* `ELAPSED_REALTIME`