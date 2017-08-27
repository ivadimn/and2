package ru.ivadimn.health.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.ivadimn.health.R;

public class PressActivity extends AppCompatActivity {

    private static final String DATE = "DATE";
    private static final String COMMENT = "COMMENT";
    private static final String PRESS_ID = "PRESS_ID";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press);
    }
}
