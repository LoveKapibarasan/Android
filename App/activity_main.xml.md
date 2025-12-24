# Activity Main

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
              android:background="#FFDDDD"
              android:gravity="center"
              android:fitsSystemWindows="true"> <!-- Avoid overwrapping on system area-->
<TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content" <!--Align with content-->
        android:layout_height="wrap_content"
        android:textSize="26sp"
        android:textStyle="bold"
        android:textColor="#FFFFFF"
        android:text="Hello, World!"
        android:layout_gravity="center" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/click"
        android:layout_gravity="center"
        android:onClick="onButtonClick"            
        android:paddingHorizontal="24dp"
        android:paddingVertical="12dp"
        android:background="@color/blue"/>
    <!--onButtonClick: Method to be called -->
</LinearLayout>
```

**Resources**
* `@string/name`
```xml
<!-- res/values/strings.xml -->
<resources>
    <string name="send">送信</string>
    <string name="cancel">キャンセル</string>
</resources>
```
* `@drawable/icon`: icon.png
* `@color/primary`: color


**android:layout_gravity**(Linear Layout)
1. center
2. center_horizontal
3. center_vertical
4. top/bottom/left/right


## Button
1. public
2. Argument: `View view`
3. The name is exactly onXXX.
4. In the same activity class.


## Layout Container

1. Linear Layout

2. Relative Layout
```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</RelativeLayout> 
```

3. Constraint Layout
```xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello, World!"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Click Me"
        <!--Ensure to place bottom of Text view -->
        app:layout_constraintTop_toBottomOf="@id/textView"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent" />
```

4. FrameLayout
* They are designed to block an area on the screen. 
* Frame Layout should be used to hold child view, because it can be difficult to display single views at a specific area on the screen without overlapping each other.