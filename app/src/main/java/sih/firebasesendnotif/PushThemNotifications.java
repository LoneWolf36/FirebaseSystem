package sih.firebasesendnotif;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PushThemNotifications.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PushThemNotifications#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PushThemNotifications extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");

    // Instance of FirebaseAuth
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PushThemNotifications() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PushThemNotifications.
     */
    // TODO: Rename and change types and number of parameters
    public static PushThemNotifications newInstance(String param1, String param2) {
        PushThemNotifications fragment = new PushThemNotifications();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.push_emergency_notif_fragment, container, false);
        final Button button=(Button) v.findViewById(R.id.push);
        final EditText e1=(EditText) v.findViewById(R.id.text1);
        final EditText e2=(EditText) v.findViewById(R.id.text2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1=e1.getText().toString();
                //int t2=Integer.parseInt(e1.getText().toString());
                //User u1= new User(ref.child("users").push().getKey(),t1);

                FirebaseUser user = mAuth.getCurrentUser();

                User u1 = new User(ref.child(user.getUid()).push().getKey(),t1);
                //note.setUid(database.child("notes").push().getKey());

                ref.child(user.getUid()).child(u1.getId()).setValue(u1);
                // DatabaseReference pushedPostRef = ref.push();
                //database.child("notes").child(note.getUid()).setValue(note);
                e1.setText("");
                e1.setText("");

                //  int PLACE_PICKER_REQUEST = 1;
                // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                //  startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

            }
        });

        return v;

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
