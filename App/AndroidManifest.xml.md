# AndroidManifest.xml


```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.myapp">

    <!-- Application Setting-->
    <application
        android:name=".MyApplication"
        android:icon="@drawable/ic_launcher"
        android:label="My App"
        android:theme="@style/AppTheme">

        <!-- Activity Declaration -->
        <activity android:name=".MainActivity">
            <!-- Intent Filter -->
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <activity android:name=".SecondActivity" />

        <!-- Service Declaration -->
        <service android:name=".MyService" />

        <!-- Contents Provider -->
        <provider
            android:name=".MyContentProvider"
            android:authorities="com.example.myapp.provider"
            android:exported="true" />
        <!-- Broadcast Receiever -->
        <receiver android:name=".WifiReceiver">
            <intent-filter>
                <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
            </intent-filter>
        </receiver>

    </application>

    <!-- Permissions -->
    <uses-permission android:name="android.permission.INTERNET" />
        <!-- Ovelay -->
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />

    <!-- SDK Versions -->
    <uses-sdk
        android:minSdkVersion="21"
        android:targetSdkVersion="30" />

</manifest>
```