package ru.ivadimn.a0202storage.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ru.ivadimn.a0202storage.R;

public class PersonActivity extends AppCompatActivity {

    public static final String TAG = "PERSON";
    public static final String INDEX = "INDEX";

    public static final int REQUEST_PHOTO = 2;

    private ImageView photo;
    private EditText name;
    private EditText phone;
    private EditText email;

    private Bitmap bmpPhoto = null;
    private boolean isEditPhoto = false;
    private int position;



    public static Intent createIntent(int position) {
        Intent intent = new Intent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
    }

    private void initUI(View v) {
        photo = (ImageView) v.findViewById(R.id.person_photo_id);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditPhoto) getPhoto();
            }
        });
        name = (EditText) v.findViewById(R.id.person_name_id);
        phone = (EditText) v.findViewById(R.id.person_phone_id);
        email = (EditText) v.findViewById(R.id.person_email_id);
        photo.setImageResource(R.drawable.person_big);
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
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();;
            }
        }
    }
}
