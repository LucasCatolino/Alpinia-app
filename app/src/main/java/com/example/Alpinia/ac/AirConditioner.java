package com.example.Alpinia.ac;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.Device;
import com.example.Alpinia.DeviceType;



public class AirConditioner extends Device {
    @SerializedName("state")
    @Expose
    private  AirConditionerState state;

    public AirConditioner(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}