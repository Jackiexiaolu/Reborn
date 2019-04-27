package com.demo.reborn.passwordrecovery;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

import okhttp3.ResponseBody;

public interface PasswordRecoveryContract {
    interface View extends BaseView<Presenter>{
        void setGraphicCode(Bitmap bm);
        void wrongGraphicCode();
        void smsCodeSent();
        void wrongSMScode();
        void setNewPassword();
        void wrongPhoneNumber();
    }

    interface Presenter extends BasePresenter{
        Bitmap apply(@NonNull ResponseBody responseBody);
        void getGraphicCode();
        void sendSMS(String phioneNumber,String graphicCode);
        void submitSMScode(String phoneNumber, String SMScode);
    }
}
