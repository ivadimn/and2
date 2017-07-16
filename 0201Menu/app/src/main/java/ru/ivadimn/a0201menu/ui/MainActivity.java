package ru.ivadimn.a0201menu.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import ru.ivadimn.a0201menu.App;
import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.model.Note;

public class MainActivity extends AppCompatActivity {

    private List<Note> notes;
    private MenuItem itemDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        notes = App.getInstance().readNotes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        itemDelete = menu.findItem(R.id.menuitem_delete);
        //itemDelete.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_add:
                break;
            case R.id.menuitem_delete:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


}
