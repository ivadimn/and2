package ru.ivadimn.health.model;

import ru.ivadimn.health.Utils;
import ru.ivadimn.health.database.DbEntity;
import ru.ivadimn.health.database.DbShema;
import ru.ivadimn.health.database.Values;

/**
 * Created by vadim on 20.08.2017.
 */

public class PressValue extends DbEntity {

    private long pressureId;
    private long time;
    private byte systol;
    private byte diastol;
    private byte puls;

    @Override
    public Values getValues() {
        return new Values(new Object[]{_id, pressureId, time, systol, diastol, puls});
    }

    @Override
    public void setValues(Values values) {
        if (values.size() == PressValueContract.COUNT) {
            _id = (long) values.get(0);
            pressureId = (long) values.get(1);
            time = (long) values.get(2);
            systol = (byte) values.get(3);
            diastol = (byte) values.get(4);
            puls = (byte) values.get(5);
        }
        else {
            throw new RuntimeException("Wrong values count: expectid " + PressValueContract.COUNT +
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
            return PressValueContract.TABLE_NAME;
        }

        @Override
        public int getTypeColumn(int index) {
            switch(index) {
                case 0:
                case 1:
                case 2:
                    return DbEntity.TYPE_LONG;
                case 3:
                case 4:
                case 5:
                    return DbEntity.TYPE_BYTE;
                default:
                    return DbEntity.TYPE_NULL;
            }
        }

        @Override
        public String[] getColumns() {
            return new String[] {_ID, PressValueContract.COLUMN_PRESSURE_ID,
                    PressValueContract.COLUMN_TIME,
                    PressValueContract.COLUMN_SYSTOL,
                    PressValueContract.COLUMN_DIASTOL,
                    PressValueContract.COLUMN_PULS};
        }

        @Override
        public int getColumnsCount() {
            return PressValueContract.COUNT;
        }

        @Override
        public String getKeyColumn() {
            return _ID;
        }
    };

    public byte getSystol() {
        return systol;
    }

    public void setSystol(byte systol) {
        this.systol = systol;
    }

    public byte getDiastol() {
        return diastol;
    }

    public void setDiastol(byte diastol) {
        this.diastol = diastol;
    }

    public byte getPuls() {
        return puls;
    }

    public void setPuls(byte puls) {
        this.puls = puls;
    }
}
