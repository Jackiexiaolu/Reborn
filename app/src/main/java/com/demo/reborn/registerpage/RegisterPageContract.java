package com.demo.reborn.registerpage;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface RegisterPageContract {
    interface View extends BaseView<Presenter>{
        void clickJump();
        void clearListView();
        void setListTip(List<Map<String, Object>> list);
        String getPhoneNumber();
        String getEmail();
        String getRealName();
        String getFinancialFacility();
        String getDuty();
        String getDepartment();
        String getPosition();
        void setPhoneNumber(String phoneNumber);
        void setEmail(String email);
        void setRealName(String realName);
        void setFinancialFacility(String financialFacility);
        void setDuty(String duty);
    }

    interface Presenter extends BasePresenter{
        void submitInformation();
        void getCompanySearchTip(String inputString);
        void setSavedInfo();
    }
}
