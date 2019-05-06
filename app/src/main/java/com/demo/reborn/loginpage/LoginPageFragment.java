package com.demo.reborn.loginpage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.R;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.loginorregister.LoginOrRegisterActivity;
import com.demo.reborn.passwordrecovery.PasswordRecoveryActivity;
import com.demo.reborn.personalcenter.PersonalCenterActivity;
import com.demo.reborn.preregisterpage.PreRegisterPageActivity;
import com.demo.reborn.recoverpassword.RecoverPasswordActivity;
import com.demo.reborn.resetpassword.ResetPasswordActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPageFragment extends Fragment implements LoginPageContract.View {

    private LoginPageContract.Presenter mPresenter;

    private EditText ed_userID;         //用户名输入区域
    private EditText ed_password;       //密码输入区域
    private Button bt_login;            //登陆按钮
    private Button bt_register;         //注册按钮
    private TextView tv_errTip1;        //用户名错误提示
    private TextView tv_errTip2;        //密码错误提示
    private TextView tv_forgetPassword; //忘记密码
    private String userID;              //读取用户名输入区域中的字符串
    private String password;            //读取密码输入区域中的字符串

    private SharedPreferences userToken;
    private Context ctx;

    public static LoginPageFragment newInstance(){ return new LoginPageFragment(); }

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


    public void storeTokenId(String token){
        SharedPreferences.Editor tokenEditor = userToken.edit();
        tokenEditor.putString("token-id",token);
        tokenEditor.apply();
    }

    public void storeSession(String session){
        SharedPreferences.Editor sessionEditor = userToken.edit();
        sessionEditor.putString("session",session);
        sessionEditor.apply();
    }

    //设置用户名错误提示
    public void setTv_errTip1(String err1){
        tv_errTip1.setText(err1);
    }
    //设置密码错误提示
    public void setTv_errTip2(String err2){
        tv_errTip2.setText(err2);
    }

    public void loginSuccess(){
        Log.e("token",userToken.getString("uniqueToken","none"));
        Log.e("session ",userToken.getString("session","none"));
        Intent intent = new Intent(getContext(),HomePageActivity.class); //TODO actual jump destination needed
        startActivity(intent);
        Toast.makeText(getContext(),"登陆成功！",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        if(mPresenter.getLoginStatus()){
            Intent intentPersonalCenter = new Intent(getContext(),PersonalCenterActivity.class);
            startActivity(intentPersonalCenter);
            getActivity().finish();
        }
        View root = inflater.inflate(R.layout.fragment_login_page,container, false);

        ctx = root.getContext();
        userToken = ctx.getSharedPreferences("userToken",MODE_PRIVATE);

        ed_userID = root.findViewById(R.id.lgn_ID);
        ed_password = root.findViewById(R.id.lgn_password);
        bt_login = root.findViewById(R.id.lgn_loginButton);
        bt_register = root.findViewById(R.id.lgn_registerButton);
        tv_errTip1 = root.findViewById(R.id.lgn_errTip1);
        tv_errTip2 = root.findViewById(R.id.lgn_errTip2);
        tv_forgetPassword = root.findViewById(R.id.lgn_forgetPassword);
        bt_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userID = ed_userID.getText().toString();
                password = ed_password.getText().toString();
               if(userID.matches("^1(3|4|5|7|8)\\d{9}$")){ //TODO more accurate regular expression needed
                    if(!password.isEmpty()){
                        mPresenter.loginAttempt(userID,password);
                    }else{
                        setTv_errTip1("");
                        setTv_errTip2("密码不能为空");
                    }
                }else{
                    setTv_errTip1("请输入正确的手机号码");
                    setTv_errTip2("");
                }
//                String phoneNumber = "13811009092";
//                String img_code = "213";
//                String message_code = "13213";


//                FinancialDataRepository.INSTANCE.getSmsCheckCode(phoneNumber,img_code,message_code).subscribe(new Observer<Object>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Object responseBodyCall) {
//                        Log.e("getBody", responseBodyCall.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("getBody", e.getMessage());
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });;


            }
        });

        tv_forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PasswordRecoveryActivity.class);
                startActivity(intent);
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PreRegisterPageActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }



    @Override
    public void setPresenter(LoginPageContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
