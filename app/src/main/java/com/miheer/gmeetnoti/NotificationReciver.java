package com.miheer.gmeetnoti;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class NotificationReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Calendar calDay = Calendar.getInstance();
        int day = calDay.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.MONDAY:
                Intent monIntent = new Intent(context,MonNotiActivity.class);
                monIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context,100,monIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder monBuilder = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setContentTitle("Class Starting Soon")
                        .setContentText("Your " +  " class will be starting\nclick to get link")
                        .setSmallIcon(R.drawable.ic_class_icon);

                notificationManager.notify(100,monBuilder.build());
                break;
            case Calendar.SATURDAY:
                Intent satIntent = new Intent(context,MonNotiActivity.class);
                satIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent satPendingIntent = PendingIntent.getActivity(context,100,satIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder satBuilder = new NotificationCompat.Builder(context)
                        .setContentIntent(satPendingIntent)
                        .setAutoCancel(true)
                        .setOnlyAlertOnce(true)
                        .setContentTitle("Class Starting Soon")
                        .setContentText("Your " +  " class will be starting\nclick to get link")
                        .setSmallIcon(R.drawable.ic_class_icon);

                notificationManager.notify(100,satBuilder.build());
                break;
        }


    }


}
