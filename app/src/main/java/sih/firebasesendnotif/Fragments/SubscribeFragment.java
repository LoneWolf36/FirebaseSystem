package sih.firebasesendnotif.Fragments;


/**
 * Created by groot on 20/3/18.
 */

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

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

public class SubscribeFragment extends Fragment {

    CheckBox c1, c2, c3, c4;
    DatabaseReference myRef;
    LinearLayout ll;
    List<String> cities;
    List<Switch> citycb;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ProgressDialog progress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        View view = inflater.inflate(R.layout.frament_subscribe, container, false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Subscribe ScheduleFragment");

        editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        cities = new ArrayList<String>();
        citycb = new ArrayList<Switch>();

        ll = this.getView().findViewById(R.id.linlay);
        myRef = FirebaseDatabase.getInstance().getReference();
        startASycnc();
    }

    public void task(final SharedPreferences preferences, final SharedPreferences.Editor editor) {
        Log.i("lw", "task: Running!");
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
                    prefs = preferences;
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

//
//    public boolean saveArray(String[] array, String arrayName, Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt(arrayName +"_size", array.length);
//        for(int i=0;i<array.length;i++) {
//            Log.d("Save Array", array[i]);
//            editor.putString(arrayName + "_" + i, array[i]);
//        }return editor.commit();
//    }


    public void startASycnc() {
        new StartAsyncTask().execute();
    }

    public class StartAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            task(prefs, editor);
            Log.i("lw", "doInBackground: Working!");
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("lw", "onPreExecute: Yay!");
            progress = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.setMessage("Populating list...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("lw", "onPostExecute: Awwh!");
            progress.dismiss();
        }
    }
}