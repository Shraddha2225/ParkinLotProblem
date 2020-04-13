package com.bridgelab.parking.Mockito;

import com.bridgelab.parking.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class TestParkingLot {
    @Mock
    ParkingLotSystem parkingLotSystem;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    ParkingLot parkingLot;
    Vehicle vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem=mock(ParkingLotSystem.class);
        parkingLot=new ParkingLot(2);
        vehicle=new Vehicle();
    }

    @Test
    public void parkFunctionTest() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                parkingLot.parkVehicle(any(),any(),any());
                return true;
            }
        }).when(parkingLotSystem).park(vehicle, VehicleType.SMALL,EnumDriverType.HANDICAPDRIVER);
        boolean vehicleParked = parkingLot.parkVehicle(vehicle,VehicleType.SMALL,EnumDriverType.HANDICAPDRIVER);
        Assert.assertTrue(vehicleParked);
    }
}
