# Context

* Application environment.
* It is used to access system resource
```java
context.getSystemService(String name)  // get system service
context.startActivity(Intent intent)   // get activity
context.startService(Intent service)   // run service
context.getPackageName()               // get package name
context.getResources()                 // Get resource
context.getApplicationContext()        // Get application context
context.getSharedPreferences()         // SharedPreferences
Intent intent = new Intent(context, MyActivity.class);
context.startActivity(intent);
```

