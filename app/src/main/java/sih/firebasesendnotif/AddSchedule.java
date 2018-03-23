package sih.firebasesendnotif;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;

public class AddSchedule extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
<<<<<<< HEAD
    DatabaseReference ref = database.getReference("");
    //DatabaseReference ref = database.getReference("users");
=======

//    DatabaseReference ref = database.getReference("");

>>>>>>> e9ac506a2d1d89c7ae7998e2a24bd15bc376c05a

    private FirebaseAuth mAuth;
    Button btnDatePicker, btnTimePicker;
    Button submitBtn;
    EditText txtDate, txtTime,txtDuration;
    private int mYear, mMonth, mDay, mHour, mMinute;
    SharedPreferences prefs ;
    String city_name;
    private OnFragmentInteractionListener mListener;
    String city;

    public AddSchedule() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = getArguments().getString("City");
        }
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        city_name = prefs.getString("city_name", "");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("pune");
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
        txtDate = (EditText) view.findViewById(R.id.in_date);
        btnTimePicker=(Button) view.findViewById(R.id.btn_time);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialogFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
            }
        });
        txtTime = (EditText) view.findViewById(R.id.in_time);
        txtDuration = (EditText) view.findViewById(R.id.txtDuration);
        submitBtn = (Button) view.findViewById(R.id.submit);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Date",txtDate.getText().toString());
                Log.d("Time",txtTime.getText().toString());
                Log.d("Duration",txtDuration.getText().toString());
<<<<<<< HEAD
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam;
                mydam = ref.child(mAuth.getUid());
=======

                ScheduleData schedule = new ScheduleData(txtDate.getText().toString(),txtTime.getText().toString(),txtDuration.getText().toString());
                ref.setValue(schedule);

>>>>>>> e9ac506a2d1d89c7ae7998e2a24bd15bc376c05a
                String key=mydam.push().getKey();
                mydam.child(key).setValue(txtDate.getText().toString());
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
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
