package com.gp2.omar.aqarcom;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Omar on 10/4/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            //String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();

            Intent i = new Intent(this, MainActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent notification = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("Aqar.com Notification Center");
            builder.setContentText(message);
            builder.setContentIntent(notification);

            builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            builder.setAutoCancel(true);

            NotificationManager mm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mm.cancel(1);
            mm.notify(0, builder.build());
        }
    }
}



