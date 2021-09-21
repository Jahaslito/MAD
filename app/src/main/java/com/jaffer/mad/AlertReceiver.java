package com.jaffer.mad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message= intent.getStringExtra("message");
        String title= intent.getStringExtra("title");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(message,title);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
