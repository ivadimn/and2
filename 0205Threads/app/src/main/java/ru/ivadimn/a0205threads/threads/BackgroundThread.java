package ru.ivadimn.a0205threads.threads;

import android.os.Handler;

import ru.ivadimn.a0205threads.commands.IExecutable;

/**
 * Created by vadim on 29.07.2017.
 */

public abstract class BackgroundThread extends Thread {
    protected Handler mHandler;

    public BackgroundThread(String tag) {
        super(tag);
    }
    public void exit() {
        mHandler.getLooper().quit();
    }

    public abstract void launch(IExecutable ex);


}
