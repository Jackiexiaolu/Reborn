package com.demo.reborn.holderinfodetail;

import android.graphics.Bitmap;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public interface HolderInfoDetailContract {

    interface View extends BaseView<Presenter> {
        void setCompanyType(String type);  //设置公司类型
        void setHolderName(String name);    //设置股东名字
        void setStockCode(ArrayList<String> codes);
        void setPieChartData(ArrayList<PieEntry> tempEntries);          //设置饼状图表格
    }

    interface Presenter extends BasePresenter {
        void loadInfo();

        void setBehavior(String startTime,String endTime);
    }

}
