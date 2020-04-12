package com.bridgelab.parking.Mockito;

import com.bridgelab.parking.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ParkingLotSystemTest {
    @Mock
    ParkingLot parkingLot;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    Vehicle vehicle;
    ParkingLotSystem parkingLotSystem;

    @Before
    public void setUp() throws Exception {
        vehicle = new Vehicle();
        parkingLotSystem =new ParkingLotSystem();
        parkingLotSystem.getAddedLot(parkingLot);
    }

    @Test
    public void testParkedVehicle_ShouldReturnTrue() {
        when(parkingLot.parkVehicle(any(),any(),any())).thenReturn(true);
        boolean isparked = parkingLotSystem.park(vehicle,VehicleType.SMALL,EnumDriverType.HANDICAPDRIVER);
        Assert.assertTrue(isparked);
    }

    @Test
    public void testUnParkedVehicle_ShouldReturnTrue() {
        when(parkingLot.getUnParked(vehicle)).thenReturn(true);
        boolean unParked = parkingLotSystem.getUnParked(vehicle);
        Assert.assertTrue(unParked);
    }

    @Test
    public void testIsVehicleParked_ShouldReturnTrue() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(true);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void testIsVehicleParked_ShouldReturnFalse() {
        when(parkingLot.isVehicleParked(vehicle)).thenReturn(false);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertFalse(isParked);
    }

    @Test
    public void testFindVehicle_ShouldReturnSlot() {
        when(parkingLot.findVehicle(vehicle)).thenReturn(1);
        int slot = parkingLotSystem.findVehicleInParkingLot(vehicle);
        Assert.assertEquals(1,slot);
    }

    @Test
    public void testFindVehicle_ShouldReturnException() {
        when(parkingLot.findVehicle(vehicle)).thenThrow(new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND));
        try{
            parkingLotSystem.findVehicleInParkingLot(vehicle);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void testSearchVehiclesByGivenFields() {
        ArrayList<String> list=new ArrayList<>();
        list.add("0 Ertiga White MH19 AB 567");
        list.add("2 Swift Yellow MH18 GH 4567");
        when(parkingLot.searchVehiclesByGivenCatagories(any(),any())).thenReturn(list);
        ArrayList<String> vehiclesList = parkingLotSystem.searchVehiclesByGivenFields(VehicleSortedCatagories.COLOUR,"White");
        Assert.assertEquals(list,vehiclesList);
    }

    @Test
    public void testSearchVehiclesByGivenFields_ShouldReturnException() {
        when(parkingLot.searchVehiclesByGivenCatagories(any(),any())).thenThrow(new ParkingLotException("Vehicle Not Present", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND));
        try{
            parkingLotSystem.searchVehiclesByGivenFields(VehicleSortedCatagories.COLOUR,"White");
        }catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }
}
