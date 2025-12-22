# Application
* Global Variable Management.
* Initialization and Configuration
* connect with Database
* output logs


```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication", "Running！");
    }
}
```

```xml
<application
    android:name=".MyApplication"
    ... >
```


|     | Application | Activity |
| ------ | ----------- | -------- |
| Lifetime   | until App ends    | Displaing    |
| UI | ❌         | ✅      |
| Usage    | Common process       | Display UI    |
