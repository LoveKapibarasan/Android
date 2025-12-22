
1. Install TestDPC as a device owner

    1. 8 click "Welcome" and scan QR code.
    2. Do not add Google account while initialization.
    3. Do not add a personal profile.
    4. DisableSafe Boot
        * User Restrictions -> DISALLOW_FACTORY_RESET


2. Enable USB debugging and uninstall Apps

* Check package name.

```bash
# list 3rd party apps
adb shell pm list packages -3 | findstr xxx
# System Apps 
adb shell pm list packages -s
```

**Disable or Uninstall**
```bash
# Youtube
adb shell pm uninstall --user 0 com.google.android.youtube
### adb shell pm disable-user --user 0 com.google.android.youtube
adb shell pm uninstall --user 0 com.google.android.apps.youtube.music
adb shell pm uninstall --user 0 com.android.chrome
# Assistamt
adb shell pm uninstall --user 0 com.google.android.apps.assistant
# Google Docs
adb shell pm uninstall --user 0 com.google.android.apps.docs
# Google Files
adb shell pm uninstall --user 0 com.google.android.apps.nbu.files
# Google App
adb shell pm uninstall --user 0 com.google.android.apps.searchlite
# Google One
adb shell pm uninstall --user 0 com.google.android.apps.subscriptions.red
# Google TV
adb shell pm uninstall --user 0 com.google.android.videos
# Google Meet
adb shell pm uninstall --user 0 com.google.android.apps.tachyon
# Google Calendar
adb shell pm uninstall --user 0 com.google.android.calendar
# FMRadio
adb shell pm uninstall --user 0 com.android.fmradio
# Contacts
adb shell pm uninstall --user 0 com.android.contacts
# Personal Safety
adb shell pm uninstall --user 0 com.google.android.apps.safetyhub
# Photo
adb shell pm uninstall --user 0 com.google.android.apps.photosgo
# Clock
adb shell pm uninstall --user 0 com.android.deskclock.go
# Themas
adb shell pm uninstall --user 0 com.android.customization.themes
# Camera
adb shell pm uninstall --user 0 com.android.camera
```
> With HyperOS, you can delete Google Apps


**Xiaomi**
```bash
# Game
adb shell pm uninstall --user 0 com.xiaomi.glgm
adb shell pm uninstall --user 0 com.miui.gameCenter.overlay
# Video
adb shell pm uninstall --user 0 com.miui.videoplayer
adb shell pm uninstall --user 0 com.miui.videoplayer.overlay
Player
adb shell pm uninstall --user 0 com.miui.player
adb shell pm uninstall --user 0 com.miui.player.overlay
# Scanner
adb shell pm uninstall --user 0 com.xiaomi.scanner
adb shell pm uninstall --user 0 com.miui.scanner.overlay
# Bug Report
adb shell pm uninstall --user 0 com.miui.bugreport
# Browser
adb shell pm uninstall --user 0 com.go.browser
# Fashion
adb shell pm uninstall --user 0 com.miui.android.fashiongallery
# Discover
adb shell pm uninstall --user 0 com.xiaomi.discover
```


3. Hostname, Username
```bash
adb shell settings get global device_name
MY_HOSTNAME='smartphone'
adb shell settings put global device_name "$MY_HOSTNAME"
```

4. Notification
* Turn on Do Not Disturb Mode

* **Overlay**: 画面の上に重ねて表示される機能

  5. Install
     0. FDroid: Open source android app store.
     1. App Block, Stay Focused + TestDPC
         * [URL](https://appblock.app/)
         > Sideload is blocked by AppBlock

     2. Material Files, CX File Manager
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
     8. Google Map
     9. Google Authenticator
     10. Gmail, Outlook
     11. Niagara Launcher