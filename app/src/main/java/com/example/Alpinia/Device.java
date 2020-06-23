package com.example.Alpinia;

// import com.example.Alpinia.Meta;
// import com.example.Alpinia.State;
// import com.example.Alpinia.Type;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("type")
    @Expose
    protected DeviceType type;
   // @SerializedName("state")
    //@Expose
    //private State state;
    @SerializedName("meta")
    @Expose
    protected Object meta = new Object();


    public Device(String name, DeviceType type){
        this.name = name;
        this.type = type;
        this.meta = null;
    }
    public Device(String name, DeviceType type, State state){
        this.name = name;
        this.type = type;
       // this.state = state;
        this.meta = null;
    }

    public Device() {}

    public String getId() {
        return id;
    }

  //  public Room getRoom() {
    //    return room;
    //}

    public String getName() {
        return name;
    }
    public DeviceType getType() {
        return type;
    }

}