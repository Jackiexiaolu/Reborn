package com.demo.reborn.financinginfodetail;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;

public interface FinancingInfoDetailContract {

    interface View extends BaseView<Presenter> {
        //设置顶部情况总结内容
        void setTopSummary(String topSum);


        //设置合并报表介绍内容
        void setPreList(String pList);

        //设置表格下方提示内容
        void setAttention(String attenion);

//        //设置偿债能力分析详细内容
//        void setDebtPaymentAbility(String debtPayment);
//
//        //设置运营能力分析详细内容
//        void setOperationAbility(String opAbility);
//
//        //设置盈利能力分析详细内容
//        void setProfitInfo(String profit);
//
//        //设置发展能力分析详细内容
//        void setFutureDevelopment(String futureDev);
//
//        //设置现金流量分析详细内容
//        void setCashFlow(String CashFlow);
//
//        //设置结尾总结内容
//        void setFinalSummary(String finalSum);

        //设置合并报表
        //void setTable(List<String> list1, List<String> list2);

        void setChartColumn(int num);//获取表格列数

        void setNewTable(List<String> list);



    }

    interface Presenter extends BasePresenter {

        void getInfo();
        void setBehavior(String startTime,String endTime);

    }
}
