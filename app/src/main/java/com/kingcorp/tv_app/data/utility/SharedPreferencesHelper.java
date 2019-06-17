package com.kingcorp.tv_app.data.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.kingcorp.tv_app.App;


public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREFERENCE_ACCESSOR = "tv_app_shared_preference";

    public SharedPreferencesHelper(App app) {
        sharedPreferences = app.getSharedPreferences(SHARED_PREFERENCE_ACCESSOR, Context.MODE_PRIVATE);
    }

    public void saveString(String value, String key) {
        sharedPreferences
                .edit()
                .putString(key, value)
                .apply();
    }

    public void saveInt(int value, String key) {
        sharedPreferences
                .edit()
                .putInt(key, value)
                .apply();

    }

    public void saveBoolean(boolean value, String key) {
        sharedPreferences
                .edit()
                .putBoolean(key, value)
                .apply();

    }

    public String getString(String key) {
        return  sharedPreferences.getString(key, null);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public boolean getBoolean(String key, boolean defaultVal) {
        return sharedPreferences.getBoolean(key, defaultVal);
    }
}
