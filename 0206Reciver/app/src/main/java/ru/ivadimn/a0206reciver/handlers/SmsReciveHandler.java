package ru.ivadimn.a0206reciver.handlers;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import ru.ivadimn.a0206reciver.App;
import ru.ivadimn.a0206reciver.model.TextMessage;
import ru.ivadimn.a0206reciver.recivers.LocalSmsReciver;

import static android.R.attr.name;

/**
 * Created by vadim on 02.08.2017.
 */

public class SmsReciveHandler extends Thread {

    private static final int SMS_HANDLE = 1;
    private static final String TAG = "RECIVER_HANDLER";
    private Context mContext;
    private Bundle mBundle;

    public SmsReciveHandler(Context context, Bundle bundle) {
        this.mContext = context;
        this.mBundle = bundle;
        Log.d(TAG, "SmsREciverHandler created");
    }

    @Override
    public void run() {
        Log.d(TAG, "SmsREciverHandler running ");
        smsSave();
    }

    private void smsSave() {
        SmsMessage[] msgs = null;
        StringBuilder sb = new StringBuilder();

        if (mBundle != null) {
            TextMessage textMessage = new TextMessage();
            Object[] pdus = (Object[]) mBundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            String format = mBundle.getString("format");
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                else
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                if (i == 0)
                    textMessage.setAddress(msgs[i].getOriginatingAddress());

                sb.append(msgs[i].getMessageBody());
            }
            textMessage.setBody(sb.toString());
            App.getInstance().addMessage(textMessage);
            mContext.sendBroadcast(LocalSmsReciver.createIntent(textMessage));
            Log.d(TAG, "Message recived: " + sb.toString());
        }
   }
}
