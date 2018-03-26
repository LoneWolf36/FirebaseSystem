package sih.firebasesendnotif;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import sih.firebasesendnotif.Fragments.QueryDataFragment;
import sih.firebasesendnotif.Fragments.ScheduleFragment;
import sih.firebasesendnotif.Fragments.SubscribeFragment;

public class NavbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;

    // Instance of FirebaseAuth
    private FirebaseAuth mAuth;

    String city_name;
    //NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // City picker intent and extract information from bundle
        city_name = getIntent().getStringExtra("City");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        AwesomeBar bar = findViewById(R.id.bar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        bar.addAction(R.drawable.awsb_ic_edit_animated, "Compose");

        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                Toast.makeText(getBaseContext(), actionItem.getText()+" clicked", Toast.LENGTH_LONG).show();
            }
        });

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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.toPopulate, new ScheduleFragment());
        ft.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addScheduleListener);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        myRef.child("city").setValue(city_name);

        //navigationView = (NavigationView) findViewById(R.id.nav_view);
//        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_schedule).setVisible(false);
        //navigationView.getMenu().findItem(R.id.add_schedule).setVisible(false);

        //NavigationView  navigationView1
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.add_schedule).setVisible(false);
        menu.findItem(R.id.nav_emergency).setVisible(false);
        menu.findItem(R.id.activity_location_picker).setVisible(false);
        menu.findItem(R.id.nav_logout).setVisible(false);
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
            new AlertDialog.Builder(this)
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
            new AlertDialog.Builder(this)
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

                            mAuth.signOut();
                            startActivity(new Intent(NavbarActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        // Location picker fragment
        else if (id == R.id.activity_location_picker) {
            startActivity(new Intent(NavbarActivity.this, LocationPickerActivity.class));
        }
        // Add schedule fragment
        else if (id == R.id.add_schedule) {
            fab.setVisibility(View.INVISIBLE);
            Bundle bundle = new Bundle();
            bundle.putString("City", city_name);
            AddScheduleFragment add = new AddScheduleFragment();
            add.setArguments(bundle);
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
<<<<<<< HEAD
        else if(id==R.id.nav_view_query){
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new QueryDataFragment());

            ft.commit();
=======
        else if (id == R.id.nav_login) {
            Intent intent = new Intent(NavbarActivity.this, LoginActivity.class);
           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            this.finish();
>>>>>>> a4bbd53a1ce6d7cefcead36fb4cf22a3bc8ee0f1
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
