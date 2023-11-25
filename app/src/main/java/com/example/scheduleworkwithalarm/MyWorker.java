package com.example.scheduleworkwithalarm;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;

public class MyWorker extends Worker {
    Context context;
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

////        Remove worker on end time
//        Calendar currentTime = Calendar.getInstance();
//
//        Calendar endTime = Calendar.getInstance();
////        startTime.set(Calendar.DAY_OF_MONTH, 17); // Set the desired start hour
////        startTime.set(Calendar.MONTH, 8); // Set the desired start hour
////        startTime.set(Calendar.YEAR, 2023); // Set the desired start hour
//        endTime.set(Calendar.HOUR_OF_DAY, 12); // Set the desired start hour
//        endTime.set(Calendar.MINUTE, 00);
//        endTime.set(Calendar.SECOND, 00);
//
//        if (currentTime.after(endTime)){
//            Log.d("MyWorker", "MyWorker => Cancelling the worker");
//            WorkManager.getInstance().cancelAllWorkByTag("PeriodicWork");
//        }


        // Fetch data here (replace with actual data fetching logic)
        Log.d("MyWorker", "MyWorker IN THE WORKER......................................");
//        fetchData();
//        WorkerUtils.makeStatusNotification("Notification from work manager", context);
//        WorkerUtils.sleep();
        // Simulate data fetching (replace with actual data fetching logic)
        Log.d("MyWorker", "MyWorker Fetching data...");
        try {
            Log.d("MyWorker", "MyWorker Worker is running.....");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("MyWorker", "MyWorker FAILED.");
        }
        Log.d("MyWorker", "MyWorker Data fetched.");


        // Return the appropriate Result based on the work status
        return Result.success();
    }

    private void fetchData() {
        // Simulate data fetching (replace with actual data fetching logic)
        Log.d("MyWorker", "MyWorker Fetching data...");
        try {
            Log.d("MyWorker", "MyWorker Worker is running.....");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("MyWorker", "MyWorker Data fetched.");
    }
}
