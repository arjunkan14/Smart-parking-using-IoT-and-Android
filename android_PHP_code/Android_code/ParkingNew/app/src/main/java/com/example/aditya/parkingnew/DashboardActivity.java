package com.example.aditya.parkingnew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.id.toggle;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    android.support.v7.widget.Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Button BookSlot,Histroy,Wallet,Qr,profile;
    TextView userName;

    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        User = getIntent().getExtras().getString("user");

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbargk);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Dashboard");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);

        userName = (TextView) hView.findViewById(R.id.usernmae);
        userName.setText("Smart Parking");

        BookSlot=(Button) findViewById(R.id.book_slot);
        Histroy=(Button) findViewById(R.id.history);
        Wallet=(Button) findViewById(R.id.wallet);

        Qr=(Button) findViewById(R.id.qr);
        profile=(Button) findViewById(R.id.profile);

        BookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplication(),ParkingLot.class);
                i.putExtra("User",User);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        Histroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplication(),Histroyy.class);
                i.putExtra("User",User);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        Wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplication(),Wallet.class);
                i.putExtra("User",User);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                finish();
                startActivity(i);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });


        Qr.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      Intent i =new Intent(getApplication(),QrCode.class);
                                      i.putExtra("User",User);
                                      startActivity(i);
                                      overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                  }
                              }
        );

        profile.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           Intent i =new Intent(getApplication(),Profile.class);
                                           i.putExtra("User",User);
                                           startActivity(i);
                                           overridePendingTransition(R.anim.right_in, R.anim.left_out);

                                       }
                                   }
        );
    }



    @Override
    public void onBackPressed() {
        finish();

        //  overridePendingTransition(R.anim.exit_anim, R.anim.enter_anim);//  R.animator.enter_anim, R.animator.exit_anim
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {

        } else if (id == R.id.Invite) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            //  shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, download this app!" +"http://play.google.com/store/apps/details?id=");
            startActivity(shareIntent);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }else if (id == R.id.Pending) {

            Intent i=new Intent(DashboardActivity.this, payPending.class);
            i.putExtra("User", User);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }else if (id == R.id.RateUs) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id="));
            startActivity(i);

        }else if (id == R.id.about) {
            Intent i=new Intent(DashboardActivity.this, AboutUs.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (id == R.id.contactus) {
            Intent i=new Intent(DashboardActivity.this,ContactsUS.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }
        else if (id == R.id.logout) {
            Intent i=new Intent(DashboardActivity.this,MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
