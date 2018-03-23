package sih.firebasesendnotif;

import android.content.Context;
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

public class CityPicker extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public String city_pick;
    Button button;
    Spinner spinner;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker2);

        button = findViewById(R.id.complete_login);
        spinner = findViewById(R.id.city_picker);
        sharedPref=getApplication().getSharedPreferences("City_info",Context.MODE_PRIVATE);
        editor=sharedPref.edit();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (city_pick.equals("")){
                    Toast.makeText(CityPicker.this, "Invalid information", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(CityPicker.this, NavBarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    Bundle b=new Bundle();
//                    b.putString("city",city_pick);
//                    intent.putExtras(b);

                    editor.putString("City",city_pick);
                    editor.commit();
                    startActivity(intent);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        city_pick = parent.getItemAtPosition(pos).toString();
        Log.d("city",city_pick);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        Toast.makeText(this, "Please select something", Toast.LENGTH_SHORT).show();
    }
}
