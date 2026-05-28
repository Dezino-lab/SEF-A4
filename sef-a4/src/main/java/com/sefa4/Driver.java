package com.sefa4;

public class Driver {
    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType; // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    public Driver(String driverID, String name, int experienceYears, String licenseType, String address, String birthdate) {

        
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
    }

    
    public String getDriverID() {
        return driverID;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experienceYears;
    }

    public String getLicense() {
        return licenseType;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExperience(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public void setLicense(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}