package ru.ivadimn.a0205threads.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim on 20.07.17.
 */

public class Person implements Serializable, Parcelable {

    private long _id;
    private String name;
    private String phone;
    private String email;
    private byte[] photo;

    //вторая версия
    private long birhtday;

    //третья версия
    private List<Friend> friends = new ArrayList<>();
    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public Person() {
        //no-op
    }

    public Person(String name, String phone, String email, long birhtday) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.birhtday = birhtday;
    }

    public Person(Parcel parcel) {
        _id = parcel.readLong();
        name = parcel.readString();
        phone = parcel.readString();
        email = parcel.readString();
        birhtday = parcel.readLong();
        photo = parcel.createByteArray();
    }

    public static long  birthdayFromString(String birth) {
        SimpleDateFormat format = new SimpleDateFormat();
        long d = 0;
        format.applyPattern("dd.mm.yyyy");
        try {
            d = format.parse(birth).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
        for (Friend f : friends) {
            f.setPersonId(_id);
        }
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

    public long getBirhtday() {
        return birhtday;
    }

    public void setBirhtday(long birhtday) {
        this.birhtday = birhtday;
    }

    public void deleteFriend(Friend friend) {
        friends.remove(friend);
    }
    public void deleteFriends(SparseIntArray sparse) {
        List<Friend> tmp = new ArrayList<>(friends);
        for (int i = 0; i < sparse.size(); i++) {
            deleteFriend(tmp.get(sparse.valueAt(i)));
        }
    }

    public void setFriend(int position, Friend friend) {
        friends.set(position, friend);
    }

    public List<Friend> getFriends() {
        return friends;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeLong(birhtday);
        parcel.writeByteArray(photo);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel parcel) {
            return new Person(parcel);
        }

        @Override
        public Person[] newArray(int i) {
            return new Person[0];
        }
    };
}
