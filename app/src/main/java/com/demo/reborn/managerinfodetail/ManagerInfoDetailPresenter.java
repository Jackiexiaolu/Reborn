package com.demo.reborn.managerinfodetail;

import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_Managers;
import com.demo.reborn.data.json.Api1_SearchCompany;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.notNull;

public class ManagerInfoDetailPresenter implements ManagerInfoDetailContract.Presenter {

    private final ManagerInfoDetailContract.View mView;
    private final FinancialDataRepository mData;
    public List<Map<String, Object>> list = new ArrayList<>();
    public long startime = 0;

    public ManagerInfoDetailPresenter(ManagerInfoDetailContract.View view) {
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

        mData.get_Api1_Managers(mData.getCompanyId()).subscribe(new Observer<Response<Api1_Managers>>() {
            @Override
            public void onSubscribe(Disposable d) {
                startime = System.currentTimeMillis();
            }

            @Override
            public void onNext(Response<Api1_Managers> api1_managers) {
                int count = api1_managers.body().managers.size();
                for (int i = 0; i < count; i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("managerName", notNull(api1_managers.body().managers.get(i).name));
                    map.put("position", notNull(api1_managers.body().managers.get(i).post));
                    map.put("introductionInfo", notNull(api1_managers.body().managers.get(i).cv));
                    list.add(map);
                }

                mView.initList(list);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                long now = System.currentTimeMillis();
                Log.d("test","timeDiff is "+ (now-startime) +"ms");

            }
        });
    }
    @Override
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","8",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_高管信息",api1_post_common.body().info);

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

