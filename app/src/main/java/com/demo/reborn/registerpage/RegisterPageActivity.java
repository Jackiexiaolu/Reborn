package com.demo.reborn.registerpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class RegisterPageActivity extends AppCompatActivity {

    private RegisterPageFragment mRegisterPageFragment;
    private RegisterPagePresenter mRegisterPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register_page);

        mRegisterPageFragment =
                (RegisterPageFragment) getSupportFragmentManager().findFragmentById(R.id.registerPageFrame);//

        if(mRegisterPageFragment == null){
            mRegisterPageFragment = RegisterPageFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mRegisterPageFragment,R.id.registerPageFrame);
        }

        mRegisterPagePresenter = new RegisterPagePresenter(mRegisterPageFragment);
    }

}
