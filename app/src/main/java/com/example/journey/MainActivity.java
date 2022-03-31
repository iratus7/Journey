package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static JourneyDatabase myAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //JourneyRepository journeyRepository =new JourneyRepository(getApplicationContext());
        myAppDatabase = Room.databaseBuilder(getApplicationContext(),JourneyDatabase.class,"journeydb").allowMainThreadQueries().build();




    }
}