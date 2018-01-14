package com.gabriela.mojereije.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gabriela.mojereije.utils.DateUtils;
import com.gabriela.mojereije.utils.NotificationUtils;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DateUtils.isTodaysDate(context))
            NotificationUtils.issueNotification(context);
    }
}
