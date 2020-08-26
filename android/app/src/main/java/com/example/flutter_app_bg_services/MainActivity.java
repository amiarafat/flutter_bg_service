package com.example.flutter_app_bg_services;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.util.stream.Stream;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    private static final String STREAM = "com.example.flutter_app_bg_services/timer";
    private static final String TAG = MainActivity.class.getSimpleName();
    Intent forService;

    Intent intent;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    EventChannel channel;
    private int time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GeneratedPluginRegistrant.registerWith(this);

        forService = new Intent(MainActivity.this, MyService.class);
        new MethodChannel(getFlutterView(), "com.example.flutter_app_bg_services").setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

                if (call.method.endsWith("startService")) {
                    startService();
                    result.success("Value: "+time);
                }
            }
        });

    }

    BinaryMessenger getFlutterView() {
        return getFlutterEngine().getDartExecutor().getBinaryMessenger();
    }

    private void startService() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(forService);
        } else {
            startService(forService);
        }

        registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
        time = intent.getIntExtra("time", 0);
        Log.d("Hello", "Time " + time);

        int mins = time / 60;
        int secs = time % 60;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopService(forService);
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService();
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(broadcastReceiver != null){

            registerReceiver(broadcastReceiver, new IntentFilter(MyService.BROADCAST_ACTION));
        }
    }
}
