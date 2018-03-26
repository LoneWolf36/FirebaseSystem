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
                String datetxt = txtDate.getText().toString();
                String timetxt = txtTime.getText().toString();
                String durationtxt = txtDuration.getText().toString();


                if(datetxt.equals("") || timetxt.equals("") || durationtxt.equals("")) {
                    Toast.makeText(getContext(),"Please Enter all the details",Toast.LENGTH_SHORT).show();

                }
                else{
                    try {
                        int num = Integer.parseInt(durationtxt);
                        Log.i("",num+" is a number");
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Please enter a Number for Duration!", Toast.LENGTH_SHORT).show();
                        Log.i("",durationtxt+" is not a number");
                    }
                    DatabaseReference ref = database.getReference(city_name);
                    DatabaseReference mydam,temp;
                    temp=ref.child("Dams");
                    mydam = temp.child(mAuth.getUid());


                    String key = mydam.push().getKey();

                    ScheduleData schedule = new ScheduleData(txtDate.getText().toString(), txtTime.getText().toString(), txtDuration.getText().toString(), 1,"Active",key);
                    //ref.setValue(schedule);
                    mydam.child(key).setValue(schedule);
                    Toast.makeText(getContext(), "Schedule Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    txtDate.setText("");
                    txtDuration.setText("");
                    txtTime.setText("");
                }

            }
        });
        clearBtn = (Button) view.findViewById(R.id.clear);
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
