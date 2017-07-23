package ru.ivadimn.a0202storage.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Friend;
import ru.ivadimn.a0202storage.model.FriendContract;
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.model.PersonContract;

/**
 * Created by vadim on 21.07.2017.
 */

public class DatabaseStorage implements IDataStore {

    public static final String TAG = "DATABASE_STORAGE";
    private List<Person> list;
    private Context context;
    private PersonDBHelper dbHelper;

    public DatabaseStorage(Context context) {
        this.context = context;
        dbHelper = new PersonDBHelper(context);
    }

    @Override
    public List<Person> getList() {
        if (list == null)
            list = readFromDB();
        return list;
    }

    @Override
    public void saveList() {

    }

    @Override
    public void insert(Person person) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues(person);
        try {
            db = dbHelper.getWritableDatabase();
            long rowId = db.insert(PersonContract.PersonEntry.PERSON_TABLE, null, values);
            person.set_id(rowId);
            list.add(person);
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Insert error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    @Override
    public void update(int position, Person person) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues(person);
        try {
            db = dbHelper.getWritableDatabase();
            int count  = db.update(PersonContract.PersonEntry.PERSON_TABLE,
                    values, PersonContract.PersonEntry._ID + " = ?", new String[] {Long.toString(person.get_id())});
            list.set(position, person);
        }
        catch(SQLiteException e) {
            Toast.makeText(context, "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    @Override
    public void delete(Person p) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(PersonContract.PersonEntry.PERSON_TABLE,  PersonContract.PersonEntry._ID + " = ?",
                    new String[] {Long.toString(p.get_id())});
            list.remove(p);
        }
        catch(SQLiteException e) {
            Toast.makeText(context, "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    private List<Person> readFromDB() {
        List<Person> p = new ArrayList<Person>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor c = db.query(PersonContract.PersonEntry.PERSON_TABLE,
                    PersonContract.PersonEntry.PROJECTION_ALL, null, null, null, null,
                    PersonContract.PersonEntry._ID);
            while(c.moveToNext()) {
                p.add(getPerson(c));
            }
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Insert error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
        return p;
    }

    //сформировать contentValues для insert и update
    private ContentValues getContentValues(Person person) {
        ContentValues values = new ContentValues();
        values.put(PersonContract.PersonEntry.COLUMN_NAME, person.getName());
        values.put(PersonContract.PersonEntry.COLUMN_PHONE, person.getPhone());
        values.put(PersonContract.PersonEntry.COLUMN_EMAIL, person.getEmail());
        values.put(PersonContract.PersonEntry.COLUMN_BIRTHDAY, person.getBirhtday());
        byte[] b = person.getPhoto();
        values.put(PersonContract.PersonEntry.COLUMN_PHOTO, b);
        return values;
    }

    private Person getPerson(Cursor c) {
        Person person = new Person();
        person.set_id(c.getLong(c.getColumnIndex(PersonContract.PersonEntry._ID)));
        person.setName(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME)));
        person.setPhone(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHONE)));
        person.setEmail(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_EMAIL)));
        person.setBirhtday(c.getLong(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_BIRTHDAY)));
        byte[] b = c.getBlob(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHOTO));
        person.setPhoto(b);
        ///////////////////
        person.setFriends(getFriends(person.get_id()));
        return person;
    }

    ////////////////////////Friend operation///////////////////////////////////////

    private void insertFriends(List<Friend> fs) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues();
        db.
        try {
            db = dbHelper.getWritableDatabase();
            long rowId = db.insert(PersonContract.PersonEntry.PERSON_TABLE, null, values);
            person.set_id(rowId);
            list.add(person);
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Insert error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    private List<Friend> getFriends(long personId) {
        List<Friend> fs = new ArrayList<Friend>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor c = db.query(FriendContract.FriendEntry.FRIEND_TABLE,
                    FriendContract.FriendEntry.PROJECTION_ALL, FriendContract.FriendEntry.COLUMN_PERSON_ID + " = ?",
                    new String[] {Long.toString(personId)}, null, null, FriendContract.FriendEntry._ID);
            while(c.moveToNext()) {
                fs.add(getFriend(c));
            }
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Insert error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
        return fs;
    }

    private Friend getFriend(Cursor c) {
        Friend friend = new Friend();
        friend.set_id(c.getLong(c.getColumnIndex(FriendContract.FriendEntry._ID)));
        friend.setPersonId(c.getLong(c.getColumnIndex(FriendContract.FriendEntry.COLUMN_PERSON_ID)));
        friend.setName(c.getString(c.getColumnIndex(FriendContract.FriendEntry.COLUMN_NAME)));
        return friend;
    }

    private List<ContentValues> getFriendContentValues(List<Friend> fs) {
        List<ContentValues> cvl = new ArrayList<ContentValues>();
        for (int i = 0; i < fs.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(FriendContract.FriendEntry.COLUMN_PERSON_ID, fs.get().getPersonId());
        }
        return cvl;
    }
}
