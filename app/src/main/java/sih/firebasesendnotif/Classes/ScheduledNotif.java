package sih.firebasesendnotif.Classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import sih.firebasesendnotif.NavbarActivity;
import sih.firebasesendnotif.R;
import sih.firebasesendnotif.RecyclerAdapter;

import static android.content.Context.MODE_PRIVATE;

public abstract class ScheduledNotif extends BroadcastReceiver {
    SharedPreferences prefs;
    ScheduleData data;
    java.util.List<String> city_list;
    int MID=0;
    //FirebaseAuth mAuth;
    public void onReceive(final Context context, Intent intent) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        prefs = context.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        // mAuth = FirebaseAuth.getInstance();
        //  addNotification(context);
        final long when = System.currentTimeMillis();
        final NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, NavbarActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (!prefs.getBoolean("admin_login", false)) {
            //User Login view all dams of city
            Log.i("UserSSS", "display here baby Array contents: " + String.valueOf(city_list.toArray()));
            for (String user_subscribed_city : city_list) {
                Log.i("City List user view ", user_subscribed_city + " in user list");
                DatabaseReference myRefuser = database.getReference(user_subscribed_city);
                myRef = myRefuser.child("Dams");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                Date currentDate = new Date();
                                ScheduleData value = dataSnapshot2.getValue(ScheduleData.class);
                                //list.add(value);
                                //ADD DATE OF ANDROID AND getTEMP FROM VALUE
                                if((Integer.parseInt(value.getDate()) - currentDate.getTime())<=60*60*24){

                                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.mipmap.ic_launcher)
                                            .setContentTitle("Water Release Scheduled")
                                            .setContentText(value.duration)
                                            .setAutoCancel(true).setWhen(when)
                                            .setContentIntent(pendingIntent)
                                            .setSound(alarmSound)
                                            .setVibrate(new long[]{1000});
                                    notificationManager.notify(MID, mNotifyBuilder.build());
                                    MID++;

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Hello", "Failed to read value.", error.toException());
                    }
                });
            }
        }
    }
}
