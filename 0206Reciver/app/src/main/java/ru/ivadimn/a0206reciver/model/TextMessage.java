package ru.ivadimn.a0206reciver.model;

/**
 * Created by vadim on 02.08.2017.
 */

public class TextMessage {
    private String address;
    private String body;

    public TextMessage() {
        //no op
    }

    public TextMessage(String address, String body) {
        this.address = address;
        this.body = body;
    }

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
}
