package com.example.journey;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employees")
public class Employee {
    @PrimaryKey
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
    public int YearBorn;
    @ColumnInfo(name= "EmployeeSpecialty")
    public String specialty;
    @ColumnInfo(name= "EmployeeAgencyId")
    public String Agencyid;
    @ColumnInfo(name="EmployeePackageId")
    public int packageid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getYearBorn() {
        return YearBorn;
    }

    public void setYearBorn(int yearBorn) {
        YearBorn = yearBorn;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAgencyid() {
        return Agencyid;
    }

    public void setAgencyid(String agencyid) {
        Agencyid = agencyid;
    }

    public int getPackageid() {
        return packageid;
    }

    public void setPackageid(int packageid) {
        this.packageid = packageid;
    }
}
