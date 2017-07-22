package ru.ivadimn.a0202storage.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ru.ivadimn.a0202storage.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vadim on 22.07.2017.
 * Это сделано для понимания как работает
 */

public class PictureFragment extends Fragment {

    public static final String TAG = ".......PICTURE_FRAGMENT";
    public static final int  PHOTO_FILE = 1;
    public static final int  PHOTO_CAMERA = 2;

    private ImageView image;
    private Button btnCamera;
    private Button btnLoad;
    private Button btnSave;
    private EditText edFile;
    private Bitmap bmpPhoto = null;

    public static Fragment createFragment() {
        Fragment fragment = new PictureFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        btnCamera = (Button) view.findViewById(R.id.btn_camera_id);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFromCamera();
            }
        });
        btnLoad = (Button) view.findViewById(R.id.btn_load_id);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFRomFile();
            }
        });
        btnSave = (Button) view.findViewById(R.id.btn_save_id);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePhoto();
            }
        });
        edFile = (EditText) view.findViewById(R.id.file_name_id);
        image = (ImageView) view.findViewById(R.id.image_id);
    }

    private void loadFRomFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_FILE);
    }

    private void loadFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PHOTO_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PHOTO_FILE) {
            Uri uri = data.getData();
            try {
                bmpPhoto = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                image.setImageBitmap(bmpPhoto);
            } catch (IOException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (resultCode == RESULT_OK && requestCode == PHOTO_CAMERA) {
            Bundle bundle = data.getExtras();
            bmpPhoto = (Bitmap) bundle.get("data");
            image.setImageBitmap(bmpPhoto);
        }
    }

    private void savePhoto()  {
        String fname = edFile.getText().toString();
        if (fname.isEmpty()) return;
        try {

            FileOutputStream fs = new FileOutputStream(new File(getDirectory().getPath() + "/" + fname + ".png"));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bmpPhoto.compress(Bitmap.CompressFormat.PNG, 50, out);
            byte[] b = out.toByteArray();
            fs.write(b);
            fs.close();
            out.close();
        }
        catch (IOException e) {
            Log.d(TAG, "Error write file: " + e.getMessage());
            Toast.makeText(getContext(), "Error write file: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }

    private File getDirectory() {
        boolean hasdir = false;
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "myphoto");
        if (!dir.exists()) {
            hasdir = dir.mkdir();
        }
        return dir;
    }
}
