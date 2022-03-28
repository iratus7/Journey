package com.example.journey;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface JourneyDao {
    @Insert
    Long insertTask(Journey journey);
    @Update
    Void updateTask(Journey journey);
    @Delete
    Void delete(Journey journey);

}
