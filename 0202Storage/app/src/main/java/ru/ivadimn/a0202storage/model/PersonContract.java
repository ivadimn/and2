package ru.ivadimn.a0202storage.model;

import android.provider.BaseColumns;

import java.util.Date;
import java.util.GregorianCalendar;

import ru.ivadimn.a0202storage.storage.Db;

/**
 * Created by vadim on 21.07.2017.
 */

public final class PersonContract {

    public  static class PersonEntry implements BaseColumns  {
        public static final String PERSON_TABLE = "persons";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_HOBBY = "hobby";


        public static final String[] PROJECTION_ALL = {_ID, COLUMN_NAME,
                COLUMN_PHONE, COLUMN_EMAIL, COLUMN_PHOTO, COLUMN_HOBBY};
    }

    //for version 1
    public static final String SQL_CREATE_TABLE =
            Db.CREATE_TABLE + PersonEntry.PERSON_TABLE + "( " +
                    PersonEntry._ID + Db.PRIMARY_KEY + Db.SEP +
                    PersonEntry.COLUMN_NAME + Db.TYPE_TEXT + Db.SEP +
                    PersonEntry.COLUMN_PHONE + Db.TYPE_TEXT + Db.SEP +
                    PersonEntry.COLUMN_EMAIL + Db.TYPE_TEXT + Db.SEP +
                    PersonEntry.COLUMN_PHOTO + Db.TYPE_BLOB + ");";

    //for version 2
    public static final String SQL_ALTER_TABLE_2 =
            Db.ALTER_TABLE + PersonEntry.PERSON_TABLE + Db.ADD_COLUMN +
                    PersonEntry.COLUMN_HOBBY + Db.TYPE_TEXT + ";";

    public static final String SQL_UPDATE_TABLE_2 =
            "UPDATE " + PersonEntry.PERSON_TABLE + " SET " + PersonEntry.COLUMN_HOBBY + " = 'no hobby';";


    public static final String SQL_DROP_TABLE =
            Db.DROP_TABLE + PersonEntry.PERSON_TABLE + ";";

}
