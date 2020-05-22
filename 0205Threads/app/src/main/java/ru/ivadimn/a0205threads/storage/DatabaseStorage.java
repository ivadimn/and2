package ru.ivadimn.a0205threads.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.ivadimn.a0205threads.model.Friend;
import ru.ivadimn.a0205threads.model.FriendContract;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.model.PersonContract;


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
            db.beginTransaction();
            long rowId = db.insert(PersonContract.PersonEntry.PERSON_TABLE, null, values);
            person.set_id(rowId);
            list.add(person);
            insertFriends(db, person.getFriends());
            db.setTransactionSuccessful();
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Insert error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void update(int position, Person person) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues(person);
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            deleteFriends(db, person.get_id());
            int count  = db.update(PersonContract.PersonEntry.PERSON_TABLE,
                    values, PersonContract.PersonEntry._ID + " = ?", new String[] {Long.toString(person.get_id())});
            insertFriends(db, person.getFriends());
            list.set(position, person);
            db.setTransactionSuccessful();

        }
        catch(SQLiteException e) {
            Toast.makeText(context, "Update error - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void delete(Person p) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            deleteFriends(db, p.get_id());
            db.delete(PersonContract.PersonEntry.PERSON_TABLE,  PersonContract.PersonEntry._ID + " = ?",
                    new String[] {Long.toString(p.get_id())});
            list.remove(p);
            db.setTransactionSuccessful();

        }
        catch(SQLiteException e) {
            Toast.makeText(context, "Delete error - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    private List<Person> readFromDB() {
        List<Person> p = new ArrayList<Person>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            c = db.query(PersonContract.PersonEntry.PERSON_TABLE,
                    PersonContract.PersonEntry.PROJECTION_ALL, null, null, null, null,
                    PersonContract.PersonEntry.COLUMN_NAME);
            while(c.moveToNext()) {
                p.add(getPerson(db, c));
            }

        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Query error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            c.close();
            db.close();
        }
        /*for (int i = 0; i < 10; i++) {
            Person person = new Person();
            person.set_id(i);
            person.setName("Use handler Name Family - 00" + String.valueOf(new Random().nextInt(100000)));
            person.setPhone(String.valueOf(new Random().nextInt(100000)));
            person.setEmail("aaaaa@mail.ru");
            person.setBirhtday(new Random().nextLong());
            p.add(person);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, person.getName());
        }*/
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

    private Person getPerson(SQLiteDatabase db, Cursor c) {
        Person person = new Person();
        person.set_id(c.getLong(c.getColumnIndex(PersonContract.PersonEntry._ID)));
        person.setName(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME)));
        person.setPhone(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHONE)));
        person.setEmail(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_EMAIL)));
        person.setBirhtday(c.getLong(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_BIRTHDAY)));
        byte[] b = c.getBlob(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHOTO));
        person.setPhoto(b);
        ///////////////////
        //person.setFriends(getFriends(db, person.get_id()));
        //имитация длительной загрузки
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return person;
    }

    ////////////////////////Friend operation///////////////////////////////////////

    private void insertFriends(SQLiteDatabase db, List<Friend> fs) throws SQLiteException {
        List<ContentValues> cvl = getFriendContentValues(fs);
        for (int i = 0; i < cvl.size(); i++) {
            ContentValues cv = cvl.get(i);
            long rowId = db.insert(FriendContract.FriendEntry.FRIEND_TABLE, null, cv);
            fs.get(i).set_id(rowId);
        }
    }

    private void deleteFriends(SQLiteDatabase db,  long personId) throws SQLiteException {
        db.delete(FriendContract.FriendEntry.FRIEND_TABLE,  FriendContract.FriendEntry.COLUMN_PERSON_ID + " = ?",
                new String[] {Long.toString(personId)});
    }

    private List<Friend> getFriends(SQLiteDatabase db, long personId) throws SQLiteException {
        List<Friend> fs = new ArrayList<Friend>();
        Cursor c = db.query(FriendContract.FriendEntry.FRIEND_TABLE,
               FriendContract.FriendEntry.PROJECTION_ALL, FriendContract.FriendEntry.COLUMN_PERSON_ID + " = ?",
               new String[] {Long.toString(personId)}, null, null, FriendContract.FriendEntry._ID);
        while(c.moveToNext())
             fs.add(getFriend(c));
        c.close();
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
            cv.put(FriendContract.FriendEntry.COLUMN_PERSON_ID, fs.get(i).getPersonId());
            cv.put(FriendContract.FriendEntry.COLUMN_NAME, fs.get(i).getName());
            cvl.add(cv);
        }
        return cvl;
    }
}
