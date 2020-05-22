package ru.ivadimn.a0207alarm.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

import ru.ivadimn.a0207alarm.R;
import ru.ivadimn.a0207alarm.model.Event;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EVENT = "EVENT";
    public static final String TITLE = "TITLE";
    private static final String MOMENT = "MOMENT";

    private static final int REQUEST_MOMENT = 1;
    public static final int VIEW_EVENT = 3;

    private EditText edTitle;
    private Button btnMoment;
    private Event event;

    public static Intent createIntent(Context context, Event e) {
        Intent intent = new Intent(context, EventActivity.class);
        if (e != null) {
            intent.putExtra(EVENT, (Parcelable) e);
        }
        return intent;
    }

    public static Event getEvent(Intent intent) {
        return intent.getParcelableExtra(EVENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        edTitle = (EditText) findViewById(R.id.event_name_id);
        btnMoment = (Button) findViewById(R.id.btn_moment_id);
        btnMoment.setOnClickListener(this);

        Intent intent = getIntent();
        event = intent.getParcelableExtra(EVENT);

        if (event != null) {
            edTitle.setText(event.getTitle());
            btnMoment.setText(getString(R.string.event_moment) + " - " + event.getMomentString());
        }
        else {
            event = new Event();
        }
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


    @Override
    public void onClick(View view) {
        getMoment();
    }

    private void getMoment() {
        long moment = (event != null) ? event.getMoment() : Calendar.getInstance().getTimeInMillis();
        Intent intent = DateTimeActivity.createIntent(this, moment);
        startActivityForResult(intent, REQUEST_MOMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MOMENT && resultCode == RESULT_OK) {
            event.setMoment(DateTimeActivity.getMoment(data));
            btnMoment.setText(getString(R.string.event_moment) + " - " + event.getMomentString());
        }
    }

    private void save() {
        event.setTitle(edTitle.getText().toString());
        Intent data = new Intent();
        data.putExtra(EVENT, (Parcelable) event);
        setResult(RESULT_OK, data);
        finish();
    }
}
