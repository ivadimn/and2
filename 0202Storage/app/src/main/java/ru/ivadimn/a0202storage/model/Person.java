package ru.ivadimn.a0202storage.model;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * Created by vadim on 20.07.17.
 */

public class Person implements Serializable {

    private int _id;
    private String name;
    private String phone;
    private String email;
    private byte[] photo;
    //private Bitmap photo;

    public Person() {
        //no-op
    }

    public Person(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setBmpPhoto(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);
            photo = out.toByteArray();
        }
    }
}
