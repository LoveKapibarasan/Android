


### Install
```bash
adb install "$app.apk"
adb sideload "$app".apk # recovery mode

adb devices

# Restart server
adb kill-server && adb start-server
```

### Pull, Push
```bash

adb push $PC_PATH $DEVICE_PATH
adb pull $DEVICE_PATH $PC_PATH
```

### Logging
```bash
adb logcat
adb logcat -s "YourAppTag"
```