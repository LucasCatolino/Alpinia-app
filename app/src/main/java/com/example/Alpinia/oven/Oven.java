package com.example.Alpinia.oven;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.Device;
import com.example.Alpinia.DeviceType;



public class Oven extends Device {
    @SerializedName("state")
    @Expose
    private OvenState state;

    public Oven(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}
