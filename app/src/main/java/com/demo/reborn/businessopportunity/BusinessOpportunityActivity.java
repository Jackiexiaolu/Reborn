package com.demo.reborn.businessopportunity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class BusinessOpportunityActivity extends AppCompatActivity {

    private BusinessOpportunityFragment mBusinessOpportunityFragment;
    private BusinessOpportunityPresenter mBusinessOpportunityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_business_opportunity);

        mBusinessOpportunityFragment =
                (BusinessOpportunityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_stockFinancingDetail);

        if (mBusinessOpportunityFragment == null) {
            mBusinessOpportunityFragment = BusinessOpportunityFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mBusinessOpportunityFragment, R.id.fragment_stockFinancingDetail);
        }

        mBusinessOpportunityPresenter = new BusinessOpportunityPresenter(mBusinessOpportunityFragment);

    }
}
