package ru.ivadimn.a0206reciver.handlers;

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

/**
 * Created by vadim on 02.08.2017.
 */

public class SmsReciveHandler extends HandlerThread {

    private static final int SMS_HANDLE = 1;
    private static final String TAG = "RECIVER_HANDLER";


    private Handler mHandler;
    public SmsReciveHandler(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SMS_HANDLE)
                    smsSave(msg.getData());
            }
        };
    }

    public void smsHandle(Bundle bundle) {
        Message msg = mHandler.obtainMessage(SMS_HANDLE);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void smsSave(Bundle bundle) {
        SmsMessage[] msgs = null;
        StringBuilder sb = new StringBuilder();

        if (bundle != null) {
            TextMessage textMessage = new TextMessage();
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            String format = bundle.getString("format");
            textMessage.setAddress(msgs[0].getOriginatingAddress());
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                else
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                sb.append(msgs[i].getMessageBody());
            }
            App.getInstance().addMessage(textMessage);
            //послать интент
            Log.d(TAG, "Message recived: " + sb.toString());
        }
   }
}
