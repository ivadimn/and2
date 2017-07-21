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
        ContentValues values = new ContentValues();
        values.put(PersonContract.PersonEntry.COLUMN_NAME, person.getName());
        values.put(PersonContract.PersonEntry.COLUMN_PHONE, person.getPhone());
        values.put(PersonContract.PersonEntry.COLUMN_EMAIL, person.getEmail());
        byte[] b = person.getPhoto();
        values.put(PersonContract.PersonEntry.COLUMN_PHOTO, b);

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
        ContentValues values = new ContentValues();
        values.put(PersonContract.PersonEntry.COLUMN_NAME, person.getName());
        values.put(PersonContract.PersonEntry.COLUMN_PHONE, person.getPhone());
        values.put(PersonContract.PersonEntry.COLUMN_EMAIL, person.getEmail());
        byte[] b = person.getPhoto();
        values.put(PersonContract.PersonEntry.COLUMN_PHOTO, b);
        long id = person.get_id();
        try {
            db = dbHelper.getWritableDatabase();
            int count  = db.update(PersonContract.PersonEntry.PERSON_TABLE,
                    values, PersonContract.PersonEntry._ID + " = ?", new String[] {Long.toString(id)});
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
                Person person = new Person();
                long id = c.getLong(c.getColumnIndex(PersonContract.PersonEntry._ID));
                person.set_id(id);
                person.setName(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_NAME)));
                person.setPhone(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHONE)));
                person.setEmail(c.getString(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_EMAIL)));
                byte[] b = c.getBlob(c.getColumnIndex(PersonContract.PersonEntry.COLUMN_PHOTO));
                person.setPhoto(b);
                p.add(person);
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

    /*private void saveImage(String fname, byte[] b) {

        File file = new File(context.getFilesDir(), fname);
        ByteArrayOutputStream bs = null;
        try {
            bs = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(file);
            bs.write(b, 0, b.length);
            bs.writeTo(out);
            bs.flush();
            bs.close();
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private byte[] readImage(String fname) {
        byte[] b = null;
        File file = new File(context.getFilesDir(), fname);
        if (!file.exists()) return null;
        b = new byte[(int)file.length()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(b);
            in.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return b;
    } */
}
