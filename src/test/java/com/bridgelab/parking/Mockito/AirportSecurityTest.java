package com.bridgelab.parking.Mockito;

import com.bridgelab.parking.AirportSecurity;
import com.bridgelab.parking.ParkingAvailabilityObserver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class AirportSecurityTest {
    AirportSecurity airportSecurity;
    ParkingAvailabilityObserver availabilityObserver;

    @Before
    public void setUp() throws Exception {
        airportSecurity = new AirportSecurity();
        availabilityObserver = mock(ParkingAvailabilityObserver.class);
    }

    @Test
    public void givenMockitoTest_WhenCheckSecurityClassWithCheckCapacityIsFull() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                airportSecurity.capacityIsFull();
                return null;
            }
        }).when(availabilityObserver).InformedParkingAvailable();

        availabilityObserver.InformedParkingAvailable();
        Assert.assertTrue(airportSecurity.isCapacityFull());
    }




}
