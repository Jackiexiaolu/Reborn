package com.demo.reborn.loginpage;


import android.util.Log;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_post_token;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.isNullOrEmpty;

public class LoginPagePresenter implements LoginPageContract.Presenter {

    private final LoginPageContract.View mView;
    private final FinancialDataRepository mData;

    public LoginPagePresenter(LoginPageContract.View view) {
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

    public boolean getLoginStatus(){
        return mData.getLoginStatus();
    }

    public void loginAttempt(String userID, String password){

        mData.post_Login_Information(userID,password).subscribe(new Observer<Response<Api1_post_token>>(){
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_token> api1_post_token) {
                String tmp = api1_post_token.headers().toString();
                String token = api1_post_token.body().token_id;
                if(notNull(api1_post_token.body().error).equals("0")){
                    mData.checkSession(api1_post_token.headers().get("Set-Cookie"));
                    mData.checkToken_id(api1_post_token.body().token_id);
                    mData.login();
                    mView.loginSuccess();
                }else if(notNull(api1_post_token.body().error).equals("502") && notNull(api1_post_token.body().info).equals("该用户未注册")){
                    mView.setTv_errTip1("用户不存在");
                    mView.setTv_errTip2("");
                }else if(notNull(api1_post_token.body().error).equals("502") && notNull(api1_post_token.body().info).equals("密码错误")){
                    mView.setTv_errTip1("");
                    mView.setTv_errTip2("密码错误");
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
