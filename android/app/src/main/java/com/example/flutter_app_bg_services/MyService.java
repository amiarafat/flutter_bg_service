package com.example.flutter_app_bg_services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    /*public MyService() {
    }*/

    @Override
    public void onCreate() {
        super.onCreate();

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationCompat.Builder builder =  new NotificationCompat.Builder(this, "message")
                    .setContentText("This is running on Background")
                    .setContentTitle("Flutter Background")
                    .setSmallIcon(R.mipmap.ic_launcher);

            startForeground(101, builder.build());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
