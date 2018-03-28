package sih.firebasesendnotif;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sih.firebasesendnotif.Classes.NotifyData;
import sih.firebasesendnotif.Classes.ScheduleData;
import sih.firebasesendnotif.Fragments.ScheduleFragment;

public class UpdateSchedule extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference ref;
    String key;
    TextView time, date, duration;
    EditText edittime, editdate, editduration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences prefs;
        String city_name;
        Context context;
        prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        time= (TextView) findViewById(R.id.time1);
        date= (TextView) findViewById(R.id.date1);
        duration= (TextView) findViewById(R.id.duration1);
        time.setText(AppGlobalData.time);
        date.setText(AppGlobalData.date);
        duration.setText(AppGlobalData.duration);
        Log.d(AppGlobalData.time, "onCreate: time of AppGlobal ");

        edittime =(EditText) findViewById(R.id.edit_time);
        editdate=(EditText) findViewById(R.id.edit_date);
        editduration=(EditText) findViewById(R.id.edit_duration);
        Button update=(Button)findViewById(R.id.update_schedule);
        Button cancel=(Button)findViewById(R.id.cancel_schedule);

        DatabaseReference myRef1 = database.getReference(city_name);
        final DatabaseReference myRef = myRef1.child("Dams").child(mAuth.getUid());

        key = AppGlobalData.key; //key to be postponed
        key = key.substring(1,key.length()-1);
        final String key1=ref.push().getKey(); //key to be active

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleData schedule1 = new ScheduleData(time.toString(),date.toString(),duration.toString(),1,"Postponed",key);
                myRef.child(key).setValue(schedule1);
                ScheduleData schedule = new ScheduleData(edittime.toString(),editdate.toString(),editduration.toString(),1,"Active",key1);
                myRef.child(key1).setValue(schedule);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.toPopulate, new ScheduleFragment());
                ft.commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleData schedule2 = new ScheduleData(time.toString(),date.toString(),duration.toString(),1,"Cancelled",key);
                myRef.child(key).setValue(schedule2);
                ScheduleData schedule = new ScheduleData(edittime.toString(),editdate.toString(),editduration.toString(),1,"Active",key1);
                myRef.child(key1).setValue(schedule);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.toPopulate, new ScheduleFragment());
                ft.commit();
            }
        });
    }
}
