


### Install
```bash
adb install
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