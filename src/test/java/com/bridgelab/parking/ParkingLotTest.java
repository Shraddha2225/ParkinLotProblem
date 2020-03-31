package com.bridgelab.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {
    ParkingLot parkingLot = null;
    VehicleParking vehicle = null;
    VehicleParking vehicle1 = null;
    List vehicleList;


    @Before
    public void setUp() {
        vehicle =new VehicleParking();
        vehicle1 =new VehicleParking();
        vehicleList = new ArrayList();
        parkingLot = new ParkingLot(2);
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle,parkingLot.DriverType.NORMALDRIVER);
            boolean Isparked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(Isparked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenVechical_WhenAlReadyParked_ShouldReturnFalse() {
        try {
            parkingLot.park(vehicle,parkingLot.DriverType.NORMALDRIVER);
            parkingLot.park(vehicle,parkingLot.DriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            Assert.assertEquals("vehicle already parked", e.getMessage());
        }
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() throws ParkingLotException {
        try {
            parkingLot.park(vehicle,parkingLot.DriverType.NORMALDRIVER);
            boolean isUnparked = parkingLot.getUnParked(vehicle);
            Assert.assertTrue(isUnparked);
        } catch (ParkingLotException e) {
        }
    }

    //UC3
    @Test
    public void givenWhenParkingLotIsFull_ShouldInformTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
            parkingLot.park(vehicle1, parkingLot.DriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenCapacityIs2_ShouldAbleToPark2Vechile() {
        parkingLot.setCapacity(2);
        parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
        parkingLot.park(vehicle1, parkingLot.DriverType.NORMALDRIVER);
        boolean isParked1 = parkingLot.isVehicleParked(vehicle);
        boolean isParked2 = parkingLot.isVehicleParked(vehicle1);
        Assert.assertTrue(isParked1 && isParked2);
    }


    @Test
    public void givenWhenLotIsFull_ShouldInformTheSecurity() {
        AirportSecurity airportSecurity=new AirportSecurity();
        parkingLot.registerParkingLotObserver(airportSecurity);
        try {
            parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
            parkingLot.park(vehicle1, parkingLot.DriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            boolean capacityFull = airportSecurity.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenWhenLotSpaceAgain_ShouldInformTheOwner() {
        ParkingLotOwner owner=new ParkingLotOwner();
        parkingLot.registerParkingLotObserver(owner);
        try {
            parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
            parkingLot.park(vehicle1, parkingLot.DriverType.NORMALDRIVER);
        } catch (ParkingLotException e) {
            parkingLot.getUnParked(vehicle);
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertFalse(capacityFull);
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

    @Test
    public void givenVehicle_WhenParkWithTime_ShouldReturnParkingTime() {
        parkingLot.setCapacity(5);
        parkingLot.initializeParkingLot();
        vehicle.setTime();
        parkingLot.parked(0,vehicle);
        LocalTime time = vehicle.getTime();
        LocalTime timeOfVehicle = parkingLot.findTimeForVehicle(vehicle);
        Assert.assertEquals(time,timeOfVehicle);
    }

    @Test
    public void givenVehicle_WhenParkWithoutTime_ShouldReturnException() {
        try {
            parkingLot.setCapacity(5);
            parkingLot.initializeParkingLot();
            parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
            parkingLot.findTimeForVehicle(vehicle);
        }   catch (ParkingLotException e){
            Assert.assertEquals("Vehicle Has Not Set Time",e.getMessage());
        }
    }


    @Test
    public void givenVehicle_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        VehicleParking vehicle2=new VehicleParking();
        parkingLot.setCapacity(3);
        parkingLot.initializeParkingLot();
        parkingLot.park(vehicle, parkingLot.DriverType.NORMALDRIVER);
        parkingLot.park(vehicle1, parkingLot.DriverType.NORMALDRIVER);
        parkingLot.park(vehicle2, parkingLot.DriverType.NORMALDRIVER);
        boolean isVehicleParked = parkingLot.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLot.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLot.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked && isVehicleParked1 && isVehicleParked2);
    }

    @Test
    public void givenDriverTypeHandicap_WhenParkedAtEmptySlot_ShouldReturnTrue() {
        VehicleParking vehicle2=new VehicleParking();
        parkingLot.setCapacity(3);
        parkingLot.initializeParkingLot();
        parkingLot.park(vehicle, parkingLot.DriverType.HANDICAPDRIVER);
        parkingLot.park(vehicle1, parkingLot.DriverType.HANDICAPDRIVER);
        parkingLot.park(vehicle2, parkingLot.DriverType.HANDICAPDRIVER);
        boolean isVehicleParked = parkingLot.isVehicleParked(vehicle);
        boolean isVehicleParked1 = parkingLot.isVehicleParked(vehicle1);
        boolean isVehicleParked2 = parkingLot.isVehicleParked(vehicle2);
        Assert.assertTrue(isVehicleParked && isVehicleParked1 && isVehicleParked2);
    }
}
