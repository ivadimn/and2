package ru.ivadimn.a0206reciver.activities;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.ivadimn.a0206reciver.App;
import ru.ivadimn.a0206reciver.R;
import ru.ivadimn.a0206reciver.Utils;
import ru.ivadimn.a0206reciver.adapters.SmsListAdapter;
import ru.ivadimn.a0206reciver.handlers.FileLoaderService;
import ru.ivadimn.a0206reciver.recivers.LocalFileLoadReciver;
import ru.ivadimn.a0206reciver.recivers.LocalSmsReciver;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "........MAIN_ACTIVITY";
    private RecyclerView recyclerView;
    private SmsListAdapter adapter;
    private LocalSmsReciver smsReciver;
    private IntentFilter intentFilter;

    private LocalFileLoadReciver fileReciver;
    private IntentFilter intentFilterFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.sms_list_id);
        adapter = new SmsListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        smsReciver = new LocalSmsReciver(updateListener);
        intentFilter = new IntentFilter(LocalSmsReciver.ACTION);
        registerReceiver(smsReciver, intentFilter);

        fileReciver = new LocalFileLoadReciver();
        intentFilterFile = new IntentFilter(LocalFileLoadReciver.ACTION);
        registerReceiver(fileReciver, intentFilterFile);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item_file_load_id:
                startService(FileLoaderService.createIntent(this, Utils.getFileName(), Utils.getFileSize()));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        adapter.updateData(App.getInstance().getMessages());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReciver);
        unregisterReceiver(fileReciver);
    }

    private LocalSmsReciver.UpdateDataListener updateListener = new LocalSmsReciver.UpdateDataListener() {
        @Override
        public void onUpdateData() {
            adapter.updateData(App.getInstance().getMessages());
        }
    };
}
