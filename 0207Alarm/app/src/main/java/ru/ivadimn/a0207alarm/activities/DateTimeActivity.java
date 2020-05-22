package ru.ivadimn.a0207alarm.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

import ru.ivadimn.a0207alarm.R;

public class DateTimeActivity extends AppCompatActivity {

    public static final int REAQUEST_DATETIME = 1;
    private static final String MOMENT = "MOMENT";
    private DatePicker datePicker;
    private TimePicker timePicker;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private final Calendar calendar = Calendar.getInstance();

    public static Intent createIntent(Context context, long moment) {
        Intent intent = new Intent(context, DateTimeActivity.class);
        intent.putExtra(MOMENT, moment);
        return intent;
    }

    public static long getMoment(Intent intent) {
        long moment = intent.getLongExtra(MOMENT, Calendar.getInstance().getTimeInMillis());
        return moment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        Intent intent = getIntent();
        long now = intent.getLongExtra(MOMENT, 0);
        if (now != 0) {
            calendar.setTimeInMillis(now);
        }
        initDate();
        initTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_save_id:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initDate() {
        datePicker = (DatePicker) findViewById(R.id.date_picker_id);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
            }
        });
    }

    private void initTime() {
        timePicker = (TimePicker) findViewById(R.id.time_picker_id);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });
    }

    private void save() {
        calendar.set(year, month, day, hour, minute, 0);
        Intent data = new Intent();
        data.putExtra(MOMENT, calendar.getTimeInMillis());
        setResult(RESULT_OK, data);
        finish();
    }
}
