package ru.ivadimn.a0202storage;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.storage.DatabaseStorage;
import ru.ivadimn.a0202storage.storage.FileStorage;

/**
 * Created by vadim on 20.07.17.
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

