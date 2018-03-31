package sih.firebasesendnotif.Services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.RemoteMessage;

import sih.firebasesendnotif.Fragments.DamLocationPicker;
import sih.firebasesendnotif.LocationPickerActivity;
import sih.firebasesendnotif.LoginActivity;
import sih.firebasesendnotif.NavbarActivity;
import sih.firebasesendnotif.R;
import sih.firebasesendnotif.SignupActivity;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseService";
    double UserLat, UserLong;
    LocationManager locationManager;
    double radius = 5;
    double distkms;
    private FusedLocationProviderClient mFusedLocationClient;

    public FirebaseMessagingService() {

    }

    public void onCreate() {
        super.onCreate();
        Log.d("Start loc", "Helooo on create");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Criteria criteria = new Criteria();
            Log.d("inside if", "is provider");
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //Location location = locationManager.getLastLocation();


            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.d("hi", "NULL");
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        Log.d("loc", "loc");
                        UserLat = location.getLatitude();
                        UserLong = location.getLongitude();
                        Log.d("LatCurr", String.valueOf(UserLat));
                        Log.d("LongCurr", String.valueOf(UserLong));
                    }
                }
            });
//            if(location != null)
//            {
//                CurrentlocationListener.onLocationChanged(location);
//
        }
    }

    //    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        String str;
