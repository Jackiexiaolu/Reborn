package com.demo.reborn.baseinfodetail;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface BaseInfoDetailContract {
    interface View extends BaseView<Presenter> {
        void setBaseInfoDetailCompanyName(String companyName);//设置公司名称，由p层函数调用

        void setEstablishDate(String date);//设置成立日期

        void setListedInfo(List<Map<String,Object>> shareList);//设置上市信息

        void setRegisteredCapital(String capital);//设置注册资本

        void setLegalRepresentative(String person);//设置法定代表

        void setRegisteredAddress(String address);//设置注册地址

        void setMainBusiness(String paragraph);//设置主营业务

        void setBaseInfoDetailGroupName(String groupName);//设置所属集团名称



    }

    interface Presenter extends BasePresenter {

        void loadInfo();//由m层加载数据

        void setBehavior(String startTime,String endTime);
    }
}
