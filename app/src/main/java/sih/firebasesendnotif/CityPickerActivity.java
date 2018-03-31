package sih.firebasesendnotif;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CityPickerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String city_pick;
    Button button;
    Spinner spinner;

    // Instance of Firebase
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);

        button = findViewById(R.id.complete_login);
        spinner = findViewById(R.id.city_picker);

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

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(CityPickerActivity.this, android.R.layout.simple_spinner_item, cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city_pick.equals("")){
                    Toast.makeText(CityPickerActivity.this, CityPickerActivity.this.getResources().getString(R.string.invalid_info), Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CityPickerActivity.this, NavbarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("City", city_pick);
                    startActivity(intent);
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
}
