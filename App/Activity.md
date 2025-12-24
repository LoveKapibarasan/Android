# Activity

* Display UI.
* Handle user input.
* Life cycle management

1. `onCreate()`
2. `onStart()`
3. `onResume()`
4. `onPause()`
5. `onStop()`
6. `onDestroy()`


### Switch

**Intent**

```java
// Intent intent = new Intent(this, SecondActivity.class); 
Intent intent = new Intent(CurrentActivity.this, NextActivity.class);
startActivity(intent);
// This = Activity itself
Intent intent = new Intent(this, NextActivity.class);
// Get Result
startActivityForResult(intent, REQUEST_CODE);


startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_,,);

```

```java
// Action is key, Start is value
intent.putExtra("ACTION", "START");
getIntent().getStringExtra("ACTION");

```


**PendingIntent**
* Reserved Intent
```java
PendingIntent.getActivity(context, requestCode, intent, flags);
PendingIntent.getService(context, requestCode, intent, flags);
PendingIntent.getBroadcast(context, requestCode, intent, flags);
```
* `requestCode`: Identifier.

* `FLAGS`
    * `FLAG_UPDATE_CURRENT`: update
    * `FLAG_CANCEL_CURRENT`: cancel and create
    * `FLAG_NO_CREATE`: If current PendingIntent is null, return null.
    * `FLAG_IMMUTABLE`: cannot edit intent from other app
    * `FLAG_ACTIVITY_NEW_TASK`: New task
    * `FLAG_ACTIVITY_NO_HISTORY`: No history.cannot go back
    * `FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS`: Do not appar on recent apps
    * `FLAG_ACTIVITY_CLEAR_TOP`:
```
A → B → FullScreenActivity → C → D
A → B → FullScreenActivity
```

```java
package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
// Toast: Notification message
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// AppCompatActivity: ActionBar（Toolbar）or Material Design
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Do not forget super()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get button from activity_main.xml
        Button myButton = findViewById(R.id.myButton);
        // Set listner
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        finish(); //Close Activity and cannot go back again
    }
}
```

### Main Activity
```xml
<!-- 1. MAIN 2. LAUNCHER -->
<!--If there is indent-filter, it should declare android:exported -->
<activity
        android:name=".LauncherActivity"
        android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
</activity>
```


### Back Stack

```
startActivity(new Intent(this, BActivity.class));
```

* Lifo
```
under ← Older
┌──────────┐
│ Activity A│
├──────────┤
│ Activity B│
├──────────┤
│ Activity C│ ← Now
└──────────┘
top ← Newer
```

# View
* Activity has views
* Button
* TextView
* ImageView
* Edit Text

### Overlay
1. App kill -> NG
2. Activity + Overlay -> NG
3. Service + View
 
### LayoutInflator

* XML -> View converter

```java
LayoutInflater inflater = getLayoutInflater(); // Get from Activity
// null: no parent view
View view = inflater.inflate(R.layout.sample_layout, null);
```


### WindowManager
* Create overlay view from service

**WindowsManager.LayoutParams**
```java

WindowManager.LayoutParams params = new WindowManager.LayoutParams(
        WindowManager.LayoutParams.MATCH_PARENT, // Width
        WindowManager.LayoutParams.MATCH_PARENT, // Height
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY :
                WindowManager.LayoutParams.TYPE_PHONE, // Window Type
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // No ficus
        PixelFormat.TRANSLUCENT // Transparency
);
```

### findViewById()
```java
// <Button android:id="@+id/btn_close"
Button btnClose = overlayView.findViewById(R.id.btn_close);
```