package com.demo.reborn.capitalraisinginfodetail;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class CapitalRaisingInfoDetailActivity extends AppCompatActivity {

    private CapitalRaisingInfoDetailFragment mCapitalRaisingInfoDetailFragment;
    private CapitalRaisingInfoDetailPresenter mCapitalRaisingInfoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_capital_raising_info_detail);

        mCapitalRaisingInfoDetailFragment =
                (CapitalRaisingInfoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.capitalRaisingInfoDetailContentFrame);

        if (mCapitalRaisingInfoDetailFragment == null) {
            mCapitalRaisingInfoDetailFragment = mCapitalRaisingInfoDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mCapitalRaisingInfoDetailFragment, R.id.capitalRaisingInfoDetailContentFrame);
        }

        mCapitalRaisingInfoDetailPresenter = new CapitalRaisingInfoDetailPresenter(mCapitalRaisingInfoDetailFragment);
    }
}

