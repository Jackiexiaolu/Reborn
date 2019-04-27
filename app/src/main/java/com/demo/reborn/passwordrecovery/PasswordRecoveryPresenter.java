package com.demo.reborn.passwordrecovery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_post_common;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PasswordRecoveryPresenter implements PasswordRecoveryContract.Presenter {

    private final PasswordRecoveryContract.View mView;
    private final FinancialDataRepository mData;

    public PasswordRecoveryPresenter(PasswordRecoveryContract.View view){
        mView = view;
        mView.setPresenter(this);
        mData = FinancialDataRepository.getINSTANCE();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

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
                if(response.headers().get("Set-Cookie") != null){
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

    public void sendSMS(String phoneNumber,String graphicCode){
        mData.post_Modify_Code(phoneNumber,graphicCode).subscribe(new Observer<Response<Api1_post_common>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                //String tmp = api1_post_common.body().error;
                if(api1_post_common.headers().get("Set-Cookie")!=null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                if(api1_post_common.body().error.equals("0")){
                    mView.smsCodeSent();
                }else if(api1_post_common.body().error.equals("502")){
                    mView.wrongGraphicCode();
                }else if(api1_post_common.body().error.equals("505")){

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

    public void submitSMScode(String phoneNumber, String SMScode){
        mData.post_Verify_Mobile(phoneNumber,SMScode).subscribe(new Observer<Response<Api1_post_common>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie")!=null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                if(api1_post_common.body().error.equals("0")){
                    mView.setNewPassword();
                }else if(api1_post_common.body().error.equals("502")){
                    mView.wrongSMScode();
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

}
