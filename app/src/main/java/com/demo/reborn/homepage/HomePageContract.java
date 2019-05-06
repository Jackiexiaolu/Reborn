package com.demo.reborn.homepage;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;
import com.demo.reborn.R;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public interface HomePageContract {

    interface View extends BaseView<HomePageContract.Presenter> {

        void setClick();//设置跳转事件

        void clickJump(String textview);//完成跳转

        //void drawLine(String textviwew);//划线

        void initList();//初始化listview

        void setNews(String string);//设置新闻部分


    }

    interface Presenter extends BasePresenter {

        void doJump(String textview);

        //void setLine(String textview);// set the red line where to show

        void LoadNews();// show the content of news

        List<Map<String, Object>> getData();

        void setFromPage(String from);

    }
}
