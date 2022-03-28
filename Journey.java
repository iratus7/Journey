package com.example.journey;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Journey {
    @PrimaryKey
    public int id;
    @ColumnInfo(name= "agency_name")
    public String agency_name;
    @ColumnInfo(name="address")
    public String address;

    public Journey(int id, String agency_name, String address) {
        this.id = id;
        this.agency_name = agency_name;
        this.address = address;
    }
}
