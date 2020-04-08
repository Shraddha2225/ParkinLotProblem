package com.bridgelab.parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingAvailabilityObserver {
    static ParkingAvailabilityObserver instanceGenerate;
    List<ParkingLotObserver> ListOfObservers;

    public ParkingAvailabilityObserver() {
        ListOfObservers = new ArrayList<>();
    }

    public static ParkingAvailabilityObserver getInstanceGenerate() {
        if (instanceGenerate == null)
            instanceGenerate = new ParkingAvailabilityObserver();
        return instanceGenerate;
    }

    public  void InformedParkingFull(){
        for (ParkingLotObserver list : ListOfObservers) {
            list.setCapacityIsFull();
        }
    }

    public  void InformedParkingAvailable(){
        for (ParkingLotObserver slotavailable : ListOfObservers ) {
            slotavailable.setParkingLotAvailable();
        }
    }

    public void registeredObserver(ParkingLotObserver observer) {
        ListOfObservers.add(observer);
    }


}
