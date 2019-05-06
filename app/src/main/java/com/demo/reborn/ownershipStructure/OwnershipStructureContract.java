package com.demo.reborn.ownershipstructure;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface OwnershipStructureContract {

    interface View extends BaseView<Presenter> {
        void setCurrentCompany(String companyName);
        void refreshList(String company_id);
        void setFatherList(List<Map<String, Object>> list);
        void setChildList(List<Map<String, Object>> list);
        void setGroupCompany(String name);
        void setSecondHolder(String name, String r);
        void removeSecondHolder();
        void removeGroup();
    }

    interface Presenter extends BasePresenter {
        String getCompanyId();
        void refreshCompany(String company_id);
        void refreshFatherList(String company_id);
        void refreshChildList(String company_id);
        void setBehavior(String startTime,String endTime);
        void setCompanyId(String id);
    }
}
