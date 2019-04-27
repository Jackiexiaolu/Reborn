package com.demo.reborn.preregisterpage;

import android.graphics.Bitmap;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface PreRegisterPageContract {

    interface View extends BaseView<Presenter>{
        String getPhoneNumber();
        String getVerificationCode();
        String getSMSCode();
        String getPassword();
        String getConfirmPassword();
        void setGraphicCode(Bitmap bm);

        void rightValidateCode();
        void errorValidateCode();
        void errorPhoneFormat();
        void errorPhoneExists();
        void errorPasswordFormat();
        void errorConfirmPassword();

        void registerSuccess();
    }

    interface Presenter extends BasePresenter{
        boolean validatePhoneNumber();
        void validateVerificationCode();
        boolean validateSMSCode();
        boolean validatePassword();
        boolean validateConfirmPassword();
        void getGraphicCode();
        void submitRegisteration();
    }
}
