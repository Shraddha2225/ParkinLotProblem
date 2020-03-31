package com.bridgelab.parking;

import java.time.LocalTime;

public class VehicleParking {
    private LocalTime time;

    public LocalTime getTime() {
        return time;
    }

    public void setTime() {
        this.time = LocalTime.now();
    }


}
