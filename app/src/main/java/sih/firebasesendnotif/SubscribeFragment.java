package sih.firebasesendnotif;



/**
 * Created by groot on 20/3/18.
 */
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;

        import com.google.firebase.messaging.FirebaseMessaging;

        import java.util.ArrayList;

        import static android.content.Context.MODE_PRIVATE;


public class SubscribeFragment extends Fragment {

    //static ArrayList<Boolean> checkstate;
    CheckBox c1,c2,c3,c4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        Boolean chk1 = prefs.getBoolean("c1", false);
        Boolean chk2 = prefs.getBoolean("c2", false);
        Boolean chk3 = prefs.getBoolean("c3", false);
        Boolean chk4 = prefs.getBoolean("c4", false);


        View view = inflater.inflate(R.layout.frament_subscribe, container, false);
        c1=(CheckBox) view.findViewById(R.id.checkBox1);
        c1.setChecked(chk1);

        c1=(CheckBox)view.findViewById(R.id.checkBox2);
        c1.setChecked(chk2);

        c1=(CheckBox)view.findViewById(R.id.checkBox3);
        c1.setChecked(chk3);

        c1=(CheckBox)view.findViewById(R.id.checkBox4);
        c1.setChecked(chk4);
        //return inflater.inflate(R.layout.frament_subscribe, container, false);

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Subscribe to Schedule");

        // final CheckBox c1 = (CheckBox) this.getView().findViewById(R.id.checkBox1);


        c1 = (CheckBox) this.getView().findViewById(R.id.checkBox1);
        c2 = (CheckBox) this.getView().findViewById(R.id.checkBox2);
        c3 = (CheckBox) this.getView().findViewById(R.id.checkBox3);
        c4 = (CheckBox) this.getView().findViewById(R.id.checkBox4);

        final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        //editor.putBoolean("c1", "Elena");
        //editor.putBoolean("c1", "Elena");
        //editor.apply();
//        final CheckBox c2 = (CheckBox) getView().findViewById(R.id.checkBox2);
//        final CheckBox c3 = (CheckBox) getView().findViewById(R.id.checkBox3);
//        final CheckBox c4 = (CheckBox) getView().findViewById(R.id.checkBox4);


        c1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (c1.isChecked())
                {
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
                    editor.putBoolean("c1", true);
                    editor.apply();
                    //Perform action when you touch on checkbox and it change to selected state
                }
                else
                {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotifications");
                    //Perform action when you touch on checkbox and it change to unselected state
                    editor.putBoolean("c1", false);
                    editor.apply();

                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (c2.isChecked())
                {
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
                    editor.putBoolean("c2", true);
                    editor.apply();
                    //Perform action when you touch on checkbox and it change to selected state

                    //Perform action when you touch on checkbox and it change to selected state
                }
                else
                {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotifications");
                    //Perform action when you touch on checkbox and it change to unselected state
                    editor.putBoolean("c2", false);
                    editor.apply();

                    //Perform action when you touch on checkbox and it change to unselected state
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (c3.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
                    editor.putBoolean("c3", true);
                    editor.apply();
                    //Perform action when you touch on checkbox and it change to selected state

                }
                else
                {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotifications");
                    //Perform action when you touch on checkbox and it change to unselected state
                    editor.putBoolean("c3", false);
                    editor.apply();

                    //Perform action when you touch on checkbox and it change to unselected state
                }
            }
        });
        c4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (c4.isChecked())
                {
                    //Perform action when you touch on checkbox and it change to selected state
                    FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");
                    editor.putBoolean("c4", true);
                    editor.apply();
                    //Perform action when you touch on checkbox and it change to selected state

                }
                else
                {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("pushNotifications");
                    //Perform action when you touch on checkbox and it change to unselected state
                    editor.putBoolean("c4", false);
                    editor.apply();
                    //Perform action when you touch on checkbox and it change to unselected state
                }
            }
        });

    }
}