package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employees",
        foreignKeys = {
        @ForeignKey(entity=TravelAgency.class,
        parentColumns = "AgencyId",
        childColumns = "EmployeeAgencyId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity=PackageTravel.class,
        parentColumns = "PackageId",
        childColumns = "EmployeePackageId",
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)})

public class Employee {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "Eid")
    public int id;
    @ColumnInfo(name= "EmployeeFname")
    public String firstName;
    @ColumnInfo(name="EmployeeLname")
    public String lastName;
    @ColumnInfo(name= "EmployeeAddress")
    public String address;
    @ColumnInfo(name= "EmployeeGender")
    public String gender;
    @ColumnInfo(name="EmployeeBorn")
    public int yearBorn;
    @ColumnInfo(name= "EmployeeSpecialty")
    public String specialty;
    @ColumnInfo(name= "EmployeeAgencyId")
    public String agencyid;
    @ColumnInfo(name="EmployeePackageId")
    public int packageid;

    public Employee(int id, String firstName, String lastName, String address, String gender, int yearBorn, String specialty, String agencyid, int packageid) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
        this.yearBorn = yearBorn;
        this.specialty = specialty;
        this.agencyid = agencyid;
        this.packageid = packageid;
    }
}
