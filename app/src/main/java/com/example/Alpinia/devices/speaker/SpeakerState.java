package com.example.Alpinia.devices.speaker;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.devices.DeviceState;

public class SpeakerState extends DeviceState {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("volume")
    @Expose
    private Integer volume;

    @SerializedName("genre")
    @Expose
    private Integer genre;
}
