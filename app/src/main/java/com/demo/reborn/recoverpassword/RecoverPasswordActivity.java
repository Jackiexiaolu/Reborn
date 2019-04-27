package com.demo.reborn.recoverpassword;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class RecoverPasswordActivity extends AppCompatActivity {

    private RecoverPasswordPresenter mRecoverPasswordPresenter;
    private RecoverPasswordFragment mRecoverPasswordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_recover_password);

        mRecoverPasswordFragment =
                (RecoverPasswordFragment)getSupportFragmentManager().findFragmentById(R.id.recoverPasswordFrame);

        if(mRecoverPasswordFragment == null){
            mRecoverPasswordFragment = RecoverPasswordFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mRecoverPasswordFragment,R.id.recoverPasswordFrame);
        }

        mRecoverPasswordPresenter = new RecoverPasswordPresenter(mRecoverPasswordFragment);
    }

}
