package ru.ivadimn.a0206reciver;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0206reciver.model.TextMessage;

/**
 * Created by vadim on 02.08.2017.
 */

public class App extends Application {

    private static App instance;
    private List<TextMessage> messages;

    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        messages = new ArrayList<>();
    }

    public void addMessage(TextMessage msg) {
        messages.add(msg);
    }

    public List<TextMessage> getMessages() {
        return messages;
    }
}
