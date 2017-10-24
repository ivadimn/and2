package ru.ivadimn.android03_01.presenter;

import ru.ivadimn.android03_01.MainActivity;
import ru.ivadimn.android03_01.model.Model;

/**
 * Created by vadim on 24.10.17.
 */

public class Presenter {
    private Model mModel;
    private MainActivity view;
    public Presenter(MainActivity view) {
        this.mModel = new Model();
        this.view = view;
    }

    private int calcNewModelValue(int modelElementIndex) {
        return mModel.getElementValueAtIndex(modelElementIndex) + 1;
    }

}
