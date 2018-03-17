package com.team.deltacentauri.firebasenotification;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by LoneWolf on 3/17/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("MyLog", "Notification title : "+remoteMessage.getNotification().getTitle());
        Log.d("MyLog", "Notification body : "+remoteMessage.getNotification().getBody());
    }
}
