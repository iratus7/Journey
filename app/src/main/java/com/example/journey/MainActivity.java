package com.example.journey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static JourneyDatabase journeyDatabase;
    private String DB_NAME = "journeydb";
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //JourneyRepository journeyRepository =new JourneyRepository(getApplicationContext());
        journeyDatabase = Room.databaseBuilder(getApplicationContext(),JourneyDatabase.class,DB_NAME).allowMainThreadQueries().build();

        //TravelAgency travelAgency1 = new TravelAgency(3,"hermes","dragoymi");
        //MainActivity.journeyDatabase.journeyDao().insertTravelAgency(travelAgency1);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new HomeFragmentR()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new HomeFragmentR()).commit();
                break;
            case R.id.nav_trip:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new TripFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TripFragmentResults()).commit();
                break;
            case R.id.nav_travelagency:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new TravelAgencyFragmentActions()).commit();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerResults, new TravelAgencyFragmentResults()).commit();
                break;
//
//            case R.id.nav_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
//                break;
//
//            case R.id.nav_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
//                break;
//
//            case R.id.nav_home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerAction, new HomeFragment()).commit();
//                break;


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
}