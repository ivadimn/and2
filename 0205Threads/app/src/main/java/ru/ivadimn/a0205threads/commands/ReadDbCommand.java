package ru.ivadimn.a0205threads.commands;

import java.util.List;

import ru.ivadimn.a0205threads.model.Person;
import ru.ivadimn.a0205threads.storage.DatabaseStorage;
import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 29.07.2017.
 */

public class ReadDbCommand extends DataCommand<List<Person>> {
    public ReadDbCommand(IDataStore store, CommandDone done) {
        super(store, done);
    }

    @Override
    public void execute() {

    }

    @Override
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                data = store.getList();
                mDone.done();
            }
        };
    }
}
