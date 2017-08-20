package ru.ivadimn.health.database;

/**
 * Created by vadim on 24.12.16.
 */

public interface Row {
    public Values getValues();
    public void setValues(Values values);
    public String[] getValuesString();

}
