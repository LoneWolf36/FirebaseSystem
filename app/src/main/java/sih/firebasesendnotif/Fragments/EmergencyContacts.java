package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmergencyContacts.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmergencyContacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmergencyContacts extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button callpolice;
    Button call_firefighter;
    Button call_disaster;
    Button msg;
    SharedPreferences prefs;
    private OnFragmentInteractionListener mListener;

    public EmergencyContacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EmergencyContacts.
     */
    // TODO: Rename and change types and number of parameters
    public static EmergencyContacts newInstance(String param1, String param2) {
        EmergencyContacts fragment = new EmergencyContacts();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_emergency_contacts, container, false);

        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        callpolice = (Button) v.findViewById(R.id.call_police);
        call_firefighter = (Button) v.findViewById(R.id.calldis);
        call_disaster =(Button) v.findViewById(R.id.callfirebrigade);
        msg = (Button)v.findViewById(R.id.msg);
        call_firefighter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                final String number = "101";

                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));


                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getContext().getResources().getString(R.string.fire_brig_connect))
                        .setMessage(getContext().getResources().getString(R.string.confirm_call))
                        .setPositiveButton(getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getContext().getResources().getString(R.string.no), null)
                        .show();
                /*
                String number = "100";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);*/
            }

        });
        call_disaster.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                final String number = "108";

                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));


                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getContext().getResources().getString(R.string.emerg_connect))
                        .setMessage(getContext().getResources().getString(R.string.confirm_call))
                        .setPositiveButton(getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getContext().getResources().getString(R.string.no), null)
                        .show();
                /*
                String number = "100";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);*/
            }

        });
        callpolice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                final String number = "100";

                final Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));


                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getContext().getResources().getString(R.string.police_connect))
                        .setMessage(getContext().getResources().getString(R.string.confirm_call))
                        .setPositiveButton(getContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(getContext().getResources().getString(R.string.no), null)
                        .show();
                /*
                String number = "100";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +number));
                startActivity(intent);*/
                                           }

                                       }
        );

        if (!prefs.getBoolean("admin_login", false)) {
            msg.setVisibility(View.INVISIBLE);
        } else {
            msg.setVisibility(View.VISIBLE);
        }
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p1 = prefs.getString("phone1",null);
                String p2 = prefs.getString("phone2",null);
                String p3 = prefs.getString("phone3",null);

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" +p1+","+p2+","+p3));
                sendIntent.putExtra("sms_body",getContext().getResources().getString(R.string.water) + prefs.getString("Dam_name","") + getContext().getResources().getString(R.string.jo)+prefs.getString("Place","") + getContext().getResources().getString(R.string.loca));
                startActivity(sendIntent);
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

   // @Override
   /* public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
