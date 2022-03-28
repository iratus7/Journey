package com.example.journey;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface JourneyDao {
    @Insert
    void insertTravelAgency(TravelAgency travelAgency);
    @Insert
    void insertEmployee(Employee employee);
    @Update
    void updateTravelAgency(TravelAgency travelAgency);
    @Update
    void updateEmployee(Employee employee);
    @Delete
    void deleteTravelAgency(TravelAgency travelAgency);
    @Delete
    void deleteEmployee(Employee employee);

}
