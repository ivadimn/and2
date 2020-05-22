package ru.ivadimn.a0207alarm.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ivadimn.a0207alarm.model.EventContract;

/**
 * Created by vadim on 06.08.2017.
 */

public class EventDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "alarm.db";
    public static final int DB_VERSION = 1;

    public EventDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        updateDatabase(db, oldVer, DB_VERSION);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer < 1) {
            db.execSQL(EventContract.SQL_CREATE_TABLE);
        }
    }
}
