package com.bridgelab.parking;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotSystem {

    List<ParkingLot> vehiclesList;
    ParkingAvailabilityObserver informationObserver;
    ParkingLot parkingLots;


    public ParkingLotSystem() {
        informationObserver = ParkingAvailabilityObserver.getInstanceGenerate();
        this.vehiclesList = new ArrayList<>();
    }

    public void getAddedLot(ParkingLot vehicleInLot) {
        this.vehiclesList.add(vehicleInLot);
    }

    public boolean isAfterLotAdded(ParkingLot vehicleInLot) {
        return this.vehiclesList.contains(vehicleInLot);
    }

    public ParkingLot getLotHavingLargeSpace(List<ParkingLot> parkingLots) {
        return parkingLots.stream().sorted(Comparator.comparing(list -> list.getEmptySlotList().size(), Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }


    public boolean park(Vehicle vehicle,VehicleType vehicleType,EnumDriverType driverType) {
        parkingLots = getLotHavingLargeSpace(vehiclesList);
        return parkingLots.parkVehicle(vehicle,vehicleType,driverType);

    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for(ParkingLot parkingLot : vehiclesList)
            return parkingLot.isVehicleParked(vehicle) ;
        return false;
    }

    public int findVehicleInParkingLot(Vehicle vehicle) {
        for(ParkingLot parkingLot: vehiclesList)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean getUnParked(Vehicle vehicle) {
        for (ParkingLot parkingLot : vehiclesList) {
            return parkingLot.getUnParked(vehicle);
        }
        return false;
    }

    public LocalTime getFindTimeForVehicle(Vehicle vehicle) {
        for(ParkingLot parkingLot: vehiclesList)
            return parkingLot.getFindTimeForPark(vehicle);
        throw new ParkingLotException("Time Not Available", ParkingLotException.ExceptionType.TIME_NOT_AVAILABLE);
    }

    public void registeredObserver(ParkingLotObserver observer) {
        informationObserver.registeredObserver(observer);
    }

    public ArrayList<String> searchVehiclesByGivenFields(VehicleSortedCatagories vehicleSort, String... catagory){

        for (ParkingLot lot: vehiclesList)
            return lot.searchVehiclesByGivenCatagories(vehicleSort,catagory);
        throw new ParkingLotException("No One Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
