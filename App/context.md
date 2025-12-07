
```java
context.getSystemService(String name)  // get system service
context.startActivity(Intent intent)   // get activity
context.startService(Intent service)   // run service
context.getPackageName()               // get package name
context.getResources()                 // Get resource
context.getApplicationContext()        // Get application context
context.getSharedPreferences()         // SharedPreferences
```

```java
alarmManager.set(int type, long triggerAtMillis, PendingIntent operation) // Alarm setting

alarmManager.setExact(int type, long triggerAtMillis, PendingIntent operation)

alarmManager.setExactAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation)
// Doze mode

alarmManager.setRepeating(int type, long triggerAtMillis, long intervalMillis, PendingIntent operation)
// Repeating

alarmManager.cancel(PendingIntent operation)
// Cancel alarm

alarmManager.canScheduleExactAlarms()  // Android 12+
// Check permissions
```