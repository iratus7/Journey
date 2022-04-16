package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Packages",indices = {@Index(value = {"TAId"}),@Index(value = {"TId"})},
        foreignKeys = {
        @ForeignKey(entity=TravelAgency.class,
                        parentColumns = "AgencyId",
                        childColumns = "TAId",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity=Trip.class,
                        parentColumns = "Tid",
                        childColumns = "TId",
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)})

public class PackageTravel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "PackageId")
    public int id;
    @ColumnInfo(name= "TAId")
    public int TravelAgencyId;
    @ColumnInfo(name="TId")
    public int TripId;
    @ColumnInfo(name="PackageStartDate")
    public String date;
    @ColumnInfo(name= "PackagePrice")
    public String price;


    @Ignore
    public PackageTravel(int id, int TravelAgencyId, int TripId, String date, String price) {
        this.id = id;
        this.TravelAgencyId = TravelAgencyId;
        this.TripId = TripId;
        this.price = price;
        this.date = date;
    }
    public PackageTravel( int TravelAgencyId, int TripId, String date, String price) {
        //this.id = id;
        this.TravelAgencyId = TravelAgencyId;
        this.TripId = TripId;
        this.price = price;
        this.date = date;
    }
}
