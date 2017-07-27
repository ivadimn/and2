package ru.ivadimn.a0204service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by vadim on 27.07.2017.
 */

public class MessageReciver extends BroadcastReceiver {

    public static final String MESSAGE_BROADCAST_ACTION = "ru.ivadimn.a0204service.MessageReciver";
    private static final String PARAM_MESSAGE = "PARAM_MESSGE";

    public static Intent createIntent(String message) {
        Intent intent = new Intent(MESSAGE_BROADCAST_ACTION);
        intent.putExtra(PARAM_MESSAGE, message);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(PARAM_MESSAGE);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
