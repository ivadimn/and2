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
    public static final int DB_VERSION = 1;

    public PersonDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(PersonContract.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
