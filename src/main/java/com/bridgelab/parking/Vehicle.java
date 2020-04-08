package com.bridgelab.parking;

import java.time.LocalTime;


public class Vehicle {
    private String color;
    private String vehicleBrandName;

    public Vehicle(){
    }

    public Vehicle(String color, String vehicleBrandName) {
        this.color = color;
        this.vehicleBrandName = vehicleBrandName;
    }

    public String getColor() {
        return color;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "color='" + color + '\'' +
                ", vehicleBrandName='" + vehicleBrandName + '\'' +
                '}';
    }
}
