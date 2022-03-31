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
    void insertTrip(Trip trip);
    @Insert
    void insertPackageTravel(PackageTravel packageTravel);
    @Update
    void updateTravelAgency(TravelAgency travelAgency);
    @Update
    void updateTrip(Trip trip);
    @Update
    void updatePackageTravel(PackageTravel packageTravel);
    @Delete
    void deleteTravelAgency(TravelAgency travelAgency);
    @Delete
    void deleteTrip(Trip trip);
    @Delete
    void deletePackageTravel(PackageTravel packageTravel);

}
