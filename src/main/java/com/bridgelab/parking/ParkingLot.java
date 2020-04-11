package com.bridgelab.parking;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    ParkingAvailabilityObserver informationObserver;
    private int actualSlotCapacity;
    List<ParkingSlots> vehicles;
    ParkingSlots parkingSlots;
    private int emptyList;

    public ParkingLot(int capacity) {
        setCapacity(capacity);
        informationObserver = ParkingAvailabilityObserver.getInstanceGenerate();
    }

    public  void setCapacity(int capacity) {
        this.actualSlotCapacity = capacity;
        initializeParkingLotForVehicle();
    }

    public int initializeParkingLotForVehicle() {
        this.vehicles = new ArrayList<>();
        IntStream.range(0,this.actualSlotCapacity).forEach(slots ->vehicles.add(null));
        return vehicles.size();
    }

    public ArrayList<Integer> getEmptySlot() {
        ArrayList<Integer> emptySlots = new ArrayList<>();
        IntStream.range(0,actualSlotCapacity)
                .filter(slot->vehicles.get(slot)==null)
                .forEach(emptySlots::add);
        return emptySlots;
    }

    public boolean parkVehicle(Vehicle vehicle, VehicleType vehicleType,EnumDriverType driverType) throws ParkingLotException {
        if(isVehicleParked(vehicle))
            throw new ParkingLotException("vehicle already parked",ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        if (vehicles.size() == actualSlotCapacity && !vehicles.contains(null)) {
            informationObserver.InformedParkingFull();
            throw new ParkingLotException("parkinglot is full", ParkingLotException.ExceptionType.LOT_IS_FULL);
    }
        ParkingSlots slots = new ParkingSlots(vehicle,vehicleType,driverType);
        int emptyList = getEmptyListOfDriverType(driverType);
        this.vehicles.set(emptyList,slots);
        return true;
    }

    public int getEmptyListOfDriverType(EnumDriverType driverType){
         ArrayList<Integer> emptyParkingSlotList = getEmptySlot();
         if(EnumDriverType.NORMALDRIVER.equals(driverType))
             Collections.sort(emptyParkingSlotList,Collections.reverseOrder());
         if(EnumDriverType.HANDICAPDRIVER.equals(driverType))
             Collections.sort(emptyParkingSlotList);
         return emptyList;
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlots = new ParkingSlots(vehicle);
        if(this.vehicles.contains(parkingSlots)) return true;
        return false;
    }

    public boolean getUnParked(Vehicle vehicle) {
        parkingSlots = new ParkingSlots(vehicle);
        if (this.vehicles.contains(parkingSlots)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlots), null);
            return true;
        }
        return false;
    }

    public int findVehicle(Vehicle vehicle) {
        parkingSlots = new ParkingSlots(vehicle);
        if(!this.vehicles.contains(parkingSlots))
            throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return this.vehicles.indexOf(parkingSlots);
    }

    /*public List<Integer> getEmptyList() {
        return IntStream.range(0, this.actualSlotCapacity).filter(slot -> vehicles.get(slot) == null).boxed().collect(Collectors.toList());
    }*/

    public LocalTime getFindTimeForPark(Vehicle vehicle) {
        parkingSlots = new ParkingSlots(vehicle);
        return  parkingSlots.time;
    }

    public ArrayList<String> searchVehiclesByGivenCatagories(VehicleSortedCatagories vehicleSort, String... catagory){
        ArrayList<String>vehicleList=new ArrayList<>();
        vehicleSort.initializeVehicleList(vehicles);
        IntStream.range(0,actualSlotCapacity)
                .filter(slot->vehicles.get(slot)!=null)
                .filter(vehicleSort.SortVehicleList(catagory).get(vehicleSort))
                .mapToObj(slot->(slot+" "+ vehicles.get(slot).getVehicle().getVehicleBrandName()+" "+vehicles.get(slot).getVehicle().getColor()+" "+vehicles.get(slot).getVehicle().getPlateNumber()))
                .collect(Collectors.toList())
                .forEach(vehicleList::add);

        if(vehicleList.isEmpty())
            throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
        return vehicleList;
    }
}
