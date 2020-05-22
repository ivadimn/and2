package ru.ivadimn.a0205threads.model;

import java.io.Serializable;

/**
 * Created by vadim on 23.07.2017.
 */

public class Friend implements Serializable {

    private long _id;
    private long personId;
    private String name;

    public Friend() {
        //no-op
    }

    public Friend(long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
