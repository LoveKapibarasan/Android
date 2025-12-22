# Content Provider


```java
public class MyContentProvider extends ContentProvider {
    
    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // Mine Type
        return "vnd.android.cursor.item/vnd.com.example.myapp.provider";
    }
}
```

```java
// Access content from other application
ContentResolver resolver = getContentResolver();
Uri uri = Uri.parse("content://com.example.myapp.provider/data");
Cursor cursor = resolver.query(uri, null, null, null, null);
```