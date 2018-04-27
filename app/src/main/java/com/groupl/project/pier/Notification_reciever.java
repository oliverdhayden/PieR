package com.groupl.project.pier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.security.acl.NotOwnerException;

class Notification_reciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final NotificationManager mNotific= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        CharSequence name="Ragav";
        String desc="Notification to allow Pier to remind you to upload";
        int imp=NotificationManager.IMPORTANCE_HIGH;
        final String ChannelID="pier_channel_01";
        final int ncode=101;
        String Body="PieR is hungry please upload a new statement!";
        String Title = "New month new statement";
        Notification n = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(ChannelID, name,
                    imp);
            mChannel.setDescription(desc);
            mChannel.setLightColor(Color.CYAN);
            mChannel.canShowBadge();
            mChannel.setShowBadge(true);
            mNotific.createNotificationChannel(mChannel);
        }
        Intent intent1 = new Intent(context,FileUpload.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            n= new Notification.Builder(context,ChannelID)
                    .setContentTitle(Title)
                    .setContentText(Body)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        else {
            n= new Notification.Builder(context)
                    .setContentTitle(Title)
                    .setContentText(Body)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        mNotific.notify(ncode, n);


    }
}
