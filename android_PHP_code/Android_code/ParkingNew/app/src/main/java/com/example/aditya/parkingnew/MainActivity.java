package com.example.aditya.parkingnew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText Name,Password;
    String UserName,UserPassword;
    Button Login,SignUp;
    SharedPreferences pref;

    TextView Login_as_admin,forgetpasword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Name =(EditText)findViewById(R.id.name);
        Password =(EditText)findViewById(R.id.password);

        Login=(Button) findViewById(R.id.login);
        SignUp=(Button) findViewById(R.id.signup);

        Login_as_admin=(TextView) findViewById(R.id.login_as_admin);

        forgetpasword=(TextView) findViewById(R.id.forgetpasword);

        forgetpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplication(), FogetPassword.class);

                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });

        UserName = Name.getText().toString();
        UserPassword = Password.getText().toString();



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserName = Name.getText().toString();
                UserPassword = Password.getText().toString();

                if (UserName.isEmpty() || UserPassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
                } else {
                    Login();
                }

            }
        });


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplication(), SignUp.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });


        Login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplication(), QrReader.class);

                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


    }

    private void Login(){

        UserName = Name.getText().toString();
        UserPassword = Password.getText().toString();

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        retrofit.Call<People> peopleCall=service.Login(UserName,UserPassword);

        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

//                  String licence=  resp.getLicense();
//
//                    Toast.makeText(MainActivity.this,licence+"",Toast.LENGTH_LONG).show();

                    Toast.makeText(MainActivity.this,"Login Sucessful!",Toast.LENGTH_LONG).show();



                    progressDialog.dismiss();

                    loginSuccessful();
//
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
//                    editor.putString(Constants.EMAIL, resp.getUser().getEmail());
//                    editor.putString(Constants.USERTYPE, resp.getUser().getUser_type());
//                    editor.putString(Constants.NAME, resp.getUser().getName());
//                    editor.putString(Constants.COLLEGEID,resp.getUser().getCollege_id());
//                    editor.putString(Constants.PAIDORNOT,resp.getUser().getPaid());
//                    editor.apply();
//
//                    Dashboard();

                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid credentials!",Toast.LENGTH_LONG).show();
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


    private  void loginSuccessful()
    {
        UserName = Name.getText().toString();
         Intent i =new Intent(getApplication(),DashboardActivity.class);

                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra("user",UserName);
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

    }

}
