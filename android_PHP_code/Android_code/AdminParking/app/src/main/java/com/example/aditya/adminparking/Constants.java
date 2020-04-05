package com.example.aditya.adminparking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 24-09-2016.
 */

public class Constants {

 public static final String BASE_URL ="http://192.168.1.38/";

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