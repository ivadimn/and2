package ru.ivadimn.a0205threads;

import android.app.Application;

import ru.ivadimn.a0205threads.storage.StorageFactory;

/**
 * Created by vadim on 30.07.2017.
 */

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StorageFactory.initStorage(getApplicationContext(), StorageFactory.DATABASE_STORAGE);
        instance = this;
    }
}
