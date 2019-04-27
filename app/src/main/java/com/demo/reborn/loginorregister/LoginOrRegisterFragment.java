package com.demo.reborn.loginorregister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

import com.demo.reborn.R;
import com.demo.reborn.loginorregister.LoginOrRegisterContract;
import com.demo.reborn.loginpage.LoginPageActivity;
import com.demo.reborn.preregisterpage.PreRegisterPageActivity;
import com.demo.reborn.registerpage.RegisterPageActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginOrRegisterFragment extends Fragment implements LoginOrRegisterContract.View {

    private LoginOrRegisterContract.Presenter mPresenter;

    private Button bt_login;
    private Button bt_register;


    public static LoginOrRegisterFragment newInstance(){ return new LoginOrRegisterFragment(); }

    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_lgn_reg, container, false);
        bt_login = root.findViewById(R.id.lr_lgnButton);
        bt_register = root.findViewById(R.id.lr_regButton);

        bt_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext(),LoginPageActivity.class); //待修改
                startActivity(intent);
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getContext(),PreRegisterPageActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void setPresenter(LoginOrRegisterContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }
}
