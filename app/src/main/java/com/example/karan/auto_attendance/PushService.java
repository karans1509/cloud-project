package com.example.karan.auto_attendance;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClient;
import com.google.android.gms.gcm.GcmListenerService;

public class PushService extends GcmListenerService {
    public static final String LOGTAG = PushService.class.getSimpleName();
    public static final String ACTION_PUSH_NOTIFICATION = "push-notification";
    // Intent keys
    public static final String INTENT_SNS_NOTIFICATION_FROM = "from";
    public static final String INTENT_SNS_NOTIFICATION_DATA = "data";

    public PushService() {
    }

    public static String getMessage(Bundle data) {
        // If a push notification is sent as plain
        // text, then the message appears in "default".
        // Otherwise it's in the "message" for JSON format.
        return data.containsKey("default") ? data.getString("default") : data.getString(
                "message", "");
    }

    private void broadcast(final String from, final Bundle data) {
        Intent intent = new Intent(ACTION_PUSH_NOTIFICATION);
        intent.putExtra(INTENT_SNS_NOTIFICATION_FROM, from);
        intent.putExtra(INTENT_SNS_NOTIFICATION_DATA, data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onMessageReceived(final String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.d(LOGTAG, "From:" + from);
        Log.d(LOGTAG, "Data:" + data.toString());

        final NotificationClient notificationClient =
                MainActivity.pinpointManager.getNotificationClient();

        NotificationClient.CampaignPushResult pushResult =
                notificationClient.handleGCMCampaignPush(from, data, this.getClass());
        Log.d("test", pushResult.toString());

        Log.d("FOREGROUND", data.get("notification").toString());
        data.putString("message", String.format("Received Campaign Push:\n%s", data.get("notification").toString()));
        broadcast(from, data);
        return;
    }
}
