package ru.ivadimn.android03_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

import ru.ivadimn.android03_01.interfaces.ViewInterface;
import ru.ivadimn.android03_01.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements ViewInterface, View.OnClickListener {

    private Presenter mPresenter;
    private int[] btnIds = {R.id.btn_counter1, R.id.btn_counter2, R.id.btn_counter3};
    private Button[] btns = new Button[3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < btns.length; i++) {
            btns[i] = (Button) findViewById(btnIds[i]);
            btns[i].setOnClickListener(this);
        }

        mPresenter = new Presenter(this);
    }

    @Override
    public void onClick(View v) {
        int btnIndex = Arrays.binarySearch(btnIds, v.getId());
        mPresenter.buttonClick(btnIndex);
    }


    @Override
    public void setButtonTExt(int btnIndex, int value) {
        btns[btnIndex].setText("Количество=" + value);
    }
}
