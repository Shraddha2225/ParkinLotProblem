package com.bridgelab.parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private int actualCapacity;
    private int currentCapacity;
    private List vehicles;
    private ParkingLotOwner owner;

    public ParkingLot(int capacity) {
        this.vehicles=new ArrayList();
        this.actualCapacity = capacity;
        this.currentCapacity = 0;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }

    public  void setCapacity(int capacity) {
        this.actualCapacity=capacity;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.actualCapacity){
            owner.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        this.vehicles.add(vehicle);
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean getUnParked(Object vehicle) {
        if ( vehicle == null)  return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            return true;
        }
        return false;
    }
}
