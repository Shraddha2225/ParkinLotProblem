package com.bridgelab.parking;

public class ParkingLot {

    private Object Car;

    public boolean getPark(Object Car) {
        this.Car = Car;
        return true;
    }

    public boolean getUnParked(Object Car) {
        if (this.Car.equals(Car)) {
            this.Car = null;
            return true;
        }
        return false;
    }
}
