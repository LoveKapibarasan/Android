# Toast

*  Alarm, small message.

```java
import android.widget.Toast;
//  Toast.LENGTH_SHORT: 2s, Toast.LENGTH_LONG: 3.5s
Toast.makeText(context, "Message", Toast.LENGTH_SHORT).show();
```

# Notification

**Notification Channel**
* Android 8.0 ~


```java

NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

String channelId = "default_channel";
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
NotificationChannel channel = new NotificationChannel(
        channelId, // 1. ID
        "Default Channel", // 2. Visible Name
        NotificationManager.IMPORTANCE_DEFAULT // 3. Importance
);
    // Register
    notificationManager.createNotificationChannel(channel);
}
// Decide Channel ID
Notification notification = new NotificationCompat.Builder(this, channelId)
        .setContentTitle("Title")
        .setContentText("Message")
        .setSmallIcon(R.drawable.ic_launcher_foreground) // icon in res/drawable. This is a system resource
        .setOngoing(true) // Foreground Service. Cannot swipe out
        .build();

notificationManager.notify(1, notification);


```