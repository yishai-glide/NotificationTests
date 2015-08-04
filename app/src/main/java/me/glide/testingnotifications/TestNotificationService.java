package me.glide.testingnotifications;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TestNotificationService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "me.glide.testingnotifications.action.FOO";
    private static final String ACTION_BAZ = "me.glide.testingnotifications.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "me.glide.testingnotifications.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "me.glide.testingnotifications.extra.PARAM2";
    private static final String[] DEFAULT_STACK = new String[]{
            "Josh Miller:Hey this is the best app ever.",
            "Jody Blum: sent a new video",
            "Josh Miller: just sent a new video",
            "Josh Miller: just sent a new photo",
            "Jody Blum: sent 5 new videos",
            "Jody Blum: is recording live..."
    };

    public static final String[] UPDATED_STACK = new String[]{
            "Josh Miller:Hey this is the best app ever.",
            "Jody Blum: sent a new video",
            "Josh Miller: sent a new video",
            "Josh Miller: sent a new photo",
            "Jody Blum: sent 5 new videos",
            "Jody Blum: hey whats up"
    };

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestNotificationService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TestNotificationService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);

        PendingIntent pending = PendingIntent.getService(context, 0, intent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pending);
    }

    public TestNotificationService() {
        super("TestNotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("yishai", "starting an IntentService");
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (ACTION_BAZ.equals(intent.getAction())) {
            nm.cancel(42);
            nm.notify(613, sendInboxNotification(UPDATED_STACK));
        } else {
            nm.notify(613, sendInboxNotification());
//        nm.notify(532, snedRegularNotification());
            nm.notify(42, sendLiveNotification());
            startActionBaz(this, "yishai", "levenglick");
        }

        NotificationReceiver.completeWakefulIntent(intent);
    }

    private Notification sendInboxNotification() {
        return sendInboxNotification(DEFAULT_STACK);
    }

    private Notification sendInboxNotification(String[] lines) {

        Notification.InboxStyle style = new Notification.InboxStyle();
        for (String line : lines) {
            style.addLine(line);
        }
        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(0)
                .setContentTitle("INBOX")
                .setContentText("WELCOME TO THE JUNGLE!!!")
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getBroadcast(this, 582, new Intent(this, MainActivity.class), 0))
                .setPriority(Notification.PRIORITY_MAX)
                .setDeleteIntent(PendingIntent.getBroadcast(this, 14, new Intent(this, NotificationDismissReceiver.class), 0))
                .setNumber(42)
                .setStyle(style)
                .setWhen(System.currentTimeMillis()).build();

        return notification;
    }

    private Notification snedRegularNotification() {
        Bitmap hugeImag = BitmapFactory.decodeResource(getResources(), R.drawable.dad);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("REGULAR")
                .setContentText("This is totally regular man, no probs")
                .setDefaults(0)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setNumber(42)
                .setDeleteIntent(PendingIntent.getBroadcast(this, 14, new Intent(this, NotificationDismissReceiver.class), 0))
                .setStyle(new Notification.BigPictureStyle().bigPicture(hugeImag))
                .setWhen(System.currentTimeMillis()).build();

        return notification;
    }

    private Notification sendLiveNotification() {
        Bitmap hugeImag = BitmapFactory.decodeResource(getResources(), R.drawable.blackjack_bender);

        Notification notification = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("LIVE")
//                .setContentText("This is a content text")
                .setDefaults(0)
                .setAutoCancel(true)
                .setStyle(new Notification.BigPictureStyle().bigPicture(hugeImag))
                .setPriority(Notification.PRIORITY_MAX)
                .setLights(0xFF003CFF, 500, 250)
                .setNumber(12)
//                .setContent(new RemoteViews(getPackageName(), R.layout.empty_layout))
                .setVibrate(new long[]{200, 200, 200, 200, 200, 200})
                .setDeleteIntent(PendingIntent.getBroadcast(this, 14, new Intent(this, NotificationDismissReceiver.class), 0))
                .setWhen(System.currentTimeMillis()).build();
        return notification;
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
