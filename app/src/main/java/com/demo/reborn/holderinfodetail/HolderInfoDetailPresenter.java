package com.demo.reborn.holderinfodetail;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_Holders;
import com.demo.reborn.data.json.Api1_post_common;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.isNullOrEmpty;

public class HolderInfoDetailPresenter implements HolderInfoDetailContract.Presenter {

    private final HolderInfoDetailContract.View mView;
    private final FinancialDataRepository mData;
    public List<Map<String,Object>> list =new ArrayList<>();
    public HolderInfoDetailPresenter(HolderInfoDetailContract.View view) {
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

    public void loadInfo(){
        mData.get_Api1_Holders(mData.getCompanyId()).subscribe(new Observer<Response<Api1_Holders>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Holders> api1_holders) {
                ArrayList<String> codes = new ArrayList<>();
                if(!isNullOrEmpty(api1_holders.body().holder.codes)) {
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.a_code))
                        codes.add(api1_holders.body().holder.codes.a_code);
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.b_code))
                        codes.add(api1_holders.body().holder.codes.b_code);
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.h_code))
                        codes.add(api1_holders.body().holder.codes.h_code);
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.n_code))
                        codes.add(api1_holders.body().holder.codes.n_code);
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.nas_code))
                        codes.add(api1_holders.body().holder.codes.nas_code);
                    if (!isNullOrEmpty(api1_holders.body().holder.codes.x_code))
                        codes.add(api1_holders.body().holder.codes.x_code);
                }
                mView.setCompanyType(notNull(api1_holders.body().holder.name));
                mView.setStockCode(codes);
                mView.setHolderName(notNull(api1_holders.body().holder.name));
                ArrayList<PieEntry> entries = new ArrayList<>();
                float others=0f;
                float totalSum=0f;
                for(int i=0;i<api1_holders.body().holders.size();i++){
                    if(i <= 5 && !isNullOrEmpty(api1_holders.body().holders.get(i).rate)){
                        String tempS=api1_holders.body().holders.get(i).rate;
                        tempS=tempS.substring(0,tempS.length()-1);
                        float tempF = Float.parseFloat(tempS);
                        if(tempF > 3.0f)
                        entries.add(new PieEntry(tempF,api1_holders.body().holders.get(i).name));
                        else
                            others += tempF;
                        totalSum += tempF;
                    }
                    else if(!isNullOrEmpty(api1_holders.body().holders.get(i).rate)){
                        String tempS=api1_holders.body().holders.get(i).rate;
                        tempS=tempS.substring(0,tempS.length()-1);
                        others+=Float.parseFloat(tempS);
                        totalSum+=Float.parseFloat(tempS);
                    }
                }
                if(totalSum < 100f)
                    others += 100f - totalSum;
                entries.add(new PieEntry(others, "其他"));
                mView.setPieChartData(entries);

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
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","5",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info股东详情",api1_post_common.body().info);

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
