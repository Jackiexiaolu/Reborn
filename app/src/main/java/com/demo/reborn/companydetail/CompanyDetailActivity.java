package com.demo.reborn.companydetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class CompanyDetailActivity extends AppCompatActivity {

    private CompanyDetailFragment mCompanyDetailFragment;
    private CompanyDetailPresenter mCompanyDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_company_detail);

        mCompanyDetailFragment =
                (CompanyDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_CompanyDetail);

        if (mCompanyDetailFragment == null) {
            mCompanyDetailFragment = CompanyDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mCompanyDetailFragment, R.id.fragment_CompanyDetail);
        }

        mCompanyDetailPresenter = new CompanyDetailPresenter(mCompanyDetailFragment);
    }
}
