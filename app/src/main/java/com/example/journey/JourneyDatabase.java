package com.example.journey;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {TravelAgency.class, Trip.class,PackageTravel.class},version = 1,exportSchema = false)//,exportSchema = false
public abstract class JourneyDatabase extends RoomDatabase {

    private static JourneyDatabase database;
    public abstract JourneyDao journeyDao();
    public synchronized static JourneyDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),JourneyDatabase.class,"journeydb").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }
}
