package ru.ivadimn.a0201menu.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import ru.ivadimn.a0201menu.App;
import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.adapters.NoteAdapter;
import ru.ivadimn.a0201menu.model.Note;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnRVClickListener {

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private List<Note> notes;
    private MenuItem itemDelete;
    private NoteAdapter adapter;

    private RecyclerView listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        notes = App.getInstance().readNotes();

        listNotes = (RecyclerView) findViewById(R.id.notes_list_id);
        adapter = new NoteAdapter(this, this);
        adapter.updateData(notes);
        listNotes.setLayoutManager(new LinearLayoutManager(this));
        listNotes.setAdapter(adapter);

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
                addNote();
                break;
            case R.id.menuitem_delete:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD) {
                Note note = data.getParcelableExtra(Note.NOTE);
                notes.add(note);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void addNote() {
        Intent intent = EditActivity.createIntent(this, null);
        startActivityForResult(intent, REQUEST_ADD);
    }

    private void editNote(int position) {
        Intent intent = EditActivity.createIntent(this, notes.get(position));
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getInstance().saveNotes(notes);
    }

    @Override
    public void onClick(View view, int position) {
        editNote(position);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
