package com.demo.reborn.preregisterpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class PreRegisterPageActivity extends AppCompatActivity {

    private PreRegisterPageFragment mPreRegisterPageFragment;
    private PreRegisterPagePresenter mPreRegisterPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_pre_register_page);

        mPreRegisterPageFragment =
                (PreRegisterPageFragment)getSupportFragmentManager().findFragmentById(R.id.PreRegisterPageFrame);

        if(mPreRegisterPageFragment == null){
            mPreRegisterPageFragment = PreRegisterPageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),mPreRegisterPageFragment,R.id.PreRegisterPageFrame);
        }

        mPreRegisterPagePresenter = new PreRegisterPagePresenter(mPreRegisterPageFragment);
    }
}
