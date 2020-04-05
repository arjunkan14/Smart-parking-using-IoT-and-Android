package com.example.aditya.parkingnew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class FogetPassword extends AppCompatActivity {

    EditText email;
    Button Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_password);

        email = (EditText) findViewById(R.id.email);
        Send = (Button) findViewById(R.id.send);

        Send.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String Email = email.getText().toString();

                                        if(Email.isEmpty())
                                        {
                                            Toast.makeText(FogetPassword.this, "Please Enter Email!!", Toast.LENGTH_LONG).show();

                                        }else {
                                            if (!Email.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                                                Toast.makeText(FogetPassword.this, "Invalid Email!!", Toast.LENGTH_LONG).show();
                                            }else {sendEmail(Email);}
                                        }

                                    }
                                }

        );
    }

    private void sendEmail(String email)

    {




        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall = service.sendEmail(email);

        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    progressDialog.dismiss();

//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
//                    editor.putString(Constants.NAME, strName);
//                    editor.putString(Constants.EMAIL, strEmail);
//                    editor.putString(Constants.CONTACT, strContact);
//                    editor.putString(Constants.LICENSE,strLicense);
//                    editor.putString(Constants.ADHARCARD,strAdharcard);
//                    editor.apply();

                    alertSuccessful();

                } else {
                    Toast.makeText(FogetPassword.this, "This Email does not exist!", Toast.LENGTH_LONG).show();
                }

                Log.d("onResponse", "" + response.code() +
                        "  response body " + response.body() +
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


    private void alertSuccessful() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Response");
        builder.setMessage("Email Sent You successfully please check it out!");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(getApplication(), MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        builder.show();
    }
}

