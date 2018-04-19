package com.groupl.project.pier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class backgroundReminder extends Service {

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("HH");
    //previous month
    int day = c.get(Calendar.DATE);
    String hour = df.format(c.getTime());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Day", String.valueOf(day));
        if (day == 8  && hour.equals("01")){
            notification();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void notification() {
        Notification notificationBar = null;
        if (Build.VERSION.SDK_INT >= 26) {
            // Configure the channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("01", "Pier", importance);
            channel.setDescription("Reminders");
            // Register the channel with the notifications manager
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);

            notificationBar = new NotificationCompat.Builder(this,"01")
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon))
                    .setContentTitle("Morning " + preference.getPreference(this,"username") + ", its time to feed PieR!" )
                    .setContentText("Please upload your new statement")
                    .build();
        } else {
            notificationBar = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon))
                    .setContentTitle("Morning " + preference.getPreference(this,"username") + ", its time to feed PieR!" )
                    .setContentText("Please upload your new statement")
                    .build();
        }



        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBar);

    }
}
