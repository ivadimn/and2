package ru.ivadimn.a0202storage.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ru.ivadimn.a0202storage.App;
import ru.ivadimn.a0202storage.R;
import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Person;

public class PersonActivity extends AppCompatActivity {

    public static final String TAG = "PERSON";
    public static final String INDEX = "INDEX";

    public static final int REQUEST_PHOTO = 2;

    private ImageView photo;
    private EditText name;
    private EditText phone;
    private EditText email;
    private MenuItem itemSave;

    private Bitmap bmpPhoto = null;
    private int position = -1;



    public static Intent createIntent(Context context, int position) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(INDEX, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Intent intent = getIntent();
        position = intent.getIntExtra(INDEX, -1);
        setTitle("Edit person");
        initUI();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person, menu);
        itemSave = menu.findItem(R.id.menuitem_save_id);
        if (position == -1) itemSave.setVisible(false);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
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
        photo.setImageResource(R.drawable.person_big);
    }

    private void initData() {
        if (position == -1) return;
        Person p = App.getInstance().getStore(App.FILE_STORAGE).getList().get(position);
        byte[] b = p.getPhoto();
        if (b != null) {
            photo.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
        }
        else {
            photo.setImageResource(R.drawable.person_big);
        }
        name.setText(p.getName());
        phone.setText(p.getPhone());
        email.setText(p.getEmail());
    }


    private void getPhoto() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, )
        //startActivityForResult(intent, REQUEST_PHOTO);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_PHOTO) {
            //Bundle bundle = data.getExtras();
            Uri uri = data.getData();
            try {
                bmpPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                //bmpPhoto = (Bitmap) bundle.get("data");
                photo.setImageBitmap(bmpPhoto);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePerson() {
        Person p = getData();
        IDataStore store = App.getInstance().getStore(App.FILE_STORAGE);
        if (position == -1)
            store.insert(p);
        else
            store.update(position, p);
    }

    private Person getData() {
        Person p = new Person(name.getText().toString(), phone.getText().toString(),
                email.getText().toString());
        if (bmpPhoto != null) {
            p.setBmpPhoto(bmpPhoto);
        }
        return p;
    }
}
