package ru.ivadimn.a0207alarm.storage;

import java.util.List;

import ru.ivadimn.a0207alarm.model.Event;

/**
 * Created by vadim on 06.08.2017.
 */

public interface IDataStore {
    public List<Event> getList();
    public void saveList();
    public void insert(Event event);
    public void update(int position, Event event);
    public void delete(Event event);
}
