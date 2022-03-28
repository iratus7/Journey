package com.example.journey;

import android.content.Context;
import android.widget.Toast;
import androidx.room.Room;

public class JourneyRepository {
    private String DB_NAME = "journeydb";
    private JourneyDatabase journeyDatabase;
    Context context;
    public JourneyRepository(Context context){
        this.context = context;
        journeyDatabase = Room.databaseBuilder(context,JourneyDatabase.class,DB_NAME).build();

        Toast.makeText(context,"Database created...",Toast.LENGTH_LONG).show();
    }
}
