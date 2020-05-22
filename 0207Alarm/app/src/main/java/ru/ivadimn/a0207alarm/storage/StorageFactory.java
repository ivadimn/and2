package ru.ivadimn.a0207alarm.storage;

import android.content.Context;

/**
 * Created by vadim on 06.08.2017.
 */

public class StorageFactory  {

    public static final int DATABASE_STORAGE = 2;

    private static IDataStore store = null;


    public static void initStorage(Context context, int storageType) {
        switch(storageType) {
            case DATABASE_STORAGE:
                store = new EventStorage(context);
                break;
            default:
                store = null;
        }
    }
    public static IDataStore getStorage() {
        if (store != null)
            return store;
        else
            throw new RuntimeException(" Error:  Storage not initialize!!!");

    }
}
