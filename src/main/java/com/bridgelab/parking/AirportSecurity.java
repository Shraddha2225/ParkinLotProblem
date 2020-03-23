package com.bridgelab.parking;

public class AirportSecurity {
    private boolean isFullcapacity;

    public void capacityIsFull() {
        isFullcapacity=true;
    }

    public boolean isCapacityFull() {
        return this.isFullcapacity;
    }
}
