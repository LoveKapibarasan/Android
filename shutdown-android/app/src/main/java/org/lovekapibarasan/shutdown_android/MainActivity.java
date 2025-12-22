package org.lovekapibarasan.shutdown_android;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;


public class MainActivity extends Activity {
    private static final int PICK_XML_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (!alarmManager.canScheduleExactAlarms()) {
            // Setting
            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
            startActivity(intent);
        }
        // Overlay
        if (!Settings.canDrawOverlays(this)) {
            Log.d(AppConstants.TAG, "canDrawOverlays: " + Settings.canDrawOverlays(this));
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 100);
        }
    }
    public void onImportButtonClick(View view) {
        // Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        // String[] mimeTypes = {"text/xml", "application/xml"};
        // intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        Intent chooser = Intent.createChooser(intent, "Select XML");
        startActivityForResult(intent, PICK_XML_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_XML_FILE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            MyApplication.getLockManager().getConfig().importUserXml(uri);
        }
    }
}