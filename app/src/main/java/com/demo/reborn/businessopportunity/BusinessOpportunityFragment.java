package com.demo.reborn.businessopportunity;

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
import android.widget.TextView;

import com.demo.reborn.MyTable;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.companydetail.CompanyDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class BusinessOpportunityFragment extends Fragment implements BusinessOpportunityContract.View  {

    private BusinessOpportunityContract.Presenter mPresenter;

    private final int RED = 0xFFE63C28;
    private final int DARKGREY = 0xffa0a0a0;
    private final int GREY = 0xffe0e0e0;
    private int densityDPI;
    private int screenWidth;
    private int screenHeight;
    private ImageView iv_stockFinancingDetail_Back;
    private TextView tv_stockFinancingDetail_Title;
    private int columnNum = 0;
    private LinearLayout ll_stockFinancingDetail;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static BusinessOpportunityFragment newInstance() {
        return new BusinessOpportunityFragment();
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
        View root = inflater.inflate(R.layout.fragment_business_opportunity, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        densityDPI  = getResources().getDisplayMetrics().densityDpi;
        screenWidth  = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        iv_stockFinancingDetail_Back = root.findViewById(R.id.iv_stockFinancingDetail_Back);
        tv_stockFinancingDetail_Title = root.findViewById(R.id.tv_stockFinancingDetail_Title);
        ll_stockFinancingDetail = root.findViewById(R.id.ll_stockFinancingDetail);

        iv_stockFinancingDetail_Back.setOnClickListener(view -> getActivity().finish());

        String tag = mPresenter.getBusinessOpportunityTag();

        tv_stockFinancingDetail_Title.setText(tag);
        mPresenter.getInfo(tag);
        return root;
    }

    @Override
    public void setPresenter(BusinessOpportunityContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setTitle(String title){
        LinearLayout linearLayout = new LinearLayout(ll_stockFinancingDetail.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView rectangle = new TextView(ll_stockFinancingDetail.getContext());
        rectangle.setBackgroundColor(RED);
        rectangle.setTextSize(17);
        linearLayout.addView(rectangle, getPixels(3), LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams rectangleParams = (LinearLayout.LayoutParams) rectangle.getLayoutParams();
        rectangleParams.setMargins(getPixels(20), getPixels(20), 0, getPixels(10));
        rectangle.setLayoutParams(rectangleParams);

        TextView textView = new TextView(ll_stockFinancingDetail.getContext());
        textView.setTextSize(17);
        textView.setTextColor(0xFF000000);
        textView.setText(title);
        linearLayout.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textViewParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textViewParams.setMargins(getPixels(7), getPixels(20), 0, getPixels(10));
        textView.setLayoutParams(textViewParams);

        ll_stockFinancingDetail.addView(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setParagraph(String paragraph){
        TextView textView = new TextView(ll_stockFinancingDetail.getContext());
        textView.setText(paragraph);
        textView.setTextSize(17);
        textView.setTextColor(DARKGREY);
        ll_stockFinancingDetail.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        textView.setLayoutParams(params);
    }

    @Override
    public void setPartEnd(){
        TextView divider = new TextView(ll_stockFinancingDetail.getContext());
        divider.setBackgroundColor(GREY);
        ll_stockFinancingDetail.addView(divider, LinearLayout.LayoutParams.MATCH_PARENT, getPixels(1));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) divider.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        params.setMargins(0,getPixels(20), 0, 0);
        divider.setLayoutParams(params);
    }

    @Override
    public void setChartColumn(int num){
        columnNum = num;
    }

    @Override
    public void setChart(ArrayList<String> strings){
        LinearLayout linearLayout = new LinearLayout(ll_stockFinancingDetail.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int width = screenWidth - getPixels(40);
        MyTable mt = new MyTable(linearLayout, width);
        mt.setColumn(columnNum);
        int weight[] = new int[columnNum];
        for(int i = 0; i < columnNum; i++) {
            weight[i] = 1;
        }
        mt.setWeight(weight);
        for(int i = 0; i < strings.size(); i++){
            mt.add(strings.get(i));
        }
        ll_stockFinancingDetail.addView(linearLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        params.setMargins(0, getPixels(10), 0, getPixels(10));
        linearLayout.setLayoutParams(params);
    }

    @Override
    public void setClickableChart(ArrayList<String> strings) {
        LinearLayout linearLayout = new LinearLayout(ll_stockFinancingDetail.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int width = screenWidth - getPixels(40);
        MyTable mt = new MyTable(linearLayout, width);
        mt.setColumn(columnNum);
        int weight[] = new int[columnNum];
        for(int i = 0; i < columnNum; i++) {
            weight[i] = 1;
        }
        mt.setWeight(weight);
        mt.add(strings.get(0));
        mt.add(strings.get(1)); //由于后面的内容以3个一循环，这里先将两个表头提取出来
        for(int i = 1; i < strings.size() - 1; i++){
            if(i % 3 == 0) {
                String id = strings.get(i + 1);
                mt.rowUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.setCompanyId(id);
                        Intent intent = new Intent(getContext(), CompanyDetailActivity.class);
                        intent.putExtra("favourite", "0");
                        startActivity(intent);
                    }
                });
            }
            else {
                mt.add(strings.get(i + 1));
            }
        }
        ll_stockFinancingDetail.addView(linearLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        params.setMargins(0, getPixels(10), 0, getPixels(10));
        linearLayout.setLayoutParams(params);
    }

    private int getPixels(int dp){
        return dp * densityDPI / 160;
    }

//    @Override
//    public void setWorkingCapitalLoan(String paragraph){
//        tv_stockFinancingDetail_workingCapitalLoan.setText(paragraph);
//        tv_stockFinancingDetail_workingCapitalLoan.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setProjectFinancing(String paragraph){
//        tv_businessOpportunity_projectFinancing.setText(paragraph);
//        tv_businessOpportunity_projectFinancing.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setBankAcceptanceBill(String paragraph){
//        tv_businessOpportunity_bankAcceptanceBill.setText(paragraph);
//        tv_businessOpportunity_bankAcceptanceBill.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setFinancialLeaseAndLeaseFactoring(String paragraph){
//        tv_businessOpportunity_financialLeaseAndLeaseFactoring.setText(paragraph);
//        tv_businessOpportunity_financialLeaseAndLeaseFactoring.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setBondFinancing(String paragraph){
//        tv_businessOpportunity_bondFinancing.setText(paragraph);
//        tv_businessOpportunity_bondFinancing.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setAssetSecuritizationFinancing(String paragraph){
//        tv_businessOpportunity_assetSecuritizationFinancing.setText(paragraph);
//        tv_businessOpportunity_assetSecuritizationFinancing.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setMergerAndAcquisitionLoan(String paragraph){
//        tv_businessOpportunity_mergerAndAcquisitionLoan.setText(paragraph);
//        tv_businessOpportunity_mergerAndAcquisitionLoan.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setFinancialAdviser(String paragraph){
//        tv_businessOpportunity_financialAdviser.setText(paragraph);
//        tv_businessOpportunity_financialAdviser.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setStockIncrease(String paragraph){
//        tv_businessOpportunity_stockIncrease.setText(paragraph);
//        tv_businessOpportunity_stockIncrease.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setSecuritiesManagementPlan(String paragraph){
//        tv_businessOpportunity_securitiesManagementPlan.setText(paragraph);
//        tv_businessOpportunity_securitiesManagementPlan.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setInsuranceInformationManagementPlan(String paragraph){
//        tv_businessOpportunity_insuranceInformationManagementPlan.setText(paragraph);
//        tv_businessOpportunity_insuranceInformationManagementPlan.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setTrustManagementPlan(String paragraph){
//        tv_businessOpportunity_trustManagementPlan.setText(paragraph);
//        tv_businessOpportunity_trustManagementPlan.setBackgroundColor(0xFFFFFF);
//    }
//
//    @Override
//    public void setEquityPledgeFinancing(String paragraph){
//        tv_businessOpportunity_equityPledgeFinancing.setText(paragraph);
//        tv_businessOpportunity_equityPledgeFinancing.setBackgroundColor(0xFFFFFF);
//    }
}
