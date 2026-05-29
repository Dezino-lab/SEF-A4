package com.sefa4;

public class Bus {
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType; // Diesel, Hybrid, Electricity

    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {

        if (!isValidBusID(busID)) {
            throw new IllegalArgumentException("Bus ID must be exactly 8 digits.");
        }

        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
    }
    
    //method for checking if driver can be assigned to the bus 
    public boolean canAssignDriver(Driver driver) {
        int driverAge = driver.getDriverAge();
        int driverExp = driver.getExperience();
        String licenceType = driver.getLicense();

        // drivers older than 50 cannot drive buses with capacity of 50 or more
        if (this.capacity >= 50 && driverAge > 50) {
            return false;
        }

        //only drivers with at least 5 years of experience can drive electric buses 
        if ("Electric".equalsIgnoreCase(this.fuelType) && driverExp < 5) {
            return false;
        }

        //only drivders with Heavy or PublicTransport license can drive electric or hybrid buses
        if ("Electric".equalsIgnoreCase(this.fuelType) || "Hybrid".equalsIgnoreCase(this.fuelType)) {
            boolean hasValidLicense = "Heavy".equalsIgnoreCase(licenceType) || "PublicTransport".equalsIgnoreCase(licenceType);
            if (!hasValidLicense) {
                return false;
            }
        }


        return true;
    }

    public static boolean isValidBusID(String busID) {
        return busID != null && busID.matches("\\d{8}");
    }

    public String getId() {
        return busID;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean updateCapacity(int newCapacity) {
        if (newCapacity > this.capacity) {
            return false; // Cannot reduce capacity
        }
        this.capacity = newCapacity;
        return true;
    }
}
