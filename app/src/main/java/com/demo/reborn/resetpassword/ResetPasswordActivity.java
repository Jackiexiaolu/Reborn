package com.demo.reborn.resetpassword;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class ResetPasswordActivity extends AppCompatActivity {

    private ResetPasswordFragment mResetPasswordFragment;
    private ResetPasswordPresenter mResetPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_reset_password);

        mResetPasswordFragment =
                (ResetPasswordFragment) getSupportFragmentManager().findFragmentById(R.id.PersonalCenterFrame);

        if(mResetPasswordFragment == null){
            mResetPasswordFragment = ResetPasswordFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mResetPasswordFragment,R.id.ResetPasswordFrame);
        }

        mResetPasswordPresenter = new ResetPasswordPresenter(mResetPasswordFragment);
    }
}
