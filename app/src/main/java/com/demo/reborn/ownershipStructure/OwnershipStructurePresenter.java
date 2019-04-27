package com.demo.reborn.ownershipstructure;

import android.util.Log;

import com.demo.reborn.Judgement;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_BaseInfo;
import com.demo.reborn.data.json.Api1_FirmgraphInvestments;
import com.demo.reborn.data.json.Api1_NewFirmgraphHolders;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class OwnershipStructurePresenter implements OwnershipStructureContract.Presenter  {

    private final OwnershipStructureContract.View mView;
    private final FinancialDataRepository mData;

    String companyName = "";

    public OwnershipStructurePresenter(OwnershipStructureContract.View view) {
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
    public String getCompanyId(){
        return mData.getCompanyId();
    }

    @Override
    public void refreshCompany(String company_id){
        if(!company_id.equals("")) {
            mData.get_Api1_BaseInfo(company_id).subscribe(new Observer<Response<Api1_BaseInfo>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<Api1_BaseInfo> api1_baseInfo) {
                    companyName = api1_baseInfo.body().name;
                    mView.setCurrentCompany(api1_baseInfo.body().name);
                }

                @Override
                public void onError(Throwable e) {
                    mView.setCurrentCompany("该公司信息尚未完善");
                    List<Map<String, Object>> emptyList = new ArrayList<>();
                    mView.setFatherList(emptyList);
                    mView.setChildList(emptyList);
                    mView.setGroupCompany("");
                    mView.removeSecondHolder();
                }

                @Override
                public void onComplete() {
                    mView.refreshList(company_id);
                }
            });
        }

    }

    @Override
    public void refreshFatherList(String company_id) {
        if (!company_id.equals("")) {
            List<Map<String, Object>> shareHolders_list = new ArrayList<>();
            final boolean[] isGroup = {false};
            mData.get_New_Firmgraph_Holders(company_id).subscribe(new Observer<Response<Api1_NewFirmgraphHolders>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<Api1_NewFirmgraphHolders> api1_newFirmgraphHoldersResponse) {
                    if (api1_newFirmgraphHoldersResponse.body().error == 0) {
                        mView.setGroupCompany(api1_newFirmgraphHoldersResponse.body().group_name);
                        if(api1_newFirmgraphHoldersResponse.body().group_name.equals(companyName)
                                && api1_newFirmgraphHoldersResponse.body().holders.size() == 0) {
                            mView.removeGroup();
                            shareHolders_list.clear();
                            isGroup[0] = true;
                        }
                        else {
                            for (int i = api1_newFirmgraphHoldersResponse.body().holders.size() - 1; i >= 0; i--) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("company", api1_newFirmgraphHoldersResponse.body().holders.get(i).holder);
                                map.put("company_id", api1_newFirmgraphHoldersResponse.body().holders.get(i).c_id);
                                if(Judgement.isNullOrEmpty(api1_newFirmgraphHoldersResponse.body().holders.get(i).rate)) {
                                    map.put("rate", "占比：未知");
                                }
                                else {
                                    map.put("rate", "占比：" + api1_newFirmgraphHoldersResponse.body().holders.get(i).rate + "%");
                                }
                                shareHolders_list.add(map);
                            }

//                            if (api1_newFirmgraphHoldersResponse.body().holders.size() > 0) {
//                                if (!Judgement.isNullOrEmpty(api1_newFirmgraphHoldersResponse.body().holders.get(0).rate)) {
//                                    mView.setFirstRate(api1_newFirmgraphHoldersResponse.body().holders.get(0).rate + "%");
//                                }
//                            } else {
//                                mView.setFirstRate("");
//                            }
                            if (!Judgement.isNullOrEmpty(api1_newFirmgraphHoldersResponse.body().second_holder_info.second_holder_name)) {
                                mView.removeSecondHolder();
                            } else {
                                if ( !Judgement.isNullOrEmpty(api1_newFirmgraphHoldersResponse.body().second_holder_info.second_holder_rate)) {
                                    mView.setSecondHolder(api1_newFirmgraphHoldersResponse.body().second_holder_info.second_holder_name,
                                            "占比" + api1_newFirmgraphHoldersResponse.body().second_holder_info.second_holder_rate + "%");
                                }
                                else {
                                    mView.setSecondHolder(api1_newFirmgraphHoldersResponse.body().second_holder_info.second_holder_name, "");
                                }
                            }
                        }
                    }
                    else{
                        Map<String, Object> map = new HashMap<>();
                        map.put("company", "该公司信息尚未完善");
                        map.put("company_id", "");
                        map.put("rate", "100%");
                        shareHolders_list.add(map);

                        mView.setGroupCompany("");
                        mView.removeSecondHolder();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mView.setCurrentCompany(e.toString());
                }

                @Override
                public void onComplete() {
                    if(!isGroup[0]) {
                        mView.setFatherList(shareHolders_list);
                    }
                }
            });
        }
    }

    @Override
    public void refreshChildList(String company_id){
        if (!company_id.equals("")) {
            List<Map<String, Object>> investments_list = new ArrayList<>();
            mData.get_Api1_FirmgraphInvestments(company_id).subscribe(new Observer<Response<Api1_FirmgraphInvestments>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<Api1_FirmgraphInvestments> api1_firmgraphInvestmentsResponse) {
                    if (api1_firmgraphInvestmentsResponse.body().error == 0) {
                        for (int i = 0; i < api1_firmgraphInvestmentsResponse.body().investments.size(); i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("company", api1_firmgraphInvestmentsResponse.body().investments.get(i).name);
                            map.put("company_id", api1_firmgraphInvestmentsResponse.body().investments.get(i).code);
                            if(Judgement.isNullOrEmpty(api1_firmgraphInvestmentsResponse.body().investments.get(i).rate)) {
                                map.put("rate", "占比：未知");
                            }
                            else {
                                map.put("rate", "占比：" + api1_firmgraphInvestmentsResponse.body().investments.get(i).rate + "%");
                            }
                            investments_list.add(map);
                        }
                    }
                    else {
                        Map<String, Object> map = new HashMap<>();
                        map.put("company", "该公司信息尚未完善");
                        map.put("company_id", "");
                        map.put("rate", "100%");
                        investments_list.add(map);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mView.setCurrentCompany(e.toString());
                }

                @Override
                public void onComplete() {
                    mView.setChildList(investments_list);
                }
            });
        }
    }

    @Override
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","6",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_股权结构树",api1_post_common.body().info);

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
