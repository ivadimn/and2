package ru.ivadimn.a0207alarm.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ru.ivadimn.a0207alarm.EventAdapter;
import ru.ivadimn.a0207alarm.R;
import ru.ivadimn.a0207alarm.model.Event;
import ru.ivadimn.a0207alarm.services.EventService;
import ru.ivadimn.a0207alarm.storage.IDataStore;
import ru.ivadimn.a0207alarm.storage.StorageFactory;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_EVENT = 1;
    public static final int EDIT_EVENT = 2;
    public static final int VIEW_EVENT = 3;

    private RecyclerView list;
    private List<Event> events;
    private EventAdapter adapter;
    private FloatingActionButton fab;
    private IDataStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StorageFactory.initStorage(this, StorageFactory.DATABASE_STORAGE);
        store = StorageFactory.getStorage();
        events = store.getList();

        list = (RecyclerView) findViewById(R.id.event_list_id);
        fab = (FloatingActionButton) findViewById(R.id.fab_id);
        fab.setOnClickListener(fabClick);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(eventClick);
        list.setAdapter(adapter);
        adapter.updateData(events);
    }

    private View.OnClickListener fabClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            addEvent();
        }
    };

    private EventAdapter.ItemClickListener eventClick = new EventAdapter.ItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            editEvent(events.get(position));
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    };

    private void addEvent() {
        Intent intent = EventActivity.createIntent(MainActivity.this, null);
        startActivityForResult(intent, ADD_EVENT);
    }

    private void editEvent(Event event) {
        Intent intent = EventActivity.createIntent(MainActivity.this, event);
        startActivityForResult(intent, EDIT_EVENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_EVENT) {
            Event event = EventActivity.getEvent(data);
            store.insert(event);
            adapter.notifyDataSetChanged();
            insertAlarm(event);
        }
    }

    private void insertAlarm(Event e) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = EventService.createIntent(this, e);
        PendingIntent p = PendingIntent.getService(this, VIEW_EVENT, intent, 0);
        manager.set(AlarmManager.RTC_WAKEUP, e.getMoment(), p);
    }
}
