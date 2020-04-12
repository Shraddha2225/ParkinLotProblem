package com.bridgelab.parking;

import java.util.ArrayList;
import java.util.Collections;

public enum  EnumDriverType {


    NORMALDRIVER{
        @Override
        public ArrayList<Integer> getEmptyList(ArrayList<Integer> emptySlot) {
            Collections.sort(emptySlot, Collections.reverseOrder());
            return emptySlot;
        }},
    HANDICAPDRIVER{
        @Override
        public ArrayList<Integer> getEmptyList(ArrayList<Integer> emptySlot){
            Collections.sort(emptySlot);
            return emptySlot;
        }

    };

    public abstract ArrayList<Integer>getEmptyList(ArrayList<Integer> emptySlot);
}
