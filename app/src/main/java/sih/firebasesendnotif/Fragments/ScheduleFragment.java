package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;

import java.util.ArrayList;

import sih.firebasesendnotif.Classes.NotifyData;
import sih.firebasesendnotif.R;
import sih.firebasesendnotif.RecyclerAdapter;
import sih.firebasesendnotif.Classes.ScheduleData;

import static android.content.Context.MODE_PRIVATE;

public class ScheduleFragment extends Fragment{

    FirebaseDatabase database;
    DatabaseReference myRef ;
    java.util.List<ScheduleData> list;
    java.util.List<NotifyData> notifyDataList;
    RecyclerView recycle;
    Button notify,update;
    Context context;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_schedule,container,false);
      //  view = (Button) v.findViewById(R.id.view);
        notify = (Button) v.findViewById(R.id.notify);
        update = (Button) v.findViewById(R.id.update);
        recycle = (RecyclerView) v.findViewById(R.id.recycle);
        database = FirebaseDatabase.getInstance();
        context=getContext();

        SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String city_name = prefs.getString("city_name", "");
        list = new ArrayList<ScheduleData>();
        notifyDataList=new ArrayList<>();
        myRef = database.getReference(city_name).child(mAuth.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,context);
                RecyclerView.LayoutManager recyce = new LinearLayoutManager(context);
                recycle.setLayoutManager(recyce);
                recycle.setLayoutManager(new VegaLayoutManager());
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);

                 for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                     ScheduleData value = dataSnapshot1.getValue(ScheduleData.class);
                     list.add(value);
                 }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });


        return v;
        // Inflate the layout for this fragment

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
