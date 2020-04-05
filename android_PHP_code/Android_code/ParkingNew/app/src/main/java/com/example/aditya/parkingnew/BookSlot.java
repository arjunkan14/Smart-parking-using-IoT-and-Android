package com.example.aditya.parkingnew;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class BookSlot extends AppCompatActivity {

    String SlotNo;

    EditText CarNumber,LicenceNo,DriverName;
    TextView Amount,CurrenTime;
    Button Next;

    String strCarNumber,strInTime,strAmount,strLicenceNo,strDriverName,strCurrenTime;

    private TimePicker timePicker1;
    private TextView time;
    private Calendar calendar;
    private String format = "";

    Button Save,InTime;
    String User;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_slot);

        User = getIntent().getExtras().getString("User");

        CurrenTime= (TextView) findViewById(R.id.current_time);

        timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
        timePicker1.setIs24HourView(true);
        time = (TextView) findViewById(R.id.textView1);

        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        //showTime1(hour, min);

        CarNumber=(EditText) findViewById(R.id.car_number);
        LicenceNo=(EditText) findViewById(R.id.license_no);
        DriverName=(EditText) findViewById(R.id.driver_name);

        Amount =(TextView) findViewById(R.id.textView1);

        InTime=(Button) findViewById(R.id.in_time);
        Save=(Button) findViewById(R.id.save_time);
        Next=(Button) findViewById(R.id.next);


        strCarNumber=CarNumber.getText().toString();
        strInTime=time.getText().toString();
        strAmount=Amount.getText().toString();
        strLicenceNo=LicenceNo.getText().toString();
        strDriverName=DriverName.getText().toString();
        SlotNo = getIntent().getExtras().getString("SlotNo");

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPendingAmount();
            }
        });






//                   Intent i = new Intent(BookSlot.this, Wallet.class);
//
//                   i.putExtra("SlotNo", SlotNo);
//                   i.putExtra("strCarNumber", strCarNumber);
//                   i.putExtra("strInTime", strInTime);
//                   i.putExtra("strAmount", strAmount);
//                   i.putExtra("strLicenceNo", strLicenceNo);
//                   i.putExtra("strDriverName", strDriverName);
//                   startActivity(i);
//                   overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
//               }
                //}



        InTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker1.setVisibility(View.VISIBLE);
                InTime.setVisibility(View.INVISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Save.setVisibility(View.VISIBLE);

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = timePicker1.getCurrentHour();
                int min = timePicker1.getCurrentMinute();
                showTime(hour, min);
                setCurrentTime();

                timePicker1.setVisibility(View.GONE);
                InTime.setVisibility(View.VISIBLE);
                Next.setVisibility(View.VISIBLE);
                Save.setVisibility(View.INVISIBLE);




            }
        });


        String ts = Constants.getDateTime();
        strCurrenTime=ts.substring(10);

        String  newString = ts.replaceAll(":", "");
        String  date = newString.replaceAll("-", "");
        String  finaldate = date.replaceAll(" ", "");
        finaldate =finaldate.substring(8);


        CurrenTime.setText("Current Time : "+strCurrenTime);

    }

    private void setCurrentTime() {
        String ts = Constants.getDateTime();
        strCurrenTime=ts.substring(10);

        CurrenTime.setText("Current Time : "+strCurrenTime);

    }

    public void setTime(View view) {
        int hour = timePicker1.getCurrentHour();
        int min = timePicker1.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
//        if (hour == 0) {
//            hour += 12;
//            format = "AM";
//        } else if (hour == 12) {
//            format = "PM";
//        } else if (hour > 12) {
//            hour -= 12;
//            format = "PM";
//        } else {
//            format = "AM";
//        }

        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));



    }

    private  void alertTimeSpan(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("You can not book before 3 hours!");

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {



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


    private void getPendingAmount(){


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall=service.getPendingAmount(User);


        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    String amountWallet = resp.getWallet_amount();

                    int newAmount = Integer.parseInt(amountWallet);

                    if (newAmount <= 0) {

                        strCarNumber = CarNumber.getText().toString();
                        strInTime = time.getText().toString();
                        strAmount = Amount.getText().toString();
                        strLicenceNo = LicenceNo.getText().toString();
                        strDriverName = DriverName.getText().toString();
                        SlotNo = getIntent().getExtras().getString("SlotNo");

                        if (strCarNumber.isEmpty() || strInTime.isEmpty() || strLicenceNo.isEmpty() || strDriverName.isEmpty()) {
                            Toast.makeText(BookSlot.this, "Fields are empty!", Toast.LENGTH_LONG).show();
                        } else {


                            String ts = Constants.getDateTime();

                            String newString = ts.replaceAll(":", "");
                            String date = newString.replaceAll("-", "");
                            String finaldate = date.replaceAll(" ", "");
                            finaldate = finaldate.substring(8, 10);

                            // Toast.makeText(BookSlot.this, finaldate, Toast.LENGTH_LONG).show();

                            String selectedTime = time.getText().toString();
                            String newTime = selectedTime.replaceAll(":", "");
                            String newselectedTime = newTime.replaceAll(" ", "");

                            String newOne = newselectedTime.substring(0, 2);

                            // Toast.makeText(BookSlot.this, newOne, Toast.LENGTH_LONG).show();

                            int timeDifference = Integer.parseInt(newOne) - Integer.parseInt(finaldate);

                            // Toast.makeText(BookSlot.this, timeDifference + "", Toast.LENGTH_LONG).show();

                            if (timeDifference > 3) {
                                alertTimeSpan();
                            } else {
                                int calculatedAmount = timeDifference * 10 + 20;
                                Intent i = new Intent(BookSlot.this, PayNow.class);
                                i.putExtra("User", User);
                                i.putExtra("SlotNo", SlotNo);
                                i.putExtra("strCarNumber", strCarNumber);
                                i.putExtra("strInTime", strInTime);
                                i.putExtra("strAmount", calculatedAmount + "");
                                i.putExtra("strLicenceNo", strLicenceNo);
                                i.putExtra("strDriverName", strDriverName);
                                startActivity(i);
                                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);


                            }
                        }

                        progressDialog.dismiss();


                    } else {

                        pendingamount();

                    }

                    Log.d("onResponse", "" + response.code() +
                            "  response body " + response.body() +
                            " responseError " + response.errorBody() + " responseMessage " +
                            response.message());
                }
            }
            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
                Log.d("onFailure", t.toString());
            }
        });

    }

    private  void pendingamount(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("You have pending Amount pay first!");

        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent i=new Intent(BookSlot.this, payPending.class);
                        i.putExtra("User", User);
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
