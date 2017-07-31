package ru.ivadimn.a0205threads.threads;

import android.os.Handler;
import android.os.Looper;

import ru.ivadimn.a0205threads.commands.IExecutable;

/**
 * Created by vadim on 29.07.2017.
 */

public class BackgroundCommandThread extends BackgroundThread {

    public BackgroundCommandThread(String tag) {
        super(tag);
    }

    @Override
    public void run() {
        Looper.prepare();
        mHandler = new Handler();
        Looper.loop();
    }

    @Override
    public void launch(IExecutable ex) {
        mHandler.post(ex.getRunnable());
    }
}
