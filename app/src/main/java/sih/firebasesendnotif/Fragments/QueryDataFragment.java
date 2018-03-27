package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.vega.library.VegaLayoutManager;

//import sih.firebasesendnotif.Classes.NotifyData;
import sih.firebasesendnotif.Classes.QueryData;
import sih.firebasesendnotif.MyQueryDataRecyclerViewAdapter;
import sih.firebasesendnotif.R;
//import sih.firebasesendnotif.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class QueryDataFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef ;
    java.util.List<QueryData> queryDataList;
    RecyclerView qrecycle;
    Context context;

    public QueryDataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_querydata, container, false);
        qrecycle=(RecyclerView)view.findViewById(R.id.qrecycle);
        database = FirebaseDatabase.getInstance();
        context=getContext();

        SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String city_name = prefs.getString("city_name", "");
        queryDataList = new ArrayList<>();
        myRef = database.getReference(city_name).child("Queries");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyQueryDataRecyclerViewAdapter recyclerAdapter = new MyQueryDataRecyclerViewAdapter(queryDataList,context);
                RecyclerView.LayoutManager qrecyce = new LinearLayoutManager(context);
//                qrecycle.setLayoutManager(qrecyce);
                qrecycle.setLayoutManager(new VegaLayoutManager());
                qrecycle.setItemAnimator( new DefaultItemAnimator());
                qrecycle.setAdapter(recyclerAdapter);

                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                   // QueryData value = dataSnapshot1.getValue(QueryData.class);
                    QueryData value=dataSnapshot1.getValue(QueryData.class);
                    queryDataList.add(value);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
