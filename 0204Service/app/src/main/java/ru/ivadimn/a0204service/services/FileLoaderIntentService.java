package ru.ivadimn.a0204service.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import ru.ivadimn.a0204service.MessageReciver;
import ru.ivadimn.a0204service.R;

/**
 * Created by vadim on 27.07.2017.
 */

public class FileLoaderIntentService extends IntentService {

    public static final String TAG = "...FILE_LOADER_INTENT_SERVICE";
    private static final String PARAME_NAME = "PARAM_NAME";
    private static final String PARAME_SIZE = "PARAM_SIZE";

    private final int ONE_SECOND = 1000;

    public FileLoaderIntentService() {
        super(TAG);
        setIntentRedelivery(true);
    }

    public static Intent createIntent(Context context, String fileName, int fileSize) {
        Intent intent = new Intent(context, FileLoaderIntentService.class);
        intent.putExtra(PARAME_NAME, fileName);
        intent.putExtra(PARAME_SIZE, fileSize);
        return intent;
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String fileName = intent.getStringExtra(PARAME_NAME);
        int fileSize = intent.getIntExtra(PARAME_SIZE, 0);
        fileLoad(fileName, fileSize);
    }

    private void fileLoad(String fileName, int fileSize) {
        NotificationCompat.Builder  notBuilder = notBuilder = new NotificationCompat.Builder(FileLoaderIntentService.this);
        notBuilder.setSmallIcon(R.drawable.ic_file_download);
        notBuilder.setContentTitle("Загрузка файла: ");
        notBuilder.setContentText(fileName);
        for (int i = 0; i < fileSize / 5; i++) {
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notBuilder.setProgress(fileSize / 5, i, false);
            startForeground(1, notBuilder.build());
        }
        Intent intent = MessageReciver.createIntent("File: " + fileName + " was downloded!! Download " + fileSize + " bytes.");
        sendBroadcast(intent);
    }
}
