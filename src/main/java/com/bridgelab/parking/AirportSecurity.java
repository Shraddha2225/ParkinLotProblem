package com.bridgelab.parking;

public class AirportSecurity implements ParkingLotObserver {
    private boolean isFullcapacity;

    @Override
    public void capacityIsFull() {
        isFullcapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }
}
