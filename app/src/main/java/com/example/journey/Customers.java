package com.example.journey;

public class Customers {
    private String name;
    private String hotel;
    private int packagetravelid;

    public Customers(String name, String hotel, int packagetravelid) {
        this.name = name;
        this.hotel = hotel;
        this.packagetravelid = packagetravelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public int getPackagetravelid() {
        return packagetravelid;
    }

    public void setPackagetravelid(int packagetravelid) {
        this.packagetravelid = packagetravelid;
    }
}
