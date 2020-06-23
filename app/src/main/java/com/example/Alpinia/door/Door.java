package com.example.Alpinia.door;

import com.example.Alpinia.door.DoorState;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.Device;
import com.example.Alpinia.DeviceType;



public class Door extends Device {
    @SerializedName("state")
    @Expose
    private DoorState state;

    public Door(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}