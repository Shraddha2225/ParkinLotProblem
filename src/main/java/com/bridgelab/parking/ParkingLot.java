package com.bridgelab.parking;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {

    public DriverType DriverType;
    private int actualCapacity;

    public enum DriverType{NORMALDRIVER,HANDICAPDRIVER}
    public List<VehicleParking> vehicles;
    private List<ParkingLotObserver> parkLotObserver;
    private int slot = 0;

    public ParkingLot(int capacity) {
        setCapacity(capacity);
        this.parkLotObserver = new ArrayList<>();
    }

    public void registerParkingLotOwner(AirportSecurity observer) {
        this.parkLotObserver.add(observer);
    }

    public void registerParkingLotObserver(ParkingLotObserver observer) {
        this.parkLotObserver.add(observer);
    }

    public  void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public int initializeParkingLot() {
        this.vehicles=new ArrayList<>();
        IntStream.range(0,this.actualCapacity).forEach(slots ->vehicles.add(null));
        return vehicles.size();
    }

    public ArrayList<Integer> getSlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        for (int slot = 0; slot < this.actualCapacity; slot++) {
            if (this.vehicles.get(slot) == null)
                emptySlots.add(slot);
        }
        return emptySlots;
    }

    /*public void registerSecurity(AirportSecurity airportSecurity) {
        this.parkingInfo = airportSecurity;
    }*/

    //Functionality To Park Vehicle In ParkingLot
    public void park(VehicleParking vehicle,DriverType driverType) throws ParkingLotException {
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked");
        if (vehicles.size() == actualCapacity && !vehicles.contains(null)) {
            for (ParkingLotObserver observer : parkLotObserver)
                observer.capacityIsFull();
            throw new ParkingLotException("parkinglot is full");
    }
        ArrayList<Integer> emptyList = getEmptyList(driverType);
        parked(emptyList.get(0),vehicle);
    }


    //parked functionality
    public void parked(int slot, VehicleParking vehicle) throws ParkingLotException {
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("VEHICLE ALREADY PARK");
        }
        this.vehicles.set(slot, vehicle);
    }

    public ArrayList<Integer> getEmptyList(DriverType driverType){
        ArrayList<Integer> emptyParkingSlotList = getSlot();
        if(DriverType.NORMALDRIVER.equals(driverType))
            Collections.sort(emptyParkingSlotList,Collections.reverseOrder());
        if(DriverType.HANDICAPDRIVER.equals(driverType))
            Collections.sort(emptyParkingSlotList);
        return emptyParkingSlotList;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean getUnParked(Object vehicle) {
        if ( vehicle == null)  return false;
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
            return true;
        }
        return false;
    }

    //find a vehicle if it is parked//
    public int findVehicleInParkingLot(Object vehicle) {
        if(!this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle Is Absent");
        return this.vehicles.indexOf(vehicle);
    }

    public LocalTime findTimeForVehicle(VehicleParking vehicle) {
        if(!this.vehicles.contains(vehicle))
            throw new ParkingLotException("Vehicle Is Absent");
        if(vehicle.getTime() == null)
            throw new ParkingLotException("Vehicle Has Not Set Time");
        return vehicle.getTime();
    }
}
