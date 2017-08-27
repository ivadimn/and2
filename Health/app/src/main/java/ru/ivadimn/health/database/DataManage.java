package ru.ivadimn.health.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.health.App;
import ru.ivadimn.health.Utils;

/**
 * Created by vadim on 24.12.16.
 */

public class DataManage {
    SQLiteOpenHelper dbHelper;

    public DataManage() {
        dbHelper = new HealthDbHelper(App.getInstance().getApplicationContext());
    }

    public List<Values> selectAll(DbShema shema, String where, String[] args) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        List<Values> values = new ArrayList<>();
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(shema.getTableName(),
                   shema.getColumns(), where, args, null, null, null);
            int colCount = cursor.getColumnCount();
            while(cursor.moveToNext()) {
                Values value = new Values(colCount);
                for (int i = 0; i < colCount; i++) {
                    value.set(i, valueFrom(shema, cursor, i));
                }
                values.add(value);
            }
        }
        catch(Exception e) {
            Toast.makeText(App.getInstance().getBaseContext(), "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
            Log.d("DATA", "Database unavailable - " + e.getMessage());
        }
        finally {
            cursor.close();
            db.close();
        }
        return values;
    }

    public int insert(DbEntity entity) {
        SQLiteDatabase db = null;
        ContentValues contentValues = null;
        DbShema shema = entity.getShema();
        try {
            db = dbHelper.getWritableDatabase();
            contentValues = getContentValues(entity);
            long rowId = db.insert(shema.getTableName(), null, contentValues);
            entity.set_id(rowId);
        }
        catch(SQLiteException e) {
            Toast.makeText(App.getInstance().getBaseContext(), "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
        return 0;
    }

    public void update(DbEntity entity) {
        SQLiteDatabase db = null;
        ContentValues contentValues = null;
        try {
            db = dbHelper.getWritableDatabase();
            contentValues = getContentValues(entity);
            db.update(entity.getShema().getTableName(),
                    contentValues, entity.getShema().getKeyColumn() +" = ?", new String[] {Utils.string(entity.get_id())});
        }
        catch(SQLiteException e) {
            Toast.makeText(App.getInstance().getBaseContext(), "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    public void delete(DbEntity entity) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.delete(entity.getShema().getTableName(),  entity.getShema().getKeyColumn() + " = ?",
                    new String[] {Utils.string(entity.get_id())});
        }
        catch(SQLiteException e) {
            Toast.makeText(App.getInstance().getBaseContext(), "Database unavailable - " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            db.close();
        }
    }

    //получить значение поля из курсора в зависимости от типа
    private Object valueFrom(DbShema shema, Cursor cursor, int index) {
        int typeValue = shema.getTypeColumn(index);
        switch(typeValue) {
            case DbEntity.TYPE_INTEGER :
                return cursor.getInt(index);
            case DbEntity.TYPE_LONG:
                return  cursor.getLong(index);
            case DbEntity.TYPE_TEXT:
                return  cursor.getString(index);
            case DbEntity.TYPE_REAL:
                return  cursor.getDouble(index);
            case DbEntity.TYPE_BLOB:
                return new Blob(cursor.getBlob(index));
            default:
                return DbEntity.TYPE_NULL;
        }
    }
    //сформировать contentValues для insert и update
    private ContentValues getContentValues(DbEntity entity) {
        ContentValues contentValues = new ContentValues();
        DbShema shema = entity.getShema();
        int colCount = shema.getColumnsCount();
        String[] cols = shema.getColumns();
        Values values = entity.getValues();
        for (int i = 1; i < colCount; i++) {
            valueTo(contentValues, cols[i], values.get(i), shema.getTypeColumn(i));
        }
        return contentValues;
    }
    //поместить в contentValue значение в зависимости от типа
    private void valueTo(ContentValues contentValues,
                         String column, Object value, int typeValue) {
        switch(typeValue) {
            case DbEntity.TYPE_INTEGER :
                contentValues.put(column, (int) value);
                break;
            case DbEntity.TYPE_LONG:
                contentValues.put(column, (long) value);
                break;
            case DbEntity.TYPE_TEXT:
                contentValues.put(column, (String) value);
                break;
            case DbEntity.TYPE_REAL:
                contentValues.put(column, (double) value);
                break;
            case DbEntity.TYPE_BLOB:
                contentValues.put(column, ((Blob) value).getBytes());
                break;
            default:
                contentValues.put(column, (byte[]) null);
                break;
        }
    }
}
