package com.demo.reborn.loginpage;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface LoginPageContract  {
    interface View extends BaseView<Presenter>{
        void loginSuccess();
        void setTv_errTip1(String err1);//设置用户名错误提示
        void setTv_errTip2(String err2);//设置密码错误提示
        void storeTokenId(String token);
        void storeSession(String session);
    }

    interface Presenter extends BasePresenter{
        void loginAttempt(String userID, String password);//判断用户名和密码是否匹配
        boolean getLoginStatus();
    }
}
