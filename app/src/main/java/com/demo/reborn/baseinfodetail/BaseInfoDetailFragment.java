package com.demo.reborn.baseinfodetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public class BaseInfoDetailFragment extends Fragment implements BaseInfoDetailContract.View{

    private BaseInfoDetailContract.Presenter mPresenter;
    private TextView tv_baseInfoDetailCompanyName;
    private TextView tv_baseInfoDetailEstablishDate;
    private TextView tv_listedInfo;
    private TextView tv_registeredCapital;
    private TextView tv_legalRepresentative;
    private TextView tv_registeredAddress;
    private TextView tv_mainBusiness;
    private ImageView iv_baseInfoDetailBack;
    private ListView lv_listedInfo;
    private TextView tv_baseInfoDetailGroupName;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @NonNull
    public static BaseInfoDetailFragment newInstance() {return new BaseInfoDetailFragment(); }

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
        View root = inflater.inflate(R.layout.fragment_baseinfo_detail, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        tv_baseInfoDetailCompanyName = root.findViewById(R.id.tv_baseInfoDetailCompanyName);
        tv_baseInfoDetailEstablishDate = root.findViewById(R.id.tv_baseInfoDetailEstablishDate);
        tv_registeredCapital = root.findViewById(R.id.tv_registeredCapital);
        tv_legalRepresentative = root.findViewById(R.id.tv_legalRepresentative);
        tv_registeredAddress = root.findViewById(R.id.tv_registeredAddress);
        tv_mainBusiness = root.findViewById(R.id.tv_mainBusiness);
        iv_baseInfoDetailBack = root.findViewById(R.id.iv_baseInfoDetailBack);
        lv_listedInfo = root.findViewById(R.id.lv_listedInfo);
        iv_baseInfoDetailBack.setOnClickListener(view -> getActivity().finish());
        tv_baseInfoDetailGroupName = root.findViewById(R.id.tv_baseInfoDetailGroupName);
        mPresenter.loadInfo();
        return root;
    }

    @Override
    public void setPresenter(BaseInfoDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setBaseInfoDetailCompanyName(String companyName){
        tv_baseInfoDetailCompanyName.setText(companyName);
        tv_baseInfoDetailCompanyName.setBackgroundColor(0xFFFFFF);

    }

    @Override
    public void setBaseInfoDetailGroupName(String groupName){
        tv_baseInfoDetailGroupName.setText(groupName);
        tv_baseInfoDetailGroupName.setBackgroundColor(0xFFFFFF);

    }

    @Override
    public void setEstablishDate(String date){
        tv_baseInfoDetailEstablishDate.setText(date);
        tv_baseInfoDetailEstablishDate.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setListedInfo(List<Map<String,Object>> shareList){
        String[] from = {"shareShortName", "shareCode"};
        int[] to = {R.id.tv_shareShortName, R.id.tv_shareCode};
        final SimpleAdapter adapter = new SimpleAdapter(getContext(), shareList, R.layout.fragment_baseinfo_detail_item, from, to);
        lv_listedInfo.setAdapter(adapter);
//        tv_listedInfo.setText("股票简称:"+ stockAbbreviation + "  股票代码:"+ stockCode);
//        tv_listedInfo.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setRegisteredCapital(String capital){
        tv_registeredCapital.setText(capital);
        tv_registeredCapital.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setLegalRepresentative(String person){
        tv_legalRepresentative.setText(person);
        tv_legalRepresentative.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setRegisteredAddress(String address){
        tv_registeredAddress.setText(address);
        tv_registeredAddress.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setMainBusiness(String paragraph){
        tv_mainBusiness.setText(paragraph);
        tv_mainBusiness.setBackgroundColor(0xFFFFFF);
    }


}
