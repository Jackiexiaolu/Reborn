package com.demo.reborn.opportunityabstract;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_Opportunity_Abstract;
import com.demo.reborn.data.json.Api1_post_common;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;

public class OpportunityAbstractPresenter implements OpportunityAbstractContract.Presenter {

    private final OpportunityAbstractContract.View mView;
    private final FinancialDataRepository mData;

    private final static String[] title = {"存量融资机会", "增量融资机会", "期限优化机会", "成本优化机会", "财报结构优化机会"};

    public OpportunityAbstractPresenter(OpportunityAbstractContract.View view) {
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

    public void loadInfo() {
        mData.get_Opportunity_Abstract(mData.getCompanyId(), "additional_financing_chance").subscribe(new Observer<Response<Api1_Opportunity_Abstract>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Opportunity_Abstract> api1_opportunity_abstractResponse) {
                if (api1_opportunity_abstractResponse.body().error.equals("0")) {
                    mView.setCompanyName(notNull(api1_opportunity_abstractResponse.body().name));
                    for(int i = 0; i < api1_opportunity_abstractResponse.body().abstract_info.size(); i++){
                        boolean empty = true;
                        for(int j = 0; j < api1_opportunity_abstractResponse.body().abstract_info.get(i).contents.size(); j++){
                            if(!isNullOrEmpty(api1_opportunity_abstractResponse.body().abstract_info.get(i).contents.get(j))) {
                                if(empty){
                                    empty = false;
                                    mView.setTitle(notNull(api1_opportunity_abstractResponse.body().abstract_info.get(i).first_level_desc));
                                }
                                mView.setParagraph(notNull(api1_opportunity_abstractResponse.body().abstract_info.get(i).contents.get(j)));
                            }
                        }
                    }
                }else if(api1_opportunity_abstractResponse.body().error.equals("400")){
                    mView.accessDenied();
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
    public void setBusinessOpportunityTag(String tag){
        mData.setBusinessOpportunityTag(tag);
    }

    @Override
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","3","2",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_商机摘要",api1_post_common.body().info);

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
