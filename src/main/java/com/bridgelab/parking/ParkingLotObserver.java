package com.bridgelab.parking;

public interface ParkingLotObserver {
    void capacityIsFull();

    void setCapacityIsFull();

    void setParkingLotAvailable();
}
