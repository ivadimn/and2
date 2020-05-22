package ru.ivadimn.a0206reciver.recivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import ru.ivadimn.a0206reciver.R;
import ru.ivadimn.a0206reciver.activities.MainActivity;
import ru.ivadimn.a0206reciver.handlers.SmsReciveHandler;
import ru.ivadimn.a0206reciver.model.TextMessage;

/**
 * Created by vadim on 02.08.2017.
 */

public class LocalSmsReciver extends BroadcastReceiver {

    public static final String ACTION = "LocalSmsReciver.SMS_RECIVED";
    private static final String SMS = "SMS";
    NotificationManager manager;
    private UpdateDataListener listener;
    private static int notifyId = 0;

    public interface UpdateDataListener {
        public void onUpdateData();
    }

    public LocalSmsReciver(UpdateDataListener listener) {
        this.listener = listener;
    }

    public static Intent createIntent(TextMessage msg) {
        Intent intent = new Intent(ACTION);
        intent.putExtra(SMS, msg);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        TextMessage msg = (TextMessage) intent.getParcelableExtra("SMS");
        showNotification(context, msg);
        if (listener != null)
            listener.onUpdateData();
    }

    private void showNotification(Context context, TextMessage msg) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        NotificationCompat.Builder  notBuilder = notBuilder = new NotificationCompat.Builder(context);
        notBuilder.setSmallIcon(R.drawable.ic_sms);
        notBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_face));
        notBuilder.setContentTitle(msg.getAddress());
        notBuilder.setContentText(msg.getBody());
        notBuilder.setContentIntent(PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT));
        notBuilder.setAutoCancel(true);
        notBuilder.setTicker("SMS");
        notBuilder.setDefaults(Notification.DEFAULT_ALL);


        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(++notifyId, notBuilder.build());
    }
}
