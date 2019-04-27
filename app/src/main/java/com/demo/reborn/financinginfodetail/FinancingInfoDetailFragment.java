package com.demo.reborn.financinginfodetail;

import android.content.Intent;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.demo.reborn.MyTable;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FinancingInfoDetailFragment extends Fragment implements FinancingInfoDetailContract.View {

    private FinancingInfoDetailContract.Presenter mPresenter;
    private TextView topSummary;
    private TextView preList;
    private TextView debtPaymentAbility;
    private TextView operationAbility;
    private TextView profitInfo;
    private TextView futureDevelopment;
    private TextView cashFlow;
    private TextView finalSummary;
    private ImageView imageView;
    private TextView tv_attention;
    private TextView tv_unit;
    private int densityDPI;
    private int screenWidth;
    private int columnNum = 0;

    private LinearLayout financial_info_detail_list;

    String startTime;
    String endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static FinancingInfoDetailFragment newInstance() {
        return new FinancingInfoDetailFragment();
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
        mPresenter.setBehavior(startTime, endTime);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_financing_info_detail, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        topSummary = root.findViewById(R.id.topSummary);
        preList = root.findViewById(R.id.preList);
//        debtPaymentAbility = root.findViewById(R.id.para1);
//        operationAbility = root.findViewById(R.id.para2);
//        profitInfo = root.findViewById(R.id.para3);
//        futureDevelopment = root.findViewById(R.id.para4);
//        cashFlow = root.findViewById(R.id.para5);
//        finalSummary = root.findViewById(R.id.finalSummary);
        financial_info_detail_list = root.findViewById(R.id.financial_info_detail_list);
        tv_attention = root.findViewById(R.id.tv_attention);
        tv_unit = root.findViewById(R.id.tv_unit);

        imageView = root.findViewById(R.id.imageView);
        imageView.setOnClickListener(view -> getActivity().finish());

        densityDPI = getResources().getDisplayMetrics().densityDpi;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        mPresenter.getInfo();
        return root;
    }

    @Override
    public void setPresenter(FinancingInfoDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    //设置顶部情况总结内容
    public void setTopSummary(String topSum) {
        topSummary.setText(topSum);
    }

    //设置合并报表介绍内容
    public void setPreList(String pList) {
        preList.setText(pList);
    }

    //设置表格下方提示
    public void setAttention(String attention) {
        tv_attention.setText(attention);
    }

//    //设置偿债能力分析详细内容
//    public void setDebtPaymentAbility(String debtPayment){
//        debtPaymentAbility.setText(debtPayment);
//    }
//
//    //设置运营能力分析详细内容
//    public void setOperationAbility(String opAbility){
//        operationAbility.setText(opAbility);
//    }
//
//    //设置盈利能力分析详细内容
//    public void setProfitInfo(String profit){
//        profitInfo.setText(profit);
//    }
//
//    //设置发展能力分析详细内容
//    public void setFutureDevelopment(String futureDev){
//        futureDevelopment.setText(futureDev);
//    }
//
//    //设置现金流量分析详细内容
//    public void setCashFlow(String CashFlow){
//        cashFlow.setText(CashFlow);
//    }
//
//    //设置结尾总结内容
//    public void setFinalSummary(String finalSum){
//        finalSummary.setText(finalSum);
//    }

//    public void setTable(List<String> list1, List<String> list2){
//
//        MyTable mt = new MyTable(financial_info_detail_list);
//        mt.setColumn(6);
//        int weight3[] = {1,1,1,1,1,1};
//        mt.setWeight(weight3);
//        for(int i=0;i<list1.size();i++)
//        {
//            mt.add(list1.get(i));
//        }
//        for(int j =0;j<list2.size();j++)
//        {
//            mt.add(list2.get(j));
//        }
//
//    }

    @Override
    public void setChartColumn(int num) {
        columnNum = num;
    }

    @Override
    public void setNewTable(List<String> list) {
        if (list.get(0).equals("")) {
            tv_unit.setVisibility(View.GONE);

        }
        else {
            int width = screenWidth - getPixels(40);
            MyTable mt = new MyTable(financial_info_detail_list, width);
            mt.setColumn(columnNum);
            int weight[] = new int[columnNum];
            for (int i = 0; i < columnNum; i++) {
                weight[i] = 1;
            }
            mt.setWeight(weight);
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        }
    }


    private int getPixels(int dp) {
        return dp * densityDPI / 160;
    }
}
