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

    private EditText edTitle;
    private EditText edText;
    private MenuItem saveItem;

    public static Intent createIntent(Context context, Note note) {
        Intent intent = new Intent(context, EditActivity.class);
        if (note != null)
            intent.putExtra(Note.NOTE, (Parcelable) note);
        return intent;
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
        saveItem = menu.findItem(R.id.menuitem_save);
        saveItem.setVisible(false);
        return true;
    }

    private void initUI() {
        edTitle = (EditText) findViewById(R.id.edit_title_id);
        edTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   saveItem.setVisible(charSequence.length() > 0);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edText = (EditText) findViewById(R.id.edit_text_id);

    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Note note = intent.getParcelableExtra(Note.NOTE);
            edTitle.setText(note.getTitle());
            edText.setText(note.getContent());
        }
    }

    private void save() {
        Note note = new Note(edTitle.getText().toString(), edText.getText().toString());
        Intent data = new Intent();
        data.putExtra(Note.NOTE, (Parcelable) note);
        setResult(RESULT_OK, data);
        finish();
    }


}
