package ru.ivadimn.a0202storage.storage;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Person;

/**
 * Created by vadim on 20.07.2017.
 */

public class FileStorage implements IDataStore {

    public static final String FILE = "persons2";
    public static final String TAG = "FILE_STORAGE";

    private List<Person> list;
    private Context context;

    public FileStorage(Context context) {
        this.context = context;
    }


    @Override
    public List<Person> getList() {
        if (list == null)
            list = readFromFile();
        return list;
    }

    @Override
    public void saveList() {
        File file = new File(context.getFilesDir(), FILE);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(list);
            out.flush();
            out.close();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Person person) {
        list.add(person);
    }

    @Override
    public void update(int position, Person person) {
        list.set(position, person);
    }

    @Override
    public void delete(Person p) {
        list.remove(p);
    }

    /*private List<Person> generatePersons() {
        List<Person> p = new ArrayList<>();
        p.add(new Person("vadim ivanov", "+79116789054", "aaaa@mail.com"));
        p.add(new Person("peter sidoprov", "+79566789054", "bbb@mail.com"));
        p.add(new Person("vasia petrov", "+79566789054", "bbb@mail.com"));
        p.add(new Person("misha kuznecov", "+79566789054", "bbb@mail.com"));
        return p;
    }*/

    private List<Person> readFromFile() {
        File file = new File(context.getFilesDir(), FILE);
        List<Person> p = new ArrayList<>();

        if (!file.exists()) {
            return p;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            p = (List<Person>) in.readObject();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
