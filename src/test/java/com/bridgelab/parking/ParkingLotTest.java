package com.bridgelab.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParkingLotTest {
    Vehicle vehicle;
    ParkingLot parkingLot ;
    ParkingLotSystem parkingLotSystem;
    ParkingLotOwner lotOwner;
    AirportSecurity airportSecurity;
    private Vehicle vehicle1;
    List expectedList;


    @Before
    public void setUp() {
        vehicle =new Vehicle();
        parkingLotSystem = new ParkingLotSystem();
        parkingLot = new ParkingLot(2);
        vehicle =new Vehicle("White","Toyota","MH19 AB 2341");
        vehicle1 =new Vehicle("Yellow","Swift","MH20 GH 4563");
        expectedList = new ArrayList();
        parkingLotSystem.getAddedLot(parkingLot);
        airportSecurity = new AirportSecurity();
        lotOwner = new ParkingLotOwner();
        parkingLotSystem.registeredObserver(lotOwner);
    }

    ///UC1//
    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
            parkingLot.parkVehicle(vehicle,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
            boolean Isparked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(Isparked);
    }

    @Test
    public void givenVehicle_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parkVehicle(vehicle,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
            parkingLot.parkVehicle(vehicle,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED,e.type);
        }
    }

    @Test
    public void givenVehicle_WhenParkingLotfull_ShouldThrowException() {
        try{
            parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(),VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_IS_FULL,e.type);
        }
    }

    //UC2//
    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
            parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            boolean isUnparked = parkingLotSystem.getUnParked(vehicle);
            Assert.assertTrue(isUnparked);
    }

    @Test
    public void givenVehicle_WhenNotAlReadyParked_ShouldThrowException() {
        try {
            parkingLotSystem.getUnParked(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    //UC3//
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        parkingLotSystem.registeredObserver(lotOwner);
        try {
            parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE, EnumDriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = lotOwner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vehicle() {
        parkingLot.setCapacity(2);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle1, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isParked1 = parkingLotSystem.isVehicleParked(vehicle);
        boolean isParked2 = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertFalse(isParked1 && isParked2);
    }


    //UC4//
    @Test
    public void givenWhenLotIsFull_ShouldInformTheSecurity() {
        parkingLotSystem.registeredObserver(airportSecurity);
        try {
            parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenVehicle_WhenLotSpaceAgain_ShouldInformTheOwner() {
        Vehicle vehicle2 = new Vehicle();
        parkingLotSystem.registeredObserver(lotOwner);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle2, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.getUnParked(vehicle);
        assertFalse(lotOwner.isCapacityFull());
    }

    @Test
    public void givenVehicle_WhenLotSpaceAgain_ShouldInformTheAirPortSecurity() {
        parkingLotSystem.registeredObserver(airportSecurity);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.getUnParked(vehicle);
        assertFalse(airportSecurity.isCapacityFull());
    }

    @Test
    public void givenInitializeParkingLot_ShouldReturnParkingCapacity() {
        parkingLot.setCapacity(10);
        int parkingLotCapacity = parkingLot.initializeParkingLotForVehicle();
        Assert.assertEquals(10,parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        ArrayList<Integer> vehicleList = new ArrayList<>();
        vehicleList.add(0);
        vehicleList.add(1);
        parkingLot.setCapacity(2);
        parkingLot.initializeParkingLotForVehicle();
        ArrayList emptySlotList = parkingLot.getEmptySlot();
        Assert.assertEquals(vehicleList, emptySlotList);
    }

    @Test
    public void givenParkAndUnParkVehicles_ShouldReturnAvailableSlots() throws ParkingLotException {
        ArrayList<Integer> vehicleList = new ArrayList<>();
        vehicleList.add(1);
        vehicleList.add(2);
        parkingLot.setCapacity(3);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.getUnParked(vehicle);
        ArrayList emptySlotList = parkingLot.getEmptySlot();
        Assert.assertEquals(vehicleList, emptySlotList);
    }

    @Test
    public void givenParkingLotOnEmptySlot_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.setCapacity(10);
        parkingLot.initializeParkingLotForVehicle();
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean vehiclePark = parkingLot.isVehicleParked(vehicle);
        assertTrue(vehiclePark);
    }

    @Test
    public void givenVehicle_WhenPresent_ShouldReturnVehicleParkingSlot() {
        parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        int vehicleSlot = parkingLotSystem.findVehicleInParkingLot(this.vehicle);
        Assert.assertEquals(0,vehicleSlot);
    }

    @Test
    public void givenVehicle_WhenNotPresent_ShouldReturnException() {
        parkingLotSystem.park(vehicle,VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        try {
            parkingLotSystem.findVehicleInParkingLot(new Vehicle());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void givenVehicle_WhenParkWithTime_ShouldReturnParkingTime() {
        parkingLot.setCapacity(5);
        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        ParkingSlots parkingSlot=new ParkingSlots(vehicle);
        LocalTime time = parkingSlot.time;
        LocalTime timeOfVehicle = parkingLotSystem.getFindTimeForVehicle(vehicle);
        Assert.assertEquals(time,timeOfVehicle);

    }

    @Test
    public void givenVehicle_WhenParkWithoutTime_ShouldReturnException() {
        try {
            parkingLotSystem.getFindTimeForVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals("Vehicle Is Not Available", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenAddedInParkingLot_ShouldReturnTrue() {
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLotSystem.getAddedLot(parkingLot);
        parkingLotSystem.getAddedLot(parkingLot1);
        boolean lotAdded = parkingLotSystem.isAfterLotAdded(parkingLot);
        boolean lot1Added = parkingLotSystem.isAfterLotAdded(parkingLot1);
        assertTrue(lotAdded && lot1Added);
    }

    //UC9//
    @Test
    public void givenParkingLotSystem_WhenVehicleShouldParkInEvenlyInDistributedLots_ShouldReturnTrue() {
        parkingLot.setCapacity(5);
        parkingLot.initializeParkingLotForVehicle();
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setCapacity(3);
        parkingLot1.initializeParkingLotForVehicle();
        Vehicle vehicle2 = new Vehicle();
        Vehicle vehicle3 = new Vehicle();
        Vehicle vehicle4 = new Vehicle();
        parkingLotSystem.getAddedLot(parkingLot1);

        parkingLotSystem.park(vehicle, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isVehiclePark1 = parkingLotSystem.isVehicleParked(vehicle);
        parkingLotSystem.park(vehicle2, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isVehiclePark2 = parkingLotSystem.isVehicleParked(vehicle2);
        parkingLotSystem.park(vehicle3, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isVehiclePark3 = parkingLotSystem.isVehicleParked(vehicle3);
        parkingLotSystem.park(vehicle4, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isVehiclePark4 = parkingLotSystem.isVehicleParked(vehicle4);
        assertTrue(isVehiclePark1 && isVehiclePark2 && isVehiclePark3 && isVehiclePark4);
    }

    //UC10//
    @Test
    public void givenParkedAtEmptySlot_WhenDriverTypeHandicap_ShouldReturnTrue() {
        parkingLot.setCapacity(5);
        Vehicle vehicle2 = new Vehicle();
        parkingLotSystem.park(vehicle, VehicleType.LARGE, EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle2, VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(new Vehicle(), VehicleType.LARGE,EnumDriverType.HANDICAPDRIVER);
        parkingLotSystem.getUnParked(vehicle2);
        parkingLotSystem.getUnParked(vehicle);
        parkingLotSystem.park(vehicle,  VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle2, VehicleType.LARGE, EnumDriverType.HANDICAPDRIVER);
        int vehicleParkedLocation = parkingLotSystem.findVehicleInParkingLot(vehicle2);
        assertEquals(0, vehicleParkedLocation);
    }

    //UC11//
    @Test
    public void givenLargeVehicle_WhenParkByEvenlyDistribution_ShouldReturnTrue() {
        parkingLot.setCapacity(3);
        parkingLotSystem.park(vehicle,VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        boolean isVehicleParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isVehicleParked);
    }

    @Test
    public void givenLargeVehicle_WhenParkByEvenlyDistribution_ShouldReturnEmptySlotAfterLargeVehiclePark() {
        int expectedSlot=0;
        parkingLot.setCapacity(3);
        parkingLotSystem.park(vehicle,VehicleType.LARGE,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.isVehicleParked(vehicle);
        int emptySlot = parkingLotSystem.findVehicleInParkingLot(vehicle);
        Assert.assertEquals(expectedSlot,emptySlot);
    }

    //UC12//
    @Test
    public void givenParkingLot_WhenParkedWhiteVehicles_ShouldReturnListOfVehicles() {
        ArrayList<String> expectedList=new ArrayList<>();
        Vehicle vehicle2 = new Vehicle("White","Swift","MH18 BN 78963");
        expectedList.add("0 Swift White MH18 BN 78963");
        parkingLot.setCapacity(3);
        parkingLotSystem.park(vehicle,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle1, VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
        parkingLotSystem.park(vehicle2,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
        ArrayList sortedVehicleList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortedCatagories.COLOUR,"White");
        Assert.assertEquals(expectedList, sortedVehicleList);
    }

    @Test
    public void givenParkingLot_WhenNoOneWhiteVehiclesParked_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("Yellow","Swift Desire","MH17 OP 98765");
        Vehicle vehicle3 = new Vehicle("Gray","Honda","MH18 BH 845621");
        parkingLot.setCapacity(3);
        try {
            parkingLotSystem.park(vehicle1,VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(vehicle2, VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.park(vehicle3, VehicleType.SMALL,EnumDriverType.NORMALDRIVER);
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortedCatagories.COLOUR,"White");
        }catch (ParkingLotException e)
        {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }
}