package sih.firebasesendnotif.Services;

import android.Manifest;
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

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {
    private static final String TAG = "FirebaseService";
    double UserLat, UserLong;
    LocationManager locationManager;
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
                        UserLong= location.getLongitude();
                        Log.d("LatCurr", String.valueOf(UserLat));
                        Log.d("LongCurr", String.valueOf(UserLong));
                    }
                }
            });
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
        if(remoteMessage.getData().size()>0){
            Log.d(TAG,"Message data : " +remoteMessage.getData());
        }

        if(remoteMessage.getNotification()!=null){
            String flag=remoteMessage.getData().get("flag");
            if(flag.equals("notify")) {
                String dam_name = remoteMessage.getData().get("dam_name");
                String latfcm = remoteMessage.getData().get("lat");
                String lonfcm = remoteMessage.getData().get("lon");
                String city_name = remoteMessage.getData().get("city_name");
                String time = remoteMessage.getData().get("time");
                String duration = remoteMessage.getData().get("duration");
                String date = remoteMessage.getData().get("date");
                //Log.i("JL",lat+"  "+lon);
                double theta = Double.parseDouble(lonfcm) - UserLong;
                double dist = Math.sin((Double.parseDouble(latfcm) * Math.PI / 180.0)) * Math.sin((UserLat * Math.PI / 180.0)) + Math.cos((Double.parseDouble(latfcm) * Math.PI / 180.0)) * Math.cos((UserLat * Math.PI / 180.0)) * Math.cos((theta * Math.PI / 180.0));
                dist = Math.acos(dist);
                dist = (dist * 180.0 / Math.PI);
                dist = dist * 60 * 1.1515 * 1.609344;
                String dis = Double.toString(dist);
                Log.d("dist", dis);
                String message = "Water Released from " + dam_name + " at time: " + time + " on date: " + date;

                String title = remoteMessage.getNotification().getTitle();
                //String message = remoteMessage.getNotification().getBody();
                Log.d(TAG, "Title " + title);
                Log.d(TAG, "Body " + message);
                sendNotification(title, message);
            }
            else if (flag.equals("alert"))
            {
                String dam_name=remoteMessage.getData().get("city_name");
                String text = remoteMessage.getData().get("text");
                String time = remoteMessage.getData().get("time");
                String city_name = remoteMessage.getData().get("city_name");
                String title=remoteMessage.getNotification().getTitle();
                String message= dam_name + " in "+city_name+"'s message: "+text+ " for release at "+time;
                sendNotification(title,message);
            }
        }
    }
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("Current Location", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        String channelId = "8605+";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
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


