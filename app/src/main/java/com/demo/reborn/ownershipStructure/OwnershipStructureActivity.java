package com.demo.reborn.ownershipstructure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class OwnershipStructureActivity extends AppCompatActivity {


    private OwnershipStructureFragment mOwnershipStructureFragment;
    private OwnershipStructurePresenter mOwnershipStructurePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ownership_structure);

        mOwnershipStructureFragment =
                (OwnershipStructureFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_ownershipStructure);

        if (mOwnershipStructureFragment == null) {
            mOwnershipStructureFragment = OwnershipStructureFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mOwnershipStructureFragment, R.id.fragment_ownershipStructure);
        }

        mOwnershipStructurePresenter = new OwnershipStructurePresenter(mOwnershipStructureFragment);
    }
}
