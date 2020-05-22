package ru.ivadimn.a0205threads.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0205threads.Utils;
import ru.ivadimn.a0205threads.model.FriendContract;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.model.PersonContract;


/**
 * Created by vadim on 21.07.2017.
 */

public class PersonDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "contacts.db";
    public static final int DB_VERSION = 3;

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
        try {
            if (oldVer < 1) {
                db.execSQL(PersonContract.SQL_CREATE_TABLE);
            }
            if (oldVer < 2) {
                db.execSQL(PersonContract.SQL_ALTER_TABLE_2);
                db.execSQL(PersonContract.SQL_UPDATE_TABLE_2);
                insertTestData(db);
            }
            if (oldVer < 3) {
                db.execSQL(FriendContract.SQL_CREATE_TABLE);
            }
        }
        catch(Exception ex) {
            Log.d(".......DB_HELPER", ex.getMessage());
        }
    }


    //для проверки
    private void insertTestData(SQLiteDatabase db) {
        List<ContentValues> cvl = getContentValues();
        for (int i = 0; i < cvl.size(); i++) {
            ContentValues cv = cvl.get(i);
            long rowId = db.insert(PersonContract.PersonEntry.PERSON_TABLE, null, cv);
        }
    }

    private List<ContentValues> getContentValues() {
        List<ContentValues> cvl = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            Person person = Utils.getPerson();
            ContentValues values = new ContentValues();
            values.put(PersonContract.PersonEntry.COLUMN_NAME, person.getName());
            values.put(PersonContract.PersonEntry.COLUMN_PHONE, person.getPhone());
            values.put(PersonContract.PersonEntry.COLUMN_EMAIL, person.getEmail());
            values.put(PersonContract.PersonEntry.COLUMN_BIRTHDAY, person.getBirhtday());
            values.put(PersonContract.PersonEntry.COLUMN_PHOTO, person.getPhoto());
            cvl.add(values);
        }
        Log.d(".........DB_HELPER", "Size - " + cvl.size());
        return cvl;
    }


}
