package com.bridgelab.parking;
import java.time.LocalTime;
import java.util.Objects;

public class ParkingSlots {
    Vehicle vehicle;
    public LocalTime time;
    public EnumDriverType driver;
    public VehicleType vehicleTypeOne;

    public ParkingSlots(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = LocalTime.now();
    }

    public ParkingSlots(Vehicle vehicle, VehicleType vehicleType,EnumDriverType driverType ) {
        this.driver = driverType;
        this.vehicle = vehicle;
        this.time=LocalTime.now();
        this.vehicleTypeOne =vehicleType;

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

    public VehicleType getVehicleTypeOne() {
        return vehicleTypeOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots parkingSlot = (ParkingSlots) o;
        return Objects.equals(vehicle, parkingSlot.vehicle);
    }
}

