package com.bridgelab.parking;

public class ParkingLot {

    private final int actualCapacity;
    private int currentCapacity;
    private Object vehicle;
    private ParkingLotOwner owner;


    public ParkingLot(int capacity) {
        this.actualCapacity = capacity;
        this.currentCapacity = 0;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.owner=owner;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.currentCapacity == this.actualCapacity){
            owner.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        this.currentCapacity++;
        this.vehicle = vehicle;
    }

    public boolean isVehicalParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }

    public boolean getUnParked(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }

}
