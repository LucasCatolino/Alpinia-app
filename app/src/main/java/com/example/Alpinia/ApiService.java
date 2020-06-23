package com.example.Alpinia;

import com.example.Alpinia.faucet.FaucetState;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    //--------------------- ROOMS MANAGMENT -----------------------

    @POST("rooms")
    @Headers("Content-Type: application/json")
    Call<Result<Room>> addRoom(@Body Room room);

    @PUT("rooms/{roomId}")
    @Headers("Content-Type: application/json")
    Call<Result<Boolean>> modifyRoom(@Path("roomId") String roomId, @Body Room room);

    @DELETE("rooms/{roomId}")
    Call<Result<Boolean>> deleteRoom(@Path("roomId") String roomId);

    @GET("rooms/{roomId}")
    Call<Result<Room>> getRoom(@Path("roomId") String roomId);

    @GET("rooms")
    Call<Result<List<Room>>> getRooms();


    //------------------ DEVICES MANAGMENT --------------------------

    @POST("devices")
    @Headers("Content-Type: application/json")
    Call<Result<List<Device>>>  addDevice(@Body Device device);

    @GET("devices")
    Call<Result<List<Device>>> getDevices();

    @DELETE("devices/{deviceId}")
    Call<Result<Boolean>> deleteDevice(@Path("deviceId") String deviceId);

    @GET("devices/{deviceId}/state")
    Call<Result<DeviceState>> getDeviceState(@Path("deviceId") String deviceId);




    ////////////// FAUCET CONTROLS ////////////////////

    @GET("devices/{deviceId}/state")
    Call<Result<FaucetState>> getFaucetState(@Path("deviceId") String deviceId);

    @PUT("devices/{deviceId}/{actionName}")
    @Headers("Content-Type: application/json")
    Call<Result<Boolean>> openOrCloseFaucet(@Path("deviceId") String deviceId, @Path("actionName") String actionName);

}
