package com.demo.reborn.financialintelligence;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.homepage.HomePageActivity;

import java.text.SimpleDateFormat;

import static com.google.common.base.Preconditions.checkNotNull;

public class FinancialIntelligenceFragment extends Fragment implements FinancialIntelligenceContract.View{

    private FinancialIntelligenceContract.Presenter mPresenter;

    private EditText et_financialIntelligence_companyName;
    private EditText et_financialIntelligence_fundingFacility;
    private EditText et_financialIntelligence_capitalManager;
    private EditText et_financialIntelligence_capitalIssuer;
    private EditText et_financialIntelligence_depositFacility;
    private EditText et_financialIntelligence_creditAmount;
    private EditText et_financialIntelligence_financingAmount;
    private EditText et_financialIntelligence_price;
    private EditText et_financialIntelligence_expireTime;

    private TextView tv_financialIntelligence_homePage;

    private ImageView iv_financialIntelligence_backIcon;

    private String companyName;
    private String fundingFacility;
    private String capitalManager;
    private String capitalIssuer;
    private String depositFacility;
    private String creditAmount;
    private String financingAmount;
    private String price;
    private String expireTime;
    private String insurance;
    private String mainArticle;

    private Button bt_financialIntelligence_save;
    private Button bt_financialIntelligence_submit;

    private Spinner sp_financialIntelligence_insurance;
    private Spinner sp_financialIntelligence_mainArticle;
    private Spinner sp_financialIntelligence_selectProduct;

    private String[] insuranceDropItems = new String[]{};
    private String[] mainArticleDropItems = new String[]{};
    private String[] productDropItems = new String[]{};

    private int saveOrModify = 0;


