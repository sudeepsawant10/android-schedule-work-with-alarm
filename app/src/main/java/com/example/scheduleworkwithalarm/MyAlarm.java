package com.example.scheduleworkwithalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class MyAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        WorkerUtils.makeStatusNotification("WorkManger Starting", context);
        WorkerUtils.sleep();

        Log.d("MyAlarm","MyAlarm onReceive....");


        // Schedule the periodic work using WorkManager
        PeriodicWorkRequest myWorkerRequest =
                new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                        .addTag("PeriodicWork")
                        .build();

//        ONE TIME REQUEST
//        WorkRequest myWorkerRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
//                        // Additional configuration
//                        .build();

        WorkManager.getInstance(context).enqueue(myWorkerRequest);

        Log.d("MyAlarm","MyAlarm onReceive end....");

    }
}
