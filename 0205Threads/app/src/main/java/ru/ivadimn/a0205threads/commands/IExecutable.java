package ru.ivadimn.a0205threads.commands;

/**
 * Created by vadim on 29.07.2017.
 */

public interface IExecutable {
    public void execute();
    public Runnable getRunnable();
}
