package ru.ivadimn.a0201menu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by vadim on 16.07.17.
 */

public class Note implements Serializable, Parcelable {

    private String title;
    private String content;

    public Note() {
        //no-op
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Note(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {

        @Override
        public Note createFromParcel(Parcel parcel) {
            return new Note(parcel);
        }

        @Override
        public Note[] newArray(int i) {
            return new Note[0];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
