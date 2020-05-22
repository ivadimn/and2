package ru.ivadimn.health;

import android.app.Application;

import ru.ivadimn.health.database.DataManage;

/**
 * Created by vadim on 26.08.2017.
 */

public class App extends Application {
    private static App instance;
    private DataManage dataManage;

    @Override
    public void onCreate() {
        super.onCreate();
        dataManage = new DataManage(this);
        instance = this;
    }
    public static App getInstance() {
        return instance;
    }

    public DataManage getDataManage() {
        return dataManage;
    }
}
