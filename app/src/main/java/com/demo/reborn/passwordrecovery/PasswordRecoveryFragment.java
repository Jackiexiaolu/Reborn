package com.demo.reborn.passwordrecovery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.reborn.R;
import com.bumptech.glide.Glide;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.resetpassword.ResetPasswordActivity;

import java.io.ByteArrayOutputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class PasswordRecoveryFragment extends Fragment implements PasswordRecoveryContract.View{

    private PasswordRecoveryContract.Presenter mPresenter;
    private EditText et_recoverPassword_phoneNumber;
    private EditText et_recoverPassword_verificationCode;
    private EditText et_recoverPassword_SMS;
    private Button bt_recoverPassword_sendSMS;
    private Button bt_recoverPassword_submit;
    private ImageView rp_graphicCode;
    @Override
    public void setPresenter(PasswordRecoveryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public static PasswordRecoveryFragment newInstance(){ return new PasswordRecoveryFragment(); }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_password_recovery, container, false);
        et_recoverPassword_phoneNumber = root.findViewById(R.id.rp_phoneNumber2);
        et_recoverPassword_verificationCode = root.findViewById(R.id.rp_verificationCode);
        et_recoverPassword_SMS = root.findViewById(R.id.rp_SMS);
        bt_recoverPassword_sendSMS = root.findViewById(R.id.rp_sendSMS);
        bt_recoverPassword_submit = root.findViewById(R.id.rp_submit);
        rp_graphicCode = root.findViewById(R.id.rp_graphicCode);
        mPresenter.getGraphicCode();

        rp_graphicCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.getGraphicCode();
                et_recoverPassword_SMS.setText("");
            }
        });

        bt_recoverPassword_sendSMS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.sendSMS(et_recoverPassword_phoneNumber.getText().toString(),et_recoverPassword_verificationCode.getText().toString());
            }
        });

        bt_recoverPassword_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.submitSMScode(et_recoverPassword_phoneNumber.getText().toString(),et_recoverPassword_SMS.getText().toString());
            }
        });
        return root;
    }

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

    public void setGraphicCode(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(this).load(bytes).into(rp_graphicCode);
    }

    public void wrongGraphicCode(){
        Toast.makeText(getContext(), "图形验证码错误或手机号码不存在", Toast.LENGTH_SHORT).show();
        et_recoverPassword_verificationCode.setText("");
    }

    public void wrongPhoneNumber(){
        Toast.makeText(getContext(), "手机号码不存在", Toast.LENGTH_SHORT).show();
        et_recoverPassword_phoneNumber.setText("");
    }

    public void smsCodeSent(){
        Toast.makeText(getContext(), "发送成功", Toast.LENGTH_SHORT).show();
    }

    public void wrongSMScode(){
        Toast.makeText(getContext(), "短信验证码错误", Toast.LENGTH_SHORT).show();
        et_recoverPassword_SMS.setText("");
    }

    public void setNewPassword(){
        Intent intent = new Intent(getContext(),ResetPasswordActivity.class);
        startActivity(intent);
    }
}
