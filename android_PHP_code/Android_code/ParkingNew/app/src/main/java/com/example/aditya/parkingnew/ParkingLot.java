package com.example.aditya.parkingnew;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aditya.parkingnew.model.People;
import com.example.aditya.parkingnew.service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ParkingLot extends AppCompatActivity {

    Button One,Two,Three,Four,Five,Six;
    private ProgressDialog pDialog;
    SharedPreferences pref;
    List<People> post;
    MyAdapter adapter;
    RecyclerView mRecyclerView;

    int numberOfColumns=2;

    String User;

    private Spinner spinners;


    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);

        User = getIntent().getExtras().getString("User");

        spinners =(Spinner)findViewById(R.id.spinnerarea);

        spinners.getBackground().setColorFilter(Color.parseColor("#10BDB4"), PorterDuff.Mode.SRC_ATOP);



        pDialog = new ProgressDialog(ParkingLot.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

//        One=(Button)findViewById(R.id.one);
//        Two=(Button)findViewById(R.id.two);
//        Three=(Button)findViewById(R.id.three);
//        Four=(Button)findViewById(R.id.four);
//        Five=(Button)findViewById(R.id.five);
//        Six=(Button)findViewById(R.id.six);

        mRecyclerView = (RecyclerView)findViewById(R.id.numbers);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplication());
        //mRecyclerView.setLayoutManager(mLayoutManager);


        post = new ArrayList<>();

        adapter = new MyAdapter(ParkingLot.this, R.layout.parkingslots_item, post);

        mRecyclerView.setAdapter(adapter);

        CheckParkingSlot();


        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#10BDB4"));
                //  Toast.makeText(getApplicationContext(), Branch.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

               // Vechile_type.setText(spinners.getSelectedItem().toString());

                // Toast.makeText(SignUp.this,, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }




    private void CheckParkingSlot(){

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_id = pref.getString(Constants.EMAIL, "");

           // showpDialog();
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                APIService service = retrofit.create(APIService.class);
                Call<List<People>> call = service.CheckSlots(user_id);

                call.enqueue(new Callback<List<People>>() {
                    @Override
                    public void onResponse(Response<List<People>> response, Retrofit retrofit) {
                        List<People> newpost = response.body();


                        post.addAll(newpost);
                        adapter.notifyItemInserted(post.size());

                       // hidepDialog();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Bad Network Connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CheckParkingSlot();
                                }
                            });
                    snackbar.setActionTextColor(Color.WHITE);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.RED);
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.WHITE);
                    snackbar.show();
                        Log.d("onFailure", t.toString());
                     //   hidepDialog();
                    }
                });
            } catch (Exception e) {
                Log.d("onResponse", "There is an error");

                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "server error!!!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                CheckParkingSlot();
                            }
                        });
                snackbar.setActionTextColor(Color.WHITE);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(Color.RED);
                TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);
                snackbar.show();
                e.printStackTrace();
              //  hidepDialog();
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


        public MyAdapter(Context context, int single_item, List<People> post) {
            this.context = context;
            this.pplList = post;
        }

        @Override
        public MYVIEW onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parkingslots_item, parent, false);

            MYVIEW myview = new MYVIEW(layoutView);
            return myview;
        }



        @Override
        public void onBindViewHolder(final MYVIEW holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(pplList.get(position).getStatus().equals("available")) {
                        Intent i = new Intent(ParkingLot.this, BookSlot.class);

                        i.putExtra("SlotNo", pplList.get(position).getSlot_no());
                        startActivity(i);
                        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
                    }else{

                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Sorry Already Booked!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        CheckParkingSlot();
                                    }
                                });
                        snackbar.setActionTextColor(Color.WHITE);
                        View snackbarView = snackbar.getView();
                        snackbarView.setBackgroundColor(Color.RED);
                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();

                    }
                }
            });

            holder.ColourButtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pplList.get(position).getStatus().equals("available")) {
                        Intent i = new Intent(ParkingLot.this, BookSlot.class);
                        i.putExtra("User",User);
                        i.putExtra("SlotNo", pplList.get(position).getSlot_no());
                        startActivity(i);
                        overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
                    }else{

                        alertBooked();


//                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Sorry Already Booked!", Snackbar.LENGTH_LONG)
//                                .setAction("RETRY", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        CheckParkingSlot();
//                                    }
//                                });
//                        snackbar.setActionTextColor(Color.WHITE);
//                        View snackbarView = snackbar.getView();
//                        snackbarView.setBackgroundColor(Color.RED);
//                        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
//                        textView.setTextColor(Color.WHITE);
//                        snackbar.show();

                    }

                }
            });

           // holder.SlotNo.setText(pplList.get(position).getSlot_no());

            String Status =pplList.get(position).getStatus();
            holder.SlotNo.setText(pplList.get(position).getSlot_no());

            if(Status.equals("available"))
            {

            }else{

                holder.ColourButtton.setBackgroundResource(R.drawable.red_car);

            }

        }


        @Override
        public int getItemCount() {
            return pplList.size();
        }

        class MYVIEW extends RecyclerView.ViewHolder {

            TextView SlotNo;
            Button ColourButtton;

            public MYVIEW(View itemView) {
                super(itemView);
                SlotNo = (TextView) itemView.findViewById(R.id.number);
                ColourButtton =(Button)itemView.findViewById(R.id.item);
            }
        }





    }

    private  void alertBooked(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle("Response");


        builder.setMessage("Sorry Already Booked!");

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
