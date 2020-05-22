package ru.ivadimn.a0205threads.threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.Toast;

import java.util.List;

import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 31.07.2017.
 */

public class ReadHandlerThread extends HandlerThread {

    private static final String PERSON = "PERSON";
    private static final int READ = 1;
    private static final int WRITE = 2;
    private Handler mHandler;
    private IDataStore mStore;
    private HandleThreadListener mListrener;

    public interface HandleThreadListener {
        public void onRead(List<Person> list);
        public void onWrite(boolean status);
    }

    public ReadHandlerThread(String name, IDataStore store, HandleThreadListener listener) {
        super(name);
        this.mStore = store;
        this.mListrener = listener;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case READ:
                        if (mListrener != null)
                            mListrener.onRead(mStore.getList());
                         break;
                    case WRITE:
                        mStore.insert((Person) msg.getData().getParcelable(PERSON));
                        if (mListrener != null)
                            mListrener.onWrite(true);
                        break;
                }
            }
        };
    }

    public void read()  {
        Message msg = mHandler.obtainMessage(READ);
        mHandler.sendMessage(msg);
        try {
            mHandler.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void insert(Person person) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PERSON, person);
        Message msg = mHandler.obtainMessage(WRITE);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
}
