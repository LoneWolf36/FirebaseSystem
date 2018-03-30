package sih.firebasesendnotif.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import sih.firebasesendnotif.LoginActivity;
import sih.firebasesendnotif.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseService";
    public FirebaseMessagingService(){

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Message data : " +remoteMessage.getData());
        }

        if(remoteMessage.getNotification()!=null){
            String dam_name = remoteMessage.getData().get("dam_name");
            String latfcm = remoteMessage.getData().get("lat");
            String lonfcm = remoteMessage.getData().get("lon");
            String city_name = remoteMessage.getData().get("city_name");
            String time = remoteMessage.getData().get("time");
            String duration = remoteMessage.getData().get("duration");
            String date = remoteMessage.getData().get("date");
            //Log.i("JL",lat+"  "+lon);


            String message = "Water Realeased from "+dam_name+" at time: "+time+" on date: "+date;

            String title=remoteMessage.getNotification().getTitle();
            //String message = remoteMessage.getNotification().getBody();
            Log.d(TAG,"Title " + title);
            Log.d(TAG,"Body " + message);
            sendNotification(title,message);
        }
    }

    @Override
    public void onDeletedMessages() {

    }
    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "8605+";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

