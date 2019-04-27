package com.demo.reborn.companydetail;

import android.widget.ImageView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.Map;

public interface CompanyDetailContract {
    interface View extends BaseView<Presenter> {
        void setCompanyName(String name);
        void setCompanyImage();
        void setFavourite(String favourite);
        void setShareholder(String name);
        void setLegalPerson(String name);
        void errorShow();
        void loginFirst();
        void collectSuccess(ImageView imageView,Map<String,Object> map);
        void collectFailure(ImageView imageView,Map<String,Object> map);
        void cancelSuccess(ImageView imageView,Map<String,Object> map);
        void cancelFailure(ImageView imageView,Map<String,Object> map);
    }

    interface Presenter extends BasePresenter {
        void getInfo();
        String getCompanyId();
        void postAddCollect(String name, String record_form, ImageView imageView, Map<String,Object> map);
        void postCancelCollect(String name,String record_form,ImageView imageView,Map<String,Object> map);
        void setBehavior(String startTime,String endTime);
    }
}
