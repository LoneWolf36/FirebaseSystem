package sih.firebasesendnotif;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import sih.firebasesendnotif.Fragments.DamLocationPicker;

public class DamDetailsPickerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int PLACE_PICKER_REQUEST = 1;
    String city_pick;
    String dam_pick;
    String lat_pick;
    String lon_pick;
    String place_pick;

    Button button1,submit;
    TextView tvdam,tvlat,tvlong,tvPlace;
    EditText phone1,phone2,phone3;
    Long p1,p2,p3;


    Spinner spinner;

    // Instance of Firebase
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dam_location_picker);
        final SharedPreferences.Editor editor =getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        button1 = findViewById(R.id.act_place);
        submit = findViewById(R.id.act_submit);
        spinner = findViewById(R.id.act_spin_city);
        tvdam =findViewById(R.id.act_dam);
        tvlong =findViewById(R.id.act_lon);
        tvlat =findViewById(R.id.act_lat);

        tvPlace =findViewById(R.id.act_tv_place);
        phone1 = findViewById(R.id.phone1);
        phone2 = findViewById(R.id.phone2);
        phone3 = findViewById(R.id.phone3);

        //submit_loc = (Button) findViewById(R.id.submit_loc);
        myRef = FirebaseDatabase.getInstance().getReference();


        myRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                Log.i("lw", "onDataChange: I am here!");
                final List<String> cities = new ArrayList<String>();
                for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    cities.add(cityName);
                }

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(DamDetailsPickerActivity.this, android.R.layout.simple_spinner_item, cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dam_pick= tvdam.getText().toString();
                //lat_pick=tvlat.getText().toString();
                //lon_pick=tvlong.getText().toString();
                place_pick=tvPlace.getText().toString();

                if (city_pick.equals("")||dam_pick.equals("")||place_pick.equals("")){
                    Toast.makeText(DamDetailsPickerActivity.this, DamDetailsPickerActivity.this.getResources().getString(R.string.invalid_info), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(DamDetailsPickerActivity.this, NavbarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("City", city_pick);
                    //final SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                    // working !Log.d("Jai Log lat",lat_pick+"  "+lon_pick+" "+dam_pick);
                    editor.putString("Dam_Name", dam_pick);
                    editor.putString("Latitude", lat_pick);
                    editor.putString("Longitude", lon_pick);
                    editor.putString("Place", place_pick);
                    editor.putBoolean("admin_det", true);
                    String p1 = String.valueOf(phone1.getText());
                    String p2 = String.valueOf(phone2.getText());
                    String p3 = String.valueOf(phone3.getText());
                    editor.putString("phone1",p1);
                    editor.putString("phone2",p2);
                    editor.putString("phone3",p3);

                    //editor.putString("Place",tvPlace.getText().toString());
                    editor.apply();
                    startActivity(intent);

                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Ali","CLicked");
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(DamDetailsPickerActivity.this);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e){
                    e.printStackTrace();
                }

            }
        });
    }

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            city_pick = parent.getItemAtPosition(pos).toString();
            final SharedPreferences.Editor editor = this.getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
            editor.putString("city_name", city_pick);
            editor.putInt("city_pos", pos);
            editor.apply();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
            Toast.makeText(this, this.getResources().getString(R.string.select), Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(this.getResources().getString(R.string.close_act))
                .setMessage(this.getResources().getString(R.string.confirm_ex_ac))
                .setPositiveButton(this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                        editor.putBoolean("admin_login",false);
                        editor.apply();

                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                })
                .setNegativeButton(this.getResources().getString(R.string.no), null)
                .show();
    }

    // TODO: Rename and change types and number of parameters
    public static DamLocationPicker newInstance() {
        DamLocationPicker fragment = new DamLocationPicker();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        //  args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == this.RESULT_OK){
                final Place place = PlacePicker.getPlace(this, data);
                //final Place myPlace = place.get(0);
                LatLng queriedLocation = place.getLatLng();
                //GET PLACE
                //tvPlace.setText(place.getAddress());
                lat_pick = String.valueOf(queriedLocation.latitude);
                lon_pick = String.valueOf(queriedLocation.longitude);
                place_pick=  place.getAddress().toString();
                //Log.e("Latitude= " , ""+queriedLocation.latitude);
                tvlong.setText(getResources().getString(R.string.lat)+"= " + lon_pick);
                tvlat.setText(getResources().getString(R.string.longi)+"= " + lat_pick);
                // tvLat.setText(place.getAddress());
                tvPlace.setText(place.getAddress());
            }
        }

    }

}
