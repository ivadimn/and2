package ru.ivadimn.a0202storage.interfaces;

import java.util.List;

import ru.ivadimn.a0202storage.model.Person;

/**
 * Created by vadim on 20.07.17.
 */

public interface IDataStore {
    public List<Person> getList();
    public void saveList(List<Person> list);
    public void add(Person person);
    public void set(int position, Person person);
    public void remove(Person p);
}