//
//
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data : " + remoteMessage.getData());
//        }
//
//        if (remoteMessage.getNotification() != null) {
//            String title = remoteMessage.getNotification().getTitle();
//            String message = remoteMessage.getNotification().getBody();
//            Log.d(TAG, "Title " + title);
//            Log.d(TAG, "Body " + message);
//            sendNotification(title, message);
//        }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.d("", "Message data : " + remoteMessage.getData());
        }
        Log.d("JL",remoteMessage.getData().toString());
        if (remoteMessage.getNotification() != null) {
            String flag = remoteMessage.getData().get("flag");
            Log.d("lw",flag);
            switch (flag) {
                case "notify": {
                    Log.i("JL", "Notify");
                    String dam_name = remoteMessage.getData().get("dam_name");
                    String latfcm = remoteMessage.getData().get("lat");
                    String lonfcm = remoteMessage.getData().get("lon");
                    String city_name = remoteMessage.getData().get("city_name");
                    String time = remoteMessage.getData().get("time");
                    String duration = remoteMessage.getData().get("duration");
                    String date = remoteMessage.getData().get("date");
                    //Log.i("JL",lat+"  "+lon);
                    String message = "Water Released from " + dam_name + " at time: " + time + " on date: " + date;
                    Log.i("lw", "onMessageReceived: "+message);

                    String title = remoteMessage.getNotification().getTitle();
                    //String message = remoteMessage.getNotification().getBody();
                    Log.d(TAG, "Title " + title);
                    Log.d(TAG, "Body " + message);
                    sendNotification(title, message);
                    break;
                }
                case "alert": {
                    Log.i("JL", "ALERT");
                    Log.d("here", "inside alert flag");
                    Log.i("lw", "alert: ");
                    String dam_name = remoteMessage.getData().get("dam_name");
                    String text = remoteMessage.getData().get("text");
//                    String time = remoteMessage.getData().get("time");
                    String city_name = remoteMessage.getData().get("city_name");
                    String title = remoteMessage.getNotification().getTitle();
                    String message = dam_name + " in " + city_name + " ALERT: " + text + " for emergency release";
                    sendNotification(title, message);
                    Log.d("title", title);
                    Log.d("message", message);
                    Log.d("notif", "built");
                    break;
                }
                case "vicinity": {
                    Log.i("JL", "Vicinity");
                    String dam_name = remoteMessage.getData().get("dam_name");
                    String latfcm = remoteMessage.getData().get("lat");
                    String lonfcm = remoteMessage.getData().get("lon");
                    String city_name = remoteMessage.getData().get("city_name");
                    String time = remoteMessage.getData().get("time");
                    String duration = remoteMessage.getData().get("duration");
                    String date = remoteMessage.getData().get("date");

//                    //Log.i("JL",lat+"  "+lon);
//                    Log.d("latfcm", latfcm);
//                    Log.d("longfcm", lonfcm);
                    double R = 6371;
                    double lat1 = UserLat;
                    double lon1 = UserLong;
//                    double lon2 = Double.parseDouble(lonfcm);
//                    double lat2 = Double.parseDouble(latfcm);
//                    double dLon = lon1 - lon2;
//                    double dLat = lat1 - lat2;
//                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//                    distkms = R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//                    Log.d(Double.toString(distkms), "distance in kms");
//                    // double R=6371;
//
//                    // double dLon=UserLong-lonfcmdo;
//                    //double dLat=UserLat-latfcmdo;
//                    //double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(UserLat)*Math.cos(deg2rad(latfcmdo))*Math.sin(dLon/2)*Math.sin(dLon/2));
//                    //double dist = R*2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
                    double lon2 = Double.parseDouble(lonfcm);

                    double lat2 = Double.parseDouble(latfcm);
                    double dLon = lon1 - lon2;
                    double dLat = lat1 - lat2;
                    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
                    distkms = R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))/1000;
                    Log.d(Double.toString(distkms), "distance in kms");
                    // double R=6371;

                    // double dLon=UserLong-lonfcmdo;
                    //double dLat=UserLat-latfcmdo;
                    //double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(UserLat)*Math.cos(deg2rad(latfcmdo))*Math.sin(dLon/2)*Math.sin(dLon/2));
                    //double dist = R*2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
                    Log.i("lw", "Lat:"+lat2);
                    Log.i("lw", "Lon:"+lon2);
                    String dis = Double.toString(distkms);
                    Log.d("lw", dis);
                    Log.i("JL", "VicinityCalc" + dis);
                    //distkms=5;
                    Location loc1 = new Location("");
                    loc1.setLatitude(UserLat);
                    loc1.setLongitude(UserLong);
                    Log.i("lw", "USER lat: "+UserLat);
                    Log.i("lw", "USER lon: "+UserLong);
                    Location loc2 = new Location("");
                    loc2.setLatitude(lat2);
                    loc2.setLongitude(lon2);
                    Float distkms = loc1.distanceTo(loc2);
                    Log.d(Double.toString(distkms), "distance in kms outside");
                    Log.i("lw", "Distance: "+distkms);

                    distkms= (distkms/1000);

                    if (distkms <= 10) {
                        Log.i("JL", "Vicinity10");

                        String message = "IN VICINITY OF DANGER Water Released from " + dam_name + " at time: " + time + " on date: " + date;
                        String title = "EMERGENCY ALERT : "+remoteMessage.getNotification().getTitle();
                        //String message = remoteMessage.getNotification().getBody();
                        Log.d(TAG, "Title " + title);
                        Log.d(TAG, "Body " + message);
                        sendNotification(title, message);
                    }
                    break;
                }
            }
        }
    }

    private void sendNotification(String title, String messageBody) {
//        Intent intent = new Intent(this, LoginActivity.class);
        Log.i("lw", "sendNotification: Im here!"+messageBody);
        Intent intent = new Intent(this, NavbarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Current Location", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "8605+";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Log.d("inside", "notification");
//        Log.d("inside","notification");
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.logo)
                        .setSound(defaultSoundUri)
                        .setColor(getResources().getColor(R.color.red))
                        .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));

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


    //private double deg2rad(Double lat) {
    private double deg2rad (Double lat){
        return lat * (Math.PI / 180);
    }

    @Override
    public void onDeletedMessages() {

    }
}
//    private void sendNotification(String title,String messageBody) {
//        if (distkms < radius) {
//            Intent intent = new Intent(this, NavbarActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.putExtra("Current Location", true);
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            String channelId = "8605+";
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            @SuppressLint("ResourceAsColor") NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle("Close to water Released area")
//                            .setColor(R.color.red)
//                            .setContentText(messageBody)
//                            .setAutoCancel(true)
//                            .setStyle(new NotificationCompat.InboxStyle()
//                                    .addLine(messageBody)
//                                    .addLine("emergency"))
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            // Since android Oreo notification channel is needed.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(channelId,
//                        "Channel human readable title",
//                        NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//            }
//            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//        } else {
//            Intent intent = new Intent(this, NavbarActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        intent.putExtra("Current Location", true);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            String channelId = "8605+";
//            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder notificationBuilder =
//                    new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle(title)
//                            .setAutoCancel(true)
//                            .setStyle(new NotificationCompat.InboxStyle()
//                                    .addLine(messageBody)
//                                    .addLine("emergency"))
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            // Since android Oreo notification channel is needed.
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel channel = new NotificationChannel(channelId,
//                        "Channel human readable title",
//                        NotificationManager.IMPORTANCE_DEFAULT);
//                notificationManager.createNotificationChannel(channel);
//            }
//            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//        }
//    }



