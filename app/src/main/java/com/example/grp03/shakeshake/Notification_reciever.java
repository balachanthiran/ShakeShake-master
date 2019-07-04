package com.example.grp03.shakeshake;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.app.NotificationCompat;

/**
 * Created by Varun on 11-04-2017.
 */

public class Notification_reciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        //NotficationManager to build a notfication
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Which activity to open when clicking on notification
        Intent repeating_intent = new Intent(context,MenuActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification properties
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_down_float)
                .setContentTitle("OOOOOOOOOOOOOOOH PLAY SHAKE SHAKE")
                .setContentText("GET YOUR HANDS READY!")
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());

    }
}
