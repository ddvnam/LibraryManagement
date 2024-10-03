package com.example.librarymanagement2;

public class Address {
    private String district;
    private String city;

    //Constructors


    public Address() {
        this.district = "";
        this.city = "";
    }

    public Address(String district, String city) {
        this.district = district;
        this.city = city;
    }

    //Getters and Setters
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
