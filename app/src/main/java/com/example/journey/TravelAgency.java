package com.example.journey;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "TAgency")
public class TravelAgency {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name= "AgencyId")
    public int id;
    @ColumnInfo(name= "AgencyName")
    public String agency_name;
    @ColumnInfo(name="AgencyAddress")
    public String address;

    public TravelAgency(int id, String agency_name, String address) {
        this.id = id;
        this.agency_name = agency_name;
        this.address = address;
    }
}
