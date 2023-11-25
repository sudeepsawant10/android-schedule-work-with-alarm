package com.example.scheduleworkwithalarm;

import static com.example.scheduleworkwithalarm.Constants.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class WorkerUtils {
    public static final String TAG = WorkerUtils.class.getSimpleName();
    /**
     * Blurs the given Bitmap image
     * @param picture Image to blur
     * @param applicationContext Application context
     * @return Blurred bitmap image
     */
    @WorkerThread
    public static Bitmap blurBitmap(Bitmap picture, Context applicationContext) {
        RenderScript rsContext = null;
        try {

            // Create the blur output of picture
            Bitmap output = Bitmap.createBitmap(
                    picture.getWidth(), picture.getHeight(), picture.getConfig());

            // Blur image logic
            rsContext = RenderScript.create(applicationContext, RenderScript.ContextType.DEBUG);
            Allocation inAlloc = Allocation.createFromBitmap(rsContext, picture);
            Allocation outAlloc = Allocation.createTyped(rsContext, inAlloc.getType());
            ScriptIntrinsicBlur theIntrinsic =
                    ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext));
            theIntrinsic.setRadius(10.f);
            theIntrinsic.setInput(inAlloc);
            theIntrinsic.forEach(outAlloc);
            outAlloc.copyTo(output);

            return output;
        } finally {
            if (rsContext != null) {
                rsContext.finish();
            }
        }
    }

    // create file in storage
    public static Uri writeBitmapToFile(Context applicationContext, Bitmap output) {
        // file name
        String name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString());
        // creating file
        File outputDir = new File(applicationContext.getFilesDir(), Constants.OUTPUT_PATH);
        // creating app dir to store file
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // should succeed
        }
        File outputFile = new File(outputDir, name);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            // store file
            output.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
        return Uri.fromFile(outputFile);
    }


    // create notification
    @SuppressLint("MissingPermission")
    public static void makeStatusNotification(String message, Context context) {
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            CharSequence name = Constants.VERBOSE_NOTIFICATION_CHANNEL_NAME;
            String description = Constants.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Add the channel
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[0]);

        // Show the notification
        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /**
     * Method for sleeping for a fixed amount of time to emulate slower work
     */
    static void sleep() {
        try {
            Thread.sleep(3000, 0);
        } catch (InterruptedException e) {
            Log.d(TAG, e.getMessage());
        }
    }
}
