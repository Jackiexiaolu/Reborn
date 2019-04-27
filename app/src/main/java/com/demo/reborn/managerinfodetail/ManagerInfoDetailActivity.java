package com.demo.reborn.managerinfodetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class ManagerInfoDetailActivity extends AppCompatActivity {

    private ManagerInfoDetailFragment mManagerInfoDetailFragment;
    private ManagerInfoDetailPresenter mManagerInfoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_manager_info_detail);

        mManagerInfoDetailFragment =
                (ManagerInfoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.managerInfoDetailContentFrame);

        if (mManagerInfoDetailFragment == null) {
            mManagerInfoDetailFragment = ManagerInfoDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mManagerInfoDetailFragment, R.id.managerInfoDetailContentFrame);
        }

        mManagerInfoDetailPresenter = new ManagerInfoDetailPresenter(mManagerInfoDetailFragment);

    }

}
