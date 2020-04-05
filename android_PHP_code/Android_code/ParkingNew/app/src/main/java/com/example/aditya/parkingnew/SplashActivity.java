package com.example.aditya.parkingnew;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView ivT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ivT = (ImageView) findViewById(R.id.iv_splash_top);

//        Animation shake = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.shake);
//        ivT.startAnimation(shake);

        ObjectAnimator animationfab = ObjectAnimator.ofFloat(ivT,"translationY", -400,50,0);
        animationfab.setDuration(1000);
        animationfab.setInterpolator(new AccelerateDecelerateInterpolator());
        animationfab.start();
        Thread t=new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
        t.start();
    }
}
