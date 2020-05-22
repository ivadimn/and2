package ru.ivadimn.health.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ivadimn.health.model.PressContract;
import ru.ivadimn.health.model.PressValueContract;

/**
 * Created by vadim on 26.08.2017.
 */

public class HealthDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "health.db";
    public static final int DB_VERSION = 1;

    public HealthDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer1) {
        updateDatabase(db, oldVer, DB_VERSION);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVer, int newVer) {
        if (oldVer < 1) {
            db.execSQL(PressContract.SQL_CREATE_TABLE);
            db.execSQL(PressValueContract.SQL_CREATE_TABLE);
        }
    }
}
