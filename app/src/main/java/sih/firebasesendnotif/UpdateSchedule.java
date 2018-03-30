package sih.firebasesendnotif;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

import es.dmoral.toasty.Toasty;
import sih.firebasesendnotif.Classes.ScheduleData;
import sih.firebasesendnotif.Fragments.DateFragment;
import sih.firebasesendnotif.Fragments.TimeDialogFragment;

public class UpdateSchedule extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String key;
    TextView time, date, duration;
    Button btnDatePicker, btnTimePicker;
    Button rescheduleBtn,cancelBtn;
    EditText txtDuration;
    SimpleDateFormat sdf;
    String datetime;
    String time_set, date_set, duration_set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_schedule);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        SharedPreferences prefs;
        String city_name;
        prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        city_name = prefs.getString("city_name", "");
        mAuth = FirebaseAuth.getInstance();

        sdf = new SimpleDateFormat("dd-m-yyyy HH:mm");
        date = findViewById(R.id.in_date);
        time = findViewById(R.id.in_time);
        duration = findViewById(R.id.txtDuration);
        cancelBtn = findViewById(R.id.cancel_schedule);
        rescheduleBtn = findViewById(R.id.reschedule);
        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        time.setHint(AppGlobalData.time.toUpperCase());
        date.setHint(AppGlobalData.date.toUpperCase());
        duration.setHint(AppGlobalData.duration.toUpperCase());

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DateFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialogFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
            }
        });

        DatabaseReference myRef1 = database.getReference(city_name);
        final DatabaseReference myRef = myRef1.child("Dams").child(mAuth.getUid());

        key = AppGlobalData.key; //key to be postponed
        final String key1=myRef.push().getKey(); //key to be active
//        myRef.child(key1).setValue()


        rescheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_set = date.getText().toString();
                duration_set = duration.getText().toString();
                time_set = time.getText().toString();
                if(time_set.equals("") || date_set.equals("") || duration_set.equals("")) {
                    Toasty.info(UpdateSchedule.this, getResources().getString(R.string.toast_fill_details), Toast.LENGTH_SHORT, true).show();
                }
                else {
                    final ScheduleData schedule1 = new ScheduleData("Nill","00:00","0",1,"Rescheduled",key,AppGlobalData.city_name,AppGlobalData.dam_name,AppGlobalData.address,AppGlobalData.lat,AppGlobalData.lon);
                    myRef.child(key).setValue(schedule1);
                    ScheduleData schedule = new ScheduleData(date_set,time_set,duration_set,1,"Active",key1,AppGlobalData.city_name,AppGlobalData.dam_name,AppGlobalData.address,AppGlobalData.lat,AppGlobalData.lon);
                    myRef.child(key1).setValue(schedule);
                    Toasty.success(UpdateSchedule.this, getResources().getString(R.string.success), Toast.LENGTH_SHORT, true).show();
                    startActivity(new Intent(UpdateSchedule.this, NavbarActivity.class));
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleData schedule2 = new ScheduleData("Nill","00:00","0",1," Cancelled",key,AppGlobalData.city_name,AppGlobalData.dam_name,AppGlobalData.address,AppGlobalData.lat,AppGlobalData.lon);
                myRef.child(key).setValue(schedule2);
                startActivity(new Intent(UpdateSchedule.this, NavbarActivity.class));
                finish();
            }
        });
    }
}
