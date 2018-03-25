package sih.firebasesendnotif;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import sih.firebasesendnotif.Classes.ScheduleData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ali on 20/3/18.
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
    }

    @Override
    public MyHoder onCreateViewHolder(ViewGroup parent, int viewType) {

       // e.getString(city_name);
        View view = LayoutInflater.from(context).inflate(R.layout.card,parent,false);
        MyHoder myHoder = new MyHoder(view);
//        mAuth = FirebaseAuth.getInstance();
//        //prefs= PreferenceManager.getDefaultSharedPreferences(parent.getContext());
//        prefs = parent.getContext().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//        city_name = prefs.getString("city_name", "");
//        Log.d("city name",city_name);
        return myHoder;
    }

    @Override
    public void onBindViewHolder(MyHoder holder, int position) {
        //prefs =   getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        //prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
   //     SharedPreferences prefs = this

  //      city_name = prefs.getString("city_name", "");


        final ScheduleData mylist = list.get(position);
        holder.date.setText("Water will be released on " + mylist.getDate());
        //holder.email.setText(mylist.getEmail());
        holder.time.setText("at " +mylist.getTime());
        holder.duration.setText("for a duration of " + mylist.getDuration() + " hours");
//        holder.notify.setOnClickListener(new View.OnClickListener() {
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
     public static class MyHoder extends RecyclerView.ViewHolder{
        TextView date,time,duration;
        Button notify;

        public MyHoder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);

            time= (TextView) itemView.findViewById(R.id.time);
            duration= (TextView) itemView.findViewById(R.id.duration);
            //ge
          // prefs= .getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        }
    }
}