    public static FinancialIntelligenceFragment newInstance(){return new FinancialIntelligenceFragment();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_financial_intelligence, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        et_financialIntelligence_companyName = root.findViewById(R.id.et_financialIntelligence_companyName);
        et_financialIntelligence_fundingFacility = root.findViewById(R.id.et_financialIntelligence_fundingFacility);
        et_financialIntelligence_capitalManager = root.findViewById(R.id.et_financialIntelligence_capitalManager);
        et_financialIntelligence_capitalIssuer = root.findViewById(R.id.et_financialIntelligence_capitalIssuer);
        et_financialIntelligence_depositFacility = root.findViewById(R.id.et_financialIntelligence_depositFacility);
        et_financialIntelligence_creditAmount = root.findViewById(R.id.et_financialIntelligence_creditAmount);
        et_financialIntelligence_financingAmount = root.findViewById(R.id.et_financialIntelligence_financingAmount);
        et_financialIntelligence_price = root.findViewById(R.id.et_financialIntelligence_price);
        et_financialIntelligence_expireTime = root.findViewById(R.id.et_financialIntelligence_expireTime);

        tv_financialIntelligence_homePage = root.findViewById(R.id.tv_financialIntelligence_homepage);
        iv_financialIntelligence_backIcon = root.findViewById(R.id.iv_financialIntelligence_backIcon);

        sp_financialIntelligence_insurance = root.findViewById(R.id.sp_financialIntelligence_insurance);
        sp_financialIntelligence_mainArticle = root.findViewById(R.id.sp_financialIntelligence_mainArticle);
        sp_financialIntelligence_selectProduct = root.findViewById(R.id.sp_financialIntelligence_selectProduct);
        bt_financialIntelligence_save = root.findViewById(R.id.bt_financialIntelligence_save);
        bt_financialIntelligence_submit = root.findViewById(R.id.bt_financialIntelligence_submit);

        mPresenter.setInsurance();
        mPresenter.setMainArticle();
        mPresenter.setProduct();
        ArrayAdapter<String> insuranceAdapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_financial_intelligence_spinner_item,insuranceDropItems);
        ArrayAdapter<String> mainArticleAdapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_financial_intelligence_spinner_item,mainArticleDropItems);
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(getContext(),R.layout.fragment_financial_intelligence_spinner_item,productDropItems);
        sp_financialIntelligence_insurance.setAdapter(insuranceAdapter);
        sp_financialIntelligence_selectProduct.setAdapter(mainArticleAdapter);
        sp_financialIntelligence_mainArticle.setAdapter(productAdapter);
        bt_financialIntelligence_submit.setEnabled(false);
        
        bt_financialIntelligence_save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(saveOrModify == 0) {
                    saveClicked();
                } else{
                    modifyClicked();
                    bt_financialIntelligence_submit.setEnabled(false);
                }
            }
        });

        tv_financialIntelligence_homePage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),HomePageActivity.class);
                startActivity(intent);
            }
        });

        iv_financialIntelligence_backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        bt_financialIntelligence_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.finalSubmit();
            }
        });
        return root;
    }

    public void setInsuranceDropItems(String[] insurance){
        insuranceDropItems = insurance;
    }

    public void setMainArticleDropItems(String[] mainArticle){
        mainArticleDropItems = mainArticle;
    }

    public void setProductDropItems(String[] product){
        productDropItems = product;
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
    public void setPresenter(FinancialIntelligenceContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void retrieveData(){
        companyName = et_financialIntelligence_companyName.getText().toString();
        fundingFacility = et_financialIntelligence_fundingFacility.getText().toString();
        capitalManager = et_financialIntelligence_capitalManager.getText().toString();
        capitalIssuer = et_financialIntelligence_capitalIssuer.getText().toString();
        depositFacility = et_financialIntelligence_depositFacility.getText().toString();
        creditAmount = et_financialIntelligence_creditAmount.getText().toString();
        financingAmount = et_financialIntelligence_financingAmount.getText().toString();
        price = et_financialIntelligence_price.getText().toString();
        expireTime = et_financialIntelligence_expireTime.getText().toString();
        insurance = sp_financialIntelligence_insurance.getSelectedItem().toString();
        mainArticle = sp_financialIntelligence_mainArticle.getSelectedItem().toString();
    }

    public void saveClicked(){
        retrieveData();
        if(validate(companyName) && validate(fundingFacility) && validate(capitalManager) && validate(capitalIssuer) &&
                validate(depositFacility) && validate(creditAmount) && validate(financingAmount) && validate(price) &&
                validate(expireTime)){
            Log.v("saved!","");
            et_financialIntelligence_capitalIssuer.setEnabled(false);
            et_financialIntelligence_capitalManager.setEnabled(false);
            et_financialIntelligence_companyName.setEnabled(false);
            et_financialIntelligence_creditAmount.setEnabled(false);
            et_financialIntelligence_depositFacility.setEnabled(false);
            et_financialIntelligence_expireTime.setEnabled(false);
            et_financialIntelligence_financingAmount.setEnabled(false);
            et_financialIntelligence_fundingFacility.setEnabled(false);
            et_financialIntelligence_price.setEnabled(false);
            sp_financialIntelligence_insurance.setEnabled(false);
            sp_financialIntelligence_mainArticle.setEnabled(false);
            sp_financialIntelligence_selectProduct.setEnabled(false);
            bt_financialIntelligence_save.setText("修改");
            bt_financialIntelligence_submit.setEnabled(true);
            saveOrModify = 1;
        }

        if(!validate(companyName)){
           et_financialIntelligence_companyName.setHint("此项不能为空");
        }else{
            et_financialIntelligence_companyName.setHint("");
        }

        if(!validate(fundingFacility)){
            et_financialIntelligence_fundingFacility.setHint("此项不能为空");
        }else{
            et_financialIntelligence_fundingFacility.setHint("");
        }

        if(!validate(capitalManager)){
            et_financialIntelligence_capitalManager.setHint("此项不能为空");
        }else{
            et_financialIntelligence_capitalManager.setHint("");
        }

        if(!validate(capitalIssuer)){
            et_financialIntelligence_capitalIssuer.setHint("此项不能为空");
        }else{
            et_financialIntelligence_capitalIssuer.setHint("");
        }

        if(!validate(depositFacility)){
            et_financialIntelligence_depositFacility.setHint("此项不能为空");
        }else{
            et_financialIntelligence_depositFacility.setHint("");
        }

        if(!validate(creditAmount)){
            et_financialIntelligence_creditAmount.setHint("此项不能为空");
        }else{
            et_financialIntelligence_creditAmount.setHint("");
        }

        if(!validate(financingAmount)){
            et_financialIntelligence_financingAmount.setHint("此项不能为空");
        }else{
            et_financialIntelligence_financingAmount.setHint("");
        }

        if(!validate(price)){
            et_financialIntelligence_price.setHint("此项不能为空");
        }else{
            et_financialIntelligence_price.setHint("");
        }

        if(!validate(expireTime)){
            et_financialIntelligence_expireTime.setHint("此项不能为空");
        }else{
            et_financialIntelligence_expireTime.setHint("");
        }
    }

    public void modifyClicked(){
        Log.v("modified!","");
        et_financialIntelligence_capitalIssuer.setEnabled(true);
        et_financialIntelligence_capitalManager.setEnabled(true);
        et_financialIntelligence_companyName.setEnabled(true);
        et_financialIntelligence_creditAmount.setEnabled(true);
        et_financialIntelligence_depositFacility.setEnabled(true);
        et_financialIntelligence_expireTime.setEnabled(true);
        et_financialIntelligence_financingAmount.setEnabled(true);
        et_financialIntelligence_fundingFacility.setEnabled(true);
        et_financialIntelligence_price.setEnabled(true);
        sp_financialIntelligence_insurance.setEnabled(true);
        sp_financialIntelligence_mainArticle.setEnabled(true);
        sp_financialIntelligence_selectProduct.setEnabled(true);
        bt_financialIntelligence_save.setText("保存");
        saveOrModify = 0;
    }

    public boolean validate(String data){
        if(data.isEmpty()) return false;
        else return true;
    }

    public void clickJump(){
        Intent intent = new Intent(getContext(),HomePageActivity.class);
        startActivity(intent);
    }

    public void testM(){
        Log.v("CompanyName: ",companyName);
        Log.v("CapitalIssuer: ",capitalIssuer);
        Log.v("MainArticle: ",mainArticle);
    }
}
