package org.lovekapibarasan.shutdown_android.notification;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import org.lovekapibarasan.shutdown_android.config.AppConstants;

public class NotificationHelper {
    public static void showNotification(Context context, String title, String message) {
        NotificationManager notificationManager = createChannel(context);

        Notification notification = new NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_DEFAULT)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(AppConstants.NOTIFICATION_ID_GENERAL, notification);
    }

    public static NotificationManager createChannel(Context context) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                AppConstants.NOTIFICATION_CHANNEL_DEFAULT,
                AppConstants.NOTIFICATION_VISIBLE_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );

        notificationManager.createNotificationChannel(channel);
        return notificationManager;
    }

    public static Notification createForegroundNotification(
            Context context,
            String title,
            String message
    ) {
        createChannel(context);

        return new NotificationCompat.Builder(context, AppConstants.NOTIFICATION_CHANNEL_DEFAULT)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Icon in res/drawable. This is a system resource
                .setOngoing(true)     // Foreground Service
                .build();
    }

    public static boolean hasNotificationPermission(Context context) {
        return context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                == android.content.pm.PackageManager.PERMISSION_GRANTED;
        // Android 12
    }
    public static void showPermissionDeniedMessage(Context context) {
        Toast.makeText(context, "Notification permission is disabled. Please allow notifications.", Toast.LENGTH_SHORT).show();
    }
}
