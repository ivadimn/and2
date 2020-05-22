package ru.ivadimn.health.database;

/**
 * Created by vadim on 25.12.16.
 */

public class Blob {
    private byte[] bytes;
    private int size;
    public Blob(int size) {
        this.size = size;
        bytes = new byte[size];
    }
    public Blob(byte[] bytes) {
        this.size = bytes.length;
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
