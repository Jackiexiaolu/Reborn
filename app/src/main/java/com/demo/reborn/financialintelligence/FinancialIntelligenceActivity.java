package com.demo.reborn.financialintelligence;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class FinancialIntelligenceActivity extends AppCompatActivity {

    private FinancialIntelligenceFragment mFinancialIntelligenceFragment;
    private FinancialIntelligencePresenter mFinancialIntelligencePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_financial_intelligence);

        mFinancialIntelligenceFragment =
                (FinancialIntelligenceFragment)getSupportFragmentManager().findFragmentById(R.id.financialIntelligenceFrame);

        if(mFinancialIntelligenceFragment == null){
            mFinancialIntelligenceFragment = FinancialIntelligenceFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFinancialIntelligenceFragment,R.id.financialIntelligenceFrame);
        }

        mFinancialIntelligencePresenter = new FinancialIntelligencePresenter(mFinancialIntelligenceFragment);
    }
}
