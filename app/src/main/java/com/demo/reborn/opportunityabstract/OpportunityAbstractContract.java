package com.demo.reborn.opportunityabstract;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.ArrayList;

public interface OpportunityAbstractContract {

    interface View extends BaseView<Presenter> {
        void setCompanyName(String companyName);
        void setTitle(String title);
        void setParagraph(String paragraph);
        void setChart(ArrayList<String> strings);
        void accessDenied();
    }

    interface Presenter extends BasePresenter {
        void loadInfo();//由m层加载数据
        void setBusinessOpportunityTag(String tag);
        void setBehavior(String startTime,String endTime);
    }
}
