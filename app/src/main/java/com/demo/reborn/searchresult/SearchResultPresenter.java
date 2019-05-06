package com.demo.reborn.searchresult;


import android.util.Log;
import android.widget.ImageView;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_CollectionList;
import com.demo.reborn.data.json.Api1_SearchCompany;
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
import static com.demo.reborn.util.MyApplication.getContext;
import static java.lang.Integer.bitCount;
import static java.lang.Integer.valueOf;


public class SearchResultPresenter implements SearchResultContract.Presenter {

    private SearchResultContract.View mView;
    private FinancialDataRepository mData;
    public List<Map<String, Object>> list = new ArrayList<>();
    public int limit = 1;//一次获取十条数据
    public int left = 0;
    public int count = 0;//根据搜索结果，一次可下载的总信息条数
    public boolean last = false;//是否是最后一页

    //这里，count,page,limit关系如下：count/limit向上取整=page;如23条信息，每页显示10条，则一共3页
    public SearchResultPresenter(SearchResultContract.View view) {
        mData = FinancialDataRepository.getINSTANCE();
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getPerPageInfo(int page) {

            mData.get_Api1_SearchCompany(mData.getKeyword(), "", 0, 0)//新启动一个线程
                    .subscribe(new Observer<Response<Api1_SearchCompany>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        //获取完所有的数据，执行OnNext
                        public void onNext(Response<Api1_SearchCompany> api1_searchCompany) {
                            if(!isNullOrEmpty(api1_searchCompany.body().count))
                            count = valueOf(api1_searchCompany.body().count);

                            Log.d("getcount", String.valueOf(count));

                            if (page == 0) {
                                if (count < 10)
                                    limit = count;
                                else
                                    limit = 10;
                            } else {
                                left = count - limit * page;
                                Log.d("left", String.valueOf(left));
                                if (left <= 10) last = true;//是最后一页
                                else last = false;

                            }
                            Log.d("循环里", String.valueOf(limit));
                            mData.get_Api1_SearchCompany(mData.getKeyword(), "", limit, page)//新启动一个线程
                                    .subscribe(new Observer<Response<Api1_SearchCompany>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        //获取完所有的数据，执行OnNext
                                        public void onNext(Response<Api1_SearchCompany> api1_searchCompany) {
                                            //ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
                                            if (last)
                                                limit = left;//如果是最后一页不足十条，则字典中只有left条数据
                                            for (int i = 0; i < limit; i++) {
                                                //Log.d("循环里", String.valueOf(limit));
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("companyName", api1_searchCompany.body().result.get(i).get(1));
                                                map.put("legalPerson", api1_searchCompany.body().result.get(i).get(2));
                                                map.put("c_id", api1_searchCompany.body().result.get(i).get(0));
                                                map.put("address", api1_searchCompany.body().result.get(i).get(4));
                                                map.put("flag", api1_searchCompany.body().result.get(i).get(5));

                                                if (limit < 10) {
                                                    //Log.d("see", (String) map.get("legalPerson"));
                                                }
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

    @Override
    public void getFavouritePageInfo(int page){
        mData.get_Api1_CollectionList(0, 0)//新启动一个线程
                .subscribe(new Observer<Response<Api1_CollectionList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    //获取完所有的数据，执行OnNext
                    public void onNext(Response<Api1_CollectionList> api1_collectionList) {
                        if (notNull(api1_collectionList.body().error).equals("0")){
                            if(isNullOrEmpty(api1_collectionList.body().count))
                                count = 0;
                            else
                                count = valueOf(api1_collectionList.body().count);

//                        Log.d("getcount", String.valueOf(count));

                        if (page == 0) {
                            if (count < 10)
                                limit = count;
                            else
                                limit = 10;
                        } else {
                            left = count - limit * page;
//                            Log.d("left", String.valueOf(left));
                            if (left <= 10) last = true;//是最后一页
                            else last = false;

                        }
//                        Log.d("循环里", String.valueOf(limit));
                        mData.get_Api1_CollectionList(limit, page)//新启动一个线程
                                .subscribe(new Observer<Response<Api1_CollectionList>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    //获取完所有的数据，执行OnNext
                                    public void onNext(Response<Api1_CollectionList> api1_collectionList) {
                                        //ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
                                        if (last)
                                            limit = left;//如果是最后一页不足十条，则字典中只有left条数据
                                        for (int i = 0; i < limit; i++) {
                                            //Log.d("循环里", String.valueOf(limit));
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("companyName", (api1_collectionList.body().result.get(i).get(1)));
                                            map.put("legalPerson", api1_collectionList.body().result.get(i).get(2));
                                            map.put("c_id", api1_collectionList.body().result.get(i).get(0));
                                            map.put("address", api1_collectionList.body().result.get(i).get(4));

//                                            if (limit < 10) {
//                                                Log.d("see", (String) map.get("legalPerson"));
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
                    }else if(notNull(api1_collectionList.body().error).equals("500")){
                            mData.logout();
                            mView.loginFirst();
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
    public void postAddCollect(String c_id, String name, String record_name, ImageView imageView,Map<String,Object> map) {
//        Log.d("debug",c_id+name+record_name);
        mData.post_Collect_Company(c_id, name, record_name)
                .subscribe(new Observer<Response<Api1_post_common>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(api1_post_common.headers().get("Set-Cookie") != null){
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
    public void postCancelCollect(String c_id,String name,String record_name,ImageView imageView,Map<String,Object> map) {
//        Log.d("debug",c_id+name+record_name);
        mData.post_Cancel_Collect(c_id, name, record_name)
                .subscribe(new Observer<Response<Api1_post_common>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(api1_post_common.headers().get("Set-Cookie") != null){
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

    @Override
    public void setSearchClick(String time){
        mData.post_Click_Search("5",mData.getCompanyId(),time)
                .subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
//                Log.e("info",api1_post_common.body().info);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
