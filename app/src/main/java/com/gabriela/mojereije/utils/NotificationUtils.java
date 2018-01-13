package com.gabriela.mojereije.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.gabriela.mojereije.R;
import com.gabriela.mojereije.listOfBills.ListOfBills;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {

    private static final String channelId = "bills notifications";
    private static final int notificationId = 10;
    private static final String notificationTitle = "Moje režije";
    private static CharSequence notificationDescription = "Platite račune na vrijeme.";


    public static void issueNotification(Context context) {
        Intent resultIntent = new Intent(context, ListOfBills.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationDescription)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(notificationId, builder.build());
        }
    }
}
