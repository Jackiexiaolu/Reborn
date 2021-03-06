package com.demo.reborn.personalcenter;
import android.util.Log;
import android.widget.ImageView;

import com.demo.reborn.R;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_CollectionList;
import com.demo.reborn.data.json.Api1_FriendsList;
import com.demo.reborn.data.json.Api1_Receive_Friend;
import com.demo.reborn.data.json.Api1_Search_Users;
import com.demo.reborn.data.json.Api1_Send_Friend_Response;
import com.demo.reborn.data.json.Api1_ShowUserInfo;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;
import static java.lang.Integer.valueOf;

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter {

    private final PersonalCenterContract.View mView;
    public final FinancialDataRepository mData;
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
        List<Map<String,Object>> reslist = new ArrayList<>();
        mData.get_Api1_FriendsList(0,0)
                .subscribe(new Observer<Response<Api1_FriendsList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_FriendsList> api1_FriendsList) {
                        System.out.println(api1_FriendsList.body().info);//好使了
                        if (last)
                            limit = left;//如果是最后一页不足十条，则字典中只有left条数据
                        for (int i = 0; i < limit; i++) {
                            //Log.d("循环里", String.valueOf(limit));
                            for(List<String> stringList: api1_FriendsList.body().request_list){
                                Map<String,Object> map = new HashMap<>();
                                map.put("id", stringList.get(1));
                                map.put("head_image", R.drawable.head);
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(stringList.get(0)).append("-")
                                        .append(stringList.get(0)).append("-")
                                        .append(stringList.get(0));
                                map.put("real_name",stringList.get(0));
                                map.put("context",stringBuilder.toString());
                                reslist.add(map);
                            }
                        }
                       // mView.initList(list);
                        mView.initListFriends(reslist);

                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });




    }
    //搜索好友
    public void getFriendsListMessage( List<List<String>> mList){
        List<Map<String,Object>> list = new ArrayList<>();
        System.out.println("开始解析数据");
        for(List<String> stringList: mList){
            Map<String,Object> map = new HashMap<>();
            map.put("id", stringList.get(0));
            map.put("head_image", R.drawable.head);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(stringList.get(5)).append("-")
                    .append(stringList.get(6)).append("-")
                    .append(stringList.get(7));
            map.put("real_name",stringList.get(3));
            map.put("message",stringBuilder.toString());
            list.add(map);

        }
        mView.initListFriendsMessage(list);
    }

    /**
     *请求列表
     * @param page
     */
    public void getResquestFriendsListPageInfo(int page){
        List<Map<String,Object>> reslist = new ArrayList<>();
        mData.get_Api1_Friends_request_list()
                .subscribe(new Observer<Response<Api1_FriendsList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_FriendsList> api1_FriendsList) {
                        System.out.println(api1_FriendsList.body().info);//好使了
                        if (last)
                            limit = left;//如果是最后一页不足十条，则字典中只有left条数据
                        for (int i = 0; i < limit; i++) {
                            //Log.d("循环里", String.valueOf(limit));
                            for(List<String> stringList: api1_FriendsList.body().request_list){
                                Map<String,Object> map = new HashMap<>();
                                map.put("id", stringList.get(1));
                                map.put("head_image", R.drawable.head);
                                map.put("real_name",stringList.get(0));
                                map.put("context","很高兴认识你！");
                                reslist.add(map);
                            }
                        }
                        // mView.initList(list);
                        mView.initListFriendsRequest(reslist);


                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    /**
     * 删除好友
     * @param id
     */
    public void deleteFriends(String id){
      //// TODO: 2019/5/2  没有接口 
    }

    /**
     * 查找用户
     * @param department
     */
    public void selectFriends(String department){
        System.out.println("开始查找"+department);
       mData.get_Api1_Search_Users(department)
                .subscribe(new Observer<Response<Api1_Search_Users>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_Search_Users> api1_Search_Users) {
                        System.out.println(api1_Search_Users.body().info);//好使了

                        // TODO: 2019/5/6  

                       getFriendsListMessage(api1_Search_Users.body().select_list);

                        
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 同意添加好友用户
     * @param
     */
    public boolean agreeFriends(String rec_id){
        System.out.println("同意");
        final boolean[] flag = new boolean[1];
        mData.get_Api1_Receive_friend_request(rec_id)
                .subscribe(new Observer<Response<Api1_Receive_Friend>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_Receive_Friend> api1_Receive_Friend) {
                        System.out.println(api1_Receive_Friend.body().info);//好使了
                         flag[0] = true;

                       // getFriendsListMessage(api1_Receive_Friend.body().select_list);


                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return flag[0];
    }
  /**
     * 添加好友
     * @param id
     */
    public void addFriends(String id){
        mData.get_Api1_Send_Friend_Request(id)
                .subscribe(new Observer<Response<Api1_Send_Friend_Response>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }


                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_Send_Friend_Response> api1_Send_Friend_Response) {
                        System.out.println(api1_Send_Friend_Response.body().info);//好使了
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("--------------------"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
