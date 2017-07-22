package ru.ivadimn.a0202storage.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ivadimn.a0202storage.model.PersonContract;

/**
 * Created by vadim on 21.07.2017.
 */

public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "contacts.db";
    public static final int DB_VERSION = 2;

    public PersonDBHelper(Context context) {
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
            db.execSQL(PersonContract.SQL_CREATE_TABLE);
        }
        if (oldVer < 2) {
            db.execSQL(PersonContract.SQL_ALTER_TABLE_2);
            db.execSQL(PersonContract.SQL_UPDATE_TABLE_2);
        }
    }
}
