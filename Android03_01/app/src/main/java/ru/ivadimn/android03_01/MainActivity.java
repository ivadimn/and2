package ru.ivadimn.android03_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

import ru.ivadimn.android03_01.interfaces.MainView;
import ru.ivadimn.android03_01.model.Model;
import ru.ivadimn.android03_01.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    private Presenter mPresenter;
    private Button btnCounter1;
    private Button btnCounter2;
    private Button btnCounter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCounter1 = (Button) findViewById(R.id.btn_counter1);
        btnCounter2 = (Button) findViewById(R.id.btn_counter2);
        btnCounter3 = (Button) findViewById(R.id.btn_counter3);
        btnCounter1.setOnClickListener(this);
        btnCounter2.setOnClickListener(this);
        btnCounter3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_counter1:
                mModel.setElementValueAtIndex(0);
                break;
            case R.id.btn_counter2:
                mModel.setElementValueAtIndex(1);
                break;
            case R.id.btn_counter3:
                mModel.setElementValueAtIndex(2);
                break;
        }
    }


    @Override
    public void setButtonTExt(int btnIndex, int value) {
        switch(btnIndex) {
            case 1:
                btnCounter1.setText("Количество=" + value);
                break;
            case 2:
                btnCounter2.setText("Количество=" + value);
                break;
            case 3:
                btnCounter3.setText("Количество=" + value);
                break;
        }
    }
}
