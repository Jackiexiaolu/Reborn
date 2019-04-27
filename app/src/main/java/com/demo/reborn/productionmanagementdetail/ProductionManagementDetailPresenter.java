package com.demo.reborn.productionmanagementdetail;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;

import com.demo.reborn.data.json.Api1_Business;
import com.demo.reborn.data.json.Api1_post_common;


import java.text.DecimalFormat;
import java.util.List;

import java.util.ArrayList;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.date2String;
import static com.demo.reborn.Judgement.fmtMicrometer;
import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.unitFormat;

public class ProductionManagementDetailPresenter implements ProductionManagementDetailContract.Presenter {


    private final ProductionManagementDetailContract.View mView;
    private final FinancialDataRepository mData;
    private int max = 0;

    public ProductionManagementDetailPresenter(ProductionManagementDetailContract.View view) {
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

        mData.get_Api1_Business(mData.getCompanyId()).subscribe(new Observer<Response<Api1_Business>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Business> api1_business) {
                String temp_business = "";

                if (isNullOrEmpty(api1_business.body().key_business_last)) {

                    temp_business += "该公司暂无公开主营业务数据";
                } else {
                    temp_business += "公司主营业务主要分为";
                    for (int i = 0; i < api1_business.body().key_business_last.size(); i++) {
                        if (i < api1_business.body().key_business_last.size() - 1 && notNull(api1_business.body().key_business_last.get(i).get(1)).equals("产品"))

                            temp_business += api1_business.body().key_business_last.get(i).get(2) + "、";

                        else if (i == api1_business.body().key_business_last.size() - 1 && notNull(api1_business.body().key_business_last.get(i).get(1)).equals("产品"))

                            temp_business += api1_business.body().key_business_last.get(i).get(2);
                    }
                    if (api1_business.body().key_business_last.size() != 0)
                        temp_business += "等几个板块。";
                    if (!isNullOrEmpty(api1_business.body().operate_rev))
                        temp_business += api1_business.body().year + "年实现营业额" + unitFormat(api1_business.body().operate_rev) +
                                "元人民币，";
                    if (!isNullOrEmpty(api1_business.body().operate_rev_YOY)) {
                        if (notNull(api1_business.body().operate_rev_YOY).contains("-"))
                            temp_business += "同比减少" + (isNullOrEmpty(api1_business.body().operate_rev_YOY)?"-":unitFormat(api1_business.body().operate_rev_YOY.substring(1))) + "；实现了利润总额" + unitFormat(api1_business.body().profit) +
                                    "元人民币，";
                        else
                            temp_business += "同比增长" + unitFormat(api1_business.body().operate_rev_YOY) + "；实现了利润总额" + unitFormat(api1_business.body().profit) +
                                    "元人民币，";
                    }
                    if (!isNullOrEmpty(api1_business.body().profit_YOY)) {
                        if (notNull(api1_business.body().profit_YOY).contains("-"))
                            temp_business += "同比减少" + (isNullOrEmpty(api1_business.body().profit_YOY)?"-":unitFormat(api1_business.body().profit_YOY.substring(1)));
                        else
                            temp_business += "同比增长" + unitFormat(api1_business.body().profit_YOY);
                    }
                    //计算最大业务板块
                    for (int j = 0; j < api1_business.body().key_business_last.size(); j++) {
                        if (notNull(api1_business.body().key_business_last.get(j).get(1)).equals("产品") && !isNullOrEmpty(api1_business.body().key_business_last.get(j).get(4))) {
                            if (Double.parseDouble(api1_business.body().key_business_last.get(max).get(4).replace("%", "")) < Double.parseDouble(api1_business.body().key_business_last.get(j).get(4).replace("%", "")))
                                max = j;
                        }
                    }
                    if (!isNullOrEmpty(api1_business.body().key_business_last.get(max).get(2)) && !isNullOrEmpty(api1_business.body().key_business_last.get(max).get(4))) {
                        temp_business += "，主营业务中" + api1_business.body().key_business_last.get(max).get(2) + "占较大业务板块,";
                        temp_business += "占比" + unitFormat(api1_business.body().key_business_last.get(max).get(4)) + "。";
                    }
                }
                mView.setBusinessInfo(temp_business);
                List<String> productTable = new ArrayList<>();
                List<String> areaTable = new ArrayList<>();
                DecimalFormat df = new DecimalFormat("0.00");
                for (int i = 0; i < api1_business.body().key_business_3_year.size(); i++)
                    for (int j = 0; j < api1_business.body().key_business_3_year.get(i).data.size(); j++)
                        if (notNull(api1_business.body().key_business_3_year.get(i).data.get(j).get(1)).equals("产品")) {
                            for (int t = 0; t < api1_business.body().key_business_3_year.get(i).data.get(j).size(); t++) {
                                productTable.add(unitFormat(api1_business.body().key_business_3_year.get(i).data.get(j).get(t)));
                            }

                        } else {
                            for (int t = 0; t < api1_business.body().key_business_3_year.get(i).data.get(j).size(); t++) {
                                areaTable.add(unitFormat(api1_business.body().key_business_3_year.get(i).data.get(j).get(t)));
                            }

                        }

                mView.setProductTable(productTable);
                mView.setAreaTable(areaTable);


//                List<String> productTable=new ArrayList<>();
//                List<String> areaTable=new ArrayList<>();
//                for(int i=0;i<api1_business.body().key_business_3_year.size();i++)
//                    for(int j=0;j<api1_business.body().key_business_3_year.get(i).data.size();j++)
//                        if (api1_business.body().key_business_3_year.get(i).data.get(j).get(1).equals("产品")){
//                            productTable.addAll(api1_business.body().key_business_3_year.get(i).data.get(j));
//                        }
//                        else {
//                            areaTable.addAll(api1_business.body().key_business_3_year.get(i).data.get(j));
//                        }
//
//                mView.setProductTable(productTable);
//                mView.setAreaTable(areaTable);
//                //Log.d("date",date2String("Sat, 30 Jun 2018 00:00:00 GMT"));


            }

            @Override
            public void onError(Throwable e) {
                mView.setBusinessInfo(e.toString());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void setBehavior(String startTime, String endTime) {
        mData.post_Browse_Recode("3", "2", "9", mData.getCompanyId(), startTime, endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if (api1_post_common.headers().get("Set-Cookie") != null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_生产经营", api1_post_common.body().info);

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
