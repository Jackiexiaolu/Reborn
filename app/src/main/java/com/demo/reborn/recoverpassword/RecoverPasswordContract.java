package com.demo.reborn.recoverpassword;

import com.demo.reborn.BasePresenter;
import com.demo.reborn.BaseView;

public interface RecoverPasswordContract {
    interface View extends BaseView<Presenter>{

    }

    interface Presenter extends BasePresenter{
        void getSmsCode();
    }
}
