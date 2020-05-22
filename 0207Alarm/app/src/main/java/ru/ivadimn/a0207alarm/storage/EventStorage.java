package ru.ivadimn.a0207alarm.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0207alarm.model.Event;
import ru.ivadimn.a0207alarm.model.EventContract;

/**
 * Created by vadim on 06.08.2017.
 */

public class EventStorage implements IDataStore {

    public static final String TAG = "EVENT_STORAGE";
    private List<Event> list;
    private Context context;
    private EventDbHelper dbHelper;

    public EventStorage(Context context) {
        this.context = context;
        dbHelper = new EventDbHelper(context);
    }

    @Override
    public List<Event> getList() {
        if (list == null)
            list = readFromDB();
        return list;
    }

    @Override
    public void saveList() {

    }

    @Override
    public void insert(Event event) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues(event);
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            long rowId = db.insert(EventContract.EventEntry.EVENT_TABLE, null, values);
            event.set_id(rowId);
            list.add(event);
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
    public void update(int position, Event event) {
        SQLiteDatabase db = null;
        ContentValues values = getContentValues(event);
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            int count  = db.update(EventContract.EventEntry.EVENT_TABLE,
                    values, EventContract.EventEntry._ID + " = ?", new String[] {Long.toString(event.get_id())});

            list.set(position, event);
            db.setTransactionSuccessful();

        }
        catch(SQLiteException ex) {
            Toast.makeText(context, "Update error - " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    @Override
    public void delete(Event e) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete(EventContract.EventEntry.EVENT_TABLE,  EventContract.EventEntry._ID + " = ?",
                    new String[] {Long.toString(e.get_id())});
            list.remove(e);
            db.setTransactionSuccessful();

        }
        catch(SQLiteException ex) {
            Toast.makeText(context, "Delete error - " + ex.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    private List<Event> readFromDB() {
        List<Event> e = new ArrayList<Event>();
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            Cursor c = db.query(EventContract.EventEntry.EVENT_TABLE,
                    EventContract.EventEntry.PROJECTION_ALL, null, null, null, null,
                    EventContract.EventEntry.COLUMN_MOMENT);
            while(c.moveToNext()) {
                e.add(getEvent(db, c));
            }
        }
        catch (SQLiteException ex) {
            Toast.makeText(context, "Query error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
        return e;
    }

    //сформировать contentValues для insert и update
    private ContentValues getContentValues(Event event) {
        ContentValues values = new ContentValues();
        values.put(EventContract.EventEntry.COLUMN_TITLE, event.getTitle());
        values.put(EventContract.EventEntry.COLUMN_MOMENT, event.getMoment());
        values.put(EventContract.EventEntry.COLUMN_REPEAT, event.getRepeat());
        return values;
    }

    private Event getEvent(SQLiteDatabase db, Cursor c) {
        Event event = new Event();
        event.set_id(c.getLong(c.getColumnIndex(EventContract.EventEntry._ID)));
        event.setTitle(c.getString(c.getColumnIndex(EventContract.EventEntry.COLUMN_TITLE)));
        event.setMoment(c.getLong(c.getColumnIndex(EventContract.EventEntry.COLUMN_MOMENT)));
        event.setRepeat(c.getLong(c.getColumnIndex(EventContract.EventEntry.COLUMN_REPEAT)));
        return event;
    }

}
