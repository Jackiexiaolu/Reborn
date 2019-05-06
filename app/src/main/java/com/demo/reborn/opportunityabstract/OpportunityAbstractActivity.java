package com.demo.reborn.opportunityabstract;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class OpportunityAbstractActivity extends AppCompatActivity {

    private OpportunityAbstractFragment mBusinessFragment;
    private OpportunityAbstractPresenter mBusinessPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_opportunity_abstract);

        mBusinessFragment =
                (OpportunityAbstractFragment) getSupportFragmentManager().findFragmentById(R.id.businessContentFrame);

        if(mBusinessFragment == null) {
            mBusinessFragment = mBusinessFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mBusinessFragment, R.id.businessContentFrame);
        }

        mBusinessPresenter = new OpportunityAbstractPresenter(mBusinessFragment);
    }
}
