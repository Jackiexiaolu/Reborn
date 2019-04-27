package com.demo.reborn.searchpage;

import android.widget.ListView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface SearchPageContract {

    interface View extends BaseView<SearchPageContract.Presenter> {

        void setKeyEvent();//点击键盘搜索键进行搜索并且保存新记录

        void showRecord();//实时显示搜索记录

        void setListRecord();//设置搜索记录上的跳转监听

        void clearRecord();//清除搜索历史记录






    }

    interface Presenter extends BasePresenter {


        boolean hasData(String tempName);//查询的字段在数据库中，tempName为要查询的字段

        void queryData(String tempName, ListView listview);//查询数据

        void insertData(String tempName);//插入数据

        void deleteData();//清空数据库

        void renewData(String string);//更新搜索历史使得重复搜索的历史显示在最新一条

        void setSearchResult(String searchResult);//将搜索结果传递给model层的成员变量，以后用到搜索结果直接调用model层的成员变量

        void setFromPage(String from);

        void getInfo(String info,ListView listView);

        void setSearchRecord(String keyword,String time);

    }
}
