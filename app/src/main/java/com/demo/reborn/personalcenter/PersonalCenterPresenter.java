package com.demo.reborn.personalcenter;
import android.util.Log;
import android.widget.ImageView;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_CollectionList;
import com.demo.reborn.data.json.Api1_Search_Users;
import com.demo.reborn.data.json.Api1_ShowUserInfo;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import retrofit2.Response;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.isNullOrEmpty;
import static java.lang.Integer.valueOf;

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter {

    private final PersonalCenterContract.View mView;
    private final FinancialDataRepository mData;
    private List<Map<String, Object>> list = new ArrayList<>();
    protected int limit = 1;//一次获取十条数据
    protected int left = 0;
    protected int count = 0;//根据搜索结果，一次可下载的总信息条数
    protected boolean last = false;//是否是最后一页

    public PersonalCenterPresenter(PersonalCenterContract.View view){
        mView = view;
        mView.setPresenter(this);
        mData = FinancialDataRepository.getINSTANCE();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void setBehavior(String startTime,String endTime){
        mData.post_Browse_Recode("3","4","17","Personal Center",startTime,endTime).
                subscribe(new Observer<Response<Api1_post_common>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }
                        //Log.e("info_个人中心浏览时长",api1_post_common.body().info);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void setModifyPersonalInfo(String time){
        mData.post_Browse_Recode("5","4","18","Personal Center Modify Info",time,"").
                subscribe(new Observer<Response<Api1_post_common>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }
                        //Log.e("info_个人中心更改个人信息",api1_post_common.body().info);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



    public void getFavouritePageInfo(final int page){
        mData.get_Api1_CollectionList(0, 0)//新启动一个线程
                .subscribe(new Observer<Response<Api1_CollectionList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_CollectionList> api1_collectionList) {
                        count = valueOf(api1_collectionList.body().count);

                        //Log.d("getcount", String.valueOf(count));

                        if (page == 0) {
                            if (count < 10)
                                limit = count;
                            else
                                limit = 10;
                        } else {
                            left = count - limit * page;
                            //Log.d("left", String.valueOf(left));
                            if (left <= 10) last = true;//是最后一页
                            else last = false;

                        }
                        Log.d("循环里", String.valueOf(limit));
                        mData.get_Api1_CollectionList( limit, page)//新启动一个线程
                                .subscribe(new Observer<Response<Api1_CollectionList>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    //获取完所有的数据，执行OnNext
                                    public void onNext(Response<Api1_CollectionList> api1_collectionList) {
                                        if (last)
                                            limit = left;//如果是最后一页不足十条，则字典中只有left条数据
                                        for (int i = 0; i < limit; i++) {
                                            //Log.d("循环里", String.valueOf(limit));
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("companyName", api1_collectionList.body().result.get(i).get(1));
                                            map.put("legalPerson", api1_collectionList.body().result.get(i).get(2));
                                            map.put("c_id", api1_collectionList.body().result.get(i).get(0));
                                            map.put("address", api1_collectionList.body().result.get(i).get(4));

//                                            if (limit < 10) {
//                                                //Log.d("see", (String) map.get("legalPerson"));
//                                            }
                                            list.add(map);
                                        }
                                        mView.initList(list);


                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getFriendsListPageInfo(int page){
        List<Map<String,Api1_Search_Users.UserInfo>> list = new ArrayList<>();
        Map<String, Api1_Search_Users.UserInfo> map = new HashMap<>();
        Api1_Search_Users.UserInfo userInfo= new Api1_Search_Users.UserInfo();
        userInfo.setReal_name("小明");
        userInfo.setDepartment("百度");
        map.put("1",userInfo);
        mView.initListFriends(list);
    }



    public void refreshList(){
        limit = 1;//一次获取十条数据
        left = 0;
        count = 0;//根据搜索结果，一次可下载的总信息条数
        last = false;//是否是最后一页
        list.clear();
    }
    @Override
    public int hasMoreInfo(int page) {

//        Log.d("P层limit", String.valueOf(limit));
//        Log.d("P层page", String.valueOf(page));
//        Log.d("P层count", String.valueOf(count));
//        Log.d("P层limit*(page+1)", String.valueOf(limit * (page + 1)));
        if (limit < 10) limit = 10;
        if (count < (limit * (page + 1))) {//eg 假设，每页10条信息限制，一共2页，页数编号为0,1。
            // 若一共16条信息，当page=1,limit=10时，20>16即已经加载到最后一页的数据，即没有更多信息了
//            Log.d("done", "0");
            return 0;
        } else return 1;

    }

    public void setCompanyId(String str) {

        mData.setCompanyId(str);

    }

    @Override
    public String getFromPage(){
        return mData.getFromPage();
    }

    @Override
    public void postAddCollect(String c_id, String name, String record_name, final ImageView imageView, final Map<String,Object> map) {
        Log.d("debug",c_id+name+record_name);
        mData.post_Collect_Company(c_id, name, record_name)
                .subscribe(new Observer<Response<Api1_post_common>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }

                        if(notNull(api1_post_common.body().info).equals("收藏成功")) {
                            mView.collectSuccess(imageView,map);

                        }
                        else {
                            mView.collectFailure(imageView,map);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void postCancelCollect(String c_id, String name, String record_name, final ImageView imageView, final Map<String,Object> map) {
//        Log.d("debug",c_id+name+record_name);
        mData.post_Cancel_Collect(c_id, name, record_name)
                .subscribe(new Observer<Response<Api1_post_common>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }

                        if(notNull(api1_post_common.body().info).equals("取消收藏成功")) {
                            mView.cancelSuccess(imageView,map);

                        }
                        else {
                            mView.cancelFailure(imageView,map);

                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void displayFav(){
        mView.setBottomBorder(1);

    }

    public void displayFriends(){
        mView.setBottomBorder(2);
    }

    public void displayMessage(){
        mView.setBottomBorder(3);
    }

    public void displayIntelligence(){
        mView.setBottomBorder(4);
    }

    public void setPersonalInfo(){
        mData.get_User_Info().subscribe(new Observer<Response<Api1_ShowUserInfo>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_ShowUserInfo> api1_showUserInfoResponse) {
                String tmp = api1_showUserInfoResponse.headers().toString();
                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.real_name)){
                    mView.setRealName(api1_showUserInfoResponse.body().user_info.real_name);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.responsibility)){
                    mView.setOccupation(api1_showUserInfoResponse.body().user_info.responsibility);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.institution)){
                    mView.setCompany(api1_showUserInfoResponse.body().user_info.institution);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.position)){
                    mView.setPosition(api1_showUserInfoResponse.body().user_info.position);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void logout(){
        mData.post_Cancel_Login("","").subscribe(new Observer<Response<Api1_post_common>>(){
            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_commonResponse) {
                mData.logout();
                mView.logoutJump();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }
}
