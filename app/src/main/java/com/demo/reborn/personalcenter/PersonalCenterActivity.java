package com.demo.reborn.personalcenter;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class PersonalCenterActivity extends AppCompatActivity {

    private PersonalCenterFragment mPersonalCenterFragment;
    private PersonalCenterPresenter mPersonalCenterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_personal_center);

        mPersonalCenterFragment =
                (PersonalCenterFragment) getSupportFragmentManager().findFragmentById(R.id.PersonalCenterFrame);

        if(mPersonalCenterFragment == null){
            mPersonalCenterFragment = PersonalCenterFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mPersonalCenterFragment,R.id.PersonalCenterFrame);
        }

        mPersonalCenterPresenter = new PersonalCenterPresenter(mPersonalCenterFragment);
    }
}
