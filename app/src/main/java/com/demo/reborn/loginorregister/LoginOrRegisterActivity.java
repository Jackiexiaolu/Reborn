package com.demo.reborn.loginorregister;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class LoginOrRegisterActivity extends AppCompatActivity {

    private LoginOrRegisterFragment mLoginOrRegisterFragment;
    private LoginOrRegisterPresenter mLoginOrRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login_or_register);

        mLoginOrRegisterFragment =
                (LoginOrRegisterFragment) getSupportFragmentManager().findFragmentById(R.id.LoginOrRegisterFrame);

        if(mLoginOrRegisterFragment == null){
            mLoginOrRegisterFragment = LoginOrRegisterFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mLoginOrRegisterFragment,R.id.LoginOrRegisterFrame);
        }

        mLoginOrRegisterPresenter = new LoginOrRegisterPresenter(mLoginOrRegisterFragment);
    }

}
