package com.demo.reborn.recoverpassword;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.demo.reborn.R;
import static com.google.common.base.Preconditions.checkNotNull;


public class RecoverPasswordFragment extends Fragment implements RecoverPasswordContract.View {

    private RecoverPasswordContract.Presenter mPresenter;

    private Button bt_submit;
    private Button bt_sendSMS;
    private EditText ed_phone;
    private EditText ed_smsCode;
    private TextView tv_errTip;

    private String phoneNumber;
    private String SMScode;

    public static RecoverPasswordFragment newInstance() { return new RecoverPasswordFragment(); }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    public void setPresenter(RecoverPasswordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void retrieveDate(){
        phoneNumber = ed_phone.getText().toString();
        SMScode = ed_smsCode.getText().toString();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_recover_password,container, false);
        ed_phone = root.findViewById(R.id.rp_phoneNumber);
        ed_smsCode = root.findViewById(R.id.rp_SMS);
        bt_submit = root.findViewById(R.id.rp_submit);
        bt_sendSMS = root.findViewById(R.id.rp_sendSMS);
        tv_errTip = root.findViewById(R.id.rp_errTip);

        bt_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                retrieveDate();
                if(phoneNumber.matches("^1(3|4|5|7|8)\\d{9}$")){
                    if(!SMScode.isEmpty()){
                        //todo:click jump
                        tv_errTip.setText("");
                    }else{
                        tv_errTip.setText("验证码不能为空");
                    }
                }else{
                    tv_errTip.setText("请输入正确的手机号码");
                }
            }
        });

        bt_sendSMS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bt_sendSMS.setEnabled(false);
                mPresenter.getSmsCode();
            }
        });
        return root;
    }

}
