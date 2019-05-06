package com.demo.reborn.personalcenter;

import android.widget.ImageView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;
import com.demo.reborn.data.json.Api1_ShowUserInfo;

import java.util.List;
import java.util.Map;

public interface PersonalCenterContract {
    interface View extends BaseView<Presenter>{
        void setBottomBorder(int flag);
        void initList(List<Map<String, Object>> list);
        void setListener();
        void onLoadCompanyInfo();
        void jumpTo();
        void collectSuccess(ImageView imageView, Map<String,Object> map);
        void collectFailure(ImageView imageView,Map<String,Object> map);
        void cancelSuccess(ImageView imageView,Map<String,Object> map);
        void cancelFailure(ImageView imageView,Map<String,Object> map);
        void logoutJump();
        void setRealName(String realName);
        void setGender(String gender);
        void setOccupation(String occupation);
        void setCompany(String company);
        void setPosition(String position);

        void initListFriends(List<Map<String, Object>> list);
        void initListFriendsMessage(List<Map<String, Object>> list);
        void initListFriendsRequest(List<Map<String,Object>> list);
    }

    interface Presenter extends BasePresenter{
        void displayFav();
        void displayFriends();
        void displayMessage();
        void displayIntelligence();
        void getFavouritePageInfo(int page);
        void getFriendsListPageInfo(int page);
        void selectFriends(String department);
        boolean agreeFriends(String rec_id);
        void addFriends(String id);
        void getFriendsListMessage(List<List<String>> mList);
        void getResquestFriendsListPageInfo(int page);
        int hasMoreInfo(int page);
        void setCompanyId(String str);
        String getFromPage();
        void logout();
        void refreshList();
        void postAddCollect(String c_id, String name, String record_name, ImageView imageView, Map<String,Object> map);
        void postCancelCollect(String c_id,String name,String record_name,ImageView imageView,Map<String,Object> map);
        void setPersonalInfo();
        void setBehavior(String startTime,String endTime);
        void setModifyPersonalInfo(String time);
    }
}
