package com.demo.reborn.businessopportunity;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_Business_Opportunity;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;

public class BusinessOpportunityPresenter implements BusinessOpportunityContract.Presenter {

    private final BusinessOpportunityContract.View mView;
    private final FinancialDataRepository mData;

    public BusinessOpportunityPresenter(BusinessOpportunityContract.View view) {
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
    public String getBusinessOpportunityTag(){
        return mData.getBusinessOpportunityTag();
    }

    @Override
    public void getInfo(String tag) {
        switch (tag) {
            case "存量融资机会":
                tag = "existing_financing_chance";
                break;
            case "增量融资机会":
                tag = "additional_financing_chance";
                break;
            case "期限优化机会":
                tag = "commitment_optimization_chance";
                break;
            case "成本优化机会":
                tag = "cost_optimization_chance";
                break;
            case "财报结构优化机会":
                tag = "financial_structure_optimization_chance";
                break;
        }
        mData.get_Business_Opportunity(mData.getCompanyId(), tag).subscribe(new Observer<Response<Api1_Business_Opportunity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_Business_Opportunity> api1_business_opportunityResponse) {
                if (api1_business_opportunityResponse.body().error == 0) {
//                    for (int i = 0; i < api1_business_opportunityResponse.body().opportunity_info.size(); i++) {
//                        mView.setTitle(api1_business_opportunityResponse.body().opportunity_info.get(i).title);
//                        for (int j = 0; j < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.size(); j++) {
//                            if (!api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).suggest_content.equals("")) {
//                                mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).suggest_content);
//                                if (api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.size() != 0
//                                        && api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table != null) {
//                                    for (int k = 0; k < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.size(); k++) {
//                                        mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableName);
//                                        mView.setChartColumn(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name.size());
//                                        ArrayList<String> data = new ArrayList<>(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name);
//                                        for (int n = 0; n < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.size(); n++) {
//                                            for (int l = 0; l < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name.size(); l++) {
//                                                data.add(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.get(n).get(l));
//                                            }
//                                        }
//                                        mView.setChart(data);
//                                    }
//                                }
//                            }
//                        }
//                        mView.setPartEnd();
//                    }
                    for (int i = 0; i < api1_business_opportunityResponse.body().opportunity_info.size(); i++) {
                        boolean empty = true;
                        for (int j = 0; j < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.size(); j++) {
                            if (!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).suggest_content)) {
                                if (empty) {
                                    empty = false;
                                    mView.setTitle(api1_business_opportunityResponse.body().opportunity_info.get(i).title);
                                }
                                mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).suggest_content);
                            }
                            if (!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table)) {
                                if (empty) {
                                    empty = false;
                                    mView.setTitle(api1_business_opportunityResponse.body().opportunity_info.get(i).title);
                                }
                                for (int k = 0; k < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.size(); k++) {
                                    boolean tableEmpty = true;
                                    mView.setChartColumn(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name.size());
                                    ArrayList<String> data = new ArrayList<>(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name);
                                    if(notNull(api1_business_opportunityResponse.body().opportunity_info.get(i).title).equals("股权质押融资") && k == 0) {
                                        for (int n = 0; n < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.size(); n++) {
                                            for (int l = 0; l < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name.size() + 1; l++) {  //这里循环次数＋1是为了将公司id也存入data中，在View层用于做跳转时使用。
                                                if (!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.get(n).get(l))) {
                                                    if (tableEmpty) {
                                                        tableEmpty = false;
                                                        mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableName);
                                                    }
                                                }
                                                data.add(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.get(n).get(l));
                                            }
                                        }
                                        if(!tableEmpty) {
                                            mView.setClickableChart(data);
                                        }
                                    }
                                    else {
                                        for (int n = 0; n < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.size(); n++) {
                                            for (int l = 0; l < api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.row_name.size(); l++) {
                                                if (!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.get(n).get(l))) {
                                                    if (tableEmpty) {
                                                        tableEmpty = false;
                                                        mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableName);
                                                    }
                                                }
                                                data.add(api1_business_opportunityResponse.body().opportunity_info.get(i).datas.get(j).table.get(k).tableData.info.get(n).get(l));
                                            }
                                        }
                                        if (!tableEmpty) {
                                            mView.setChart(data);
                                        }
                                    }
                                }
                            }
                        }
                        if(!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list)){
                            if (empty) {
                                empty = false;
                                mView.setTitle(notNull(api1_business_opportunityResponse.body().opportunity_info.get(i).title));
                            }
                            for (int j = 0; j < api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.size(); j++) {
                                if(!isNullOrEmpty(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data)) {
                                    mView.setParagraph(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_name);
                                    for(int l = 0; l < api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data.size(); l++) {
                                        mView.setChartColumn(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data.get(l).row_name.size());
                                        ArrayList<String> data = new ArrayList<>(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data.get(l).row_name);
                                        for (int k = 0; k < api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data.get(l).info.size(); k++) {
                                            data.addAll(api1_business_opportunityResponse.body().opportunity_info.get(i).table_list.get(j).table_data.get(l).info.get(k));
                                        }
                                        mView.setChart(data);
                                    }
                                }
                            }
                        }
                        if(!empty) {
                            mView.setPartEnd();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.setTitle(e.toString());
            }

            @Override
            public void onComplete() {
                int a = 0,b = 0;
                int c = a + b;
            }
        });
    }

    @Override
    public void setBehavior(String startTime,String endTime){
        String tag =  mData.getBusinessOpportunityTag();
        String third_event_id = "";
        switch (tag) {
            case "存量融资机会":
                third_event_id = "14";
                break;
            case "增量融资机会":
                third_event_id = "12";
                break;
            case "期限优化机会":
                third_event_id = "16";
                break;
            case "成本优化机会":
                third_event_id = "13";
                break;
            case "财报结构优化机会":
                third_event_id = "15";
                break;
        }
        mData.post_Browse_Recode("3","3",third_event_id,mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_商机",api1_post_common.body().info);

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
    public void setCompanyId(String id){
        mData.setCompanyId(id);
    }
}
