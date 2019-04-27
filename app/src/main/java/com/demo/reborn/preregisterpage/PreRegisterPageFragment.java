package com.demo.reborn.preregisterpage;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.reborn.CountDownTimerUtils;
import com.demo.reborn.R;
import com.demo.reborn.registerpage.RegisterPageActivity;

import java.io.ByteArrayOutputStream;

public class PreRegisterPageFragment extends Fragment implements PreRegisterPageContract.View {

    private PreRegisterPageContract.Presenter mPresenter;

    private Button bt_preRegisterPage_submit;
    private TextView tv_preRegisterPage_sendSMS;
    private ImageView iv_preRegisterPage_graphicCode;
    private EditText et_preRegisterPage_phoneNumber;
    private EditText et_preRegisterPage_verificationCode;
    private EditText et_preRegisterPage_smsCode;
    private EditText et_preRegisterPage_password;
    private EditText et_preRegisterPage_confirmPassword;
    private ImageView imageView8;

    private String phoneNumber;
    private String verificationCode;
    private String smsCode;
    private String password;
    private String confirmPassword;

    private boolean clearToSendSms = false;
    private boolean clearToSubmit = true;


    public static PreRegisterPageFragment newInstance(){return new PreRegisterPageFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_pre_register_page,container, false);

        et_preRegisterPage_phoneNumber = root.findViewById(R.id.et_preRegisterPage_phoneNumber);
        et_preRegisterPage_verificationCode = root.findViewById(R.id.et_preRegisterPage_verificationCode);
        et_preRegisterPage_smsCode = root.findViewById(R.id.et_preRegisterPage_smsCode);
        et_preRegisterPage_password = root.findViewById(R.id.et_preRegisterPage_password);
        et_preRegisterPage_confirmPassword = root.findViewById(R.id.et_preRegisterPage_confirmPassword);
        iv_preRegisterPage_graphicCode = root.findViewById(R.id.iv_preRegisterPage_graphicCode);
        bt_preRegisterPage_submit = root.findViewById(R.id.bt_preRegisterPage_submit);
        tv_preRegisterPage_sendSMS = root.findViewById(R.id.tv_preRegisterPage_sendSms);
        imageView8 = root.findViewById(R.id.imageView8);

        mPresenter.getGraphicCode();

        bt_preRegisterPage_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validateInfo()){
                    mPresenter.submitRegisteration();
                }else{

                }
            }
        });

        iv_preRegisterPage_graphicCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.getGraphicCode();
                et_preRegisterPage_smsCode.setTextColor(getResources().getColor(R.color.black));
                et_preRegisterPage_smsCode.setText("");
            }
        });

        tv_preRegisterPage_sendSMS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.validateVerificationCode();
