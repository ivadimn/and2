package ru.ivadimn.a0207alarm.model;

import android.provider.BaseColumns;

import ru.ivadimn.a0207alarm.storage.Db;

/**
 * Created by vadim on 06.08.2017.
 */

public class EventContract {

    public  static class EventEntry implements BaseColumns {
        public static final String EVENT_TABLE = "events";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_MOMENT = "moment";
        public static final String COLUMN_REPEAT = "repeat";

        public static final String[] PROJECTION_ALL = {_ID, COLUMN_TITLE,
                COLUMN_MOMENT, COLUMN_REPEAT};
    }

    //for version 1
    public static final String SQL_CREATE_TABLE =
            Db.CREATE_TABLE + EventEntry.EVENT_TABLE + "( " +
                    EventEntry._ID + Db.PRIMARY_KEY + Db.SEP +
                    EventEntry.COLUMN_TITLE + Db.TYPE_TEXT + Db.SEP +
                    EventEntry.COLUMN_MOMENT + Db.TYPE_TEXT + Db.SEP +
                    EventEntry.COLUMN_REPEAT + Db.TYPE_TEXT + ");";


}
