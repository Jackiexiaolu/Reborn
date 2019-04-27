package com.demo.reborn.resetpassword;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_post_common;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {

    private final ResetPasswordContract.View mView;
    private final FinancialDataRepository mData;

    public ResetPasswordPresenter(ResetPasswordContract.View view){
        mView = view;
        mView.setPresenter(this);
        mData = FinancialDataRepository.getINSTANCE();
    }

    public void submitNewPassword(String password, String confirmPassword){
        if(password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")){
            if(password.equals(confirmPassword)){
                mData.post_Change_Pwd(password).subscribe(new Observer<Response<Api1_post_common>>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_post_common> api1_post_common) {
                        if(api1_post_common.headers().get("Set-Cookie")!=null) {
                            mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                        }
                        if(api1_post_common.body().error.equals("0")) {
                            mView.resetSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }else{
                mView.wrongConfirmation();
            }
        }else{
            mView.wrongFormat();
        }
        //mData.post_Change_Pwd(password).subscribe()
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
