package com.demo.reborn.homepage;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class HomePageActivity extends AppCompatActivity {

    private HomePageFragment mHomePageFragment;
    private HomePagePresenter mHomePagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        mHomePageFragment =
                (HomePageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_homepage);

        if (mHomePageFragment == null) {
            mHomePageFragment = HomePageFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mHomePageFragment, R.id.fragment_homepage);
        }

        mHomePagePresenter = new HomePagePresenter(mHomePageFragment);
        //启动activity时隐藏键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }


}
