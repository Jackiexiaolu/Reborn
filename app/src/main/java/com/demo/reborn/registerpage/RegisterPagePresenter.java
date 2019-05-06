package com.demo.reborn.registerpage;

import android.widget.ListView;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_SearchCompany;
import com.demo.reborn.data.json.Api1_SearchInstitution;
import com.demo.reborn.data.json.Api1_ShowUserInfo;
import com.demo.reborn.data.json.Api1_post_common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.isNullOrEmpty;
import static java.lang.Integer.valueOf;

public class RegisterPagePresenter implements RegisterPageContract.Presenter{

    private final RegisterPageContract.View mView;
    private final FinancialDataRepository mData;
    private List<Map<String, Object>> list = new ArrayList<>();
    private boolean clearForRegister;


    public RegisterPagePresenter(RegisterPageContract.View view){
        mView = view;
        mView.setPresenter(this);
        mData = FinancialDataRepository.getINSTANCE();
    }

    public void submitInformation(){
        mData.post_Personal_Information(mView.getRealName(),"none",mView.getFinancialFacility(),mView.getDepartment(),mView.getPosition(),mView.getDuty(),mView.getPhoneNumber(),mView.getEmail()).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                if(api1_post_common.body().error.equals("0")){
                    mView.clickJump();
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

    public void setSavedInfo(){
        mData.get_User_Info().subscribe(new Observer<Response<Api1_ShowUserInfo>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_ShowUserInfo> api1_showUserInfoResponse) {
                String tmp = api1_showUserInfoResponse.headers().toString();
                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.real_name)){
                    mView.setRealName(api1_showUserInfoResponse.body().user_info.real_name);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.responsibility)){
                    mView.setDuty(api1_showUserInfoResponse.body().user_info.responsibility);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.institution)){
                    mView.setFinancialFacility(api1_showUserInfoResponse.body().user_info.institution);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.email)){
                    mView.setEmail(api1_showUserInfoResponse.body().user_info.email);
                }

                if(!isNullOrEmpty(api1_showUserInfoResponse.body().user_info.office_phone)){
                    mView.setPhoneNumber(api1_showUserInfoResponse.body().user_info.office_phone);
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

    public void getCompanySearchTip(String inputString){
        if(!inputString.isEmpty()) {
            mData.get_Api1_SearchInstitution(inputString,4, 0)//新启动一个线程
                    .subscribe(new Observer<Response<Api1_SearchInstitution>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<Api1_SearchInstitution> api1_searchInstitution) {
                            mView.clearListView();
                            list.clear();
                            int count = valueOf(api1_searchInstitution.body().count);
                            int limit = 0;
                            if(count > 4){
                                limit = 4;
                            }else{
                                limit = count;
                            }
                            for (int i = 0; i < limit; i++) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("companyName", api1_searchInstitution.body().result.get(i).get(1));
                                list.add(map);
                            }
                            mView.setListTip(list);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else{
            mView.clearListView();
        }
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
