package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sih.firebasesendnotif.Classes.NotifyData;
import sih.firebasesendnotif.Classes.ScheduleData;
import sih.firebasesendnotif.R;
import sih.firebasesendnotif.RecyclerAdapter;

import static android.content.Context.MODE_PRIVATE;

public class ScheduleFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    java.util.List<ScheduleData> list;
    java.util.List<NotifyData> notifyDataList;
    java.util.List<String> city_list;
    DatabaseReference cityRef;
    RecyclerView recycle;
    //    Button notify, update;
    Context context;
    Boolean flagger;
    Boolean setLayoutFlag;
    ArrayList<String> keys;
    java.util.List<ScheduleData> update_list;
    SharedPreferences prefs;

    public ScheduleFragment() {
        // Required empty public constructor
        flagger = false;
        setLayoutFlag = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prefs = getSharedPreferences("JaisPrefrence", Context.MODE_PRIVATE);
        prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);
//        notify = (Button) v.findViewById(R.id.notify);
//        update = (Button) v.findViewById(R.id.update);

//        EasyFlipView easyFlipView = (EasyFlipView) v.findViewById(R.id.flip_view);
//        easyFlipView.onTouchEvent()
//        easyFlipView.flipTheView();
//        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
//            @Override
//            public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide)
//            {
//
//                // ...
//                // Your code goes here
//                // ...
//
//            }
//        });

        recycle = (RecyclerView) v.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();
        context = getContext();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String city_name = prefs.getString("city_name", "");
        keys =new ArrayList<>();
        update_list = new ArrayList<ScheduleData>();
        list = new ArrayList<ScheduleData>();
        city_list = new ArrayList<String>();
        notifyDataList = new ArrayList<>();
        DatabaseReference myRef1 = database.getReference(city_name);

        cityRef = FirebaseDatabase.getInstance().getReference();
        cityRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    Boolean chk1 = prefs.getBoolean(cityName, false);
                    if (chk1) {
                        Log.i("added", cityName + " in list");
                        city_list.add(cityName);
                    }
                }
                flagger = true;
                populate_my_list();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


//        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        //      FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //    final String city_name = prefs.getString("city_name", "");
//
//        if(prefs.getBoolean("admin_login",false)){
//            //Admin Login view only auth dam
//            Log.i("Admin view ","display");
//         //   DatabaseReference myRef1 = database.getReference(city_name);
//            myRef=myRef1.child("Dams").child(mAuth.getUid());
//
//            myRef.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, context);
//                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(context);
//                    recycle.setLayoutManager(recyce);
//                    recycle.setLayoutManager(new VegaLayoutManager());
//                    recycle.setItemAnimator(new DefaultItemAnimator());
//                    recycle.setAdapter(recyclerAdapter);
//
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        ScheduleData value = dataSnapshot1.getValue(ScheduleData.class);
//                        list.add(value);
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError error) {
//                    // Failed to read value
//                    Log.w("Hello", "Failed to read value.", error.toException());
//                }
//            });
//
//        }
//
//        else{
//            //User Login view all dams of city
////            String[] citieslocal =loadArray("citieslocal", getActivity());
//
////            Log.i("UserSSS","display here baby  Array contents: "+citieslocal.length);
//
////            for(String user_subscribed_city:city_list){
////            for(int i=0;i<citieslocal.length;i++){
////                String user_subscribed_city=citieslocal[i];
////                Log.i("City List user view ",   user_subscribed_city+" in user list");
////                DatabaseReference myRefuser = database.getReference(user_subscribed_city);
////                myRef=myRefuser.child("Dams");
////                myRef.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////
////
////                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, context);
////                        RecyclerView.LayoutManager recyce = new LinearLayoutManager(context);
////                        recycle.setLayoutManager(recyce);
////                        //recycle.setLayoutManager(new VegaLayoutManager());
////                        recycle.setItemAnimator(new DefaultItemAnimator());
////                        recycle.setAdapter(recyclerAdapter);
////
////                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
////                                ScheduleData value = dataSnapshot2.getValue(ScheduleData.class);
////                                list.add(value);
////                            }
////                        }
////
////
////                    }
////                    @Override
////                    public void onCancelled(DatabaseError error) {
////                        // Failed to read value
////                        Log.w("Hello", "Failed to read value.", error.toException());
////                    }
////                });
////            }
////
////
//       }
//
//        if(flagger==true) {
//            Log.d("flagger",flagger.toString());
//           // populate_my_list();
//        }
//        // Inflate the layout for this fragment
//
//    }

        return v;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


//    public String[] loadArray(String arrayName, Context mContext) {
//        SharedPreferences prefs = mContext.getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//        int size = prefs.getInt(arrayName + "_size", 0);
//        String array[] = new String[size];
//        for(int i=0;i<size;i++)
//            array[i] = prefs.getString(arrayName + "_" + i, null);
//        return array;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //  populate_my_list();
    }

    public void populate_my_list() {
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String city_name = prefs.getString("city_name", "");
        final RecyclerAdapter recyclerAdapter= new RecyclerAdapter(list,context);
        final RecyclerView.LayoutManager recyce=new LinearLayoutManager(context);;
        if (prefs.getBoolean("admin_login", false)) {
            //Admin Login view only auth dam
            Log.i("Admin view ", "display");
            DatabaseReference myRef1 = database.getReference(city_name);
            myRef = myRef1.child("Dams").child(mAuth.getUid());

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, context);
                    RecyclerView.LayoutManager recyce = new LinearLayoutManager(context);
                    recycle.setLayoutManager(recyce);
                    recycle.setItemAnimator(new DefaultItemAnimator());
                    recycle.setAdapter(recyclerAdapter);
                    //if (setLayoutFlag) {
                    //    recycle.setLayoutManager(new VegaLayoutManager());
                    //   setLayoutFlag = false;
                    //}

                    recycle.setLayoutManager(recyce);
                    recycle.setItemAnimator(new DefaultItemAnimator());
                    recycle.setAdapter(recyclerAdapter);

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ScheduleData value = dataSnapshot1.getValue(ScheduleData.class);
                        list.add(value);
                        keys.add(dataSnapshot.getKey());
                        Log.i(keys.toArray().toString(), "onDataChange: Keys from ScheduleFrag");
                    }
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });

        } else {
            //User Login view all dams of city
            Log.i("UserSSS", "display here baby Array contents: " + String.valueOf(city_list.toArray()));
            for (String user_subscribed_city : city_list) {
                Log.i("City List user view ", user_subscribed_city + " in user list");
                DatabaseReference myRefuser = database.getReference(user_subscribed_city);
                myRef = myRefuser.child("Dams");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list, context);
                        RecyclerView.LayoutManager recyce = new LinearLayoutManager(context);
                        recycle.setLayoutManager(recyce);
                        recycle.setItemAnimator(new DefaultItemAnimator());
                        recycle.setAdapter(recyclerAdapter);
                        //if (setLayoutFlag) {
                        //    recycle.setLayoutManager(new VegaLayoutManager());
                        //    setLayoutFlag = false;
                        //}

                        //recyce = new LinearLayoutManager(context);

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                ScheduleData value = dataSnapshot2.getValue(ScheduleData.class);
                                list.add(value);
                                keys.add(dataSnapshot.getKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("Hello", "Failed to read value.", error.toException());
                    }
                });
            }
        }
    }
}