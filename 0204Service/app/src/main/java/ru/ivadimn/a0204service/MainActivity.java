package ru.ivadimn.a0204service;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.ivadimn.a0204service.services.FileLoaderIntentService;
import ru.ivadimn.a0204service.services.FileLoaderService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String ACTIVITY_RECREATE = "ACTIVITY_RECREATE";
    private TextView value;
    private MessageReciver reciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
            Toast.makeText(this, savedInstanceState.getString(ACTIVITY_RECREATE), Toast.LENGTH_SHORT).show();

        String lastState = (String) getLastCustomNonConfigurationInstance();
        if (lastState != null)
            Toast.makeText(this, lastState, Toast.LENGTH_SHORT).show();

        value = (TextView) findViewById(R.id.value_id);
        findViewById(R.id.btn_start_id).setOnClickListener(this);
        findViewById(R.id.btn_bind_id).setOnClickListener(this);
        findViewById(R.id.btn_start_intent_id).setOnClickListener(this);
        reciver = new MessageReciver();
        IntentFilter intentFilter = new IntentFilter(MessageReciver.MESSAGE_BROADCAST_ACTION);
        registerReceiver(reciver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_id:
                startService(FileLoaderService.createIntent(this, Utils.getFileName(), Utils.getFileSize()));
                break;
            case R.id.btn_bind_id:
                bindService(FileLoaderService.createIntent(this, "", 0), serviceConnection,
                        Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_start_intent_id:
                for (int i = 0; i < 10; i++) {
                    startService(FileLoaderIntentService.createIntent(this,
                            String.valueOf(i) + "-" + Utils.getFileName(), Utils.getFileSize()));
                }

                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(reciver);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            FileLoaderService loaderService = ((FileLoaderService.BinderService) iBinder).getService();
            loaderService.startFileLoad(Utils.getFileName(), Utils.getFileSize(),
                    operationListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private FileLoaderService.OperationListener operationListener = new FileLoaderService.OperationListener() {
        @Override
        public void onOperationDone() {
            unbindService(serviceConnection);
        }
    };

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return "onRetainCustomNonConfigurationInstance";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ACTIVITY_RECREATE, "Активити пересоздалась");
    }
}
