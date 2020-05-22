package ru.ivadimn.a0202storage.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.adapters.FriendAdapter;
import ru.ivadimn.a0202storage.fragments.DatePickerFragment;
import ru.ivadimn.a0202storage.fragments.EditFriendDlg;
import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.interfaces.ItemClickListener;
import ru.ivadimn.a0202storage.model.Friend;
import ru.ivadimn.a0202storage.model.Person;
import ru.ivadimn.a0202storage.storage.StorageFactory;

public class PersonActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener{

    ///////// constants
    public static final String TAG = ".......PERSON_ACTiVITY";
    public static final String INDEX = "INDEX";
    public static final String KEY = "KEY";
    public static final int REQUEST_PHOTO = 2;
    public static final int NEW_RECORD = -1;
/////////Views
    private RecyclerView rvFriends;
    private ImageView photo;
    private EditText name;
    private EditText phone;
    private EditText email;
    private Button btnBirth;
    private MenuItem itemSave;
    private MenuItem itemDel;
    private Menu menu;
    private Bitmap bmpPhoto = null;
    private FriendAdapter adapter;

///////// members
    private int positionPerson = NEW_RECORD;
    private long datePerson;
    private long keyPerson;

    private List<Friend> friends = new ArrayList<>();
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");


   public static Intent createIntent(Context context, int position, long key) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(INDEX, position);
        intent.putExtra(KEY, key);
        Log.d(TAG, "createIntent - " + position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();
        positionPerson = intent.getIntExtra(INDEX, NEW_RECORD);
        keyPerson = intent.getLongExtra(KEY, NEW_RECORD);
        setTitle("Edit person");
        initUI();
        initData();
        adapter = new FriendAdapter(listener);
        rvFriends.setAdapter(adapter);
        adapter.updateData(friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        this.menu = menu;
        itemSave = menu.findItem(R.id.menuitem_save_id);
        itemDel = menu.findItem(R.id.menuitem_deletefriend_id);
        itemDel.setVisible(false);
        if (positionPerson == NEW_RECORD) itemSave.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuitem_save_id:
                savePerson();
                setResult(RESULT_OK, null);
                finish();
                return true;
            case R.id.menuitem_add_id :
                editFriend(NEW_RECORD);
                return true;
            case R.id.menuitem_deletefriend_id:
                deleteFriends(adapter.getSelectedList());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (adapter.isSelectedMode()) {
            adapter.setSelectedMode(false);
            adapter.getSelectedList().clear();
            adapter.notifyDataSetChanged();
            setUINormalMode();
        }
        else
            super.onBackPressed();
    }

    private void initUI() {
        rvFriends = (RecyclerView) findViewById(R.id.friend_list_id);
        rvFriends.setLayoutManager(new LinearLayoutManager(this));
        photo = (ImageView) findViewById(R.id.person_photo_id);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhoto();
            }
        });
        name = (EditText) findViewById(R.id.person_name_id);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (itemSave != null)
                    itemSave.setVisible(charSequence.length() > 0);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        phone = (EditText) findViewById(R.id.person_phone_id);
        email = (EditText) findViewById(R.id.person_email_id);
        btnBirth = (Button) findViewById(R.id.person_birth_id);
        btnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBirthday();
            }
        });
        photo.setImageResource(R.drawable.person_big);
    }

    private void initData() {
        if (positionPerson == NEW_RECORD) return;
        Person p = StorageFactory.getStorage().getList().get(positionPerson);
        friends = p.getFriends();
        datePerson = p.getBirhtday();
        byte[] b = p.getPhoto();
        if (b != null) {
            bmpPhoto = BitmapFactory.decodeByteArray(b, 0, b.length);
            photo.setImageBitmap(bmpPhoto);
        }
        else {
            photo.setImageResource(R.drawable.person_big);
        }
        name.setText(p.getName());
        phone.setText(p.getPhone());
        email.setText(p.getEmail());
        btnBirth.setText(getString(R.string.person_birthday) + ": " + format.format(datePerson));
    }


    private void getPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_PHOTO) {
            Uri uri = data.getData();
            try {
                bmpPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                photo.setImageBitmap(bmpPhoto);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void savePerson() {
        Person p = getData();
        p.setFriends(friends);
        IDataStore store = StorageFactory.getStorage();
        if (positionPerson == NEW_RECORD)
            store.insert(p);
        else
            store.update(positionPerson, p);
    }

    private Person getData() {
        Person p = new Person(name.getText().toString(), phone.getText().toString(),
                email.getText().toString(), datePerson);
        p.set_id(keyPerson);
        if (bmpPhoto != null) {
            p.setBmpPhoto(bmpPhoto);
        }
        return p;
    }

    private void setBirthday() {
        DatePickerFragment fragment = DatePickerFragment.createFragment(datePerson);
        fragment.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onDateSelect(long d) {
        datePerson = d;
        btnBirth.setText(getString(R.string.person_birthday) + ": " +format.format(datePerson));
    }



    private final ItemClickListener listener = new ItemClickListener() {
        @Override
        public void onClick(View view, int pos) {
            //pfgecnbnm диалог редактированяи
            editFriend(pos);
        }

        @Override
        public void onLongClick(View view, int position) {
            startSelectedMode();
        }
    };

    //////// редактирование друзей
    private final EditFriendDlg.DialogListener dlgListener = new EditFriendDlg.DialogListener() {
        @Override
        public void onOkClick(int pos, String name) {
            if (pos == NEW_RECORD)
                friends.add(new Friend(keyPerson, name));
            else
                friends.get(pos).setName(name);
            adapter.notifyDataSetChanged();
        }

    };
    private void editFriend(int pos) {
        EditFriendDlg dlg;
        if (pos == -1)
            dlg = EditFriendDlg.createDialog(NEW_RECORD, null, dlgListener);
        else
            dlg = EditFriendDlg.createDialog(pos, friends.get(pos).getName(), dlgListener);
        dlg.show(getSupportFragmentManager(), TAG);
    }
/////////////////////////

    private void startSelectedMode() {
        setUISelectedMode();
        adapter.notifyDataSetChanged();
    }

    private void deleteFriends(SparseIntArray sparse) {
        List<Friend> tmp = new ArrayList<>(friends);
        for (int i = 0; i < sparse.size(); i++) {
           friends.remove(tmp.get(sparse.valueAt(i)));
        }
        sparse.clear();
        adapter.setSelectedMode(false);
        setUINormalMode();
        adapter.notifyDataSetChanged();
    }

    //обновляем UI в зависимости от режима
    private void setUINormalMode() {
        photo.setEnabled(true);
        name.setEnabled(true);
        phone.setEnabled(true);
        email.setEnabled(true);
        btnBirth.setEnabled(true);
        menu.setGroupVisible(R.id.group_save, true);
        itemDel.setVisible(false);
    }

    private void setUISelectedMode() {
        photo.setEnabled(false);
        name.setEnabled(false);
        phone.setEnabled(false);
        email.setEnabled(false);
        btnBirth.setEnabled(false);
        menu.setGroupVisible(R.id.group_save, false);
        itemDel.setVisible(true);
    }
}
