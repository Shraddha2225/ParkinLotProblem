package com.bridgelab.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    ParkingLot parkingLot = null;
    Object vehicle = null;

    @Before
    public void setUp() {
        vehicle = new Object();
        parkingLot = new ParkingLot(3);
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        try {
            parkingLot.park(vehicle);
            boolean Isparked = parkingLot.isVehicalParked(vehicle);
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
            Assert.assertEquals("parkinglot is full", e.getMessage());
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

}
