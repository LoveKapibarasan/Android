# SharedPreference
* boolean（true/false）
* int
* float
* long
* String
* Set<String>

```java
SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
prefs.edit()
     .putBoolean("isFirstLaunch", false)  // Key and Value
     .apply();  // Save

// Get
SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true); // Default: true
```

