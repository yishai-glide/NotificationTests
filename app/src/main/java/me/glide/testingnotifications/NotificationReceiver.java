package me.glide.testingnotifications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class NotificationReceiver extends WakefulBroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Yishai", "got a Broadcast");
        ComponentName cn = new ComponentName(context.getPackageName(), TestNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(cn)));
    }
}
