package sih.firebasesendnotif.Classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sih.firebasesendnotif.NavbarActivity;
import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

public class Alarmnotif extends BroadcastReceiver {

    SharedPreferences prefs;
    java.util.List<String> city_list;
    ScheduleData data;
    DatabaseReference cityRef;
    String fDate;
    int MID = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {
        final long when = System.currentTimeMillis();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference[] myRef = new DatabaseReference[1];
        prefs = context.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NavbarActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        city_list = new ArrayList<String>();
        cityRef = FirebaseDatabase.getInstance().getReference();
        cityRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    Boolean chk1 = prefs.getBoolean(cityName, false);
                    if (chk1) {
                        Log.i("added", cityName + " in list");
                        city_list.add(cityName);
                        Log.e("inside",city_list.toString());
                    }
                }
                Log.e("hi","s");
                Log.e("inside1",city_list.toString());


                if (!prefs.getBoolean("admin_login", false)) {
                    Log.i("Alarm me", "display Array contents city list: " + String.valueOf(city_list.size()));
                    for (String user_subscribed_city : city_list) {
                        Log.e("in city karle", String.valueOf(city_list));
                        DatabaseReference myRefuser = database.getReference(user_subscribed_city);
                        myRef[0] = myRefuser.child("Dams");
                        myRef[0].addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("class", "1");
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    Log.e("loop", "1");
                                    for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                        Log.e("loop", "2");
                                        Date currentDate = new Date();
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                        ScheduleData value = dataSnapshot2.getValue(ScheduleData.class);
                                        Date futureDate = null;
                                        String citname = String.valueOf(value.city_name);
                                        String damname = String.valueOf(value.dam_name);
                                        String time = String.valueOf(value.time);
                                        String test = value.getDate();
                                        Log.e("Testing", citname);
                                        Log.e("Testing date", test);
                                        try {
                                            futureDate = dateFormat.parse(value.getDate());
                                            fDate = value.getDate();
                                            Log.e("originall", String.valueOf(futureDate));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Log.e("null in date", ":(");
                                            Log.e("current in date", String.valueOf(currentDate));
                                        }
                                        if(!test.equals("Nill"))
                                        {
                                                long diff = futureDate.getTime() - currentDate.getTime();
                                                Log.e("Difference", String.valueOf(diff));
                                                if (diff <= 60 * 60 * 24 * 1000 && diff >= 1000 || diff <= 14*60 * 60 * 24 * 1000 && diff >= 1000 || diff <= 7*60 * 60 * 24 * 1000 && diff >= 1000 || diff <= 2*60 * 60 * 24 * 1000 && diff >= 1000) {
                                                    Log.e("Chalja", "plis");
                                                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                                    String channelId = "12";
                                                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context, channelId)
                                                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                                                    R.mipmap.ic_launcher))
                                                            .setSmallIcon(R.mipmap.ic_launcher)
                                                            .setContentTitle(context.getResources().getString(R.string.timely))
                                                            .setStyle(new NotificationCompat.InboxStyle()
                                                                    .addLine(damname +" " + citname)
                                                                    .addLine(futureDate + " "+ time))
                                                            .setContentTitle("Scheduled alert:")
                                                            .setContentText("Water Released from" + damname + " at time: " + time + " on date: " + test)
                                                            .setSound(alarmSound)
                                                            .setSmallIcon(R.drawable.logo)
                                                            .setAutoCancel(true).setWhen(when)
                                                            .setContentIntent(pendingIntent).setStyle(new NotificationCompat.BigTextStyle().bigText("Water Released from " + damname + " at time: " + time + " on date: " + test));
                                                    notificationManager.notify(MID, mNotifyBuilder.build());
                                                    MID++;
                                                }
                                        }
                                    }

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("Hello", "Failed to read value.");
                            }

                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        }
}