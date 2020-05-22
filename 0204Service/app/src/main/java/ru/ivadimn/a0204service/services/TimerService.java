package ru.ivadimn.a0204service.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vadim on 26.07.2017.
 */

public class TimerService extends Service {

    private ExecutorService mExecutor;
    private Runnable mTick;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutor = Executors.newSingleThreadExecutor();
        mTick = new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
