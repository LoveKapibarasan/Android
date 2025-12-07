
### JDK = Java Development Kit

* `javac`: java compiler
* JRE（Java Runtime Environment）
* **JDK 8, 11, 17, 21, 23**

#### ENV
* `Path: %JAVA_HOME%\bin `
* `JAVA_HOME`: where JDK is installed.

#### IDEs

1. **Eclipse**
* [Eclipse](https://adoptium.net/temurin/releases/)
* Do not have SDK by default.

2. **IntelliJ**
* `C:\Program Files\JetBrains\IntelliJ IDEA <version>\jbr`
3. **Android SDK**
* `C:\Program Files\Android\Android Studio\jbr`
* `C:\Users\lovek\AppData\Local\Programs\Android Studio\jbr`

> `jbr`= JetBrains Runtime
 
* `ANDROID_HOME`:  `..\android-sdk`

```powershell
...\jbr\bin\java -version
```
```bash
# Licence(Strictly follow this structure)
..android-sdk\cmdline-tools\latest\bin\sdkmanager --licenses
..\android-sdk\cmdline-tools\latest\bin\sdkmanager "platforms;android-36"
..\android-sdk\cmdline-tools\latest\bin\sdkmanager "build-tools;35.0.0"
..\android-sdk\cmdline-tools\latest\bin\sdkmanager --list_installed
```

* `platform/Android SDK Platform $APILevel`: Library
* `Build-Tools xx.yy.z`: To create APK
```gradle
// app/build.gradle
android {
    compileSdkVersion 36  ← Platform 36 
    buildToolsVersion "35.0.0"  ← Build-Tools 35 
}
```


**Gradle**

```bash
# Run button
.\gradlew assembleDebug --stacktrace && adb install -r app/build/outputs/apk/debug/app-debug.apk
.\gradlew clean assembleDebug

# Build + Install
.\gradlew installDebug
```

* Java SDK
* Android API (Depend on Version)

`AndroidManifest.xml`
```xml
<manifest>
    <uses-permission ... />  ← Permissions
    
    <application>
        <activity ... />     ← Screen
        <activity ... />
        
        <service ... />      ← Service
        
        <receiver ... />     ← Receiver
    </application>
</manifest>
```
