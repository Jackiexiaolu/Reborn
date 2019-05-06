package com.demo.reborn.historydetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.demo.reborn.R;
import com.demo.reborn.util.ActivityUtils;

public class HistoryDetailActivity extends AppCompatActivity {

    private HistoryDetailFragment mHistoryDetailFragment;
    private HistoryDetailPresenter mHistoryDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_history_detail);


        mHistoryDetailFragment =
                (HistoryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.historyDetailContentFrame);

        if (mHistoryDetailFragment == null) {
            mHistoryDetailFragment = HistoryDetailFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mHistoryDetailFragment, R.id.historyDetailContentFrame);
        }

        mHistoryDetailPresenter = new HistoryDetailPresenter(mHistoryDetailFragment);
    }
}
