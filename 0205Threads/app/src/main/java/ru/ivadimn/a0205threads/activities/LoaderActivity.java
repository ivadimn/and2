package ru.ivadimn.a0205threads.activities;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import ru.ivadimn.a0205threads.R;
import ru.ivadimn.a0205threads.adapters.PersonAdapter;
import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.IDataStore;
import ru.ivadimn.a0205threads.storage.StorageFactory;
import ru.ivadimn.a0205threads.threads.DataLoader;

public class LoaderActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Person>> {

    private static final int LOADER_ID = 1;

    private RecyclerView recyclerView;
    private List<Person> persons;
    private PersonAdapter adapter;
    private IDataStore store;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        store = StorageFactory.getStorage();
        recyclerView = (RecyclerView) findViewById(R.id.person_list_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(this);
        recyclerView.setAdapter(adapter);
        progressBar = (ProgressBar) findViewById(R.id.loading_data_id);
        progressBar.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);


    }

    @Override
    public Loader<List<Person>> onCreateLoader(int i, Bundle bundle) {

        return new DataLoader(this, store);
    }

    @Override
    public void onLoadFinished(Loader<List<Person>> loader, List<Person> data) {
        this.persons = data;
        adapter.updateData(this.persons);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Person>> loader) {
        persons = null;
        adapter.updateData(persons);
    }
}
