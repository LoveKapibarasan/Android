
1. Install TestDPC as a device owner
> 8 click "Welcome" and scan QR code.
> Do not add Google account while initialization.
> – In the Google Sign in page, enter `afw#TestDPC` in the Email or Phone section.
```bash
adb shell dpm set-device-owner com.afwsamples.testdpc/.DeviceAdminReceiver
```
* Do not create private profile.

**Safe Boot**
* User Restrictions -> DISALLOW_FACTORY_RESET


2. Enable USB debugging and uninstall Apps

* Check package name.

```bash
# list 3rd party apps
adb shell pm list packages -3 | findstr xxx
adb shell pm uninstall --user 0 com.google.example
# System Apps 
adb shell pm list packages -s
```
**Disable**
```bash

adb shell pm uninstall --user 0 com.google.android.youtube
# adb shell pm disable-user --user 0 com.google.android.youtube
adb shell pm uninstall --user 0 com.google.android.apps.youtube.music
adb shell pm uninstall --user 0 com.android.chrome
adb shell pm uninstall --user 0 com.google.android.apps.assistant
adb shell pm uninstall --user 0 com.google.android.apps.docs
adb shell pm uninstall --user 0 com.google.android.apps.nbu.files
# Google App
adb shell pm uninstall --user 0 com.google.android.apps.searchlite
# Google One
adb shell pm uninstall --user 0 com.google.android.apps.subscriptions.red
# Google TV
adb shell pm uninstall --user 0 com.google.android.videos
# Google Meet
adb shell pm uninstall --user 0 com.google.android.apps.tachyon
adb shell pm uninstall --user 0 com.google.android.calendar
# FMRadio
adb shell pm uninstall --user 0 com.android.fmradio
adb shell pm uninstall --user 0 com.android.contacts
adb shell pm uninstall --user 0 com.google.android.apps.safetyhub
adb shell pm uninstall --user 0 com.google.android.apps.photosgo

adb shell pm uninstall --user 0 com.android.deskclock.go

 adb shell pm uninstall --user 0 com.android.customization.themes

adb shell pm uninstall --user 0 com.android.camera
```
> With HyperOS, you can delete Google Apps


**Xiaomi**
```bash
adb shell pm uninstall --user 0 com.miui.gameCenter.overlay
 
adb shell pm uninstall --user 0 com.miui.videoplayer
adb shell pm uninstall --user 0 com.miui.videoplayer.overlay

adb shell pm uninstall --user 0 com.miui.player
adb shell pm uninstall --user 0 com.miui.player.overlay

adb shell pm uninstall --user 0 com.xiaomi.scanner
adb shell pm uninstall --user 0 com.miui.scanner.overlay
adb shell pm uninstall --user 0 com.xiaomi.glgm

adb shell pm uninstall --user 0 com.miui.bugreport

adb shell pm uninstall --user 0 com.go.browser

adb shell pm uninstall --user 0 com.miui.theme.lite

adb shell pm uninstall --user 0 com.mi.globalminusscreen
adb shell pm uninstall --user 0 com.mi.globallayout
adb shell pm uninstall --user 0 com.miui.analytics.go
adb shell pm uninstall --user 0 com.miui.qr
adb shell pm uninstall --user 0 com.miui.android.fashiongallery
adb shell pm uninstall --user 0 com.miui.msa.global
adb shell pm uninstall --user 0 com.xiaomi.discover
adb shell pm uninstall --user 0 com.android.customization.themes
```


3. Hostname, Username
```bash
 
adb shell settings get global device_name
MY_HOSTNAME='smartphone'
adb shell settings put global device_name "$MY_HOSTNAME"
```

4. Notification
```powershell
 adb shell pm list packages --user 0 | ForEach-Object {
    $package = $_ -replace 'package:', ''
    if ($package -ne "") {
        Write-Host "Disabling: $package"
        adb shell "appops set $package POST_NOTIFICATION ignore"
    }
}

 # 通知システム全体を無効化
 adb shell settings put global heads_up_notifications_enabled 0

 # 通知バッジを無効化
 adb shell settings put secure notification_badging 0

 # 通知ドットを無効化
 adb shell settings put secure notification_bubbles 0

 # ロック画面の通知を無効化
 adb shell settings put secure lock_screen_show_notifications 0

 # サイレントモードを常時ON
 adb shell settings put global zen_mode 1

 # 通知音も無効化
 adb shell settings put system notification_sound null

 # バイブレーションも無効化
 adb shell settings put system notification_vibration_intensity 0

 adb shell pm uninstall --user 0 com.gogo.launcher
```

* Turn on Do Not Disturb Mode

* **Overlay**:画面の上に重ねて表示される機能

  5. Install
     1. App Block, Stay Focused
        * [URL](https://appblock.app/)
       > If blocked, use [AppMirror](https://www.apkmirror.com/)
       > Sideload is blocked 
     2. Material Files, VLC for Android, CX File Manager
        * [Github](https://github.com/zhanghai/MaterialFiles)
     3. Shogi Wars

     4. Firefox
         * [URL](https://thekiwibrowser.com/)
     5. Temux
        * [URL](https://termux.dev/en/)
        * Download apk from F-Droid
     6. ( Syncthing )
         * [URL]()
     7. Wireguard
        * Enable Always on VPN
     8. Claude.ai
     9. Google Map
     10. Google Authenticator
     11. Gmail, Outlook
     12. Niagara Launcher
     13. RealVNC Viewer