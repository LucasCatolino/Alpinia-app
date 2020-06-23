package com.example.Alpinia.ac;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.example.Alpinia.DeviceState;

public class AirConditionerState extends DeviceState {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("temperature")
    @Expose
    private Integer temperature;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("verticalSwing")
    @Expose
    private String verticalSwing;

    @SerializedName("horizontalSwing")
    @Expose
    private String horizontalSwing;

    @SerializedName("fanSpeed")
    @Expose
    private String fanSpeed;

    public AirConditionerState(String status, Integer temperature, String mode, String verticalSwing, String horizontalSwing, String fanSpeed) {
        this.status = status;
        this.temperature = temperature;
        this.mode = mode;
        this.verticalSwing = verticalSwing;
        this.horizontalSwing = horizontalSwing;
        this.fanSpeed = fanSpeed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public void setVerticalSwing(String verticalSwing) {
        this.verticalSwing = verticalSwing;
    }

    public String getHorizontalSwing() {
        return horizontalSwing;
    }

    public void setHorizontalSwing(String horizontalSwing) {
        this.horizontalSwing = horizontalSwing;
    }

    public String getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(String fanSpeed) {
        this.fanSpeed = fanSpeed;
    }


}