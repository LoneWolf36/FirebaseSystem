package sih.firebasesendnotif.Fragments;



/**
 * Created by groot on 20/3/18.
 */
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.CheckBox;
        import android.widget.LinearLayout;
        import android.widget.Spinner;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.messaging.FirebaseMessaging;

        import java.util.ArrayList;
        import java.util.List;

        import sih.firebasesendnotif.CityPickerActivity;
        import sih.firebasesendnotif.R;

        import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Belal on 18/09/16.
 */


public class SubscribeFragment extends Fragment {

    //static ArrayList<Boolean> checkstate;
    Spinner spinner;
    CheckBox c1,c2,c3,c4;
    private DatabaseReference myRef;
    LinearLayout ll;
    List<String> cities;
    List<CheckBox> citycb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
//        Boolean chk1 = prefs.getBoolean("c1", false);
//        Boolean chk2 = prefs.getBoolean("c2", false);
//        Boolean chk3 = prefs.getBoolean("c3", false);
//        Boolean chk4 = prefs.getBoolean("c4", false);
        View view = inflater.inflate(R.layout.frament_subscribe, container, false);
//        c1=view.findViewById(R.id.checkBox1);
//        c1.setChecked(chk1);
//
//        c1=view.findViewById(R.id.checkBox2);
//        c1.setChecked(chk2);
//
//        c1=view.findViewById(R.id.checkBox3);
//        c1.setChecked(chk3);
//
//        c1=view.findViewById(R.id.checkBox4);
//        c1.setChecked(chk4);
//

        return view;
    }



    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Subscribe ScheduleFragment");
//
//        c1 = (CheckBox) this.getView().findViewById(R.id.checkBox1);
//        c2 = (CheckBox) this.getView().findViewById(R.id.checkBox2);
//        c3 = (CheckBox) this.getView().findViewById(R.id.checkBox3);
//        c4 = (CheckBox) this.getView().findViewById(R.id.checkBox4);

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        cities = new ArrayList<String>();
        citycb = new ArrayList<CheckBox>();

        ll=this.getView().findViewById(R.id.linlay);
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("cities").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                Log.i("lw", "onDataChange: I am here!");
                for (DataSnapshot citySnapshot: dataSnapshot.getChildren()) {
                    String cityName = citySnapshot.getValue(String.class);
                    //xif(cities.c)
                    cities.add(cityName);
                    Boolean chk1 = prefs.getBoolean(cityName, false);
                    CheckBox cb = new CheckBox(getActivity());
                    cb.setChecked(chk1);
                    cb.setTextSize(22);
                    cb.setTextColor(((int) R.color.primary));
                    citycb.add(cb);
                    cb.setText(cityName);
                    ll.addView(cb);
                }



                for(final CheckBox cb:citycb){
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (cb.isChecked())

                            {
                                Log.i("topic", cb.getText().toString());
                                System.out.println(cb.getText().toString());
                                FirebaseMessaging.getInstance().subscribeToTopic(cb.getText().toString());
                                editor.putBoolean(cb.getText().toString(), true);
                                editor.apply();
                            }
                            else
                            {
                                FirebaseMessaging.getInstance().unsubscribeFromTopic(cb.getText().toString());
                                editor.putBoolean(cb.getText().toString(), false);
                                editor.apply();

                            }
                        }
                    });
                }
            //    saveArray(cities.toArray(new String[cities.size()]),"citieslocal",getActivity());

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
}