package com.demo.reborn.searchresult;

import android.provider.ContactsContract;
import android.widget.ImageView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import java.util.List;
import java.util.Map;

public interface SearchResultContract {
    interface View extends BaseView<Presenter> {
        void initList(List<Map<String, Object>> list);//填充listview

        void jumpTo();//跳转至搜索结果页面

        void collectSuccess(ImageView imageView,Map<String,Object> map);

        void collectFailure(ImageView imageView,Map<String,Object> map);

        void cancelSuccess(ImageView imageView,Map<String,Object> map);

        void cancelFailure(ImageView imageView,Map<String,Object> map);

        void loginFirst();
    }

    interface Presenter extends BasePresenter {

        void getPerPageInfo(final int page);//获得搜索结果

        void getFavouritePageInfo(int page);

        int hasMoreInfo(int page);//是否请求完成，请求完成返回0，未请求完成返回1

        void setCompanyId(String str);//获取每个item的公司id

        String getFromPage();//获取上一activity的intent

        void postAddCollect(String c_id, String name, String record_form, ImageView imageView,Map<String,Object> map);

        void postCancelCollect(String c_id,String name,String record_form,ImageView imageView,Map<String,Object> map);

        void setSearchClick(String time);


    }
}
