package com.example.aditya.adminparking.service;



import com.example.aditya.adminparking.model.People;

import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface APIService {

   @FormUrlEncoded
   @POST("/Parking/ParkingCheck/check.php")
    Call<List<People>>CheckSlots(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/Parking/Login/login.php")
    Call<People>Login(@Field("UserName") String UserName, @Field("UserPassword") String UserPassword);


    @FormUrlEncoded
    @POST("/Parking/Gate/openGate.php")
    Call<People>openGate(@Field("User") String User);



 @FormUrlEncoded
 @POST("/Parking/Gate/closeGate.php")
 Call<People>closeGate(@Field("User") String User);




    @FormUrlEncoded
    @POST("/Parking/ParkingCheck/bookslot.php")
    Call<People>Booknow(@Field("User") String User, @Field("SlotNo") String SlotNo, @Field("strLicenceNo") String strLicenceNo, @Field("strCarNumber") String strCarNumber,
                        @Field("strInTime") String strInTime, @Field("strAmount") String strAmount);



    @FormUrlEncoded
    @POST("/Parking/Wallet/getWalletAmount.php")
    Call<People>getWalletAmount(@Field("User") String User);


     @FormUrlEncoded
     @POST("/Parking/Wallet/addmoney.php")
     Call<People>AddamountWallet(@Field("User") String User, @Field("Amount") String Amount);


    @FormUrlEncoded
    @POST("/Parking/History/history.php")
    Call<List<People>>getHistory(@Field("User") String User);


}


