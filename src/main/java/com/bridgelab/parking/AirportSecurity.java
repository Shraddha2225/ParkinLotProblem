package com.bridgelab.parking;

public class AirportSecurity implements ParkingLotObserver {
    private boolean isFullCapacity;

    @Override
    public void capacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void setCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void setParkingLotAvailable() {
        isFullCapacity = false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
