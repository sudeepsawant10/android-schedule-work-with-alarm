package com.example.scheduleworkwithalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the current time
//        Calendar currentTime = Calendar.getInstance();

        // Define the start and end times for the desired time window
        Calendar startTime = Calendar.getInstance();
//        startTime.set(Calendar.DAY_OF_MONTH, 17); // Set the desired start hour
//        startTime.set(Calendar.MONTH, 8); // Set the desired start hour
//        startTime.set(Calendar.YEAR, 2023); // Set the desired start hour
        startTime.set(Calendar.HOUR_OF_DAY, 10); // Set the desired start hour
        startTime.set(Calendar.MINUTE, 15);
        startTime.set(Calendar.SECOND, 00);

        setAlarm(startTime);

    }

    private void setAlarm(Calendar startTime) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyAlarm.class);

        // pending alarm intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Worker Set at 2:20", Toast.LENGTH_SHORT).show();
    }
}