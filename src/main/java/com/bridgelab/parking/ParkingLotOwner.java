package com.bridgelab.parking;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullcapacity;

    @Override
    public void capacityIsFull() {
        isFullcapacity = true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }

}
