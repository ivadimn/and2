package ru.ivadimn.health.database;

/**
 * Created by vadim on 25.12.16.
 */

public interface DbShema {
    public String getTableName();
    public int getTypeColumn(int index);
    public String[] getColumns();
    public int getColumnsCount();
    public String getKeyColumn();
    public String getWhere();
}
