package ru.ivadimn.a0201menu.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ru.ivadimn.a0201menu.R;
import ru.ivadimn.a0201menu.model.Note;

public class EditActivity extends AppCompatActivity {

    public static final String NOTE = "NOTE";
    public static final String INDEX = "INDEX";

    private EditText edTitle;
    private EditText edText;
    private int index = -1;

    public static Intent createIntent(Context context, Note note, int position) {
        Intent intent = new Intent(context, EditActivity.class);
        if (note != null) {
            intent.putExtra(INDEX, position);
            intent.putExtra(NOTE, (Parcelable) note);
        }
        return intent;
    }

    public static Note getNote(Intent intent) {
        return intent.getParcelableExtra(NOTE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        initUI();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_save:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
        edTitle = (EditText) findViewById(R.id.edit_title_id);
        edText = (EditText) findViewById(R.id.edit_text_id);

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) return;
        Note note = intent.getParcelableExtra(NOTE);
        if (note != null) {
            edTitle.setText(note.getTitle());
            edText.setText(note.getContent());
        }
        index = intent.getIntExtra(INDEX, -1);

    }

    private void save() {
        Note note = new Note(edTitle.getText().toString(), edText.getText().toString());
        Intent data = new Intent();
        data.putExtra(INDEX, index);
        data.putExtra(NOTE, (Parcelable) note);
        setResult(RESULT_OK, data);
        finish();
    }


}
