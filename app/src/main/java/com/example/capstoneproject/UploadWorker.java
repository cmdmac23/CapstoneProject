package com.example.capstoneproject;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class UploadWorker extends Worker {
    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Result doWork() {

        Intent intent = new Intent(CreatePlanner.context, PlannerMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(CreatePlanner.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(CreatePlanner.context, "channelid")
                .setSmallIcon(R.drawable.notificationlogo)
                .setContentTitle("inFocus")
                .setContentText(getInputData().getString("BODY"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(CreatePlanner.context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(100, builder.build());

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
