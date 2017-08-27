package ru.ivadimn.health;

import android.app.Application;

/**
 * Created by vadim on 26.08.2017.
 */

public class App extends Application {
    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    public static App getInstance() {
        return instance;
    }
}
