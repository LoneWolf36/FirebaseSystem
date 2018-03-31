package sih.firebasesendnotif.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class SubscribeFragment extends Fragment {

    //static ArrayList<Boolean> checkstate;
    Spinner spinner;
    CheckBox c1, c2, c3, c4;
    LinearLayout ll;
    Button b1;
    List<String> cities;
    List<Switch> citycb;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        View view = inflater.inflate(R.layout.frament_subscribe, container, false);
        b1 = (Button) view.findViewById(R.id.subscribe);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Subscribe ScheduleFragment");

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        cities = new ArrayList<String>();
        citycb = new ArrayList<Switch>();

        b1 = (Button) view.findViewById(R.id.subscribe);

        ll = this.getView().findViewById(R.id.linlay);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                Log.i("lw", "onDataChange: I am here!");
                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    //xif(cities.c)
                    cities.add(cityName);
                    Boolean chk1 = prefs.getBoolean(cityName, false);
                    Switch cb = new Switch(getActivity());
                    cb.setChecked(chk1);
                    cb.setTextSize(22);
                    cb.setTextColor(((int) R.color.primary));
                    citycb.add(cb);
                    cb.setText(cityName);
                    ll.addView(cb);
                }

                for (final Switch cb : citycb) {
                    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (cb.isChecked())

                            {
                                Log.i("topic", cb.getText().toString());
                                System.out.println(cb.getText().toString());
                                FirebaseMessaging.getInstance().subscribeToTopic(cb.getText().toString());
                                editor.putBoolean(cb.getText().toString(), true);
                                editor.apply();


                            } else {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(cb.getText().toString());
                                editor.putBoolean(cb.getText().toString(), false);
                                editor.apply();
                            }
                        }
                    });
                }
                //    saveArray(cities.toArray(new String[cities.size()]),"citieslocal",getActivity());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("cities").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Is better to use a List, because you don't know the size
                        // of the iterator returned by dataSnapshot.getChildren() to
                        // initialize the array
                        Log.i("lw", "onDataChange: I am here!");
                        Boolean flag = false;
                        for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                            String cityName = citySnapshot.getValue(String.class);
                            //xif(cities.c)
                            cities.add(cityName);
                            Boolean chk1 = prefs.getBoolean(cityName, false);
                            Switch cb = new Switch(getActivity());

                            if (chk1) {
                                flag = true;
                            }
                        }
                        if (flag == true) {
                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.toPopulate, new ScheduleFragment());
                            ft.commit();
                        } else {
                            Toast.makeText(getContext(), getContext().getResources().getString(R.string.no_city), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}