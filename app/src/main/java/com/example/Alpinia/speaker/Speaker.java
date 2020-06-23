package com.example.Alpinia.speaker;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.Device;
import com.example.Alpinia.DeviceType;



public class Speaker extends Device {
    @SerializedName("state")
    @Expose
    private SpeakerState state;

    public Speaker(String name, DeviceType type) {
        this.name = name;
        this.type = type;
    }
}
