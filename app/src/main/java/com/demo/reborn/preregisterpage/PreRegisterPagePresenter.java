package com.demo.reborn.preregisterpage;

import com.demo.reborn.data.FinancialDataRepository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.reborn.data.json.Api1_Holders;
import com.demo.reborn.data.json.Api1_post_common;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.isNullOrEmpty;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class PreRegisterPagePresenter implements PreRegisterPageContract.Presenter{

    private final PreRegisterPageContract.View mView;
    private final FinancialDataRepository mData;

    private String tmpPassword = "";
    private String tmpInfo = "none";

    public PreRegisterPagePresenter(PreRegisterPageContract.View view){
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

    public boolean validatePhoneNumber(){
        return mView.getPhoneNumber().matches("^1\\d{10}$");
    }

    public void validateVerificationCode(){
        if(validatePhoneNumber()) {
            mData.post_Sms_CheckCode(mView.getPhoneNumber(), mView.getVerificationCode()).subscribe(new Observer<Response<Api1_post_common>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.e("start_msg: ", "start");
                }

                @Override
                public void onNext(retrofit2.Response<Api1_post_common> api1_post_common) {
                    String temp = api1_post_common.headers().toString();
                    if(!isNullOrEmpty(api1_post_common.headers().get("Set-Cookie"))){
                        mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                    }
                    Log.e("next_msg: ", "success");
                    if (notNull(api1_post_common.body().error).equals("0")) {
                        mView.rightValidateCode();
                    } else if(notNull(api1_post_common.body().error).equals("502")){     //验证码错误
                        mView.errorValidateCode();
                    }else if(notNull(api1_post_common.body().error).equals("505")){      //手机号已存在
                        mView.errorPhoneExists();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("error_msg: ", e.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.e("complete_msg", "completed");
                }
            });
        }else {
            mView.errorPhoneFormat();
        }
    }

    public void submitRegisteration(){
        mData.post_Register_Information(mView.getPhoneNumber(),mView.getPassword(),mView.getSMSCode()).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                String outcome = api1_post_common.body().info;
                if(api1_post_common.headers().get("Set-Cookie")!=null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                if(api1_post_common.body().error.equals("0")){
                    mData.login();
                    mView.registerSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error?","y");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public boolean validateSMSCode(){
        //todo requires model
        return !mView.getSMSCode().isEmpty();
    }

    public boolean validatePassword(){
        tmpPassword = mView.getPassword();
        return(tmpPassword.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$"));
    }

    public boolean validateConfirmPassword() {
        return (mView.getConfirmPassword().equals(tmpPassword) && !tmpPassword.isEmpty());
    }

    public Bitmap apply(@NonNull ResponseBody responseBody) {
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }


    public void getGraphicCode(){
        mData.get_Image_Code().subscribe(new Observer<Response<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ResponseBody> response) {
                String tmp = response.headers().get("Set-Cookie");
                if(!isNullOrEmpty(response.headers().get("Set-Cookie"))){
                    mData.checkSession(response.headers().get("Set-Cookie"));
                }
                mView.setGraphicCode(apply(response.body()));
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
