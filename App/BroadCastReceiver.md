# BroadCastReceiver

* Receives event that is broadcasted by system.


1. Wifi

```java
public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiManager.WIFI_STATE_CHANGED.equals(action)) {
            // Wi-Fi state is changed
        }
    }
}
```

2. Boot

* Android < 8.0 
```java
if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {}
```
```xml
        <receiver android:name=".BootBroadcastReceiver" android:enabled="true" android:exported="false">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </receiver>
```


* Android > 8.0

1. `WorkManager`
```java
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import android.os.Bundle;
import android.widget.Toast;

public class BootService extends Worker {
    public BootService(Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Do sth
        Log.d("BootService", "Device has booted and WorkManager started!");
        return Result.success();
    }
}
```

```java
OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(BootService.class)
        .setInitialDelay(0, TimeUnit.SECONDS) 
        .build();
WorkManager.getInstance(context).enqueue(workRequest);
```

2. JobScheduler
* Background task scheduling.

````java
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

public class BootJobScheduler {
    public void scheduleBootJob(Context context) {
        // LOLLI.POP: Android 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ComponentName componentName = new ComponentName(context, BootJobService.class);
            JobInfo jobInfo = new JobInfo.Builder(0, componentName)
                    .setPersisted(true) // After reboot, task remains
                    .setRequiresCharging(false)  // isCharging
                    .build();

            // Context.JOB_SCHEDULER_SERVICE = JobScheduler
            // (JobScheduler): Cast Operator
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(jobInfo);
        }
    }
}
```


```java
public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        // This method is called
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
```