package com.example.scribesupport.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scribesupport.MainActivity;
import com.example.scribesupport.R;
import com.example.scribesupport.splashscreen.FrontPageActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView imageView=findViewById(R.id.Logo);
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomanim);
//        imageView.startAnimation(animation);
        // on below line we are configuring our window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = new Intent(SplashScreenActivity.this, FrontPageActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 startActivity(i);
                 finish();
            }
        }, 2000);

    }
}
