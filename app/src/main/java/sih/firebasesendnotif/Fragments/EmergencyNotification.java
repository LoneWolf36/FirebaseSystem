package sih.firebasesendnotif.Fragments;

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
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmergencyNotification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmergencyNotification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyNotification extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    SharedPreferences prefs ;
    String city_name;
    Button submit;
    LinearLayout ll;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public EmergencyNotification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyNotification.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyNotification newInstance(String param1, String param2) {
        EmergencyNotification fragment = new EmergencyNotification();
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
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        city_name = prefs.getString("city_name", "");
        Log.d("La", "Im HERE BABY1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("La", "Im HERE BABY");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //ref = database.getReference("pune");
        View v = inflater.inflate(R.layout.fragment_emergency_notification, container, false);

        //Button submit=(Button) v.findViewById(R.id.push);
        return v;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
        final EditText text = (EditText) view.findViewById(R.id.text1);
        submit=(Button) view.findViewById(R.id.push);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam,myalert;
                myalert = ref.child("Alert");
                //mydam = ref.child(mAuth.getUid());
                //myalert = mydam.child("Alert");
                //myalert = ref.child("Alert");
                String key=myalert.push().getKey();
                myalert.child(key).setValue(text.getText().toString());
                //myalert.child(key).setValue(text.getText().toString());
                //myalert.child(key).setValue(text.getText().toString());
                text.setText("");
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
//
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
