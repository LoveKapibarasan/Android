# Gradle

```bash
# Run button
.\gradlew assembleDebug --stacktrace && adb install -r app/build/outputs/apk/debug/app-debug.apk
.\gradlew clean assembleDebug

# Build + Install
.\gradlew installDebug
```

```
MyProject/
 ├─ settings.gradle
 ├─ build.gradle
 ├─ gradlew
 ├─ gradle/
 │   └─ wrapper/
 │       └─ gradle-wrapper.properties
 ```


### .kts
* Kotlin

### setting.gradle

```kotlin
pluginManagement {
    repositories {
        // Google Maven Repository
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
// Do not overwrite repository in each module 
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "root_dir_name"
// app module
include(":app")
```

### build.gradle.kts

```kotlin

plugins {
    id("com.android.application")
    kotlin("android") // Kotlin
}

// Check libs.versions.toml
plugins {
    alias(libs.plugins.android.application) apply false
}

// Android Settings
android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}
// Dependencies
dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
}
```

### gradle.properties
```properties

# JVM memory setting
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8

# Paralell build
org.gradle.parallel=true

# AndroidX
android.useAndroidX=true

# Jetifier（Old Support Library-> AndroidX）
android.enableJetifier=true
```
### Gradlew
* Gradle Wrapper Script.
```
gradlew       ← macOS / Linux
gradlew.bat   ← Windows
```