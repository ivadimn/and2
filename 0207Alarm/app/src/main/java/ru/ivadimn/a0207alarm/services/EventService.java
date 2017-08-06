package ru.ivadimn.a0207alarm.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.ivadimn.a0207alarm.R;
import ru.ivadimn.a0207alarm.activities.EventActivity;
import ru.ivadimn.a0207alarm.model.Event;

/**
 * Created by vadim on 06.08.2017.
 */

public class EventService extends Service {

   private ExecutorService executor;

    public static Intent createIntent(Context context, Event event) {
        Intent intent = new Intent(context, EventService.class);
        if (event != null) {
            intent.putExtra(Event.ID, event.get_id());
            intent.putExtra(Event.TITLE, event.getTitle());
            intent.putExtra(Event.MOMENT, event.getMoment());
        }
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(5);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Event event = new Event(intent.getLongExtra(Event.ID, 0),
                intent.getStringExtra(Event.TITLE),
                intent.getLongExtra(Event.MOMENT, 0), 0);
        executor.execute(new InvokeEvent(event));
        return START_REDELIVER_INTENT;
    }

    private class InvokeEvent implements Runnable {
        private NotificationCompat.Builder  notBuilder;
        private Event event;
        public InvokeEvent(Event event) {
            this.event = event;
            notBuilder = new NotificationCompat.Builder(getBaseContext());
            notBuilder.setContentTitle("Событие наступило...");
        }
        @Override
        public void run() {
            showNotification();
        }
        private void showNotification() {
            Intent intent = EventActivity.createIntent(getBaseContext(), event);
            notBuilder.setContentText(event.getTitle());
            notBuilder.setSmallIcon(R.drawable.ic_event);
            notBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_event_run));
            notBuilder.setContentIntent(PendingIntent.getActivity(getBaseContext(),
                    EventActivity.VIEW_EVENT, intent, PendingIntent.FLAG_CANCEL_CURRENT));
            notBuilder.setAutoCancel(true);
            notBuilder.setDefaults(Notification.DEFAULT_ALL);
            NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify((int)event.get_id(), notBuilder.build());
        }
    }
}
