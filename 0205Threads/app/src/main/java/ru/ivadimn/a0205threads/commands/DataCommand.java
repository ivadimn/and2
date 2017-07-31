package ru.ivadimn.a0205threads.commands;

import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 29.07.2017.
 */

public abstract class DataCommand<T> extends BaseCommand {

    protected IDataStore store;
    protected T data;

    public DataCommand(IDataStore store, CommandDone done) {
        super(done);
        this.store = store;
    }

    public T getData() {
        return data;
    }

}
