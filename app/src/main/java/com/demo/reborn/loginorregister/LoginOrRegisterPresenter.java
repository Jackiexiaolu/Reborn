package com.demo.reborn.loginorregister;


public class LoginOrRegisterPresenter implements LoginOrRegisterContract.Presenter {

    private final LoginOrRegisterContract.View mView;

    public LoginOrRegisterPresenter(LoginOrRegisterContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
