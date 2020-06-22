package com.example.Alpinia.devices.speaker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.devices.Device;
import com.example.Alpinia.devices.DeviceType;



public class Speaker extends Device {
    @SerializedName("state")
    @Expose
    private SpeakerState state;

    public Speaker(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}
