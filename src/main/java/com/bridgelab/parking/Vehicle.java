package com.bridgelab.parking;
import java.time.LocalTime;

public class Vehicle {
    String color;
    String vehicleBrandName;
    String plateNumber;

    public Vehicle(){
    }

    public Vehicle(String color, String vehicleBrandName, String plateNumber) {
        this.color = color;
        this.vehicleBrandName = vehicleBrandName;
        this.plateNumber = plateNumber;
    }

    public String getColor() {
        return color;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }
}
