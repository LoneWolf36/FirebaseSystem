package sih.firebasesendnotif;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Add_schedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Add_schedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add_schedule extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Add_schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add_schedule.
     */
    // TODO: Rename and change types and number of parameters
    public static Add_schedule newInstance(String param1, String param2) {
        Add_schedule fragment = new Add_schedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_schedule, container, false);
        return v;
    }




    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtDate = (EditText) view.findViewById(R.id.in_date);
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
        txtTime = (EditText) view.findViewById(R.id.in_time);


        btnTimePicker=(Button) view.findViewById(R.id.btn_time);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimeDialogFragment();
                newFragment.show(getActivity().getFragmentManager(),"TimePicker");
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
