package com.demo.reborn.capitalraisinginfodetail;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface CapitalRaisingInfoDetailContract {

    interface View extends BaseView<Presenter> {


        void setGroupCapitalRaisingOverviewInfo(String paragraph);//p层获取数据之后调用此函数填充集团融资情况概览部分内容

        void setGroupCreditInfo(String paragraph);//集团贷款银行的授信情况

        void setBondCapitalRaisingInfo(String paragraph);//集团债券融资情况

        void setDebtInfo(String paragraph);//集团有息负债情况

        void setEquityFinancing(String paragraph);//集团股权融资情况

        void setAssetManagementPlanInfo(String paragraph);//集团资产管理计划情况

        void setCorporateFinanceOverviewInfo(String paragraph);//企业融资概览

        void setCorporateCreditInfo(String paragraph);//企业授信用信

        void setCorporateBondInfo(String paragraph);//企业债券信息

        void setCorporateEquityFinancingInfo(String paragraph);//企业股权融资

        void setCorporateDebtInfo(String paragraph);//企业有息债券

        void setCorporateAssetManagementPlanInfo(String paragraph);//企业资产管理计划情况

        void setCreditTable(List<String> list);//集团主要合作情况表格

        void setBondTable(List<Map<String, String>> list1, List<Map<String, String>> list2, List<Map<String, String>> list3);//债券融资表格

        void setDebtTable(List<String> list,String latest);//有息债券表格

        void setCashFlowTable(List<String> list,String latest);//现金流表格

        void setFinancingTable(List<String> list);//股权融资表格

        void setCorporateCreditTable(List<String> list);//企业授信表格

        void setCorporateBondTable(List<Map<String, String>> list1, List<Map<String, String>> list2, List<Map<String, String>> list3);//企业债券融资表格

        void setCorporateDebtTable(List<String> list,String latest);//企业有息债券表格

        void setCorporateCashFlowTable(List<String> list,String latest);//企业现金流表格

        void setCorporateFinancingTable(List<String> list);//企业股权融资表格

        void initTotalPriceChart(int valueSize);

        void setLineChartTotalPriceData(ArrayList<Entry> yValues1, ArrayList<Entry> yValues2, ArrayList<Entry> yValues3, ArrayList<Entry> yValues4, ArrayList<String> xValues);

        void initPriceRateChart(int valueSize);

        void setLineChartPriceRateData(ArrayList<Entry> yValues1, ArrayList<Entry> yValues2, ArrayList<Entry> yValues3, ArrayList<Entry> yValues4, ArrayList<String> xValues);

        //void setProgressBar(boolean show);

        void accessDenied();//权限不足，toast并返回上一activity

        void removeView();//当接口返回错误时调用

        void setTrustTable(List<String> list);//集团信托资管计划表

        void setInsuranceTable(List<String> list);//集团保险资管计划表

        void setSecurityTable(List<String> list);//集团证券资管计划表

        void setCorporateTrustTable(List<String> list);//企业信托资管计划表

        void setCorporateInsuranceTable(List<String> list);//企业保险资管计划表

        void setCorporateSecurityTable(List<String> list);//企业证券资管计划表

        void setProgressBar(boolean show);

    }

    interface Presenter extends BasePresenter {
        void getInfo();//从m层获取数据

        void setBehavior(String startTime,String endTime);

    }

}
