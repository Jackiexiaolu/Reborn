package com.demo.reborn.passwordrecovery;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class PasswordRecoveryActivity extends AppCompatActivity {

    private PasswordRecoveryFragment mPasswordRecoveryFragment;
    private PasswordRecoveryPresenter mPasswordRecoveryPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_password_recovery);

        mPasswordRecoveryFragment =
                (PasswordRecoveryFragment) getSupportFragmentManager().findFragmentById(R.id.PasswordRecoveryFrame);
        if(mPasswordRecoveryFragment == null) {
            mPasswordRecoveryFragment = PasswordRecoveryFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mPasswordRecoveryFragment,R.id.PasswordRecoveryFrame);
        }

        mPasswordRecoveryPresenter = new PasswordRecoveryPresenter(mPasswordRecoveryFragment);
    }
}
