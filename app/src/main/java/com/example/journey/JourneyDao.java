package com.example.journey;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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


    @Query("SELECT * FROM TAgency")
    List<TravelAgency> getTravelAgency();
    @Query("SELECT * FROM Trips")
    List<Trip> getTrip();
    @Query("SELECT * FROM Packages")
    List<PackageTravel> getPackageTravel();
    @Query("SELECT AgencyName FROM TAgency")
    List<String> getANames();
    @Query("SELECT TripCity FROM Trips")
    List<String> getTCity();
    @Query("SELECT Tid FROM Trips WHERE TripCity = :city")
    int getTid(String city);
    @Query("SELECT AgencyId FROM TAgency WHERE AgencyName = :agency")
    int getAid(String agency);
    @Query("SELECT TripCity FROM Trips WHERE Tid = :id")
    String getTripCity(int id);
    @Query("SELECT AgencyName FROM TAgency WHERE AgencyId = :id")
    String getAgencyName(int id);
    @Query("SELECT (Trips.TripCity|| ', ' ||TAgency.AgencyName|| ', ' ||Packages.PackageStartDate|| ', ' ||Packages.PackagePrice) AS RESULT FROM Packages,Trips,TAgency WHERE( Trips.Tid = Packages.TId AND TAgency.AgencyId = Packages.TAId)")
    List<String> getPackageDetails();
    @Query("SELECT PackageId FROM Packages WHERE ROWID = :position")
    int getSelectedPackageId(int position);
    @Query("SELECT TripCityCoordinates FROM Trips")
    List<String> getTCoordinates();
    @Query("SELECT TripDuration FROM Trips")
    List<String> getTDuration();
    @Query("SELECT  (Trips.TripCity|| ', ' ||TAgency.AgencyName|| ', ' ||Packages.PackageStartDate|| ', ' ||Packages.PackagePrice)||'€' AS RESULT FROM Packages,Trips,TAgency WHERE( Trips.Tid = Packages.TId AND TAgency.AgencyId = Packages.TAId)")
    List<String> getHomePackets();

}
