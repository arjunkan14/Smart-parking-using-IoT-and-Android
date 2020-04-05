package com.example.aditya.adminparking;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.aditya.adminparking.model.People;
import com.example.aditya.adminparking.service.APIService;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by admin on 4/28/2018.
 */

public class QrReader extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    String TAG="QRREADER";


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.activity_main);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // call the alert dialog
        Alert(rawResult);

    }


    public void Alert(final Result rawResult){
        AlertDialog.Builder builder = new AlertDialog.Builder(QrReader.this);
        builder.setTitle("Qr scan result");
        builder.setMessage("Result :"+rawResult.getText()+"\nType :"+rawResult.getBarcodeFormat().toString())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // back to previous activity

                        //openGate(rawResult.getText());

                        Intent i = new Intent(QrReader.this,Histroyy.class);
                        i.putExtra("User",rawResult.getText());
                        finish();
                        startActivity(i);
                        finish();

                    }
                })
                .setNegativeButton("Scan Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
// If you would like to resume scanning, call this method below:
                        mScannerView.resumeCameraPreview(QrReader.this);
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void openGate(String user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);



        retrofit.Call<People> peopleCall=service.openGate(user);

        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();


                if (resp.getMessage().equals("success")) {



                    Toast.makeText(QrReader.this,"Gate is Opening!",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(QrReader.this,"No Parking availble!",Toast.LENGTH_LONG).show();
                }

                Log.d("onResponse", "" + response.code() +
                        "  response body "  + response.body() +
                        " responseError " + response.errorBody() + " responseMessage " +
                        response.message());
            }

            @Override
            public void onFailure(Throwable t) {

                Log.d("onFailure", t.toString());
            }
        });
    }
}
