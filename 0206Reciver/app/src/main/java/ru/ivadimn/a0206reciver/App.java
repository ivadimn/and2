package ru.ivadimn.a0206reciver;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

import ru.ivadimn.a0206reciver.handlers.FileLoaderService;
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
        if (!isConnectedWifi())
            FileLoaderService.suspendLoad();
    }

    public void addMessage(TextMessage msg) {
        messages.add(msg);
    }

    public List<TextMessage> getMessages() {
        return messages;
    }

    public boolean isConnectedWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi.isConnectedOrConnecting();
    }
}
