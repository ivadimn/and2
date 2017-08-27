package ru.ivadimn.health.model;

import android.provider.BaseColumns;

import ru.ivadimn.health.database.Db;

/**
 * Created by vadim on 26.08.2017.
 */

public class PressContract implements BaseColumns {

    public static final String TABLE_NAME = "press";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_COMMENT = "comment";
    public static final int COUNT = 3;

    //for version 1
    public static final String SQL_CREATE_TABLE =
            Db.CREATE_TABLE + TABLE_NAME + "( " +
                    _ID + Db.PRIMARY_KEY + Db.SEP +
                    COLUMN_DATE + Db.TYPE_INT + Db.SEP +
                    COLUMN_COMMENT + Db.TYPE_TEXT + ");";
}
