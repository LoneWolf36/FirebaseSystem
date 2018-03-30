package sih.firebasesendnotif.Fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sih.firebasesendnotif.R;
import sih.firebasesendnotif.Classes.ScheduleData;

import static android.content.Context.MODE_PRIVATE;

public class AddScheduleFragment extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");

    private FirebaseAuth mAuth;
    Button btnDatePicker, btnTimePicker;
    Button submitBtn,clearBtn;
    EditText txtDuration;
    TextView txtDate, txtTime;
    SharedPreferences prefs;
    String city_name;
    SimpleDateFormat sdf;
    DateFormat form =new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String datetime;
    Date date_in_mili;
    //String city;

    public AddScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            city = getArguments().getString("City");
//        }
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        city_name = prefs.getString("city_name", "");
        sdf=new SimpleDateFormat("dd-MM-yyyy hh:mm");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //ref = database.getReference("pune");
        View v = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        return v;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDatePicker = (Button) view.findViewById(R.id.btn_date);
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
       // txtDate.setOnClickListener(new View.OnClickListener() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               // showDatePicker();
                DialogFragment newFragment = new DateFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        txtDate = (TextView) view.findViewById(R.id.in_date);
        btnTimePicker=(Button) view.findViewById(R.id.btn_time);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialogFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
            }
        });
        txtTime = (TextView) view.findViewById(R.id.in_time);
        txtDuration = (EditText) view.findViewById(R.id.txtDuration);
        submitBtn = (Button) view.findViewById(R.id.submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Date",txtDate.getText().toString());
                Log.d("Time",txtTime.getText().toString());
                Log.d("Duration",txtDuration.getText().toString());
               // Log.d("lat", String.valueOf(Lat)  + "   " + String.valueOf(Lng) + "   " + place + "     " + dam_name);

                String datetxt = txtDate.getText().toString();
                String timetxt = txtTime.getText().toString();
                String durationtxt = txtDuration.getText().toString();

                if(datetxt.equals("") || timetxt.equals("") || durationtxt.equals("")) {
                    Toast.makeText(getContext(),getContext().getResources().getString(R.string.toast_fill_details),Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        int num = Integer.parseInt(durationtxt);
                        Log.i("",num+" is a number");
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.toast_fill_dur), Toast.LENGTH_SHORT).show();
                        Log.i("",durationtxt+" is not a number");
                    }
                    DatabaseReference ref = database.getReference(city_name);
                    DatabaseReference mydam,temp;
                    temp=ref.child("Dams");
                    mydam = temp.child(mAuth.getUid());
                    datetime=datetxt+" "+timetxt;
                    Log.d("dT",datetime);
                    Calendar c=Calendar.getInstance();
                    try {
                        date_in_mili=sdf.parse(datetime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Log.d("dT",date_in_mili.getTime());
                    String key = mydam.push().getKey();

                    long dim;
                    String Lat = prefs.getString("Latitude", "" );
                    String Lng = prefs.getString("Longitude", "" );
                   // Log.d("Jai Log lat",Lat+"  "+Lng);

                    String place = prefs.getString("Place", "" );
                    String dam_name = prefs.getString("Dam_Name", "" );
//                    Log.d("karle",Lat);
//                    Log.d("karle",Lng);
//                    Log.d("karle",place);
//                    Log.d("karle",dam_name);
                      //dim=date_in_mili.getTime();
                    //System.out.println(dim);
                    //DT DATE TO DIM
 //                   dim=1;
                    dim=date_in_mili.getTime();
                    c.setTime(date_in_mili);
                    System.out.println(dim);
                    System.out.println(c.getTimeInMillis());
                    ScheduleData schedule = new ScheduleData(txtDate.getText().toString(), txtTime.getText().toString(), txtDuration.getText().toString(),dim,"Active",key,city_name,dam_name, place, Lat, Lng);
   //dim is date and time in miliseconds
                    //System.out.println(dim);
//                    Calendar calendar =Calendar.getInstance();
//                    calendar.setTimeInMillis(dim);
//                    Log.d("back",form.format(calendar.getTime()));
                   // ScheduleData schedule = new ScheduleData(txtDate.getText().toString(), txtTime.getText().toString(), txtDuration.getText().toString(),dim,"Active",key,city_name, dam_name, place, Lat, Lng);
                    //ref.setValue(schedule);
                    mydam.child(key).setValue(schedule);
                    Toast.makeText(getContext(),getContext().getResources().getString(R.string.toast_success), Toast.LENGTH_SHORT).show();
                    txtDate.setText("");
                    txtDuration.setText("");
                    txtTime.setText("");
                }
            }
        });
        clearBtn = (Button) view.findViewById(R.id.cancel_schedule);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDate.setText("");
                txtDuration.setText("");
                txtTime.setText("");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
