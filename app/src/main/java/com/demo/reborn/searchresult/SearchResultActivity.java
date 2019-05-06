package com.demo.reborn.searchresult;

import android.content.Intent;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.demo.reborn.R;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.util.ActivityUtils;

public class SearchResultActivity extends AppCompatActivity {

    private SearchResultFragment mSearchResultFragment;
    private SearchResultPresenter mSearchResultPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_search_result);
        mSearchResultFragment =
                (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.searchResultContentFrame);

        if(mSearchResultFragment == null){
            mSearchResultFragment = SearchResultFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mSearchResultFragment, R.id.searchResultContentFrame);
        }

        mSearchResultPresenter = new SearchResultPresenter(mSearchResultFragment);

    }
    /*
 public void setToolBar()
 {
    toolbar.inflateMenu(R.menu.menu_financialhomepage);
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.toolbar_item_one:
                    Toast.makeText(SearchResultActivity.this,
                            "click!", Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.toolbar_item_two:
                        Toast.makeText(SearchResultActivity.this,
                                "click!", Toast.LENGTH_SHORT).show();
                        break;

                        }
                        return true;
            }
            });
        //设置左侧NavigationIcon点击事件
         toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
             @Override public void onClick(View v) {
                     Intent intent = new Intent(SearchResultActivity.this, HomePageActivity.class);
                     startActivity(intent);
                 }

         });
 }
 */

        }
         

