package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Packages",indices = {@Index(value = {"TAId"}, unique = true),@Index(value = {"TId"}, unique = true)},
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
    public String TravelAgencyId;
    @ColumnInfo(name="TId")
    public String TripId;
    @ColumnInfo(name="PackageStartDate")
    public String date;
    @ColumnInfo(name= "PackagePrice")
    public String price;


    public PackageTravel(int id, String TravelAgencyId, String TripId, String date, String price) {
        this.id = id;
        this.TravelAgencyId = TravelAgencyId;
        this.TripId = TripId;
        this.price = price;
        this.date = date;
    }
}
