package com.demo.reborn.loginpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class LoginPageActivity extends AppCompatActivity {

    private LoginPageFragment mLoginPageFragment;
    private LoginPagePresenter mLoginPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login_page);

        mLoginPageFragment =
                (LoginPageFragment) getSupportFragmentManager().findFragmentById(R.id.loginPageFrame);

        if(mLoginPageFragment == null){
            mLoginPageFragment = LoginPageFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mLoginPageFragment,R.id.loginPageFrame);
        }

        mLoginPagePresenter = new LoginPagePresenter(mLoginPageFragment);

    }

}
