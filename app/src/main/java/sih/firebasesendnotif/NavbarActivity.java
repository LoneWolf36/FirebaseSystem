package sih.firebasesendnotif;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.florent37.awesomebar.AwesomeBar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import butterknife.ButterKnife;
import sih.firebasesendnotif.Classes.Alarmnotif;
import sih.firebasesendnotif.Classes.ScheduledNotif;
import sih.firebasesendnotif.Fragments.AddScheduleFragment;
import sih.firebasesendnotif.Fragments.ContactAuthority;
import sih.firebasesendnotif.Fragments.DamLocationPicker;
import sih.firebasesendnotif.Fragments.EmergencyContacts;
import sih.firebasesendnotif.Fragments.EmergencyNotificationFragment;
import sih.firebasesendnotif.Fragments.QueryDataFragment;
import sih.firebasesendnotif.Fragments.ScheduleFragment;
import sih.firebasesendnotif.Fragments.SubscribeFragment;

public class NavbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    NavigationView navigationView;
    // Instance of FirebaseAuth
    private FirebaseAuth mAuth;

    //String city_name;
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
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //code for Notification
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 56);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(NavbarActivity.this, Alarmnotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NavbarActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) NavbarActivity.this.getSystemService(NavbarActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


        ButterKnife.bind(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("JaisPrefrence", MODE_PRIVATE);

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

        bar.displayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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


        if (!prefs.getBoolean("admin_login", false)) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.add_schedule).setVisible(false);
            menu.findItem(R.id.nav_emergency).setVisible(false);
            menu.findItem(R.id.fragment_dam_location_picker).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_view_query).setVisible(false);
        } else {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.add_schedule).setVisible(true);
            menu.findItem(R.id.nav_emergency).setVisible(true);
            menu.findItem(R.id.fragment_dam_location_picker).setVisible(true);
            menu.findItem(R.id.nav_logout).setVisible(true);
            menu.findItem(R.id.nav_subscribe).setVisible(false);
            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_contact).setVisible(false);
        }


        FragmentTransaction ft;

        Boolean first_open = prefs.getBoolean("first_open", true);
        if (first_open) {
            fab.setVisibility(View.INVISIBLE);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new SubscribeFragment());
            ft.commit();
            editor.putBoolean("first_open", false);
            editor.apply();
        } else if (!first_open && prefs.getBoolean("admin_login", false)) {
            fab.setVisibility(View.VISIBLE);
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.toPopulate, new ScheduleFragment());
            ft.commit();
        } else {
            fab.setVisibility(View.INVISIBLE);
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.toPopulate, new ScheduleFragment());
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getApplicationContext().getResources().getString(R.string.exit))
                    .setMessage(getApplicationContext().getResources().getString(R.string.confirm_exit))
                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.no), null)
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
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new ScheduleFragment());
            ft.commit();
            if (!prefs.getBoolean("admin_login", false)) {
                fab.setVisibility(View.INVISIBLE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }
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
            new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getApplicationContext().getResources().getString(R.string.logout))
                    .setMessage(getApplicationContext().getResources().getString(R.string.confirm_logout))
                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            //        SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                            //      editor.putString("city_name", "");
                            //    editor.apply();

                            mAuth = FirebaseAuth.getInstance();
                            final SharedPreferences.Editor editor = getSharedPreferences("JaisPrefrence", MODE_PRIVATE).edit();
                            editor.putBoolean("admin_login", false);
                            editor.apply();
                            mAuth.signOut();
                            //   startActivity(new Intent(NavbarActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.no), null)
                    .show();
        }
//
//        // Location picker fragme
//        else if (id == R.id.fragment_dam_location_picker) {
//            new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle(getApplicationContext().getResources().getString(R.string.place))
//                    .setMessage(getApplicationContext().getResources().getString(R.string.confirm_place))
//                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            startActivity(new Intent(NavbarActivity.this, LocationPickerActivity.class));
//                        }
//                    })
//                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.no), null)
//                    .show();
//        }

        // Location picker fragme
        else if (id == R.layout.activity_location_picker) {
            new AlertDialog.Builder(this, R.style.AppTheme_Dark_Dialog)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getApplicationContext().getResources().getString(R.string.place))
                    .setMessage(getApplicationContext().getResources().getString(R.string.confirm_place))
                    .setPositiveButton(getApplicationContext().getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(NavbarActivity.this, LocationPickerActivity.class));
                        }
                    })
                    .setNegativeButton(getApplicationContext().getResources().getString(R.string.no), null)
                    .show();
        }
            //startActivity(new Intent(NavbarActivity.this, LocationPickerActivity.class));
        else if (id == R.id.fragment_dam_location_picker) {

            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new DamLocationPicker());
            ft.commit();
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
        } else if (id == R.id.nav_contact) {
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
        } else if (id == R.id.nav_view_query) {
            fab.setVisibility(View.INVISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.toPopulate, new QueryDataFragment());
            ft.commit();
        }else if (id == R.id.nav_login) {
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
