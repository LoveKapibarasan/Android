# Service

* No user interface.
* Background process.



```java
Intent serviceIntent = new Intent(this, MyService.class);
startService(serviceIntent);
stopService(new Intent(this, MyService.class));
```


```java
public class MusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.music_file); 
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        // If service is stopped, restart
        return START_STICKY; 
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;  
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop(); 
        mediaPlayer.release();  
    }
}
```