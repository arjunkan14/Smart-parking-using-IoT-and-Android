package com.example.aditya.parkingnew.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 17-09-2016.
 */

public class People {

    @SerializedName("post_id") @Expose
    private String post_id;
    @SerializedName("user_email")@Expose
    private String user_email;
    @SerializedName("user_id") @Expose
    private String user_id;
    @SerializedName("slot_no") @Expose private String slot_no;
    @SerializedName("status") @Expose private String status;
    @SerializedName("wallet_amount")@Expose private String wallet_amount;
    @SerializedName("message")@Expose private String message;
    @SerializedName("license")@Expose private String license;

    @SerializedName("car_no")@Expose private String car_no;
    @SerializedName("license_no")@Expose private String license_no;
    @SerializedName("in_time")@Expose private String in_time;
    @SerializedName("amount")@Expose private String amount;
    @SerializedName("bookingtime")@Expose private String bookingtime;


    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public String getLicense_no() {
        return license_no;
    }

    public void setLicense_no(String license_no) {
        this.license_no = license_no;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBookingtime() {
        return bookingtime;
    }

    public void setBookingtime(String bookingtime) {
        this.bookingtime = bookingtime;
    }




    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getPost_id() {
        return post_id;
    }
    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
    public String getUser_email() { return user_email; }
    public void setUser_email(String user_email) {this.user_email = user_email;}
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getSlot_no() {
        return slot_no;
    }
    public void setSlot_no(String slot_no) {
        this.slot_no = slot_no;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {this.status = status;}
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {this.license = license;}
    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }



}

