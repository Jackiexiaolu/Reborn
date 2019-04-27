package com.demo.reborn.financialintelligence;

import com.demo.reborn.data.FinancialDataRepository;
import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.reborn.data.json.Api1_Holders;
import com.demo.reborn.data.json.Api1_post_common;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class FinancialIntelligencePresenter implements FinancialIntelligenceContract.Presenter {

    private final FinancialIntelligenceContract.View mView;
    private final FinancialDataRepository mData;
    private String[] tempDropItems = new String[]{"项目1","项目2","项目3"};

    public FinancialIntelligencePresenter(FinancialIntelligenceContract.View view){
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

    public void finalSubmit(){//String fin_borrower,String asset_comp,String inv_agency,String
        mData.post_Commit_Trust("none","none","none","none","none","none","none","none","none","none","none","none" +
                "none","","none","none","none","none","none").subscribe(new Observer<Response<Api1_post_common>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                if(api1_post_common.body().info.equals("successful")){
                    mView.clickJump();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error: ",e.getMessage());
                //mView.clickJump();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void setInsurance(){
        //todo:interact with model to get actual drop items
        mView.setInsuranceDropItems(tempDropItems);
    }

    public void setMainArticle(){
        //todo:interact with model to get actual drop items
        mView.setMainArticleDropItems(tempDropItems);
    }

    public void setProduct(){
        //todo:interact with model to get actual drop items
        mView.setProductDropItems(tempDropItems);
    }
}
