package com.example.journey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static FirebaseFirestore db;
    public static JourneyDatabase journeyDatabase;
    private String DB_NAME = "journeydb";
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db= FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        journeyDatabase = Room.databaseBuilder(getApplicationContext(),JourneyDatabase.class,DB_NAME).allowMainThreadQueries().build();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new MapsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new MapsFragment()).commit();
                break;
            case R.id.nav_trip:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new TripFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TripFragmentResults()).commit();
                break;
            case R.id.nav_travelagency:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new TravelAgencyFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TravelAgencyFragmentResults()).commit();
                break;
            case R.id.nav_packets:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new PackageFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new PackageFragmentResults()).commit();
                break;
            case R.id.nav_customers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new CustomersFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new CustomersFragmentResults()).commit();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new SearchFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new SearchFragmentResults()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }


    //about menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            try {
                Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String cpuInfo=in.readLine();
                process = Runtime.getRuntime().exec("cat /proc/meminfo");
                in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                Integer mem=Integer.parseInt(in.readLine().replaceAll("\\D+",""));
                Double memGb= (double)mem /1048576;

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                alertDialog.setTitle("ABOUT");
                alertDialog.setMessage("JOURNEY App 1.0" + "\n" + "Model: " + Build.MANUFACTURER + " - " + Build.MODEL + "\n" + "Android " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")" + "\n" + "CPU" + cpuInfo.substring(11) + "\n" +"MEMORY: "+String.format("%.2f", memGb) +"Gb");

                alertDialog.setButton("ΣΥΝΕΧΕΙΑ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // here you can add functions
                    }
                });
                alertDialog.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}