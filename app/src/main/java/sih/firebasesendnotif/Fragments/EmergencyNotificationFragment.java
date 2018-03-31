package sih.firebasesendnotif.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import sih.firebasesendnotif.Classes.EmergencyData;
import sih.firebasesendnotif.R;

import static android.content.Context.MODE_PRIVATE;

public class EmergencyNotificationFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("");
    private FirebaseAuth mAuth;
    SharedPreferences prefs ;
    String city_name;
    Button submit, postfb, posttw;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public EmergencyNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        city_name = prefs.getString("city_name", "");
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        Twitter.initialize(getContext());
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //ref = database.getReference("pune");
        View v = inflater.inflate(R.layout.fragment_emergency_notification, container, false);
        return v;
        }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText text = (EditText) view.findViewById(R.id.text1);
        submit=(Button) view.findViewById(R.id.push);
        postfb = view.findViewById(R.id.post_fb);
        posttw = view.findViewById(R.id.post_tw);

        //final SharedPreferences.Editor editor = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getActivity().getSharedPreferences("JaisPrefrence", MODE_PRIVATE);

        postfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text.getText().toString();
                String mess =getContext().getResources().getString(R.string.water)+prefs.getString("Dam_Name","")+getContext().getResources().getString(R.string.jo)+prefs.getString("Place","")+getContext().getResources().getString(R.string.loca)+
                        getContext().getResources().getString(R.string.auth_mes)+message;
                Log.i("JL",mess);
               if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("http://mowr.gov.in/"))
                           .setQuote(mess)
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });

        posttw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = text.getText().toString();
                String mess =getContext().getResources().getString(R.string.water)+prefs.getString("Dam_Name","")+getContext().getResources().getString(R.string.jo)+prefs.getString("Place","")+getContext().getResources().getString(R.string.loca)+
                        getContext().getResources().getString(R.string.auth_mes)+message;
                Log.i("JL",mess);
                TwitterConfig config = new TwitterConfig.Builder(getContext())
                        .logger(new DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                        .debug(true)
                        .build();
                Twitter.initialize(config);
                TweetComposer.Builder builder = new TweetComposer.Builder(getContext())
                        .text(mess);
                builder.show();
                }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = database.getReference(city_name);
                DatabaseReference mydam,myalert;
                myalert = ref.child("Alert");
                //mydam = ref.child(mAuth.getUid());
                //myalert = mydam.child("Alert");
                //myalert = ref.child("Alert");
                String dam_name =prefs.getString("Dam_Name","Unset");

                String key=myalert.push().getKey();
               // EmergencyData emergencyData =new EmergencyData(text.getText().toString(),mAuth.getUid(),"1",city_name);
               EmergencyData emergencyData =new EmergencyData(text.getText().toString(),dam_name,"123",city_name);
                myalert.child(key).setValue(emergencyData);
                //myalert.child(key).setValue(text.getText().toString());
                //myalert.child(key).setValue(text.getText().toString());
                text.setText("");
            }
        });
    }
}
