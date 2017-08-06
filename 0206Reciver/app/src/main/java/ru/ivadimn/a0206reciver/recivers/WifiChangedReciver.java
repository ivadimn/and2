package ru.ivadimn.a0206reciver.recivers;

import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import ru.ivadimn.a0206reciver.App;
import ru.ivadimn.a0206reciver.handlers.FileLoaderService;

/**
 * Created by vadim on 03.08.2017.
 */

public class WifiChangedReciver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        Log.d("......WIFI_CHANGED", action);
        if(intent.getAction().equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)){
            boolean connected = intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false);
            if(!connected) {
                Log.d("......WIFI_CHANGED", "wifi disconnected");
                context.sendBroadcast(new Intent(LocalFileLoadReciver.createIntent(false)));

            }
        }
        else if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
            NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if( netInfo.isConnected() )
            {
                Log.d("......WIFI_CHANGED", "wifi connecte");
                context.sendBroadcast(new Intent(LocalFileLoadReciver.createIntent(true)));
            }
        }
    }
}
