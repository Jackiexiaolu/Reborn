package com.demo.reborn.financialreport;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface FinancialReportContract {

    interface View extends BaseView<Presenter> {
        void setCompanyName(String company);            //设置公司名字
        void setLegalPerson(String name);           //设置法人名字
        void setShareholder(String company, String percent);            //设置第一大股东
        void setHistory(String paragraph);          //设置历史沿革
        void setManager(String names);          //设置高管信息
        void setBusinessInfo(String paragraph); //设置生产经营情况
        void setProductList(List<Map<String,Object>> productList);      //设置根据产品划分的表格
        void setLocationList(List<Map<String,Object>> locationList);        //设置根据地区划分的表格
        void setFinancingInfo(String paragraph);            //设置财务情况
        void setRaisingInfo(String paragraph);          //设置融资情况
        void clickJump(String textview);
        void setTags(List<String> list);
        void setGroupName(String name);
        void errorShow();//错误提示
        void errorShow(String e);
        boolean showProgressBar(int max);
        void setProgressBar(int progress);
        void hideProgressBar();
        void openPDF(String path);

    }

    interface Presenter extends BasePresenter {
        void doJump(String textview);
        void destroyEarth();
        void showPDF();
        void setClickPDF(String time);
        void setBehavior(String startTime,String endTime);
    }

}
