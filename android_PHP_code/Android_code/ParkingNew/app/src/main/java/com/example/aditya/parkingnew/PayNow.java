package com.example.aditya.parkingnew;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class PayNow extends AppCompatActivity {
    private ProgressDialog pDialog;


    String SlotNo,strCarNumber,strInTime,strAmount,strLicenceNo,strDriverName,User;

    TextView amount;

    Button Paynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);



        User = getIntent().getExtras().getString("User");

        //Toast.makeText(PayNow.this, User+"UserName", Toast.LENGTH_LONG).show();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        SlotNo = getIntent().getExtras().getString("SlotNo");
        strCarNumber = getIntent().getExtras().getString("strCarNumber");
        strInTime = getIntent().getExtras().getString("strInTime");
        strAmount = getIntent().getExtras().getString("strAmount");
        strLicenceNo= getIntent().getExtras().getString("strLicenceNo");
        strDriverName= getIntent().getExtras().getString("strDriverName");

        amount=(TextView)findViewById(R.id.amount_to_pay);

        amount.setText("Rs: "+strAmount+"/-");

        Paynow=(Button) findViewById(R.id.payNow);

        Paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWalletAmount();
            }
        });

    }


    private void BookNow() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall=service.Booknow(User,SlotNo,strCarNumber,strLicenceNo,strInTime,strAmount);


        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    progressDialog.dismiss();

                    AlertDialog.Builder builder = new AlertDialog.Builder(PayNow.this);

                    builder.setTitle("Response");

                    builder.setMessage("Booked successfully");

                    builder.setPositiveButton("",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // dialog.cancel();


                                }
                            });
                    builder.setNegativeButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                    Intent intent =new Intent(PayNow.this,DashboardActivity.class);
                                    intent.putExtra("user",User);
                                    finish();
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    //finish();
                                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                    NotificationCompat.Builder b = new NotificationCompat.Builder(getApplication());
                                    b.setAutoCancel(true)
                                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                                            .setWhen(System.currentTimeMillis())
                                            .setSmallIcon(R.drawable.pic)
                                            .setTicker("Booked successfully")
                                            .setContentTitle("Smart Parking")
                                            .setContentText("Booked successfully")
                                            .setContentInfo(" ");

                                    NotificationManager nm = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                    nm.notify(1, b.build());
                                }
                            });

                    builder.show();

                }
                else{

                }

                Log.d("onResponse", "" + response.code() +
                        "  response body "  + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", t.toString());
            }
        });

    }


    private void getWalletAmount(){


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall=service.getWalletAmount(User);


        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    String amountWallet=resp.getWallet_amount();

                    int newAmount= Integer.parseInt(amountWallet)- Integer.parseInt(strAmount);

                    if(newAmount<0){

                        alertpaymoney();

                    }else{
                        BookNow();
                    }

                    progressDialog.dismiss();


                }
                else{

                }

                Log.d("onResponse", "" + response.code() +
                        "  response body "  + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", t.toString());
            }
        });

    }


    private void alertpaymoney() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("You dont have enough amount in your wallet! "+"\n"+"Add money to wallet!");

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent i = new Intent(PayNow.this,Wallet.class);
                        i.putExtra("User",User);
                        finish();
                        startActivity(i);



                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();

                    }
                });

        builder.show();
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
