package com.demo.reborn.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.GlideImageLoader;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialintelligence.FinancialIntelligenceActivity;
import com.demo.reborn.loginpage.LoginPageActivity;
import com.demo.reborn.searchpage.SearchPageActivity;
import com.demo.reborn.searchresult.SearchResultActivity;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageContract.View {

    private HomePageContract.Presenter mPresenter;
    private Banner banner;
    List<Integer> images=new ArrayList<>();
    private static NavigationBar navigationBar;



    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        navigationBar.setStatus(1);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    private EditText et_searchPut;
    private ImageView iv_information;
    private RadioButton tv_headline;
    private RadioButton tv_review;
    private RadioButton tv_bulletin;
    private ImageView iv_newsPic;
    private TextView tv_newTitle;
    private TextView tv_newsContent;
    private RadioButton tv_news;
    private ListView lv_newsShow;
    private RadioGroup rg_homepageBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_page, container, false);
        navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        //控件的绑定
        et_searchPut = root.findViewById(R.id.et_searchPut);
        iv_information= root.findViewById(R.id.iv_information);
        iv_newsPic = root.findViewById(R.id.iv_newsPic);
        tv_newTitle = root.findViewById(R.id.tv_newsTitle);
        tv_newsContent = root.findViewById(R.id.tv_newsContent);
        tv_headline = root.findViewById(R.id.tv_headline);
        tv_news = root.findViewById(R.id.tv_news);
        tv_review = root.findViewById(R.id.tv_review);
        tv_bulletin = root.findViewById(R.id.tv_bulletin);
        lv_newsShow = root.findViewById(R.id.lv_newsShow);
        tv_headline.setChecked(true);
        //轮播图
        //context=this.getContext();
        banner = root.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        images.add(R.drawable.img_homepage1);
        images.add(R.drawable.toutiao1);
        images.add(R.drawable.toutiao2);
        banner.setImages(images);
        banner.start();

        //跳转
        setClick();
        rg_homepageBar = root.findViewById(R.id.rg_homepageBar);
        rg_homepageBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.tv_headline:
                        setNews("headline");
                        Toast toast1 = Toast.makeText(getContext(),"点击了头条",Toast.LENGTH_SHORT);
                        toast1.show();
                        break;
                    case R.id.tv_bulletin:
                        setNews("bulletin");
                        Toast toast2 = Toast.makeText(getContext(),"点击了公告",Toast.LENGTH_SHORT);
                        toast2.show();
                        break;
                    case R.id.tv_news:
                        setNews("news");
                        Toast toast3 = Toast.makeText(getContext(),"点击了新闻",Toast.LENGTH_SHORT);
                        toast3.show();
                        break;
                    case R.id.tv_review:
                        setNews("review");
                        Toast toast4 = Toast.makeText(getContext(),"点击了评论",Toast.LENGTH_SHORT);
                        toast4.show();
                        break;

                }

            }
        });
    return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }


    @Override
    public void setClick() {
        et_searchPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.doJump("search");
            }
        });

        iv_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doJump("user");
            }
        });

    }

    @Override
    public void initList() {
        String[] from = {"picture", "title", "content"};
        int[] to = {R.id.iv_newsPic, R.id.tv_newsTitle, R.id.tv_newsContent};
        final SimpleAdapter adapter = new SimpleAdapter(getContext(), mPresenter.getData(), R.layout.homepage_listview_item, from, to);
        lv_newsShow.setAdapter(adapter);

    }


    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void clickJump(String textview) {
        switch (textview) {
            case "user":
                startActivity(new Intent(getContext(),LoginPageActivity.class));
                break;

            case "financial":
                Intent intentFinancial = new Intent(getContext(), SearchResultActivity.class);
                mPresenter.setFromPage("FinancialReport");
                startActivity(intentFinancial);
                break;

            case "business":
                Intent intentBusiness = new Intent(getContext(),SearchResultActivity.class);
                mPresenter.setFromPage("Business");
                startActivity(intentBusiness);
                break;

            case "risk":
                startActivity(new Intent(getContext(), FinancialIntelligenceActivity.class));
                break;

            case "search":
                startActivity(new Intent(getContext(), SearchPageActivity.class));
                break;

        }
    }

//    //在某个位置划线
//    @Override
//    public void drawLine(String textview) {
//        final Drawable drawable = getResources().getDrawable(R.drawable.underline);
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        final Drawable drawable_un = getResources().getDrawable(R.drawable.underline_unselected);
//        drawable_un.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//
//        switch (textview) {
//            case "headline":
////                tv_headline.setCompoundDrawables(null, null, null, drawable);
////                tv_news.setCompoundDrawables(null, null, null, drawable_un);
////                tv_review.setCompoundDrawables(null, null, null, drawable_un);
////                tv_bulletin.setCompoundDrawables(null, null, null, drawable_un);
//                break;
//            case "news":
////                tv_headline.setCompoundDrawables(null, null, null, drawable_un);
////                tv_news.setCompoundDrawables(null, null, null, drawable);
////                tv_review.setCompoundDrawables(null, null, null, drawable_un);
////                tv_bulletin.setCompoundDrawables(null, null, null, drawable_un);
//                break;
//            case "review":
////                tv_headline.setCompoundDrawables(null, null, null, drawable_un);
////                tv_news.setCompoundDrawables(null, null, null, drawable_un);
////                tv_review.setCompoundDrawables(null, null, null, drawable);
////                tv_bulletin.setCompoundDrawables(null, null, null, drawable_un);
//                break;
//            case "bulletin":
////                tv_headline.setCompoundDrawables(null, null, null, drawable_un);
////                tv_news.setCompoundDrawables(null, null, null, drawable_un);
////                tv_review.setCompoundDrawables(null, null, null, drawable_un);
////                tv_bulletin.setCompoundDrawables(null, null, null, drawable);
//                break;
//        }
//    }


    @Override
    public void setNews(String string) {

        String[] from = {"picture", "title", "content"};
        int[] to = {R.id.iv_newsPic, R.id.tv_newsTitle, R.id.tv_newsContent};

        switch (string) {
            case "headline":
                final SimpleAdapter adapter1 = new SimpleAdapter(getContext(), mPresenter.getData(), R.layout.homepage_listview_item, from, to);
                lv_newsShow.setAdapter(adapter1);
                break;
            case "news":
                final SimpleAdapter adapter2 = new SimpleAdapter(getContext(), mPresenter.getData(), R.layout.homepage_listview_item, from, to);
                lv_newsShow.setAdapter(adapter2);
                break;
            case "review":
                final SimpleAdapter adapter3 = new SimpleAdapter(getContext(), mPresenter.getData(), R.layout.homepage_listview_item, from, to);
                lv_newsShow.setAdapter(adapter3);
                break;
            case "bulletin":
                final SimpleAdapter adapter4 = new SimpleAdapter(getContext(), mPresenter.getData(), R.layout.homepage_listview_item, from, to);
                lv_newsShow.setAdapter(adapter4);
                break;
        }


    }


}

