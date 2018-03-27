package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sih.firebasesendnotif.CityPickerActivity;
import sih.firebasesendnotif.LocationPickerActivity;
import sih.firebasesendnotif.NavbarActivity;
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
public class DamLocationPicker extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int PLACE_PICKER_REQUEST = 1;
    double Lat;
    double Long;
    TextView tvPlace;
    TextView tvLat;
    TextView tvLong;
    Button button1;
    Spinner spinner;
    String city_pick;
    Button button;
    private DatabaseReference myRef;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
 //   private OnFragmentInteractionListener mListener;

    public DamLocationPicker() {
        // Required empty public constructor
    }

    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DamLocationPicker.
     */
    // TODO: Rename and change types and number of parameters
    public static DamLocationPicker newInstance() {
        DamLocationPicker fragment = new DamLocationPicker();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
      //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    myRef = FirebaseDatabase.getInstance().getReference();
//        myRef.child("cities").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // Is better to use a List, because you don't know the size
//            // of the iterator returned by dataSnapshot.getChildren() to
//            // initialize the array
//            Log.i("lw", "onDataChange: I am here!");
//            final List<String> cities = new ArrayList<String>();
//
//            for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
//                String cityName = citySnapshot.getValue(String.class);
//                cities.add(cityName);
//            }
//
//            ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity().this, android.R.layout.simple_spinner_item, cities);
//            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinner.setAdapter(cityAdapter);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//        }
//    });

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//            spinner.setOnItemSelectedListener(this);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (city_pick.equals("")){
//                        //Toast.makeText(getActivity().this, "Invalid information", Toast.LENGTH_SHORT).show();
//                    }else{
//                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                        try {
//                        Intent intent = builder.build(getActivity());
//                        //Intent intent = new Intent(getActivity().this, NavbarActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.putExtra("City", city_pick);
//                        startActivity(intent);
//                        }catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        }
//    }

//    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        // An item was selected. You can retrieve the selected item using
//        // parent.getItemAtPosition(pos)
//        city_pick = parent.getItemAtPosition(pos).toString();
//        final SharedPreferences.Editor editor = this.getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
//        editor.putString("city_name", city_pick);
//        editor.apply();
//    }

//    public void onNothingSelected(AdapterView<?> parent) {
//        // Another interface callback
//        Toast.makeText(this, "Please select something", ""Toast.LENGTH_SHORT).show();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dam_location_picker, container ,false);
        tvPlace= (TextView)v.findViewById(R.id.tvPlace);
        tvLat= (TextView)v.findViewById(R.id.tvLat);
        tvLong= (TextView)v.findViewById(R.id.tvLong);
        button1 = (Button) v.findViewById(R.id.button1);
        //button = v.findViewById(R.id.complete_login);
        spinner = v.findViewById(R.id.city_picker);
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
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == getActivity().RESULT_OK){
                final Place place = PlacePicker.getPlace(getActivity(), data);
                //final Place myPlace = place.get(0);
                LatLng queriedLocation = place.getLatLng();
                tvPlace.setText(place.getAddress());
                Lat = queriedLocation.latitude;
                Long = queriedLocation.longitude;
                //Log.e("Latitude= " , ""+queriedLocation.latitude);
                tvLong.setText("Longitude= " + String.valueOf(Lat));
                tvLat.setText("Latitude= " + String.valueOf(Long));
                // tvLat.setText(place.getAddress());
            }
        }

    }





    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
  //      mListener = null;
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }

  //  @Override
 //   public void onNothingSelected(AdapterView<?> parent) {

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

