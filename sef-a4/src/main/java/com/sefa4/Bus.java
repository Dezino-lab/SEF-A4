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
