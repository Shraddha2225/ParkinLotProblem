package com.bridgelab.parking;
import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlots {
    Vehicle vehicle;
    public LocalTime time;
    public EnumDriverType driver;

    public ParkingSlots(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = LocalTime.now();
    }

    public ParkingSlots(Vehicle vehicle,EnumDriverType driverType ) {
        this.driver = driverType;
        this.vehicle = vehicle;
        this.time=LocalTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public EnumDriverType getDriver() {
        return driver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots parkingSlot = (ParkingSlots) o;
        return Objects.equals(vehicle, parkingSlot.vehicle);
    }
}

