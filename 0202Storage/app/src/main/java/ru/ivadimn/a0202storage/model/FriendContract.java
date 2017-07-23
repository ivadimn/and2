package ru.ivadimn.a0202storage.model;

import android.provider.BaseColumns;

import ru.ivadimn.a0202storage.storage.Db;

/**
 * Created by vadim on 23.07.2017.
 */

public class FriendContract {

    public  static class FriendEntry implements BaseColumns {
        public static final String FRIEND_TABLE = "friends";
        public static final String COLUMN_PERSON_ID = "personId";
        public static final String COLUMN_NAME = "name";

        public static final String[] PROJECTION_ALL = {_ID, COLUMN_PERSON_ID,
                COLUMN_NAME};
    }

    //for version 3
    public static final String SQL_CREATE_TABLE =
            Db.CREATE_TABLE + FriendEntry.FRIEND_TABLE + "( " +
                    FriendEntry._ID + Db.PRIMARY_KEY + Db.SEP +
                    FriendEntry.COLUMN_PERSON_ID + Db.TYPE_INT + Db.SEP + ");";


}
