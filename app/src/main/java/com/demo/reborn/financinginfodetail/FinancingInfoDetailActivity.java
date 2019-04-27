package com.demo.reborn.financinginfodetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportFragment;
import com.demo.reborn.financialreport.FinancialReportPresenter;
import com.demo.reborn.util.ActivityUtils;

public class FinancingInfoDetailActivity extends AppCompatActivity {

    private FinancingInfoDetailFragment mFinancingInfoDetailFragment;
    private FinancingInfoDetailPresenter mFinancingInfoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_financing_info_detail);

        mFinancingInfoDetailFragment =
                (FinancingInfoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.financingInfoDetailContentFrame);

        if (mFinancingInfoDetailFragment == null) {
            mFinancingInfoDetailFragment = FinancingInfoDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFinancingInfoDetailFragment, R.id.financingInfoDetailContentFrame);
        }

        mFinancingInfoDetailPresenter = new FinancingInfoDetailPresenter(mFinancingInfoDetailFragment);


    }
}
