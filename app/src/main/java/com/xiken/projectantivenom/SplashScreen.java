package com.xiken.projectantivenom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class SplashScreen extends TutorialActivity {
    Handler handler;

    public static final String IS_FIRST_TIME_INSTALL = "com.xiken.projectantivenom";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash_screen);
//        getSupportActionBar().setTitle("ProjectAntiVenom");
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent intent = new Intent(SplashScreen.this,nav_activity.class);
//                startActivity(intent);
//                finish();
//            }
//        },1500);
        sharedPreferences = getSharedPreferences(IS_FIRST_TIME_INSTALL, MODE_PRIVATE);
        if (!sharedPreferences.getBoolean("isFirstTime", true)){
            Intent intent = new Intent(SplashScreen.this,nav_activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

            addFragment(new Step.Builder().setTitle("First fragment")
            .setDrawable(R.raw.camera_logo)
                    .setBackgroundColor(Color.DKGRAY)
            .setTitle("Camera")
                    .setContent("Gives you an option to scan an image of the snake upon clicking it")
                    .setSummary("Continue and learn to how to do ")
                    .build());
        addFragment(new Step.Builder().setTitle("Call Ambulance")
                .setDrawable(R.raw.ambulance)
                .setBackgroundColor(Color.parseColor("#8e44ad"))
                .setTitle("Call Ambulance")
                .setContent("If Snake has bitten you then call the ambulance")

                .build());
        addFragment(new Step.Builder().setTitle("First Aid Tips")
                .setDrawable(R.raw.firstaid)
                .setBackgroundColor(Color.RED)
                .setSummary("Continue and learn how to do it")
                .build());


    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void finishTutorial() {
        sharedPreferences.edit().putBoolean("isFirstTime", false).apply();
        Intent intent = new Intent(SplashScreen.this,nav_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
