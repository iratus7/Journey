package com.example.journey;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Journey.class},version = 1,exportSchema = false)
public abstract class JourneyDatabase extends RoomDatabase {

    public abstract JourneyDao journeyDao();
}
