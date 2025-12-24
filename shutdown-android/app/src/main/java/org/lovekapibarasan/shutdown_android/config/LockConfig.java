package org.lovekapibarasan.shutdown_android.config;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LockConfig {

    private final Context context;
    private final SharedPreferences prefs;
    private final Gson gson;

    private static final String PREFS_NAME = "lock_config_prefs";
    private static final String KEY_TRIGGERS = "repeating_triggers";

    public LockConfig(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
        Log.d("LockConfig", "LockConfig initialized");
    }

    public List<RepeatingTriggerConfig> getRepeatingTriggers() {
        String json = prefs.getString(KEY_TRIGGERS, null);
        if (json == null) {
            Log.d("LockConfig", "No saved triggers, returning default test data");
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<RepeatingTriggerConfig>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public void saveRepeatingTriggers(List<RepeatingTriggerConfig> triggers) {
        String json = gson.toJson(triggers);
        prefs.edit().putString(KEY_TRIGGERS, json).apply();
        Log.d("LockConfig", "Triggers saved");
    }
}
