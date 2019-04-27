package com.demo.reborn.registerpage;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.R;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.loginorregister.LoginOrRegisterActivity;
import com.demo.reborn.personalcenter.PersonalCenterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegisterPageFragment extends Fragment implements RegisterPageContract.View{

    private RegisterPageContract.Presenter mPresenter;

    private EditText et_registerPage_phoneNumber;
    private EditText et_registerPage_financialFacility;
    private EditText et_registerPage_email;
    private EditText et_registerPage_duty;
    private EditText et_registerPage_name;
    private ListView lv_registerPage_searchTip;
    private Spinner sp_registerPage_department;
    private Spinner sp_registerPage_position;
    private ImageView iv_registerPage_backIcon;
    private boolean stopTipping = true;

    private Button bt_submitReg;

    private String phoneNumber;
    private String email;
    private String name;
    private String financialFacility;
    private String duty;
    private String department;
    private String position;

    private String[] departmentDropItems = {"金融市场部","资产管理部","投资银行部","公司业务部","战略客户部（大客户部）","国际业务部"};
    private String[] positionDropItems = {"总经理","副总经理","处长","副处长","行长","副行长","客户经理","经理","高级经理","副董事","董事","执行董事","董事总经理"};
    private Intent getSource;
    private String source;
    private boolean clearToSend = false;

    public static RegisterPageFragment newInstance(){ return new RegisterPageFragment(); }

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_register_page,container, false);
        getSource = getActivity().getIntent();
        et_registerPage_phoneNumber = root.findViewById(R.id.et_registerPage_phoneNumber);
        et_registerPage_email = root.findViewById(R.id.et_registerPage_email);
        et_registerPage_name = root.findViewById(R.id.et_registerPage_name);
        et_registerPage_financialFacility = root.findViewById(R.id.et_registerPage_financialFacility);
        et_registerPage_duty = root.findViewById(R.id.et_registerPage_duty);
        lv_registerPage_searchTip = root.findViewById(R.id.lv_registerPage_searchTip);
        sp_registerPage_department = root.findViewById(R.id.sp_registerPage_department);
        sp_registerPage_position = root.findViewById(R.id.sp_registerPage_position);
        iv_registerPage_backIcon = root.findViewById(R.id.iv_registerPage_backIcon);
        bt_submitReg=root.findViewById(R.id.bt_registerPage_submit);

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_financial_intelligence_spinner_item,departmentDropItems);
        ArrayAdapter<String> positionAdapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_financial_intelligence_spinner_item,positionDropItems);
        sp_registerPage_department.setAdapter(departmentAdapter);
        sp_registerPage_position.setAdapter(positionAdapter);
        mPresenter.setSavedInfo();
        iv_registerPage_backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        bt_submitReg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                retrieveText();
                validateData();
                if(clearToSend){
                    mPresenter.submitInformation();
                }
            }
        });

        et_registerPage_phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    getPhoneNumber();
                    if(!validatePhoneNumber()){
                        et_registerPage_phoneNumber.setHint("此项不能为空");
                    }
                }
            }
        });

        et_registerPage_email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    getEmail();
                    if(!validateEmail()){
                        et_registerPage_email.setText("");
                        et_registerPage_email.setHint("邮箱地址格式错误");
                    }
                }
            }
        });

        et_registerPage_financialFacility.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    stopTipping = false;
                }
            }
        });

        et_registerPage_financialFacility.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!stopTipping) {
                    mPresenter.getCompanySearchTip(et_registerPage_financialFacility.getText().toString());
                }else{
                    clearListView();
                }
            }
        });

        lv_registerPage_searchTip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                stopTipping = true;
                ListAdapter adapter = lv_registerPage_searchTip.getAdapter();
                Map<String, String> map = (Map<String, String>) adapter.getItem(position);
                et_registerPage_financialFacility.setText(map.get("companyName"));
            }
        });

        return root;
    }

    /*public boolean validatePassword(){
        return password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{9,16}$");
    }
    public boolean validateConfirmPassword(){
        return confirmPassword.equals(password);
    }*/

    public boolean validateEmail(){
        return email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
    }

    public boolean validatePhoneNumber(){
//        return phoneNumber.matches("^1(3|4|5|7|8)\\d{9}$");
        return !phoneNumber.isEmpty();
    }

    public boolean validateName(){
        return !name.isEmpty();
    }

    public boolean validateFinancialFacility(){
        return !financialFacility.isEmpty();
    }

    public boolean validateDuty(){
        return !duty.isEmpty();
    }

    public void clickJump(){
        Intent intent = new Intent(getContext(),HomePageActivity.class);
        if(getSource != null){
            source = getSource.getStringExtra("from");
//            Toast.makeText(getContext(), source, Toast.LENGTH_SHORT).show();
        }
        if(source.equals("PersonalCenter")){
           Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
           intent.setClass(getContext(),PersonalCenterActivity.class);
        }else if(source.equals("Register")){
            Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
            intent.setClass(getContext(),HomePageActivity.class);
        }

        startActivity(intent);
    }

    public void retrieveText(){
        phoneNumber = et_registerPage_phoneNumber.getText().toString();
        email = et_registerPage_email.getText().toString();
        name = et_registerPage_name.getText().toString();
        financialFacility = et_registerPage_financialFacility.getText().toString();
        duty = et_registerPage_duty.getText().toString();
        department = sp_registerPage_department.getSelectedItem().toString();
        position = sp_registerPage_position.getSelectedItem().toString();
    }

    public void validateData(){
        if(validateEmail() && validatePhoneNumber() && validateName() && validateFinancialFacility() && validateDuty()){
            clearToSend = true;
            et_registerPage_phoneNumber.setHint("");
            et_registerPage_name.setHint("");
            et_registerPage_financialFacility.setHint("");
            et_registerPage_duty.setHint("");
            et_registerPage_email.setHint("");
        }

        if(!validateEmail()){
            clearToSend = false;
            //et_registerPage_email.setTextColor(getResources().getColor(R.color.red));
            et_registerPage_email.setText("");
            //et_registerPage_email.setText("邮箱地址格式错误");
            et_registerPage_email.setHint("邮箱地址格式错误");
            //et_registerPage_email.setTextColor();
        }

        if(!validatePhoneNumber()){
            clearToSend = false;
            //et_registerPage_phoneNumber.setTextColor(getResources().getColor(R.color.red));
//            et_registerPage_phoneNumber.setText("号码格式错误");
            //et_registerPage_phoneNumber.setText("");
            et_registerPage_phoneNumber.setHint("此项不能为空");
        }

        if(!validateName()){
            clearToSend = false;
            et_registerPage_name.setHint("此项不能为空");
        }

        if(!validateFinancialFacility()){
            clearToSend = false;
            et_registerPage_financialFacility.setHint("此项不能为空");
        }

        if(!validateDuty()){
            clearToSend = false;
            et_registerPage_duty.setHint("此项不能为空");
        }

    }

    public void clearListView(){
        lv_registerPage_searchTip.setAdapter(null);
    }

    public void setListTip(List<Map<String, Object>> list){
        if(!et_registerPage_financialFacility.getText().toString().isEmpty()){
            String[] from = {"companyName"};
            int[] to = {R.id.tv_registerPage_listItem};
            SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.fragment_register_page_list_item, from, to);
            lv_registerPage_searchTip.setAdapter(adapter);
        }else{
            clearListView();
        }
    }

    @Override
    public void setPresenter(RegisterPageContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }


    public String getPhoneNumber(){
        phoneNumber = et_registerPage_phoneNumber.getText().toString();
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        et_registerPage_phoneNumber.setText(phoneNumber);
    }

    public String getEmail(){
        email = et_registerPage_email.getText().toString();
        return email;
    }

    public void setEmail(String email){
        et_registerPage_email.setText(email);
    }

    public String getRealName(){
        name = et_registerPage_name.getText().toString();
        return name;
    }

    public void setRealName(String realName){
        et_registerPage_name.setText(realName);
    }

    public String getFinancialFacility(){
        financialFacility = et_registerPage_financialFacility.getText().toString();
        return financialFacility;
    }

    public void setFinancialFacility(String financialFacility){
        et_registerPage_financialFacility.setText(financialFacility);
    }

    public String getDuty(){
        duty = et_registerPage_duty.getText().toString();
        return duty;
    }

    public void setDuty(String duty){
        et_registerPage_duty.setText(duty);
    }

    public String getDepartment(){
        department = sp_registerPage_department.getSelectedItem().toString();
        return department;
    }

    public String getPosition(){
        position = sp_registerPage_position.getSelectedItem().toString();
        return position;
    }
}
