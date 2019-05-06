package com.demo.reborn.productionmanagementdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class ProductionManagementDetailActivity extends AppCompatActivity {


    private ProductionManagementDetailFragment mProductionManagementDetailFragment;
    private ProductionManagementDetailPresenter mProductionManagementDetailPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_production_management_detail);


         mProductionManagementDetailFragment =
                (ProductionManagementDetailFragment) getSupportFragmentManager().findFragmentById(R.id.productionManagementDetailContentFrame);

        if (mProductionManagementDetailFragment == null) {
            mProductionManagementDetailFragment = ProductionManagementDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mProductionManagementDetailFragment, R.id.productionManagementDetailContentFrame);
        }

        mProductionManagementDetailPresenter = new ProductionManagementDetailPresenter(mProductionManagementDetailFragment);
    }
}
