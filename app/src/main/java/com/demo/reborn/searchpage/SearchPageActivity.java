package com.demo.reborn.searchpage;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.demo.reborn.R;
import com.demo.reborn.data.remote.FinancialDataRemote;

import com.demo.reborn.util.ActivityUtils;

public class SearchPageActivity extends AppCompatActivity {
    private SearchPageFragment mSearchPageFragment;
    private SearchPagePresenter mSearchPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new FinancialDataRemote();
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_search_page);

        mSearchPageFragment=
                (SearchPageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_searchpage);

        if(mSearchPageFragment == null){
            mSearchPageFragment = SearchPageFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mSearchPageFragment, R.id.fragment_searchpage);
        }

        mSearchPagePresenter = new SearchPagePresenter(mSearchPageFragment);

    }
}
