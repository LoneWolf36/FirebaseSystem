package sih.firebasesendnotif;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerActivity extends AppCompatActivity {
    int PLACE_PICKER_REQUEST = 1;
    double Lat;
    double Long;
    TextView tvPlace;
    TextView tvLat;
    TextView tvLong;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_picker);
        tvPlace= (TextView)findViewById(R.id.tvPlace);

    }
//    private void permission() {
//        if (ContextCompat.checkSelfPermission(PlacePickerActivity.this, Manifest.permission.) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                new AlertDialog.Builder(this)
//                        .setTitle("Permission required")
//                        .setMessage("This permission is needed for accessing images from the storage")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(PlacePickerActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_FINE_LOCATION_CODE);
//                            }
//                        })
//                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .create()
//                        .show();
//            } else
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_FINE_LOCATION_REQUEST_CODE);
//        }
//    }
    public void goPlacePicker(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(PlacePickerActivity.this), PLACE_PICKER_REQUEST);
        }catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PLACE_PICKER_REQUEST){
            if(resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(PlacePickerActivity.this, data);

                tvPlace.setText(place.getAddress());
//                tvLong.setText(place.getAddress());
//                tvLat.setText(place.getAddress());
            }
        }

    }
}