//                if(clearToSendSms){
//
//                }
// else{
//                    et_preRegisterPage_verificationCode.setTextColor(getResources().getColor(R.color.red));
//                    et_preRegisterPage_verificationCode.setText("错误");
//                   // mPresenter.getGraphicCode();
//                }
            }
        });

        et_preRegisterPage_phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    et_preRegisterPage_phoneNumber.setTextColor(getResources().getColor(R.color.black));
                    et_preRegisterPage_phoneNumber.setText("");
                }
            }
        });

        et_preRegisterPage_verificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    et_preRegisterPage_verificationCode.setTextColor(getResources().getColor(R.color.black));
                    et_preRegisterPage_verificationCode.setText("");
                }
            }
        });

        et_preRegisterPage_smsCode.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    et_preRegisterPage_smsCode.setTextColor(getResources().getColor(R.color.black));
                    et_preRegisterPage_smsCode.setText("");
                }
            }
        });

        et_preRegisterPage_password.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    et_preRegisterPage_password.setTextColor(getResources().getColor(R.color.black));
                    et_preRegisterPage_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_preRegisterPage_password.setText("");
                }
            }
        });

        et_preRegisterPage_confirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    et_preRegisterPage_confirmPassword.setTextColor(getResources().getColor(R.color.black));
                    et_preRegisterPage_confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_preRegisterPage_confirmPassword.setText("");
                }
            }
        });

        //bt_preRegisterPage_sendSMS.setOnClickListener(View );

        imageView8.setOnClickListener(view -> getActivity().finish());
        return root;
    }

    @Override
    public void setGraphicCode(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(this).load(bytes).into(iv_preRegisterPage_graphicCode);
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

    @Override
    public void setPresenter(PreRegisterPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public String getPhoneNumber(){
        phoneNumber = et_preRegisterPage_phoneNumber.getText().toString();
        return phoneNumber;
    }

    public String getVerificationCode(){
        verificationCode = et_preRegisterPage_verificationCode.getText().toString();
        return verificationCode;
    }

    public String getSMSCode(){
        smsCode = et_preRegisterPage_smsCode.getText().toString();
        return smsCode;
    }

    public String getPassword(){
        password = et_preRegisterPage_password.getText().toString();
        return password;
    }

    public String getConfirmPassword(){
        confirmPassword = et_preRegisterPage_confirmPassword.getText().toString();
        return confirmPassword;
    }

    public boolean validateInfo(){
        clearToSubmit = true;
        //tv_preRegisterPage_sendSMS.setEnabled(false);
        if (!mPresenter.validatePhoneNumber()){
                et_preRegisterPage_phoneNumber.setTextColor(getResources().getColor(R.color.red));
                et_preRegisterPage_phoneNumber.setText("号码格式错误");
                clearToSubmit = false;
            }else{
                et_preRegisterPage_phoneNumber.setTextColor(getResources().getColor(R.color.black));
            }

        /*if(!mPresenter.validateVerificationCode()){
            et_preRegisterPage_verificationCode.setTextColor(getResources().getColor(R.color.red));
            et_preRegisterPage_verificationCode.setText("图形验证码错误");
        }*/

        if(!mPresenter.validateSMSCode()){
            et_preRegisterPage_smsCode.setTextColor(getResources().getColor(R.color.red));
            et_preRegisterPage_smsCode.setText("短信验证码错误");
            clearToSubmit = false;
            clearToSendSms = false;
        }

        if(!mPresenter.validatePassword()){
            et_preRegisterPage_password.setTextColor(getResources().getColor(R.color.red));
            et_preRegisterPage_password.setInputType(InputType.TYPE_NULL);
            et_preRegisterPage_password.setText("密码需为9-16位字母数字");
            clearToSubmit = false;
        }

        if(!mPresenter.validateConfirmPassword()){
            et_preRegisterPage_confirmPassword.setTextColor(getResources().getColor(R.color.red));
            et_preRegisterPage_confirmPassword.setInputType(InputType.TYPE_NULL);
            et_preRegisterPage_confirmPassword.setText("密码输入不一致");
            clearToSubmit = false;
        }

        return clearToSubmit;
    }

    public void rightValidateCode(){
        //et_preRegisterPage_verificationCode.setText("正确");
        clearToSendSms = true;
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tv_preRegisterPage_sendSMS, 60000, 1000); //倒计时1分钟
        mCountDownTimerUtils.start();
        //tv_preRegisterPage_sendSMS.setEnabled(false);
    }

    public void errorValidateCode(){
        et_preRegisterPage_verificationCode.setTextColor(getResources().getColor(R.color.red));
        et_preRegisterPage_verificationCode.setText("验证码错误");
        clearToSendSms = false;
    }

    public void errorPhoneFormat(){
        et_preRegisterPage_phoneNumber.setTextColor(getResources().getColor(R.color.red));
        et_preRegisterPage_phoneNumber.setText("号码格式错误");
        clearToSubmit = false;
        clearToSendSms = false;
    }

    public void errorPhoneExists(){
        et_preRegisterPage_phoneNumber.setTextColor(getResources().getColor(R.color.red));
        et_preRegisterPage_phoneNumber.setText("此号码已存在");
        clearToSubmit = false;
        clearToSendSms = false;
    }

    public void errorPasswordFormat(){
        et_preRegisterPage_password.setTextColor(getResources().getColor(R.color.red));
        et_preRegisterPage_password.setInputType(InputType.TYPE_NULL);
        et_preRegisterPage_password.setText("密码需为9-16位字母数字");
        clearToSubmit = false;
    }

    public void errorConfirmPassword(){
        et_preRegisterPage_confirmPassword.setTextColor(getResources().getColor(R.color.red));
        et_preRegisterPage_confirmPassword.setInputType(InputType.TYPE_NULL);
        et_preRegisterPage_confirmPassword.setText("密码输入不一致");
        clearToSubmit = false;
    }

    public void registerSuccess(){
        Intent intent = new Intent(getContext(),RegisterPageActivity.class);
        intent.putExtra("from","Register");
        startActivity(intent);
    }
}
