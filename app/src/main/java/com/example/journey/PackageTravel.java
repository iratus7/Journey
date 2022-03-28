package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Packages")
public class PackageTravel {
    @PrimaryKey
    @ColumnInfo(name= "PackageId")
    public int id;
    @ColumnInfo(name= "PackageCity")
    public String city;
    @ColumnInfo(name="PackageCountry")
    public String country;
    @ColumnInfo(name= "PackagePrice")
    public String price;
    @ColumnInfo(name= "PackageDuration")
    public String duration;
    @ColumnInfo(name="PackageStartDate")
    public int date;
    @ColumnInfo(name="PackageCityCoordinates")
    public int coordinates;

    public PackageTravel(int id, String city, String country, String price, String duration, int date, int coordinates) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.price = price;
        this.duration = duration;
        this.date = date;
        this.coordinates = coordinates;
    }
}
