package com.bridgelab.parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private int actualCapacity;
    private List vehicles;
    private ParkingLotObserver parkingInfo;

    public ParkingLot(int capacity) {
        this.vehicles = new ArrayList();
        this.actualCapacity = capacity;
    }

    public void registerOwner(ParkingLotOwner owner) {
        this.parkingInfo = owner;
    }

    public  void setCapacity(int capacity) {
        this.actualCapacity=capacity;
    }

    public void registerSecurity(AirportSecurity airportSecurty) {
        this.parkingInfo = airportSecurty;
    }

    public void park(Object vehicle) throws ParkingLotException {
        if (this.vehicles.size() == this.actualCapacity){
            parkingInfo.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
        }
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");
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
