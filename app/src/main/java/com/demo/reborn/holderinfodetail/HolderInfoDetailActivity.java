package com.demo.reborn.holderinfodetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;


public class HolderInfoDetailActivity extends AppCompatActivity {

    private HolderInfoDetailFragment mSharehodlerInfoDetailFragment;
    private HolderInfoDetailPresenter mShareholderInfoDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_hodler_info_detail);

        mSharehodlerInfoDetailFragment =
                (HolderInfoDetailFragment) getSupportFragmentManager().findFragmentById(R.id.shareHodlerInfoDetailContentFrame);

        if (mSharehodlerInfoDetailFragment == null) {
            mSharehodlerInfoDetailFragment = HolderInfoDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mSharehodlerInfoDetailFragment, R.id.shareHodlerInfoDetailContentFrame);
        }

        mShareholderInfoDetailPresenter = new HolderInfoDetailPresenter(mSharehodlerInfoDetailFragment);

    }
}
