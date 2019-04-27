package com.demo.reborn.companydetail;

import android.util.Log;
import android.widget.ImageView;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_FinancingIndex;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;

public class CompanyDetailPresenter implements CompanyDetailContract.Presenter{

    private final CompanyDetailContract.View mView;
    private final FinancialDataRepository mData;

    public CompanyDetailPresenter(CompanyDetailContract.View view) {
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
    public void getInfo() {
        mData.get_Api1_FinancingIndex(mData.getCompanyId()).subscribe(new Observer<Response<Api1_FinancingIndex>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_FinancingIndex> api1_financingIndexResponse) {
                if(notNull(api1_financingIndexResponse.body().error).equals("0")) {
                    mView.setCompanyName(notNull(api1_financingIndexResponse.body().name));
                    mView.setFavourite(notNull(api1_financingIndexResponse.body().flag + ""));
                    mView.setLegalPerson(notNull(api1_financingIndexResponse.body().legal_name));
                    mView.setShareholder(notNull(api1_financingIndexResponse.body().first_holder_name));
                }else if(notNull(api1_financingIndexResponse.body().error).equals("500")){
                    mData.logout();
                    mView.loginFirst();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error",e.toString());
                mView.errorShow();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void postAddCollect(String name, String record_name, ImageView imageView,Map<String,Object> map) {
        Log.d("debug",mData.getCompanyId()+name+record_name);
        mData.post_Collect_Company(mData.getCompanyId(), name, record_name)
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
    public void postCancelCollect(String name, String record_name, ImageView imageView, Map<String,Object> map) {
        Log.d("debug",mData.getCompanyId()+name+record_name);
        mData.post_Cancel_Collect(mData.getCompanyId(), name, record_name)
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

    @Override
    public String getCompanyId(){
        return mData.getCompanyId();
    }

    @Override
    public void setBehavior(String startTime,String endTime){
        mData.post_Browse_Recode("3","1","3",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_公司主页",api1_post_common.body().info);

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
