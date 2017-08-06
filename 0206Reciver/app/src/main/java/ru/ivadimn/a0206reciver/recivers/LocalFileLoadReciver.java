package ru.ivadimn.a0206reciver.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.ivadimn.a0206reciver.handlers.FileLoaderService;

/**
 * Created by vadim on 04.08.2017.
 */

public class LocalFileLoadReciver extends BroadcastReceiver {

    public static final String ACTION = "ru.ivadimn.recivers.LocalFileLoadReciver";
    private static final String STATE_LOAD = "STATE_LOAD";


    public static Intent createIntent(boolean loadEnabled) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(STATE_LOAD, loadEnabled);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean loadEnabled = intent.getBooleanExtra(STATE_LOAD, false);
        if (loadEnabled) {
            if (!FileLoaderService.isWifiEnabled()) {
                FileLoaderService.resumeLoad();
            }
        }
        else {
            if (FileLoaderService.isWifiEnabled()) {
                FileLoaderService.suspendLoad();
            }
        }
    }
}
