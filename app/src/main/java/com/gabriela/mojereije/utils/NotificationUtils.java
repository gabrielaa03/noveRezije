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

    public static final String channelId = "bills notifications";
    public static final int notificationId = 10;


    public static void issueNotification(Context context) {
        Intent resultIntent = new Intent(context, ListOfBills.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Naslov")
                .setContentText("Tekst obavijesti")
                .setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(notificationId, builder.build());
        }
    }
}
