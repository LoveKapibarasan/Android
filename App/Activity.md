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


startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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


```java
package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
// Toast: Notification message
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
```