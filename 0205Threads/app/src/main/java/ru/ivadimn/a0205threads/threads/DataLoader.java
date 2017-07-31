package ru.ivadimn.a0205threads.threads;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 30.07.2017.
 */

public class DataLoader extends AsyncTaskLoader<List<Person>> {

    private static final String TAG = "......DataLoader";

    private List<Person> persons;
    private IDataStore store;

    public DataLoader(Context context, IDataStore store) {
        super(context);
        this.store = store;

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartingLoading");
        if (persons != null)
            deliverResult(persons);
        if (persons == null || takeContentChanged())
            forceLoad();
    }

    @Override
    public List<Person> loadInBackground() {
        persons = store.getList();
        Log.d(TAG, "loadInBackground");
        return persons;
    }

    @Override
    public void forceLoad() {
        Log.d(TAG, "forceLoad");
        super.forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.d(TAG, "onStopLoading");
    }

   @Override
    public void deliverResult(List<Person> data) {
       Log.d(TAG, "deliverResult");
       if (isReset()) {
           return;
       }
       persons = data;
       if (isStarted())
        super.deliverResult(data);
    }
}
