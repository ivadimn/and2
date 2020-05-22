package ru.ivadimn.android03_01.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import ru.ivadimn.android03_01.MainActivity;
import ru.ivadimn.android03_01.interfaces.ViewInterface;
import ru.ivadimn.android03_01.model.Model;

/**
 * Created by vadim on 24.10.17.
 */

public class Presenter {
    private static final String MODEL_STATE = "MODEL_STATE";
    private static final String COUNTERS = "COUNTERS";
    private Model mModel;
    private ViewInterface view;
    public Presenter(ViewInterface view) {
        this.mModel = new Model();
        this.view = view;
    }

    private int calcNewModelValue(int modelElementIndex) {
        return mModel.getElementValueAtIndex(modelElementIndex) + 1;
    }

    public void buttonClick(int index) {
        int newModelValue;
        newModelValue = calcNewModelValue(index);
        mModel.setElementValueAtIndex(index, newModelValue);
        view.setButtonTExt(index, newModelValue);

    }


    public void saveState(Bundle outState) {
        outState.putIntegerArrayList(COUNTERS, mModel.getmList());
    }

    public void restoreState(Bundle inState) {
        mModel.setmList(inState.getIntegerArrayList(COUNTERS));
        for (int i = 0; i < 3; i++) {
            view.setButtonTExt(i, mModel.getElementValueAtIndex(i));
        }
    }

}
