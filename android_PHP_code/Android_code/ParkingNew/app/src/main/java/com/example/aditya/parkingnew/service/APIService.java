package com.example.aditya.parkingnew.service;

import com.example.aditya.parkingnew.model.People;


import java.util.List;

import okhttp3.MultipartBody;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface APIService {

   @FormUrlEncoded
   @POST("/Parking/ParkingCheck/check.php")
    Call<List<People>>CheckSlots(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/Parking/Login/login.php")
    Call<People>Login(@Field("UserName") String UserName, @Field("UserPassword") String UserPassword);


    @FormUrlEncoded
    @POST("/Parking/Login/signup.php")
    Call<People>SIGNUP(@Field("strName") String strName, @Field("strEmail") String strEmail,
                       @Field("strContact") String strContact, @Field("strLicense") String strLicense,
                       @Field("strAdharcard") String strAdharcard, @Field("strPassword") String strPassword,
                       @Field("strConfirmpassword") String strConfirmpassword);


    @FormUrlEncoded
    @POST("/Parking/ParkingCheck/bookslot.php")
    Call<People>Booknow(@Field("User") String User, @Field("SlotNo") String SlotNo, @Field("strLicenceNo") String strLicenceNo, @Field("strCarNumber") String strCarNumber,
                        @Field("strInTime") String strInTime, @Field("strAmount") String strAmount);


 @FormUrlEncoded
 @POST("/Parking/ParkingCheck/pendingAmount.php")
 Call<People>payNow(@Field("User") String User,@Field("strAmount") String strAmount);





    @FormUrlEncoded
    @POST("/Parking/Wallet/getWalletAmount.php")
    Call<People>getWalletAmount(@Field("User") String User);

 @FormUrlEncoded
 @POST("/Parking/Wallet/getPendingAmount.php")
 Call<People>getPendingAmount(@Field("User") String User);




     @FormUrlEncoded
     @POST("/Parking/Wallet/addmoney.php")
     Call<People>AddamountWallet(@Field("User") String User, @Field("Amount") String Amount);


    @FormUrlEncoded
    @POST("/Parking/History/history.php")
    Call<List<People>>getHistory(@Field("User") String User);



 @FormUrlEncoded
 @POST("/Parking/Profile/chngepass.php")
  Call<People>chngepass(@Field("OldPass") String OldPass, @Field("NewPass") String NewPass, @Field("User") String User);







 @FormUrlEncoded
 @POST("/Parking/Profile/sendEmailForgetpass.php")
 Call<People>sendEmail(@Field("Email") String Email);
}


