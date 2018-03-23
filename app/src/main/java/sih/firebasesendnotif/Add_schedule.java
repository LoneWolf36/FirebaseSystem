package sih.firebasesendnotif;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    DatabaseReference ref;
    FirebaseAuth mAuth;
    Button btnDatePicker, btnTimePicker;
    Button button;
    EditText txtDate, txtTime,txtDuration;
    SharedPreferences sp;
    private int mYear, mMonth, mDay, mHour, mMinute;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String city;
    private OnFragmentInteractionListener mListener;

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
        //Bundle b=this.getArguments()


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.push_emergency_notif_fragment, container, false);
        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=(Button) view.findViewById(R.id.submit);
        sp=getActivity().getSharedPreferences("City_info",Context.MODE_PRIVATE);
        city=sp.getString("City","");
        ref=database.getReference(city);
        mAuth=FirebaseAuth.getInstance();
        btnDatePicker = (Button) view.findViewById(R.id.btn_date);
        Log.d("cit_in_add",city);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1=txtDate.getText().toString();
                String t2=txtTime.getText().toString();
                String t3=txtDuration.getText().toString();
                //int t2=Integer.parseInt(e1.getText().toString());
                String schedId=ref.child(mAuth.getUid()).push().getKey();
                ScheduleData u1= new ScheduleData(t1,t2,t3);

                //note.setUid(database.child("notes").push().getKey());


                ref.child(mAuth.getUid()).child(schedId).setValue(u1);
                // DatabaseReference pushedPostRef = ref.push();
                //database.child("notes").child(note.getUid()).setValue(note);
                txtDate.setText("");
                txtTime.setText("");
                txtDuration.setText("");

                //  int PLACE_PICKER_REQUEST = 1;
                // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                //  startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            }
        });
    }

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
