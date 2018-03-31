package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DamLocationPicker.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DamLocationPicker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DamLocationPicker extends Fragment{
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");

    private FirebaseAuth mAuth;
    int PLACE_PICKER_REQUEST = 1;
    double Lat;
    double Long;
    TextView tvPlace;
    TextView tvLat;
    TextView tvLong,tvDam;
    Button button1;
    Button submit_loc;
    Spinner spinner;
    String city_name;
    Button button;
    DatabaseReference myRef;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinner);
        myRef = FirebaseDatabase.getInstance().getReference();
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        submit_loc = (Button) view.findViewById(R.id.submit_loc);
    //    final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);




        myRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<String> cities = new ArrayList<String>();
                for (DataSnapshot citySnapshot : dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    cities.add(cityName);
                }
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city_name = adapterView.getItemAtPosition(i).toString();
                editor.putString("city_name",city_name);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(), getContext().getResources().getString(R.string.blank), Toast.LENGTH_SHORT).show();
            }
        });
        submit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d("Jai Log lat",Lat+"  "+Long);
                editor.putString("Latitude",String.valueOf(Lat));
                editor.putString("Longitude",String.valueOf(Long));
                editor.putString("Dam_Name",tvDam.getText().toString());
                editor.putString("Place",tvPlace.getText().toString());

                editor.apply();
                Log.d("lat", String.valueOf(Lat)  + "   " + String.valueOf(Long) + "   " +tvDam.getText().toString() + "     " + tvPlace.getText().toString());

                Toast.makeText(getContext(), getContext().getResources().getString(R.string.loc_full), Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.toPopulate, new AddScheduleFragment());
                ft.commit();
            }
        });
    }
    public static DamLocationPicker newInstance() {
        DamLocationPicker fragment = new DamLocationPicker();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dam_location_picker, container ,false);
        tvPlace= (TextView)v.findViewById(R.id.tvPlace);
        tvLat= (TextView)v.findViewById(R.id.tvLat);
        tvLong= (TextView)v.findViewById(R.id.tvLong);
        button1 = (Button) v.findViewById(R.id.fplace);
        //button = v.findViewById(R.id.complete_login);
        spinner = v.findViewById(R.id.city_picker);
        button = v.findViewById(R.id.complete_login);

        tvDam = v.findViewById(R.id.tvDam);

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        String lat =prefs.getString("Latitude","Unset");
        String lon =prefs.getString("Longitude","Unset");
        String dam_name =prefs.getString("Dam_Name","Unset");
        String place =prefs.getString("Place","Unset");
        String city =prefs.getString("city_name","Unset");

        tvLong.setText(lon);
        tvLat.setText(lat);
        tvDam.setText(dam_name);
        tvPlace.setText(place);
//
//        tvLat.setText(lat);
//        tvLong.setText(lon);
//        tvDam.setText(dam_name);
//        spinner.setSelection(2,true);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(getActivity());
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
        }catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == getActivity().RESULT_OK){
                final Place place = PlacePicker.getPlace(getActivity(), data);
                //final Place myPlace = place.get(0);
                LatLng queriedLocation = place.getLatLng();
                tvPlace.setText(place.getAddress());
                Lat = queriedLocation.latitude;
                Long = queriedLocation.longitude;
                //Log.e("Latitude= " , ""+queriedLocation.latitude);
                tvLong.setText(getContext().getResources().getString(R.string.lat)+"= " + String.valueOf(Lat));
                tvLat.setText(getContext().getResources().getString(R.string.longi)+"= " + String.valueOf(Long));
                // tvLat.setText(place.getAddress());
                editor.putString("Latitude",String.valueOf(Lat));
                editor.putString("Longitude",String.valueOf(Long));

            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
  //      mListener = null;
    }
}


