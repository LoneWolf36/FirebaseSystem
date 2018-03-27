package sih.firebasesendnotif;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import sih.firebasesendnotif.Fragments.AddScheduleFragment;
import sih.firebasesendnotif.Fragments.ContactAuthority;
import sih.firebasesendnotif.Fragments.EmergencyContacts;
import sih.firebasesendnotif.Fragments.EmergencyNotificationFragment;
import sih.firebasesendnotif.Fragments.ScheduleFragment;
import sih.firebasesendnotif.Fragments.SubscribeFragment;

public class NavbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;

    // Instance of FirebaseAuth
    private FirebaseAuth mAuth;

    //String city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        final SharedPreferences prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);

        // City picker intent and extract information from bundle
       // city_name = getIntent().getStringExtra("City");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        AwesomeBar bar = findViewById(R.id.bar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });

        bar.displayHomeAsUpEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        FrameLayout fL = (FrameLayout) findViewById(R.id.fLFragments);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addScheduleListener);

        // Initialize FirebaseAuth instance
//        mAuth = FirebaseAuth.getInstance();

        // Firebase database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference(mAuth.getUid());
      //  myRef.child("city").setValue(city_name);


        if(!prefs.getBoolean("admin_login",false))
        {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.add_schedule).setVisible(false);
            menu.findItem(R.id.nav_emergency).setVisible(false);
            menu.findItem(R.id.activity_location_picker).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(false);
        }
        else{
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.add_schedule).setVisible(true);
            menu.findItem(R.id.nav_emergency).setVisible(true);
            menu.findItem(R.id.activity_location_picker).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_login).setVisible(false);
        }


        FragmentTransaction ft;

        Boolean first_open = prefs.getBoolean("first_open", true);
        if(first_open){
            fab.setVisibility(View.INVISIBLE);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new SubscribeFragment());
            ft.commit();
            editor.putBoolean("first_open", false);
            editor.apply();
        }
        else {
            fab.setVisibility(View.INVISIBLE);
            ft=getSupportFragmentManager().beginTransaction();
            ft.add(R.id.toPopulate, new ScheduleFragment());
            ft.commit();
        }

    }


    private View.OnClickListener addScheduleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new AddScheduleFragment());
            ft.commit();
            fab.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this,R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Exit")
                    .setMessage("Are you sure you want to close the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // Schedule fragment
        if (id == R.id.nav_schedule) {
            //Fragment fragment = getFragmentManager().findFragmentById(R.id.fMtoDisplay);
            fab.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new ScheduleFragment());
            ft.commit();
        }
        // Emergency fragment
        else if (id == R.id.nav_emergency) {
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new EmergencyNotificationFragment());
            ft.commit();
        }
        // Logout Activity
        else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(this,R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                    //        SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                      //      editor.putString("city_name", "");
                        //    editor.apply();

                            mAuth =FirebaseAuth.getInstance();
                            final SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                            editor.putBoolean("admin_login",false);
                            editor.apply();
                            mAuth.signOut();
                         //   startActivity(new Intent(NavbarActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        // Location picker fragment
        else if (id == R.id.activity_location_picker) {
            new AlertDialog.Builder(this,R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("PickPlace")
                    .setMessage("Do you want set this position for warning?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(NavbarActivity.this, LocationPickerActivity.class));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        // Add schedule fragment
        else if (id == R.id.add_schedule) {
            fab.setVisibility(View.INVISIBLE);
//            Bundle bundle = new Bundle();
//            bundle.putString("City", city_name);
            AddScheduleFragment add = new AddScheduleFragment();
   //         add.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, add);
            ft.commit();
        }
        // Subscribe fragment
        else if (id == R.id.nav_subscribe) {
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new SubscribeFragment());
            ft.commit();
        }
        else if(id== R.id.nav_contact){
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new ContactAuthority());
            ft.commit();
        }
        //emergency contacts
        else if (id == R.id.emergency_contacts) {
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new EmergencyContacts());

            ft.commit();
        }
        else if (id == R.id.nav_login) {
            Intent intent = new Intent(NavbarActivity.this, LoginActivity.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
