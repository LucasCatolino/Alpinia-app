package com.example.Alpinia.devices.oven;

import com.example.Alpinia.devices.oven.OvenState;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.devices.Device;
import com.example.Alpinia.devices.DeviceType;



public class Oven extends Device {
    @SerializedName("state")
    @Expose
    private OvenState state;

    public Oven(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}
