package com.demo.reborn.recoverpassword;

public class RecoverPasswordPresenter implements RecoverPasswordContract.Presenter {

    private final RecoverPasswordContract.View mView;

    public RecoverPasswordPresenter(RecoverPasswordContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
    public void getSmsCode(){

    }
}
