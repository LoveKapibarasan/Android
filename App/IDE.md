# Android Studio
### Logs
* View → Tool Windows → Logcat
1. Device
2. App
3. Log level: 	Verbose


### JDK = Java Development Kit

* `javac`: java compiler
* JRE（Java Runtime Environment）
* **JDK 8, 11, 17, 21, 23**

#### ENV
* `Path: %JAVA_HOME%\bin `
* `JAVA_HOME`: where JDK is installed.

#### JSK location by IDEs

1. **Eclipse**
* [Eclipse](https://adoptium.net/temurin/releases/)
* Do not have SDK by default.

2. **IntelliJ**
* Windows: `C:\Program Files\JetBrains\IntelliJ IDEA <version>\jbr`

3. **Android SDK**
* Windows: `C:\Program Files\Android\Android Studio\jbr`, `C:\Users\lovek\AppData\Local\Programs\Android Studio\jbr`

> `jbr`= JetBrains Runtime
 
* `ANDROID_HOME`:  `..\android-sdk`

```powershell
...\jbr\bin\java -version
```


