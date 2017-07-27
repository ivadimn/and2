package ru.ivadimn.a0204service.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.ivadimn.a0204service.MessageReciver;
import ru.ivadimn.a0204service.R;

/**
 * Created by vadim on 27.07.2017.
 */

public class FileLoaderService extends Service {

    public static final String TAG = "...FILE_LOADER_SERVICE";
    private static final String PARAME_NAME = "PARAM_NAME";
    private static final String PARAME_SIZE = "PARAM_SIZE";

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
        private NotificationCompat.Builder  notBuilder;

        public FileLoader(int startId, String fileName, int fileSize, OperationListener listener) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.startId = startId;
            this.listener = listener;
            notBuilder = new NotificationCompat.Builder(FileLoaderService.this);
            notBuilder.setSmallIcon(R.drawable.ic_file_download);
            notBuilder.setContentTitle("Загрузка файла: ");
        }
        @Override
        public void run() {
            fileLoad();
        }
        private void fileLoad() {
            notBuilder.setContentText(fileName);
            for (int i = 0; i < fileSize / 5; i++) {
                try {
                    Thread.sleep(ONE_SECOND);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 notBuilder.setProgress(fileSize / 5, i, false);
                startForeground(startId, notBuilder.build());
            }
            Intent intent = MessageReciver.createIntent("File: " + fileName + " was downloded!! Download " + fileSize + " bytes.");
            sendBroadcast(intent);

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
        executor.execute(new FileLoader(1, fileName, fileSize, listener));
    }
}
