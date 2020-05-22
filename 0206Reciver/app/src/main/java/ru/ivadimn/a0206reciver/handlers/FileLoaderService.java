package ru.ivadimn.a0206reciver.handlers;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import ru.ivadimn.a0206reciver.R;

/**
 * Created by vadim on 04.08.2017.
 */

public class FileLoaderService extends Service {

    public static final String TAG = "...FILE_LOADER_SERVICE";
    private static final String PARAME_NAME = "PARAM_NAME";
    private static final String PARAME_SIZE = "PARAM_SIZE";

    private static final Object mutex = new Object();
    private static boolean isWifi = true;

    private final int ONE_SECOND = 1000;
    private ExecutorService executor;

    public interface OperationListener {
        public void onOperationDone();
    }



    public static Intent createIntent(Context context, String fileName, int fileSize) {
        Intent intent = new Intent(context, FileLoaderService.class);
        intent.putExtra(PARAME_NAME, fileName);
        intent.putExtra(PARAME_SIZE, fileSize);
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
        return new BinderService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileName = intent.getStringExtra(PARAME_NAME);
        int fileSize = intent.getIntExtra(PARAME_SIZE, 0);
        executor.execute(new FileLoader(startId, fileName, fileSize, null));
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executor.shutdown();
        Log.d(TAG, "Service was destroyed");
    }

    class FileLoader implements Runnable {

        private String fileName;
        private int fileSize;
        private int startId;
        private OperationListener listener;
        private NotificationCompat.Builder  builder;

        public FileLoader(int startId, String fileName, int fileSize, OperationListener listener) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.startId = startId;
            this.listener = listener;
            builder = new NotificationCompat.Builder(FileLoaderService.this);
            builder.setSmallIcon(R.drawable.ic_file_load);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_cloud));
            builder.setContentTitle("Load file ... ");
        }
        @Override
        public void run() {
            fileLoad();
        }
        private synchronized void fileLoad() {
            builder.setContentText(fileName);
            for (int i = 0; i < fileSize / 5; i++) {
                try {
                    Thread.sleep(ONE_SECOND);
                    if (!isWifiEnabled()) {
                        synchronized (mutex) {
                            mutex.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                builder.setProgress(fileSize / 5, i, false);
                startForeground(startId, builder.build());
            }


            if (listener != null)
                listener.onOperationDone();
            else
                stopSelf(startId);
            Log.d(TAG, "Поток:" + Thread.currentThread().getName() + " was finished");
        }
    }

    public class BinderService extends Binder {
        public FileLoaderService getService() {
            return FileLoaderService.this;
        }
    }

    public void startFileLoad(String fileName, int fileSize, OperationListener listener) {
        executor.execute(new FileLoader(fileName.hashCode(), fileName, fileSize, listener));
    }

    public static synchronized void suspendLoad() {
        isWifi = false;
        Log.d(TAG, "File loader was LOCK");
    }

    public static synchronized void resumeLoad() {
        isWifi = true;
        synchronized (mutex) {
            mutex.notifyAll();
        }
        Log.d(TAG, "File loader was UNLOCK");
    }

    public static synchronized boolean isWifiEnabled() {
        return isWifi;
    }

    public static synchronized void setWifiEnabled(boolean isWifiEnabled) {
        FileLoaderService.isWifi = isWifiEnabled;
    }
}
