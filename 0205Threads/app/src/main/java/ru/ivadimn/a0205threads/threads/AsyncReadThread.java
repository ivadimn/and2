package ru.ivadimn.a0205threads.threads;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import ru.ivadimn.a0205threads.ProgressDlg;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 30.07.2017.
 */

public class AsyncReadThread extends AsyncTask<IDataStore,  Void, List<Person>> {

    private static final String TAG = "AsyncReadThread";
    private WeakReference<AsyncReadListener> listener;
    private AppCompatActivity activity;
    private ProgressDlg progressDlg;

    public AsyncReadThread(AppCompatActivity activity, AsyncReadListener listener) {
        this.listener = new WeakReference<AsyncReadListener>(listener);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressDlg = ProgressDlg.createDialog("Загрузка данных ...");
        progressDlg.show(activity.getSupportFragmentManager(), TAG);
    }

    @Override
    protected List<Person> doInBackground(IDataStore... iDataStores) {
        List<Person> p = iDataStores[0].getList();
        return p;
    }

    @Override
    protected void onPostExecute(List<Person> persons) {
        if (listener.get() != null)
            listener.get().finished(persons);
        progressDlg.dismiss();
    }

    public interface AsyncReadListener {
        public void finished(List<Person> p);
    }
}
