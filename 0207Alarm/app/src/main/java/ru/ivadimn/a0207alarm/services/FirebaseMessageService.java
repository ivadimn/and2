package ru.ivadimn.a0207alarm.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Set;

/**
 * Created by vadim on 07.08.2017.
 */

public class FirebaseMessageService extends FirebaseMessagingService {

    private static final String TAG = "FIREBASE_SERVICE";
    protected static final String MESSAGE = "MESSAGE";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        RemoteMessage.Notification n = remoteMessage.getNotification();
        String body = n.getBody();

        FirebaseMessageHandler fbh = new FirebaseMessageHandler(remoteMessage.getFrom());
        fbh.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fbh.showMessage("Message - " + body);
        Log.d(TAG, "Message - " + body);

    }

    private class FirebaseMessageHandler extends HandlerThread {
        private Handler mHandler;

        public FirebaseMessageHandler(String name) {
            super(name);
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            mHandler = new Handler(getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    Bundle b = msg.getData();
                    String m = b.getString(MESSAGE);
                    Toast.makeText(FirebaseMessageService.this, m, Toast.LENGTH_SHORT).show();
                }
            };
        }

        public void showMessage(String message) {
            Message msg  = mHandler.obtainMessage(1);
            Bundle b = new Bundle();
            b.putString(MESSAGE, message);
            msg.setData(b);
            mHandler.sendMessage(msg);
        }
    }
}
