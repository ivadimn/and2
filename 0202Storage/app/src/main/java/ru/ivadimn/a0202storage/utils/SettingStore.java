package ru.ivadimn.a0202storage.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ru.ivadimn.a0202storage.interfaces.IOptionsStore;

/**
 * Created by vadim on 19.07.17.
 */

public class SettingStore implements IOptionsStore {

    public static final String SETTING_STORED = "settings";

    private SharedPreferences mPreference;


    public SettingStore(Context context) {
        mPreference = context.getSharedPreferences(SETTING_STORED, Context.MODE_PRIVATE);
    }

    @Override
    public void addOption(String key, String value) {
        mPreference.edit().putString(key, value).apply();
    }

    @Override
    public String checkOption(String key) {
        return mPreference.getString(key, null);
    }

    @Override
    public void deleteOption(String key) {
        mPreference.edit().remove(key).apply();
    }
}
