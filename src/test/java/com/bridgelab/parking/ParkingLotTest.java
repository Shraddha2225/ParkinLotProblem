package com.bridgelab.parking;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
    }

    @Test
    public void givenVehicle_WhenParked_ShouldReturnTrue() {
        System.out.println("WELCOME TO PARKING LOT PROBLEM");
        boolean park = parkingLot.getPark(new Object());
        Assert.assertTrue(park);
    }

    @Test
    public void givenVehicle_WhenUnParked_ShouldReturnTrue() {
        Object Car=new Object();
        parkingLot.getPark(Car);
        boolean unParked = parkingLot.getUnParked(Car);
        Assert.assertTrue(unParked);
    }
}
