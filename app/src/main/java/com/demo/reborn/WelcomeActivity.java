package com.demo.reborn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.demo.reborn.personalcenter.ui.ui.activity.IMActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               //startActivity(new Intent(WelcomeActivity.this, HomePageActivity.class));
                startActivity(new Intent(WelcomeActivity.this,IMActivity.class));
                WelcomeActivity.this.finish();
            }
        }, 3000);   //持续时间为3秒
    }



}
