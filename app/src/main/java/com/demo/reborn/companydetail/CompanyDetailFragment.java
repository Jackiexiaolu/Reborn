package com.demo.reborn.companydetail;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportActivity;
import com.demo.reborn.opportunityabstract.OpportunityAbstractActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

public class CompanyDetailFragment extends Fragment implements CompanyDetailContract.View{
    private CompanyDetailContract.Presenter mPresenter;

    private TextView tv_companyDetail_companyName;
    private ImageView imageView;
    private ImageView iv_companyDetail_icon;
    private TextView tv_companyDetail_holderDetail;
    private TextView tv_companyDetail_legalPersonDetail;
    private TextView tv_companyDetail_financialReport;
    private TextView tv_companyDetail_businessOpportunity;
    private ImageView iv_companyDetail_favourite;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    int favourite = 0;

    public static CompanyDetailFragment newInstance() {
        return new CompanyDetailFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        startTime = df.format(new Date());
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
        endTime = df.format(new Date());
        mPresenter.setBehavior(startTime,endTime);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_company_detail, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        tv_companyDetail_companyName = root.findViewById(R.id.tv_companyDetail_companyName);
        imageView = root.findViewById(R.id.imageView);
        iv_companyDetail_icon = root.findViewById(R.id.iv_companyDetail_icon);
        tv_companyDetail_holderDetail = root.findViewById(R.id.tv_companyDetail_holderDetail);
        tv_companyDetail_legalPersonDetail = root.findViewById(R.id.tv_companyDetail_legalPersonDetail);
        tv_companyDetail_financialReport = root.findViewById(R.id.tv_companyDetail_financialReport);
        tv_companyDetail_businessOpportunity = root.findViewById(R.id.tv_companyDetail_businessOpportunity);
        iv_companyDetail_favourite = root.findViewById(R.id.iv_companyDetail_favourite);

        iv_companyDetail_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("companyName", tv_companyDetail_companyName.getText().toString());
                map.put("legalPerson", tv_companyDetail_legalPersonDetail.getText().toString());
                map.put("c_id", mPresenter.getCompanyId());
                if (favourite == 0) {//未收藏
                    mPresenter.postAddCollect(tv_companyDetail_companyName.getText().toString(), "ANDROID", iv_companyDetail_favourite, map);
                } else {
                    mPresenter.postCancelCollect(tv_companyDetail_companyName.getText().toString(), "ANDROID", iv_companyDetail_favourite, map);
                }
            }
        });

        imageView.setOnClickListener(view -> Objects.requireNonNull(getActivity()).finish());

        tv_companyDetail_financialReport.setOnClickListener(v -> {
            navigationBar.setStatus(2);
            Intent intentFinancial = new Intent(getContext(), FinancialReportActivity.class);
            startActivity(intentFinancial);
        });

        tv_companyDetail_businessOpportunity.setOnClickListener(v -> {
            navigationBar.setStatus(3);
            Intent intentBusiness = new Intent(getContext(), OpportunityAbstractActivity.class);
            startActivity(intentBusiness);
        });


        mPresenter.getInfo();
        return root;
    }

    @Override
    public void setPresenter(CompanyDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setCompanyName(String name){
        tv_companyDetail_companyName.setText(name);
        tv_companyDetail_financialReport.setText(name + "融报");
        tv_companyDetail_businessOpportunity.setText(name + "商机");
    }

    @Override
    public void setCompanyImage(){

    }

    @Override
    public void setFavourite(String favourite){
        if(favourite.equals("1")){
            iv_companyDetail_favourite.setImageResource(R.drawable.ic_star_solid);
        }
    }

    @Override
    public void setShareholder(String name){
        tv_companyDetail_holderDetail.setText(name);
    }

    @Override
    public void setLegalPerson(String name){
        tv_companyDetail_legalPersonDetail.setText(name);
    }

    //错误提示
    @Override
    public void errorShow(){
        new AlertDialog.Builder(getContext())
                .setTitle("错误提示")
                .setMessage("请求出错，请重试。")
                .setPositiveButton("确定", null)
                .show();
    }

    public void loginFirst(){
        Toast.makeText(getContext(),"请先登录！",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void collectSuccess(ImageView imageView, Map<String,Object> map){
        Toast.makeText(getContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_solid);
        map.put("flag","1");
        favourite = 1;


    }

    @Override
    public void collectFailure(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "收藏失败！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_border);
        map.put("flag","0");
        favourite = 0;


    }

    @Override
    public void cancelSuccess(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "取消收藏成功！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_border);
        map.put("flag","0");
        favourite = 0;

    }

    @Override
    public void cancelFailure(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "取消收藏失败！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_solid);
        map.put("flag","1");
        favourite = 1;

    }
}
