package com.bridgelab.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {
    ParkingLot parkingLot = null;
    Object vehicle = null;
    List vehicleList;
    Object vehicle1 = null;

    @Before
    public void setUp() {
        vehicle = new Object();
        vehicle1 =new Object();
        vehicleList = new ArrayList();
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle);
            boolean Isparked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(Isparked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenVechical_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLot.park(vehicle);
            parkingLot.park(new Object());
        } catch (ParkingLotException e) {
            Assert.assertEquals("vehicle already parked", e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        try {
            parkingLot.park(vehicle);
            boolean isUnparked = parkingLot.getUnParked(vehicle);
            Assert.assertTrue(isUnparked);
        } catch (ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vechile() {
        Object vehicle2 = new Object();
        parkingLot.setCapacity(2);
        try {
            parkingLot.park(vehicle);
            parkingLot.park(vehicle2);
            boolean isParked1 = parkingLot.isVehicleParked(vehicle);
            boolean isParked2 = parkingLot.isVehicleParked(vehicle2);

            Assert.assertTrue(isParked1 && isParked2);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenWhenLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity = new AirportSecurity();
        parkingLot.registerParkingLotOwner(airportSecurity);
        try {
            parkingLot.park(vehicle);
            parkingLot.park(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenWhenLotSpaceAgain_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        try {
            parkingLot.park(vehicle);
            parkingLot.park(new Object());
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenInitializeParkingLot_ShouldReturnParkingCapacity() {
        parkingLot.setCapacity(10);
        int parkingLotCapacity = parkingLot.initializeParkingLot();
        Assert.assertEquals(10,parkingLotCapacity);
    }

    @Test
    public void givenParkingLot_ShouldReturnAvailableSlots() {
        vehicleList.add(0);
        vehicleList.add(1);
        parkingLot.setCapacity(2);
        parkingLot.initializeParkingLot();
        ArrayList emptySlotList = parkingLot.getSlot();
        Assert.assertEquals(vehicleList, emptySlotList);
    }

    @Test
    public void givenParkingLot_WhenParkWithProvidedSlot_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.setCapacity(3);
        parkingLot.parked(0, vehicle);
        boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(vehicleParked);
    }

    @Test
    public void givenParkAndUnParkVehicles_ShouldReturnEmptySlots() throws ParkingLotException {
        vehicleList.add(0);
        vehicleList.add(2);
        parkingLot.setCapacity(3);
        parkingLot.parked(0, vehicle);
        parkingLot.parked(1,vehicle1);
        parkingLot.getUnParked(vehicle);
        ArrayList emptySlotList = parkingLot.getSlot();
        Assert.assertEquals(vehicleList, emptySlotList);
    }

    @Test
    public void givenParkingLotOnEmptySlot_ShouldReturnTrue() throws ParkingLotException {
        parkingLot.setCapacity(10);
        parkingLot.initializeParkingLot();
        ArrayList<Integer> emptySlotList = parkingLot.getSlot();
        parkingLot.parked(emptySlotList.get(1), vehicle);
        boolean vehiclePark = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(vehiclePark);
    }

    @Test
    public void givenVehicle_WhenPresent_ShouldReturnVehicleSlot() {
        parkingLot.setCapacity(5);
        parkingLot.initializeParkingLot();
        parkingLot.parked(0,vehicle);
        parkingLot.parked(1,vehicle1);
        int vehicleSlot = parkingLot.findVehicleInParkingLot(this.vehicle);
        Assert.assertEquals(0,vehicleSlot);
    }

    @Test
    public void givenVehicle_WhenNotPresent_ShouldReturnException() {
        try {
            parkingLot.setCapacity(5);
            parkingLot.initializeParkingLot();
            parkingLot.parked(0, vehicle);
            parkingLot.findVehicleInParkingLot(vehicle1);
        }catch (ParkingLotException e){
            Assert.assertEquals("Vehicle Is Absent",e.getMessage());
        }
    }
}
