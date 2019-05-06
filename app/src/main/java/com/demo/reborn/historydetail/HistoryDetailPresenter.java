package com.demo.reborn.historydetail;

import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.SimpleAdapter;

import com.demo.reborn.R;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_ChangeInfo;
import com.demo.reborn.data.json.Api1_SearchCompany;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.util.MyApplication.getContext;

public class HistoryDetailPresenter implements HistoryDetailContract.Presenter {
    private final HistoryDetailContract.View mView;
    private final FinancialDataRepository mData;
    public static  List<Map<String, Object>> list = new ArrayList<>();
    private Handler handler;
    private long startTime = 0;
    private long endTime = 0;

    public HistoryDetailPresenter(HistoryDetailContract.View view) {
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
        mData.get_Api1_ChangeInfo(mData.getCompanyId()).
                subscribe(new Observer<Response<Api1_ChangeInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        startTime = System.currentTimeMillis();
                        Log.d(TAG, "开始请求的时间"+ startTime);
                    }

                    @Override
                    public void onNext(Response<Api1_ChangeInfo> api1_changeInfo) {

                        int count = api1_changeInfo.body().changeinfos.size();
                        Log.e("P层条目", String.valueOf(count));

                        for (int i = 0; i < count; i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("changeDate", notNull(api1_changeInfo.body().changeinfos.get(i).time));
                            map.put("changeType", notNull(api1_changeInfo.body().changeinfos.get(i).item));
                            map.put("beforeChange", notNull(api1_changeInfo.body().changeinfos.get(i).before));
                            map.put("afterChange", notNull(api1_changeInfo.body().changeinfos.get(i).after));
                            list.add(map);
                        }

                        mView.initList(list);
                        endTime = System.currentTimeMillis();
                        Log.e(TAG, "结束时间"+ endTime);
                        Log.e(TAG, "时间间隔为："+ (endTime-startTime));
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        endTime = System.currentTimeMillis();
                        Log.e(TAG, "结束时间"+ endTime);
                    }
                });
    }
    @Override
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","7",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_历史沿革",api1_post_common.body().info);

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

