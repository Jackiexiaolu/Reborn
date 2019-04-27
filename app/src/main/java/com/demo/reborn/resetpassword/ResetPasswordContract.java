package com.demo.reborn.resetpassword;

import android.widget.ImageView;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface ResetPasswordContract {
    interface View extends BaseView<Presenter>{
        void wrongFormat();
        void wrongConfirmation();
        void resetSuccess();
    }

    interface Presenter extends BasePresenter{
        void submitNewPassword(String password, String confirmPassword);
    }
}
