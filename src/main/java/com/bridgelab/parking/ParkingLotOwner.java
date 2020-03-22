package com.bridgelab.parking;

public class ParkingLotOwner {
    private boolean isFullcapacity;

    public void capacityIsFull() {
        isFullcapacity=true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }

    public void registerOwner(ParkingLotOwner owner) {

    }
}
