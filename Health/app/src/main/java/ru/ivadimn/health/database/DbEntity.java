package ru.ivadimn.health.database;

import android.provider.BaseColumns;

/**
 * Created by vadim on 24.12.16.
 */

public abstract class DbEntity implements Row, BaseColumns {

    public static final int TYPE_NULL = 0;
    public static final int TYPE_BYTE = 1;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_LONG = 3;
    public static final int TYPE_REAL = 4;
    public static final int TYPE_TEXT = 5;
    public static final int TYPE_BLOB = 6;

    protected long _id;
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public abstract DbShema getShema();

}
