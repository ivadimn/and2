package ru.ivadimn.a0202storage.interfaces;

/**
 * Created by vadim on 19.07.17.
 */

public interface IOptionsStore {
    public void addOption(String key, String value);
    public String checkOption(String key);
    public void deleteOption(String key);
}
