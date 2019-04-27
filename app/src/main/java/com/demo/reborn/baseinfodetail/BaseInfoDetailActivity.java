package com.demo.reborn.baseinfodetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class BaseInfoDetailActivity extends AppCompatActivity {

    private BaseInfoDetailFragment mBaseInfoDetailFragment;
    private BaseInfoDetailPresenter mBaseInfoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_base_info_detail);

        mBaseInfoDetailFragment =
                (BaseInfoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.baseInfoDetailContentFrame);

        if (mBaseInfoDetailFragment == null) {
            mBaseInfoDetailFragment = mBaseInfoDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mBaseInfoDetailFragment, R.id.baseInfoDetailContentFrame);
        }

        mBaseInfoDetailPresenter = new BaseInfoDetailPresenter(mBaseInfoDetailFragment);
    }
}
