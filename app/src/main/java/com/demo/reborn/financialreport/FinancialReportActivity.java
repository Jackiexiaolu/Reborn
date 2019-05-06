package com.demo.reborn.financialreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class FinancialReportActivity extends AppCompatActivity {

    private FinancialReportFragment mFinancialReportFragment;
    private FinancialReportPresenter mFinancialReportPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_financial_report);

        mFinancialReportFragment =
                (FinancialReportFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_financialReport);

        if (mFinancialReportFragment == null) {
            mFinancialReportFragment = FinancialReportFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFinancialReportFragment, R.id.fragment_financialReport);
        }

        mFinancialReportPresenter = new FinancialReportPresenter(mFinancialReportFragment);

    }
}
