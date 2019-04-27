package com.demo.reborn.resetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.demo.reborn.R;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.reborn.loginpage.LoginPageActivity;
import com.demo.reborn.personalcenter.PersonalCenterContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class ResetPasswordFragment extends Fragment implements ResetPasswordContract.View{
    private ResetPasswordContract.Presenter mPresenter;

    private EditText et_resetPassword_newPassword;
    private EditText et_resetPassword_confirmPassword;
    private Button bt_resetPassword_submit;

    public static ResetPasswordFragment newInstance(){ return new ResetPasswordFragment(); }

    @Override
    public void setPresenter(ResetPasswordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_reset_password, container, false);
        et_resetPassword_confirmPassword = root.findViewById(R.id.snp_newPasswordConfirm);
        et_resetPassword_newPassword = root.findViewById(R.id.snp_newPassword);
        bt_resetPassword_submit = root.findViewById(R.id.snp_submit);

        bt_resetPassword_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.submitNewPassword(et_resetPassword_newPassword.getText().toString(), et_resetPassword_confirmPassword.getText().toString());
            }
        });
        return root;
    }

    public void wrongFormat(){
        Toast.makeText(getContext(), "密码格式错误", Toast.LENGTH_SHORT).show();
        et_resetPassword_newPassword.setText("");
        et_resetPassword_confirmPassword.setText("");
    }

    public void wrongConfirmation(){
        Toast.makeText(getContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
    }

    public void resetSuccess(){
        Toast.makeText(getContext(), "重置成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(),LoginPageActivity.class);
        startActivity(intent);
    }

}
