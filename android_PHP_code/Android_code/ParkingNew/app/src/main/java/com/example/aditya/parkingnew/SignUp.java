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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class SignUp extends AppCompatActivity {

    EditText Name,Email,Contact,License,Adharcard,Password,Confirmpassword;

    String strName,strEmail,strContact,strLicense,strAdharcard,strPassword,strConfirmpassword;

    Button SignUp;

    SharedPreferences pref;


    CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Name=(EditText)findViewById(R.id.signupname);
        Email=(EditText)findViewById(R.id.email);
        Contact=(EditText)findViewById(R.id.contact);
        License=(EditText)findViewById(R.id.license);
        Adharcard=(EditText)findViewById(R.id.adharcard);
        Password=(EditText)findViewById(R.id.password);
        Confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        checkBox=(CheckBox)findViewById(R.id.checkBox);

        SignUp =(Button)findViewById(R.id.signUp);

        strName=Name.getText().toString();
        strEmail=Email.getText().toString();
        strContact=Contact.getText().toString();
        strLicense=License.getText().toString();
        strAdharcard=Adharcard.getText().toString();
        strPassword=Password.getText().toString();
        strConfirmpassword=Confirmpassword.getText().toString();


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strName=Name.getText().toString();
                strEmail=Email.getText().toString();
                strContact=Contact.getText().toString();
                strLicense=License.getText().toString();
                strAdharcard=Adharcard.getText().toString();
                strPassword=Password.getText().toString();
                strConfirmpassword=Confirmpassword.getText().toString();

                if(strName.isEmpty() || strEmail.isEmpty() ||strContact.isEmpty() ||strLicense.isEmpty() ||strAdharcard.isEmpty() ||
                        strPassword.isEmpty() ||strConfirmpassword.isEmpty()){
                    Toast.makeText(SignUp.this,"Fields are empty",Toast.LENGTH_LONG).show();
                }
                else {
                    if(strPassword.equals(strConfirmpassword)) {
                        if (!strEmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                            Toast.makeText(SignUp.this, "Invalid Email!!", Toast.LENGTH_LONG).show();
                        }else {
                             if(!checkBox.isChecked())
                             {
                                 Toast.makeText(SignUp.this, "Please accept terms and conditions!", Toast.LENGTH_LONG).show();
                             }
                             else {
                                 SIGNUP();
                             }
                        }
                    }else{
                        Toast.makeText(SignUp.this,"Password Mismatched!",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

    }


    private void SIGNUP() {

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

        retrofit.Call<People> peopleCall=service.SIGNUP(strName,strEmail,strContact,strLicense,strAdharcard,strPassword,strConfirmpassword);

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

                }
                else{
                    Toast.makeText(SignUp.this,"This email already exist!",Toast.LENGTH_LONG).show();
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


    private  void alertSuccessful(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Response");
        builder.setMessage("Registered Successfully!");
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent i =new Intent(getApplication(),MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(i);
                        overridePendingTransition(R.anim.right_in, R.anim.left_out);
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
}
