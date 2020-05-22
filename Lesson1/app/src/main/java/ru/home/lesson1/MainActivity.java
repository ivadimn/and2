package ru.home.lesson1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ListAdapter.ListActionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter adapter = new ListAdapter(this);
        list.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Toast.makeText(this, "Опа", Toast.LENGTH_SHORT).show();
                return true;
            default: {
                return false;
            }
        }
    }

    @Override
    public void deleteItem() {
        Toast.makeText(this, "Удалить", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addItem() {
        Toast.makeText(this, "Добавить", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showActionMode(final ActionMode.Callback callback) {
        startSupportActionMode(callback);
    }
}
