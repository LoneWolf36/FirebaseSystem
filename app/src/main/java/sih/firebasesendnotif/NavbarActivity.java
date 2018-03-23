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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NavbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;

    // Instance of FirebaseAuth
    private FirebaseAuth mAuth;

    String city_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // City picker intent and extract information from bundle
        city_name = getIntent().getStringExtra("City");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        FrameLayout fL = (FrameLayout) findViewById(R.id.fLFragments);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.toPopulate, new ScheduleFragment());
        //ft.replace(R.id.fMtoDisplay, new ButtonsFragment());
        ft.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(addScheduleListener);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Firebase database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(mAuth.getUid());
        myRef.child("city").setValue(city_name);
    }


    private View.OnClickListener addScheduleListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder alert = new AlertDialog.Builder(NavbarActivity.this);
            alert.setTitle("Do you want to create a new schedule?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("ALI ", "SAYS YES");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.toPopulate, new AddScheduleFragment());
                    ft.commit();
                }
            });

            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d("ALI ", "SAYS NO");
                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
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
                            mAuth.signOut();
                            startActivity(new Intent(NavbarActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
