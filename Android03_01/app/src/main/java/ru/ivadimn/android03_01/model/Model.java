package ru.ivadimn.android03_01.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by vadim on 24.10.17.
 */

public class Model  {

    private List<Integer> mList;
    public Model() {
        mList = new ArrayList<>(3);
        mList.add(0);
        mList.add(0);
        mList.add(0);
    }

    public int getElementValueAtIndex(int index) {
        return mList.get(index);
    }
    public void setElementValueAtIndex(int index, int value) {
        mList.set(index, value);
    }

}
