package ru.ivadimn.a0206reciver.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vadim on 02.08.2017.
 */

public class TextMessage implements Parcelable {

    private String address;
    private String body;

    public TextMessage() {
        //no op
    }

    public TextMessage(String address, String body) {
        this.address = address;
        this.body = body;
    }

    protected TextMessage(Parcel in) {
        address = in.readString();
        body = in.readString();
    }

    public static final Creator<TextMessage> CREATOR = new Creator<TextMessage>() {
        @Override
        public TextMessage createFromParcel(Parcel in) {
            return new TextMessage(in);
        }

        @Override
        public TextMessage[] newArray(int size) {
            return new TextMessage[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(body);
    }
}
