package ru.ivadimn.a0201menu.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0201menu.App;
import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.adapters.NoteAdapter;
import ru.ivadimn.a0201menu.model.Note;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnRVClickListener,
        EditDialog.DialogListener {
    public static final String TAG = "MAIN_ACTIVITY";

    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    private List<Note> notes;

    private NoteAdapter adapter;
    private MenuItem itemEdit;

    private RecyclerView listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        notes = App.getInstance().readNotes();

        listNotes = (RecyclerView) findViewById(R.id.notes_list_id);
        adapter = new NoteAdapter(this);
        adapter.setListener(this);
        adapter.updateData(notes);
        listNotes.setLayoutManager(new LinearLayoutManager(this));
        listNotes.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_add:
                addNote();
                //или можно так
                //addNoteDlg();
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
                Note note = EditActivity.getNote(data);
                notes.add(note);
                adapter.notifyDataSetChanged();
            }
            else if (requestCode == REQUEST_EDIT) {
                int position = data.getIntExtra(EditActivity.INDEX, -1);
                Note note = EditActivity.getNote(data);
                if (position > -1) {
                    notes.set(position, note);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void addNote() {
        Intent intent = EditActivity.createIntent(this, null, -1);
        startActivityForResult(intent, REQUEST_ADD);
    }

    private void addNoteDlg() {
        EditDialog dlg = EditDialog.createDailog(-1, null, this);
        dlg.show(getSupportFragmentManager(), TAG);
    }

    private void editNote(int position) {
        Intent intent = EditActivity.createIntent(this, notes.get(position), position);
        startActivityForResult(intent, REQUEST_EDIT);
    }

    private void editNoteDlg(int position) {
        EditDialog dlg = EditDialog.createDailog(position, notes.get(position), this);
        dlg.show(getSupportFragmentManager(), TAG);
    }

    private void deleteNotes(SparseIntArray sparse) {
        List<Note> tmp = new ArrayList<>(notes);
        for (int i = 0; i < sparse.size(); i++) {
            notes.remove(tmp.get(sparse.valueAt(i)));
        }
        sparse.clear();
    }

    @Override
    protected void onStop() {
        super.onStop();
        App.getInstance().saveNotes(notes);
    }

    @Override
    public void onClick(View view, int position) {
        if (adapter.isActionMode()) {
            itemEdit.setVisible(adapter.getSelectedList().size() == 1);
        }
        else
            popup(view, position);
            //или можно
           // editNote(position);
    }

    @Override
    public void onLongClick(View view, int position) {
        startSupportActionMode(acCallback);
    }

    public ActionMode.Callback acCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.action_menu, menu);
            itemEdit = menu.findItem(R.id.menuitem_edit);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()) {
                case R.id.menuitem_edit:
                    editNote(adapter.getSelectedList().keyAt(0));
                    //или можно так
                    //editNoteDlg(adapter.getSelectedList().keyAt(0));
                    break;
                case R.id.menuitem_delete:
                    deleteNotes(adapter.getSelectedList());
                    adapter.notifyDataSetChanged();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.setActionMode(false);
            adapter.getSelectedList().clear();
            adapter.notifyDataSetChanged();
        }
    };

    private void popup(View view, final int position) {
        PopupMenu pMenu = new PopupMenu(this, view);
        pMenu.inflate(R.menu.action_menu);
        pMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.menuitem_edit:
                        editNote(position);
                        //или можно так
                        //editNoteDlg(position);
                        break;
                    case R.id.menuitem_delete:
                        notes.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
        pMenu.show();
    }

    @Override
    public void onOkClick(int position, Note note) {
        if (position == -1)
            notes.add(note);
        else
            notes.set(position, note);

        adapter.notifyDataSetChanged();
    }
}
