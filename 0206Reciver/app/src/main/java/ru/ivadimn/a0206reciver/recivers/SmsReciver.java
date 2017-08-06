package ru.ivadimn.a0206reciver.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import ru.ivadimn.a0206reciver.handlers.SmsReciveHandler;

/**
 * Created by vadim on 02.08.2017.
 */

public class SmsReciver extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            new SmsReciveHandler(context, bundle).start();
        }
    }


}
