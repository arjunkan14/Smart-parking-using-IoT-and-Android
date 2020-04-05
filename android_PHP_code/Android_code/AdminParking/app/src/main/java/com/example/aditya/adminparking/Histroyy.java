package com.example.aditya.adminparking;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.adminparking.model.People;
import com.example.aditya.adminparking.service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Histroyy  extends AppCompatActivity {

    private ProgressDialog pDialog;
    List<People> post = new ArrayList<>();
    List<People> oldposts;
    int listpostid =0;
    MyAdapter adapter;
    String user_id;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    android.support.v7.widget.Toolbar toolbar;

    String postType,User;

    Button open,close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histroyy);

        User = getIntent().getExtras().getString("User");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("History");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        open=(Button)findViewById(R.id.open);
        close=(Button)findViewById(R.id.close);


        mRecyclerView = (RecyclerView) findViewById(R.id.listViewtollhistoryActivity);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        post=new ArrayList<>();


        //  adapter = new MyAdapter(getActivity(),R.layout.single_item, post);

        adapter = new MyAdapter(getApplicationContext(), R.layout.single_item, post);
        //lv.setAdapter(adapter);
        mRecyclerView.setAdapter(adapter);

        oldposts=new ArrayList<>();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        getHistory();

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGate();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeGate();
            }
        });

    }

    private void openGate() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall=service.openGate(User);


        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    progressDialog.dismiss();

                  openGate();
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

    private void closeGate() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.string_title_upload_progressbar_));
        progressDialog.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);


        retrofit.Call<People> peopleCall=service.closeGate(User);


        peopleCall.enqueue(new retrofit.Callback<People>() {
            @Override
            public void onResponse(retrofit.Response<People> response, Retrofit retrofit) {

                People resp = response.body();

                progressDialog.dismiss();

                if (resp.getMessage().equals("success")) {

                    progressDialog.dismiss();

                    closeGate();
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



    @Override
    public void onBackPressed() {
        finish();
    }


    private void getHistory() {

        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);
            //   Toast.makeText(getActivity(),String.valueOf(listpostid), Toast.LENGTH_LONG).show();

            Call<List<People>> call = service.getHistory(User);

            call.enqueue(new Callback<List<People>>()
            {
                @Override
                public void onResponse(Response<List<People>> response, Retrofit retrofit) {
                    List<People> newpost = response.body();

                    post.addAll(newpost);
                    adapter.notifyDataSetChanged();
                    hidepDialog();
                }

                @Override
                public void onFailure(Throwable t) {

                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", e.toString());

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "server error!!!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getHistory();
                        }
                    });
            snackbar.setActionTextColor(Color.WHITE);
            View snackbarView = snackbar.getView();
            snackbarView.setBackgroundColor(Color.RED);
            TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
            e.printStackTrace();
            hidepDialog();
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MYVIEW> {

        private Context context;
        private People people;
        private List<People> pplList;
        int  AD_TYPE=1,CONTENT_TYPE=3;

        public MyAdapter(Context context, int single_item, List<People> post) {
            this.context = context;
            this.pplList = post;
        }
        @Override
        public int getItemViewType(int position)
        {
            if (position % 2 == 0)
                return AD_TYPE;
            return CONTENT_TYPE;
        }

        @Override
        public MYVIEW onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutView = null;

            if (viewType == AD_TYPE)
            {
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);

            }
            else
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);

            MYVIEW myview = new MYVIEW(layoutView);
            return myview;

        }
        @Override
        public void onBindViewHolder(final MYVIEW holder, final int position) {


            holder.Vechiletype.setText("Car No : "+pplList.get(position).getCar_no());
            holder.Vechile_number.setText("License No : "+pplList.get(position).getLicense_no());

            holder.Amount.setText("Amount : "+pplList.get(position).getAmount());
            holder.Date.setText("Booking Time : "+pplList.get(position).getBookingtime());



        }

        @Override
        public int getItemCount() {
            return pplList.size();
        }


        class MYVIEW extends RecyclerView.ViewHolder {

            TextView Vechiletype,Vechile_number,Amount,Date;


            public MYVIEW(View itemView) {
                super(itemView);

                Vechiletype = (TextView) itemView.findViewById(R.id.vechiletype);
                Vechile_number = (TextView) itemView.findViewById(R.id.vechile_number);
                Amount = (TextView) itemView.findViewById(R.id.amount);
                Date = (TextView) itemView.findViewById(R.id.date);

            }
        }

    }


    private  void OpenGate(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("Gate Opening!");

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


    private  void CloseGate(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("Gate Closing!");

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


}