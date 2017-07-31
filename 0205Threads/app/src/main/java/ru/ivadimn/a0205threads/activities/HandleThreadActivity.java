package ru.ivadimn.a0205threads.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ru.ivadimn.a0205threads.R;
import ru.ivadimn.a0205threads.Utils;
import ru.ivadimn.a0205threads.adapters.PersonAdapter;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.IDataStore;
import ru.ivadimn.a0205threads.storage.StorageFactory;
import ru.ivadimn.a0205threads.threads.ReadHandlerThread;

public class HandleThreadActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String  TAG = "HandleThreadActivity";
    private RecyclerView recyclerView;
    private List<Person> persons;
    private PersonAdapter adapter;
    private IDataStore store;
    private ProgressBar progressBar;
    private ReadHandlerThread mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_thread);
        store = StorageFactory.getStorage();
        findViewById(R.id.btn_load_id).setOnClickListener(this);
        findViewById(R.id.btn_insert_id).setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.person_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.loading_data_id);
        progressBar.setVisibility(View.INVISIBLE);
        mHandler = new ReadHandlerThread(TAG, store, listener);
        mHandler.start();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_load_id:
                progressBar.setVisibility(View.VISIBLE);
                mHandler.read();
                break;
            case R.id.btn_insert_id:
                Person p = Utils.getPerson();
                p.setName("AAAAAA bbbbbbbbbb");
                mHandler.insert(p);
                break;
        }
    }

    private ReadHandlerThread.HandleThreadListener listener = new ReadHandlerThread.HandleThreadListener() {
        @Override
        public void onRead(List<Person> list) {
            persons = list;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.updateData(persons);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }

        @Override
        public void onWrite(boolean status) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(HandleThreadActivity.this, "Person record was inserted!!!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.quit();
    }
}
