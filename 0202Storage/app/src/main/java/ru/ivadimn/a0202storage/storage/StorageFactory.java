package ru.ivadimn.a0202storage.storage;

import android.content.Context;

import ru.ivadimn.a0202storage.interfaces.IDataStore;

/**
 * Created by vadim on 21.07.2017.
 */

public class StorageFactory {

    public static final int FILE_STORAGE = 1;
    public static final int DATABASE_STORAGE = 2;
    public static final int JSON_STORAGE = 3;

    private static IDataStore store = null;


    public static void initStorage(Context context, int storageType) {
        switch(storageType) {
            case FILE_STORAGE:
                store = new FileStorage(context);
                break;
            case DATABASE_STORAGE:
                store = new DatabaseStorage(context);
                break;
            case JSON_STORAGE:
                store = new JsonStorage(context);
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
