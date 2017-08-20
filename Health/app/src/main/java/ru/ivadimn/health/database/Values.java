package ru.ivadimn.health.database;

/**
 * Created by vadim on 25.12.16.
 */

public class Values {

    private Object[] values;
    private int count;
    public Values(int count) {
        this.count = count;
        values = new Object[count];
    }

    public Values(Object[] objects) {
        this.count = objects.length;
        this.values = objects;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public void set(int index, Object obj) {
        if (index >= count) return;
        values[index] = obj;
    }

    public Object get(int index) {
        if (index >= count) return null;
        return values[index];
    }
}
