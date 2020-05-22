package ru.ivadimn.a0205threads.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ru.ivadimn.a0205threads.R;
import ru.ivadimn.a0205threads.adapters.PersonAdapter;
import ru.ivadimn.a0205threads.commands.BaseCommand;
import ru.ivadimn.a0205threads.commands.ReadDbCommand;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.DatabaseStorage;
import ru.ivadimn.a0205threads.storage.IDataStore;
import ru.ivadimn.a0205threads.storage.StorageFactory;
import ru.ivadimn.a0205threads.threads.AsyncReadThread;
import ru.ivadimn.a0205threads.threads.BackgroundCommandThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtData;
    private BackgroundCommandThread commandThread;
    private IDataStore storage;
    private RecyclerView recyclerView;
    private List<Person> persons;
    private PersonAdapter adapter;

    private ProgressBar progressBar;
    private ReadDbCommand readCommand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = StorageFactory.getStorage();
        findViewById(R.id.btn_start_id).setOnClickListener(this);
        findViewById(R.id.btn_asynctask_id).setOnClickListener(this);
        findViewById(R.id.btn_loader_id).setOnClickListener(this);
        findViewById(R.id.btn_handlerthread_id).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.person_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.loading_data_id);
        commandThread = new BackgroundCommandThread("MAIN_ACTIVITY");
        commandThread.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_id:
                progressBar.setVisibility(View.VISIBLE);
                readCommand = new ReadDbCommand(storage, commandDone);
                commandThread.launch(readCommand);
                break;
            case R.id.btn_asynctask_id:
                AsyncReadThread task = new AsyncReadThread(this, asyncDone);
                task.execute(storage);
                break;
            case R.id.btn_loader_id:
                Intent intentLoder = new Intent(this, LoaderActivity.class);
                startActivity(intentLoder);
                break;
            case R.id.btn_handlerthread_id:
                Intent intentHandle = new Intent(this, HandleThreadActivity.class);
                startActivity(intentHandle);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commandThread.exit();
    }

    private BaseCommand.CommandDone commandDone = new BaseCommand.CommandDone() {
        @Override
        public void done() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Person> p = readCommand.getData();
                    updateView(p);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
    };

    private AsyncReadThread.AsyncReadListener asyncDone = new AsyncReadThread.AsyncReadListener() {
        @Override
        public void finished(List<Person> p) {
            updateView(p);
        }
    };

    private void updateView(List<Person> p) {
        persons = p;
        adapter.updateData(persons);
    }
}
