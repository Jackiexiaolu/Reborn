package com.demo.reborn.businessopportunity;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.ArrayList;

public interface BusinessOpportunityContract {
    interface View extends BaseView<Presenter> {
        void setTitle(String title);
        void setParagraph(String paragraph);
        void setChartColumn(int num);
        void setChart(ArrayList<String> strings);
        void setClickableChart(ArrayList<String> strings);
        void setPartEnd();
    }

    interface Presenter extends BasePresenter {
        void getInfo(String tag);
        String getBusinessOpportunityTag();
        void setBehavior(String startTime,String endTime);
        void setCompanyId(String id);
    }
}
