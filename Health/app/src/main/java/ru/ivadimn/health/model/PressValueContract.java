package ru.ivadimn.health.model;

import android.provider.BaseColumns;

import ru.ivadimn.health.database.Db;

/**
 * Created by vadim on 20.08.2017.
 */

public class PressValueContract implements BaseColumns{

    public static final String TABLE_NAME = "press_value";
    public static final String COLUMN_PRESSURE_ID = "pressure_id";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_SYSTOL = "systol";
    public static final String COLUMN_DIASTOL = "diastol";
    public static final String COLUMN_PULS = "puls";
    public static final int COUNT = 6;

    //for version 1
    public static final String SQL_CREATE_TABLE =
            Db.CREATE_TABLE + TABLE_NAME + "( " +
                    _ID + Db.PRIMARY_KEY + Db.SEP +
                    COLUMN_PRESSURE_ID + Db.TYPE_INT + Db.SEP +
                    COLUMN_TIME + Db.TYPE_INT + Db.SEP +
                    COLUMN_SYSTOL + Db.TYPE_INT + Db.SEP +
                    COLUMN_DIASTOL + Db.TYPE_INT + Db.SEP +
                    COLUMN_PULS + Db.TYPE_INT + ");";

}
