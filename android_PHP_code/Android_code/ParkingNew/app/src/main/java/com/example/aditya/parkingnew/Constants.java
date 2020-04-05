package com.example.aditya.parkingnew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 24-09-2016.
 */

public class Constants {

 public static final String BASE_URL ="http://192.168.0.105/";

    //"https://necrophilic-probabi.000webhostapp.com/";

    public static final String EMAIL="Email";
   public static final String CONTACT="CONTACT";
   public static final String LICENSE="LICENSE";
   public static final String ADHARCARD="ADHARCARD";


    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }














}