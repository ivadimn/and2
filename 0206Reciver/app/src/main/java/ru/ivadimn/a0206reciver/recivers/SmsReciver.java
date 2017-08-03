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
    private SmsReciveHandler smsHandler;

    public SmsReciver() {
        super();
        smsHandler = new SmsReciveHandler(ACTION);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        StringBuilder sb = new StringBuilder();
        if (bundle != null) {
            smsHandler.smsHandle(bundle);
        }
    }
}
