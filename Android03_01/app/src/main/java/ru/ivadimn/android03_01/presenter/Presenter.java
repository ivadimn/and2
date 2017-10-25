package ru.ivadimn.android03_01.presenter;

import ru.ivadimn.android03_01.MainActivity;
import ru.ivadimn.android03_01.interfaces.ViewInterface;
import ru.ivadimn.android03_01.model.Model;

/**
 * Created by vadim on 24.10.17.
 */

public class Presenter {
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
}
