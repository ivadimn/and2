package ru.ivadimn.a0202storage.storage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.ivadimn.a0202storage.interfaces.IDataStore;
import ru.ivadimn.a0202storage.model.Friend;
import ru.ivadimn.a0202storage.model.Person;

/**
 * Created by vadim on 24.07.2017.
 */

public class JsonStorage implements IDataStore {

    public static final String FILE = "json_persons";
    public static final String TAG = "JSON_STORAGE";

    private List<Person> list;
    private Context context;

    public JsonStorage(Context context) {
        this.context = context;
    }

    @Override
    public List<Person> getList() {
        if (list == null)
            list = readFromJSON();
        return list;
    }

    @Override
    public void saveList() {
        File file = new File(context.getFilesDir(), FILE);
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonList = gson.toJson(list.toArray());
        try {
            BufferedWriter  bw = new BufferedWriter(new FileWriter(file));
            bw.write(jsonList);
            bw.flush();
            bw.close();
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

    }

    private List<Person> createPersons() {
        List<Person> plist = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Person p = new Person("Person-" + i, "+790012345" + i, "person" + i + "@mail.ru", 0);
            p.set_id(i);
            p.setFriends(createFriends(i));
            plist.add(p);
        }
        return plist;
    }

    private List<Friend> createFriends(long personId) {
        List<Friend> flist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            flist.add(new Friend(personId, "Friend-" + personId + "-" + i));
        }
        return flist;
    }

    private List<Person> readFromJSON() {
        File file = new File(context.getFilesDir(), FILE);
        List<Person> p = new ArrayList<>();

        if (!file.exists()) {
            return p;
        }
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            while((line = in.readLine()) != null) {
                sb.append(line);
            }
            Gson gson = new GsonBuilder().create();
            Person[] pa = gson.fromJson(sb.toString(), Person[].class);
            p.addAll(Arrays.asList(pa));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
}
