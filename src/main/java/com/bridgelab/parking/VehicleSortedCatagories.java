package com.bridgelab.parking;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

public enum VehicleSortedCatagories {

    COLOUR,NAME_COLOUR, NAME, TIME_IN_MINUTES, DRIVER_VEHICLE_TYPE, ALL_PARKED;
    private static List<ParkingSlots> vehicle;
    private static Map<VehicleSortedCatagories, IntPredicate> sortFieldPredicateMap = new HashMap<>();


    public static void initializeVehicleList(List<ParkingSlots> vehicles) {
        vehicle = vehicles;
    }

    public static Map<VehicleSortedCatagories, IntPredicate> SortVehicleList(String...catagory){
        sortFieldPredicateMap.put(VehicleSortedCatagories.COLOUR, slot->vehicle.get(slot).getVehicle().getColor().equals(catagory[0]));
        sortFieldPredicateMap.put(VehicleSortedCatagories.NAME_COLOUR, slot->vehicle.get(slot).getVehicle().getVehicleBrandName().equals(catagory[0]) && vehicle.get(slot).getVehicle().getColor().equals(catagory[1]));
        sortFieldPredicateMap.put(VehicleSortedCatagories.NAME, slot->vehicle.get(slot).getVehicle().getVehicleBrandName().equals(catagory[0]));
        sortFieldPredicateMap.put(VehicleSortedCatagories.TIME_IN_MINUTES, slot->vehicle.get(slot).getTime().getMinute()- LocalTime.now().getMinute()<=Integer.parseInt(catagory[0]));
        sortFieldPredicateMap.put(VehicleSortedCatagories.DRIVER_VEHICLE_TYPE, slot-> vehicle.get(slot).getVehicleTypeOne().equals(VehicleType.valueOf(catagory[0])) && vehicle.get(slot).getDriver().equals(EnumDriverType.valueOf(catagory[1])));
        sortFieldPredicateMap.put(VehicleSortedCatagories.ALL_PARKED, slot-> vehicle.get(slot)!=null);
        return sortFieldPredicateMap;
    }
}
