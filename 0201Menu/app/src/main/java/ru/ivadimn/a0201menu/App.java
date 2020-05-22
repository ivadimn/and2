package ru.ivadimn.a0201menu;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0201menu.model.Note;

/**
 * Created by vadim on 16.07.17.
 */

public class App extends Application {
    public static final String TAG = "APPLICATION";
    public static final String NOTES_FILE = "notes.txt";

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static App getInstance() {
        return instance;
    }

    public List<Note> readNotes() {
        List<Note>  noteList = new ArrayList<>();
        File file = new File(getFilesDir(), NOTES_FILE);
        if (file.exists()) {

            try {
                ObjectInputStream inObj = new ObjectInputStream(new FileInputStream(file));
                Note[] notes = (Note[]) inObj.readObject();
                for (Note n : notes) {
                    noteList.add(n);
                }
                inObj.close();
            } catch (IOException e) {
                Log.d("READ_NOTES", e.getMessage());
            } catch (ClassNotFoundException e) {
                Log.d("READ_NOTES", e.getMessage());
            }
        }
        return noteList;
    }

    public void saveNotes(List<Note> noteList) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(getFilesDir(), NOTES_FILE), false);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            Note[] notes = new Note[noteList.size()];
            for (int i = 0; i < noteList.size(); i++) {
                notes[i] = noteList.get(i);
            }
            objOut.writeObject(notes);
            objOut.close();
        }
        catch (FileNotFoundException ex) {
            Log.d("SAVE_NOTES", ex.getMessage());
        }
        catch(IOException ex) {
            Log.d("SAVE_NOTES", ex.getMessage());
        }
    }
}
