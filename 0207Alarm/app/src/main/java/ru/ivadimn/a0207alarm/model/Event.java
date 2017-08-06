package ru.ivadimn.a0207alarm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by vadim on 06.08.2017.
 */

public class Event implements Parcelable, Serializable {

    public static final String ID = "ID";
    public static final String EVENT = "EVENT";
    public static final String TITLE = "TITLE";
    public static final String MOMENT = "MOMENT";

    private long _id;
    private String title;
    private long moment;
    private long repeat;

    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public Event() {
        //no-op
        moment = Calendar.getInstance().getTimeInMillis();
    }

    public Event(String title, long moment, long repeat) {
        this.title = title;
        this.moment = moment;
        this.repeat = repeat;
    }

    public Event(long _id, String title, long moment, long repeat) {
        this._id = _id;
        this.title = title;
        this.moment = moment;
        this.repeat = repeat;
    }

    protected Event(Parcel in) {
        _id = in.readLong();
        title = in.readString();
        moment = in.readLong();
        repeat = in.readLong();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getMomentString() {
        return format.format(moment);
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMoment() {
        return moment;
    }

    public void setMoment(long moment) {
        this.moment = moment;
    }

    public long getRepeat() {
        return repeat;
    }

    public void setRepeat(long repeat) {
        this.repeat = repeat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(_id);
        parcel.writeString(title);
        parcel.writeLong(moment);
        parcel.writeLong(repeat);
    }
}
