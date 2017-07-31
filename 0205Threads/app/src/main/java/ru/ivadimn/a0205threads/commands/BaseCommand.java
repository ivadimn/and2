package ru.ivadimn.a0205threads.commands;

import android.provider.BaseColumns;

import ru.ivadimn.a0205threads.storage.IDataStore;

/**
 * Created by vadim on 29.07.2017.
 */

public abstract class BaseCommand implements IExecutable {


    protected CommandDone mDone;

    public interface CommandDone {
        public void done();
    }

    public BaseCommand(CommandDone done) {
         this.mDone = done;
    }

}
