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
    DatabaseReference ref = database.getReference("");
    //DatabaseReference ref = database.getReference("users");

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
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam;
                mydam = ref.child(mAuth.getUid());
                String key=mydam.push().getKey();
                mydam.child(key).setValue(txtDate.getText().toString());
            }
        });

    }
//    private void showDatePicker() {
//        DateFragment date = new DateFragment();
//        /**
//         * Set Up Current Date Into dialog
//         */
//        Calendar calender = Calendar.getInstance();
//        Bundle args = new Bundle();
//        //calender.set(1900+mYear,mMonth,mDay);
//        args.putInt("year", calender.get(Calendar.YEAR));
//        args.putInt("month", calender.get(Calendar.MONTH));
//        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
//        date.setArguments(args);
//        /**
//         * Set Call back to capture selected date
//         */
//        date.setCallBack(ondate);
//        date.show(getActivity().getFragmentManager(), "Date Picker");
//    }
//
//    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
//
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//
//
//            SimpleDateFormat fdf = new SimpleDateFormat("yyyy-MM-dd");
//
//
//
//            Date date= new Date();
//            date.setMonth(monthOfYear);
//            date.setDate(dayOfMonth);
//            date.setYear(year);
//
//
//            //
//            // Display a date in day, month, year format
//            //
//            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String today = formatter.format(date);
//
//
//
//
//
//
//            //txtDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)+ "-" + String.valueOf(year));
//            txtDate.setText(today+" "+ year);
//        }
//    };




//    public void showTimePicker(){
//        TimeDialogFragment tdf = new TimeDialogFragment();
//        tdf.show(getFragmentManager().beginTransaction(), "TimePickerFragment");
//    }
//
//    TimePickerDialog.OnTimeSetListener test = new TimePickerDialog.OnTimeSetListener(){
//        @Override
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            time_finish.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
//        }
//    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
