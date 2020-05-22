package ru.ivadimn.a0205threads.storage;

import java.util.List;

import ru.ivadimn.a0205threads.model.Person;

/**
 * Created by vadim on 20.07.17.
 */

public interface IDataStore {
    public List<Person> getList();
    public void saveList();
    public void insert(Person person);
    public void update(int position, Person person);
    public void delete(Person p);
}
