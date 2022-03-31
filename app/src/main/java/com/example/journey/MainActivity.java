package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static JourneyDatabase journeyDatabase;
    private String DB_NAME = "journeydb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JourneyRepository journeyRepository =new JourneyRepository(getApplicationContext());
        journeyDatabase = Room.databaseBuilder(getApplicationContext(),JourneyDatabase.class,DB_NAME).allowMainThreadQueries().build();

        TravelAgency travelAgency1 = new TravelAgency(2,"hermes","dragoymi");
        MainActivity.journeyDatabase.journeyDao().insertTravelAgency(travelAgency1);

    }
}