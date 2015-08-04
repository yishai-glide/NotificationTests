package me.glide.testingnotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationDismissReceiver extends BroadcastReceiver {
    public static final String TAG = "yishai";
    public NotificationDismissReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "dismissed the MODAFUCKING NOTIFICATION!!!");
    }
}
