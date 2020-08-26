package com.example.flutter_app_bg_services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    /*public MyService() {
    }*/

    private Intent intent;
    public static final String BROADCAST_ACTION = "com.example.flutter_app_bg_services-timer";
    private Handler handler = new Handler();
    private long initial_time;
    long timeInMilliseconds = 0L;

    @Override
    public void onCreate() {
        super.onCreate();

        initial_time = SystemClock.uptimeMillis();
        intent = new Intent(BROADCAST_ACTION);
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            NotificationCompat.Builder builder =  new NotificationCompat.Builder(this, "message")
                    .setContentText("This is running on Background")
                    .setContentTitle("Flutter Background")
                    .setSmallIcon(R.mipmap.ic_launcher);

            startForeground(101, builder.build());
        }
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            DisplayLoggingInfo();
            handler.postDelayed(this, 1000); // 1 seconds
        }
    };

    private void DisplayLoggingInfo() {

        timeInMilliseconds = SystemClock.uptimeMillis() - initial_time;

        int timer = (int) timeInMilliseconds / 1000;
        intent.putExtra("time", timer);
        sendBroadcast(intent);

        Log.d("Hello", "Service:Time: " +timer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
