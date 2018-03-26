package sih.firebasesendnotif;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;

import sih.firebasesendnotif.Classes.NotifyData;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.view.View;
import android.widget.TextView;
import sih.firebasesendnotif.Classes.ScheduleData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chota ali and bada karle on 20/3/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHoder> {
    List<ScheduleData> list;
    private Context context;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    SharedPreferences prefs;
    String city_name;
    private FirebaseAuth mAuth;
    EditText txtDate, txtTime,txtDuration;

    //    prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//    //prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//    city_name = prefs.getString("city_name", "");


    public RecyclerAdapter(Context context){
        this.context =context;
    }

    public RecyclerAdapter(List<ScheduleData> list, Context context) {
        this.list = list;
        this.context = context;
        prefs =context.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        //getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");

    }
    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        //SharedPreferences.Editor e= prefs.edit();
        // e.getString(city_name);
        //prefs= PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        //Log.d("city name",city_name);
        // e.getString(city_name);
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        MyHoder myHoder = new MyHoder(view);
        mAuth = FirebaseAuth.getInstance();
        //prefs= PreferenceManager.getDefaultSharedPreferences(parent.getContext());
        prefs = parent.getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
        Log.d("city name",city_name);
        return myHoder;
    }
    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        final ScheduleData mylist = list.get(position);
        holder.date.setText("Water will be released on " + mylist.getDate());
        holder.status.setText(mylist.getStatus());
        String events= mylist.getDate();
        //holder.email.setText(mylist.getEmail());
        holder.time.setText("at " +mylist.getTime());
        holder.duration.setText("for a duration of " + mylist.getDuration() + " hours");
        holder.notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("city name in database",city_name);
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam;
                mydam = ref.child("Notify");
                NotifyData schedule = new NotifyData(mylist.getDate().toString(),mylist.getTime().toString(),mylist.getDuration().toString(),city_name);
                String key=mydam.push().getKey();
                mydam.child(key).setValue(schedule);
                //               mydam = ref.child(mAuth.getUid());
//                ScheduleData schedule = new ScheduleData(mylist.getDate().toString(),mylist.getTime().toString(),mylist.getDuration().toString(),1);
//                ref.setValue(schedule);
//
//                String key=mydam.push().getKey();
//                mydam.child(key).setValue(schedule);
            }
        });
        holder.countDownStart(events);

    }


//    @Override
//    public void onBindViewHolder(MyHoder holder, int position) {
//        //prefs =   getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//        //prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//   //     SharedPreferences prefs = this
//
//  //      city_name = prefs.getString("city_name", "");
//
//
//        final ScheduleData mylist = list.get(position);
//        holder.date.setText("Water will be released on " + mylist.getDate());
//        //holder.email.setText(mylist.getEmail());
//        holder.time.setText("at " +mylist.getTime());
//        holder.duration.setText("for a duration of " + mylist.getDuration() + " hours");
////        holder.notify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("city name in database",city_name);
//
//                DatabaseReference ref = database.getReference(city_name);
//                DatabaseReference mydam;
//                mydam = ref.child(mAuth.getUid());
//                ScheduleData schedule = new ScheduleData(mylist.getDate().toString(),mylist.getTime().toString(),mylist.getDuration().toString(),1);
//                ref.setValue(schedule);
//
//                String key=mydam.push().getKey();
//                mydam.child(key).setValue(schedule);
//                txtDate.setText("");
//                txtDuration.setText("");
//                txtTime.setText("");
//            }
//        });

    //    }
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
        Button notify;
        private TextView txtDay, txtHour, txtMinute, txtSecond;
        private TextView tvEventStart;
        private Handler handler;
        private Runnable runnable;
        //

          //        @Override
        protected void onCreate(Bundle savedInstanceState) {
//           super.onCreate(savedInstanceState);
//            setContentView(R.layout.card);
//

        }
        //     public static class MyHoder extends RecyclerView.ViewHolder{
//        TextView date,time,duration;
//        Button notify;
//
        public MyHoder(View itemView) {
            super(itemView);
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.card);
            date = (TextView) itemView.findViewById(R.id.date);
            notify =(Button) itemView.findViewById(R.id.notify);
            Log.e("date",date.getText().toString());
            txtDay = (TextView) itemView.findViewById(R.id.txtDay);
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);
            txtMinute = (TextView) itemView.findViewById(R.id.txtMinute);
            txtSecond = (TextView) itemView.findViewById(R.id.txtSecond);
            tvEventStart = (TextView) itemView.findViewById(R.id.tveventStart);
            time= (TextView) itemView.findViewById(R.id.time);
            duration= (TextView) itemView.findViewById(R.id.duration);
            status =(TextView) itemView.findViewById(R.id.status);
        }

        public void countDownStart(final String events) {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        // Please here set your event date//YYYY-MM-DD
                        Date futureDate = dateFormat.parse(String.valueOf(events));
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
                        } else {
                            tvEventStart.setVisibility(View.VISIBLE);
                            tvEventStart.setText("Water Released");
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