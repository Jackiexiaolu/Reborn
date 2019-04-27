package com.demo.reborn.historydetail;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface HistoryDetailContract {

    interface View extends BaseView<Presenter> {

        void initList(List<Map<String,Object>> list);//填充listview
    }

    interface  Presenter extends BasePresenter{

        void loadInfo();//加载数据
        void setBehavior(String startTime,String endTime);
    }
}
