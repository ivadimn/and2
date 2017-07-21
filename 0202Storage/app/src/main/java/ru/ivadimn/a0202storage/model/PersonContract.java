package ru.ivadimn.a0202storage.model;

import android.provider.BaseColumns;

/**
 * Created by vadim on 21.07.2017.
 */

public final class PersonContract {


    public static final String SEP = ", ";
    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_BLOB = " BLOB";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + PersonEntry.PERSON_TABLE + "( " +
            PersonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + SEP +
            PersonEntry.COLUMN_NAME + TYPE_TEXT + SEP +
            PersonEntry.COLUMN_PHONE + TYPE_TEXT + SEP +
            PersonEntry.COLUMN_EMAIL + TYPE_TEXT + SEP +
            PersonEntry.COLUMN_PHOTO + TYPE_BLOB + ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + PersonEntry.PERSON_TABLE;

    public  static class PersonEntry implements BaseColumns  {
        public static final String PERSON_TABLE = "persons";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHOTO = "photo";
        public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME,
                COLUMN_PHONE, COLUMN_EMAIL, COLUMN_PHOTO};
    }

}
