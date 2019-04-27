package com.demo.reborn.productionmanagementdetail;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;

public interface ProductionManagementDetailContract {

    interface View extends BaseView<Presenter> {
        void setBusinessInfo(String businessInfo);//设置生产经营详情

        //void setEachBusinessInfo(String businessInfo);//设置各板块内容

        void setProductTable(List<String> list);//主营业务表(产品)

        void setAreaTable(List<String> list);//主营业务表(地区)
    }

    interface Presenter extends BasePresenter {

        void getInfo();//从m层获取数据

        void setBehavior(String startTime,String endTime);
    }
}
