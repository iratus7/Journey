package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "Tid")
    public int id;
    @ColumnInfo(name= "TripCity")
    public String city;
    @ColumnInfo(name="TripCountry")
    public String country;
    @ColumnInfo(name= "TripDuration")
    public int duration;
    @ColumnInfo(name= "TripType")
    public String type;
    @ColumnInfo(name="TripCityCoordinates")
    public String coordinates;

    public Trip(int id, String city, String country, int duration, String type,String coordinates) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.duration = duration;
        this.type = type;
        this.coordinates = coordinates;
    }
}
