package ru.ivadimn.health.model;

import ru.ivadimn.health.database.DbEntity;
import ru.ivadimn.health.database.DbShema;
import ru.ivadimn.health.database.Values;

/**
 * Created by vadim on 26.08.2017.
 */

public class Press extends DbEntity {

    private long date;
    private String comment;

    public Press() {
        //no-op
    }

    public Press(long date, String comment) {
        this.date = date;
        this.comment = comment;
    }

    public Press(long id, long date, String comment) {
        this._id = id;
        this.date = date;
        this.comment = comment;
    }


    @Override
    public Values getValues() {
        return  new Values(new Object[]{_id, date, comment});
    }

    @Override
    public void setValues(Values values) {
        if (values.size() == PressValueContract.COUNT) {
            _id = (long) values.get(0);
            date = (long) values.get(1);
            comment = (String) values.get(2);
        }
        else {
            throw new RuntimeException("Wrong values count: expectid " + PressContract.COUNT +
                    ", but found " + values.size());
        }
    }

    @Override
    public DbShema getShema() {
        return shema;
    }

    public static DbShema shema = new DbShema() {
        @Override
        public String getTableName() {
            return PressContract.TABLE_NAME;
        }

        @Override
        public int getTypeColumn(int index) {
            switch(index) {
                case 0:
                case 1:
                    return DbEntity.TYPE_LONG;
                case 2:
                    return DbEntity.TYPE_TEXT;
                default:
                    return DbEntity.TYPE_NULL;
            }
        }

        @Override
        public String[] getColumns() {
            return new String[] {PressContract._ID, PressContract.COLUMN_DATE,
            PressContract.COLUMN_COMMENT};
        }

        @Override
        public int getColumnsCount() {
            return PressContract.COUNT;
        }

        @Override
        public String getKeyColumn() {
            return PressContract._ID;
        }
    };

    public long getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }
}
