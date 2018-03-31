package sih.firebasesendnotif;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import sih.firebasesendnotif.Classes.NotifyData;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import sih.firebasesendnotif.Classes.ScheduleData;
import sih.firebasesendnotif.Fragments.AddScheduleFragment;
import sih.firebasesendnotif.Fragments.ContactAuthority;
import sih.firebasesendnotif.Fragments.QueryDataFragment;
import sih.firebasesendnotif.Fragments.ScheduleFragment;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bada ali and chhota karle on 20/3/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder> {
    List<ScheduleData> list;
    ArrayList<String> myKeys;
    private Context context;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    SharedPreferences prefs;
    String city_name;
    String events;
    String events1;
    private FirebaseAuth mAuth;
    EditText txtDate, txtTime,txtDuration;

    public RecyclerAdapter(List<ScheduleData> list, Context context) {
        this.list = list;
        this.context = context;
        prefs = context.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
    }


    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        mAuth = FirebaseAuth.getInstance();
        //prefs= PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        prefs = parent.getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
        Log.d("city name",city_name);
        //myHoder.countDownStart(events,events1);
        return new MyHoder(view);
    }

    @Override
    public void onBindViewHolder(final MyHoder myHoder, int position) {
        final ScheduleData mylist = list.get(position);
        myHoder.date.setText(context.getResources().getString(R.string.water_rel)+": "+ mylist.getDate());
        Log.e("getdate format",mylist.getDate());
        myHoder.date.setText(context.getResources().getString(R.string.water_rel)+": "+ mylist.getDate()+ mylist.getDam_name()+" in "+mylist.getCity_name());
        myHoder.status.setText(mylist.getStatus());

        //code to make the Active green. It doesnt seem to work, do look into it
        if(myHoder.status.getText().toString().equals("Accept")){
            String status = myHoder.status.getText().toString();
            Log.e("status",""+status);
            myHoder.status.setTextColor(Color.parseColor("#00FF00"));
        }
        //code segment ends here
        events= mylist.getDate();
        events1= events+" " +mylist.getTime();
        myHoder.time.setText(context.getResources().getString(R.string.at)+": "+mylist.getTime());
        myHoder.duration.setText(context.getResources().getString(R.string.for_duration) +": "+ mylist.getDuration()+" "+context.getResources().getString(R.string.hourss));

        // LOGIC FOR HIDING BUTTONS ON CARDS
        if (!prefs.getBoolean("admin_login", false)) {
            myHoder.notify.setVisibility(View.INVISIBLE);
            myHoder.update.setVisibility(View.INVISIBLE);
            myHoder.query.setVisibility(View.VISIBLE);
            myHoder.share.setVisibility(View.VISIBLE);
            myHoder.query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavbarActivity myActivity = (NavbarActivity) context;
                    myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.toPopulate, new ContactAuthority()).commit();
                }
            });
            myHoder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message=context.getResources().getString(R.string.water)+mylist.getDam_name()+context.getResources().getString(R.string.jo)+mylist.getAddress()+context.getResources().getString(R.string.at)+mylist.getTime()+context.getResources().getString(R.string.for_duration)+mylist.getDate() ;
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, message);
                    context.startActivity(Intent.createChooser(share, "Share using"));
                }
            });
        } else {
            myHoder.query.setVisibility(View.INVISIBLE);
            myHoder.share.setVisibility(View.INVISIBLE);
            myHoder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppGlobalData.date = myHoder.date.getText().toString();
                    AppGlobalData.duration = myHoder.duration.getText().toString();
                    AppGlobalData.time = myHoder.time.getText().toString();
                    AppGlobalData.address=mylist.getAddress();
                    AppGlobalData.city_name=mylist.getCity_name();
                    AppGlobalData.dam_name=mylist.getDam_name();
                    AppGlobalData.key=mylist.getUid();
                    AppGlobalData.lat=mylist.getLat();
                    AppGlobalData.lon=mylist.getLon();

                    Intent intent = new Intent(context, UpdateSchedule.class);
                    context.startActivity(intent);
                }
            });
            myHoder.notify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LOGIC FOR NOTIFICATION
                    String lat =prefs.getString("Latitude","Unset");
                    String lon =prefs.getString("Longitude","Unset");
                    String dam_name =prefs.getString("Dam_Name","Unset");
                    String place =prefs.getString("Place","Unset");
                    String city =prefs.getString("city_name","Unset");
                    Log.d("city name in database",city_name);
                    DatabaseReference ref = database.getReference(city_name);
                    DatabaseReference mydam;
                    mydam = ref.child("Notify");
                    NotifyData schedule = new NotifyData(mylist.getDate(),mylist.getTime(),mylist.getDuration(),city_name,mylist.getDam_name(),mylist.getAddress(),mylist.getLat(),mylist.getLon());
                    String key=mydam.push().getKey();
                    mydam.child(key).setValue(schedule);
                }
            });
        }
       // myHoder.countDownStart(events,events1);
        myHoder.countDownStart(events,events1);
    }

    @Override
    public int getItemCount() {
        int arr = 0;
        try{
            if(list.size()==0){
                arr = 0;
            }
            else{

                arr=list.size();
            }
        }catch (Exception e){
        }
        return arr;
    }

    class MyHoder extends RecyclerView.ViewHolder{
        TextView date,time,duration,status;
        Button notify,update,query,share;
        private TextView txtDay, txtHour, txtMinute, txtSecond;
        private TextView tvEventStart;
        private Handler handler;
        private Runnable runnable;

        protected void onCreate(Bundle savedInstanceState) {
        }
        public MyHoder(View itemView) {
            super(itemView);
            share = itemView.findViewById(R.id.share);
            query = itemView.findViewById(R.id.query);
            date = (TextView) itemView.findViewById(R.id.date);
            notify =(Button) itemView.findViewById(R.id.notify);
            update = itemView.findViewById(R.id.update);
            Log.e("date",date.getText().toString());
            txtDay = (TextView) itemView.findViewById(R.id.txtDay);
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);
            txtMinute = (TextView) itemView.findViewById(R.id.txtMinute);
            txtSecond = (TextView) itemView.findViewById(R.id.txtSecond);
            tvEventStart = (TextView) itemView.findViewById(R.id.tveventStart);
            time= (TextView) itemView.findViewById(R.id.time);
            duration= (TextView) itemView.findViewById(R.id.duration);
            status =(TextView) itemView.findViewById(R.id.status);
//            parent_id = itemView.findViewById(R.id.parent_id);
//            parent_id.setVisibility(View.INVISIBLE);
            //countDownStart(events,events1);
        }
        public void countDownStart(final String events, final String events1) {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        // Please here set your event date//YYYY-MM-DD
                        Date futureDate = dateFormat.parse(String.valueOf(events1));
                        Date currentDate = new Date();
                        if (!currentDate.after(futureDate)) {
                            long diff = futureDate.getTime() - currentDate.getTime();
                            long days = diff / (24 * 60 * 60 * 1000);
                            diff -= days * (24 * 60 * 60 * 1000);
                            long hours = diff / (60 * 60 * 1000);
                            diff -= hours * (60 * 60 * 1000);
                            long minutes = diff / (60 * 1000);
                            diff -= minutes * (60 * 1000);
                            long seconds = diff / 1000;
                            txtDay.setText("" + String.format("%02d", days));
                            txtHour.setText("" + String.format("%02d", hours));
                            txtMinute.setText("" + String.format("%02d", minutes));
                            txtSecond.setText("" + String.format("%02d", seconds));
                            Log.e("Full time",events1);
                        } else {
                            futureDate = dateFormat.parse(String.valueOf(events1));
                            tvEventStart.setVisibility(View.VISIBLE);
                            tvEventStart.setText(context.getResources().getString(R.string.water));
                            textViewGone();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 1 * 1000);
        }

        public void textViewGone() {
            itemView.findViewById(R.id.LinearLayout1).setVisibility(View.GONE);
            itemView.findViewById(R.id.LinearLayout2).setVisibility(View.GONE);
            itemView.findViewById(R.id.LinearLayout3).setVisibility(View.GONE);
            itemView.findViewById(R.id.LinearLayout4).setVisibility(View.GONE);
        }
    }
}