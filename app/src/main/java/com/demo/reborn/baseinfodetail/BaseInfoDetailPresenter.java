package com.demo.reborn.baseinfodetail;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_BaseInfo;
import com.demo.reborn.data.json.Api1_post_common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;

public class BaseInfoDetailPresenter implements BaseInfoDetailContract.Presenter {

    private final BaseInfoDetailContract.View mView;
    private final FinancialDataRepository mData;


    public BaseInfoDetailPresenter(BaseInfoDetailContract.View view) {
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
        mData.get_Api1_BaseInfo(mData.getCompanyId()).subscribe(new Observer<Response<Api1_BaseInfo>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("onSubscribe thread ", " is : " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Response<Api1_BaseInfo> api1_baseInfo) {

                Log.d("onNext thread ", " is : " + Thread.currentThread().getName());
                DecimalFormat df = new DecimalFormat("0.00");
                mView.setBaseInfoDetailCompanyName(notNull(api1_baseInfo.body().name));
                mView.setEstablishDate(notNull(api1_baseInfo.body().estab_date));
                mView.setBaseInfoDetailGroupName(notNull(api1_baseInfo.body().group_name));
                List<Map<String, Object>> shareList = new ArrayList<>();
                if (api1_baseInfo.body().list_info.size() != 0) {
                    for (int i = 0; i < api1_baseInfo.body().list_info.size(); i++) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("shareShortName", "股票简称：" + notNull(api1_baseInfo.body().list_info.get(i).s_name));
                        map.put("shareCode", "股票代码:" + notNull(api1_baseInfo.body().list_info.get(i).s_id));

                        shareList.add(map);
                    }
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("shareShortName", "暂无数据");
                    map.put("shareCode", "");
                    shareList.add(map);

                }

                mView.setListedInfo(shareList);


                if (!isNullOrEmpty(api1_baseInfo.body().money))
                    mView.setRegisteredCapital(df.format(Double.parseDouble(api1_baseInfo.body().money) / 10000));
                else
                    mView.setRegisteredCapital("暂无数据");
                mView.setLegalRepresentative(notNull(api1_baseInfo.body().legal_name));
                mView.setRegisteredAddress(notNull(api1_baseInfo.body().reg_address));


                //生产经营情况
                String temp_business = "";
                if (isNullOrEmpty(api1_baseInfo.body().main_business)) {
                    temp_business += "暂无数据";
                } else {
                    temp_business += "公司主营业务主要分为";
                    for (int i = 0; i < api1_baseInfo.body().main_business.size(); i++) {
                        if (i < api1_baseInfo.body().main_business.size() - 1)

                            temp_business += notNull(api1_baseInfo.body().main_business.get(i)) + "、";

                        else if (i == api1_baseInfo.body().main_business.size() - 1)

                            temp_business += notNull(api1_baseInfo.body().main_business.get(i));

                    }

                    temp_business += "等几个板块。";

                }
                mView.setMainBusiness(temp_business);

            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("onComplete thread ", " is : " + Thread.currentThread().getName());
            }
        });
    }

    @Override
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","4",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info",api1_post_common.body().info);

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