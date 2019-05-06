package com.demo.reborn.capitalraisinginfodetail;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.MyScrollTable;
import com.demo.reborn.MyScrollTableForTrust;
import com.demo.reborn.MyTable;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.demo.reborn.util.MyApplication.getContext;
import static com.google.common.base.Preconditions.checkNotNull;

public class CapitalRaisingInfoDetailFragment extends Fragment implements CapitalRaisingInfoDetailContract.View {

    private CapitalRaisingInfoDetailContract.Presenter mPresenter;
    private TextView tv_groupCapitalRaisingOverviewInfo;
    private TextView tv_blankCreditLineInfo;
    private TextView tv_bondCapitalRaisingInfo;
    private TextView tv_debtInfo;
    private TextView tv_equityFinancingInfo;
    private TextView tv_assetManagementPlanInfo;
    private TextView tv_corporateFinanceOverviewInfo;
    private TextView tv_creditLineInfo;
    private TextView tv_corporateBondInfo;
    private TextView tv_corporateEquityFinancingInfo;
    private TextView tv_corporateDebtInfo;
    private TextView tv_corporateAssetManagementPlanInfo;
    private TextView tv_corporateCFTable;
    private ImageView iv_capitalRaisingBack;
    private LinearLayout tv_bTable;
    private LinearLayout tv_cTable;
    private LinearLayout tv_dTable;
    private LinearLayout tv_cfTable;
    private LinearLayout tv_fTable;
    private LinearLayout tv_corporateCreditTable;
    private LinearLayout tv_corporateBondTable;
    private LinearLayout tv_corporateFinancingTable;
    private LinearLayout tv_corporateDebtTable;
    private LinearLayout tv_corporateCashFlowTable;
    private LineChart mLineChartTotalPrice;//金额折线图
    private LineChart mLinechartPriceRate;//票面利率折线图
    private ProgressDialog progressDialog;
    private LinearLayout ll_tableTrust;
    private LinearLayout ll_trustTable;
    private LinearLayout ll_insuranceTable;
    private LinearLayout ll_tableInsurance;
    private LinearLayout ll_securityTable;
    private LinearLayout ll_tableSecurity;
    private LinearLayout ll_cTableTrust;
    private LinearLayout ll_cTrustTable;
    private LinearLayout ll_cInsuranceTable;
    private LinearLayout ll_cTableInsurance;
    private LinearLayout ll_cSecurityTable;
    private LinearLayout ll_cTableSecurity;



    private TextView tv_corporateCashFlowUnit;
    private TextView tv_corporateCreditUnit;
    private TextView tv_unit;
    private TextView tv_cashFlowTable;
    private TextView tv_cfUnit;
    private ConstraintLayout cl_group;
    private TextView tv_titleTrust;
    private TextView tv_titleInsurance;
    private TextView tv_titleSecurity;
    private TextView tv_titleOfTrust;
    private TextView tv_titleOfInsurance;
    private TextView tv_titleOfSecurity;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @NonNull
    public static CapitalRaisingInfoDetailFragment newInstance() {
        return new CapitalRaisingInfoDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_capital_raising_info_detail, container, false);
        //showLoadingDialog();
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        tv_groupCapitalRaisingOverviewInfo = root.findViewById(R.id.tv_groupCapitalRaisingOverviewInfo);
        tv_blankCreditLineInfo = root.findViewById(R.id.tv_blankCreditLineInfo);
        tv_bondCapitalRaisingInfo = root.findViewById(R.id.tv_bondCapitalRaisingInfo);
        tv_debtInfo = root.findViewById(R.id.tv_debtInfo);
        tv_equityFinancingInfo = root.findViewById(R.id.tv_equityFinancingInfo);
        tv_assetManagementPlanInfo = root.findViewById(R.id.tv_assetManagementPlanInfo);
        tv_corporateFinanceOverviewInfo = root.findViewById(R.id.tv_corporateFinanceOverviewInfo);
        tv_creditLineInfo = root.findViewById(R.id.tv_creditLineInfo);
        tv_corporateBondInfo = root.findViewById(R.id.tv_corporateBondInfo);
        tv_corporateEquityFinancingInfo = root.findViewById(R.id.tv_corporateEquityFinancingInfo);
        tv_corporateDebtInfo = root.findViewById(R.id.tv_corporateDebtInfo);
        tv_corporateAssetManagementPlanInfo = root.findViewById(R.id.tv_corporateAssetManagementPlanInfo);
        tv_bTable = root.findViewById(R.id.tv_bTable);
        tv_cTable = root.findViewById(R.id.tv_cTable);
        tv_dTable = root.findViewById(R.id.tv_dTable);
        tv_cfTable = root.findViewById(R.id.tv_cfTable);
        tv_fTable = root.findViewById(R.id.tv_fTable);
        tv_corporateCreditTable = root.findViewById(R.id.tv_corporateCreditTable);
        tv_corporateBondTable = root.findViewById(R.id.tv_corporateBondTable);
        tv_corporateFinancingTable = root.findViewById(R.id.tv_corporateFinancingTable);
        tv_corporateDebtTable = root.findViewById(R.id.tv_corporateDebtTable);
        tv_corporateCashFlowTable = root.findViewById(R.id.tv_corporateCashFlowTable);
        iv_capitalRaisingBack = root.findViewById(R.id.iv_capitalRaisingBack);
        mLineChartTotalPrice = root.findViewById(R.id.tv_lineChart1);
        mLinechartPriceRate = root.findViewById(R.id.tv_lineChart2);
        ll_tableTrust = root.findViewById(R.id.ll_tableTrust);
        ll_trustTable = root.findViewById(R.id.ll_trustTable);
        ll_insuranceTable = root.findViewById(R.id.ll_insuranceTable);
        ll_tableInsurance = root.findViewById(R.id.ll_tableInsurance);
        ll_securityTable = root.findViewById(R.id.ll_securityTable);
        ll_tableSecurity = root.findViewById(R.id.ll_tableSecurity);
        ll_cTrustTable = root.findViewById(R.id.ll_cTrustTable);
        ll_cTableTrust = root.findViewById(R.id.ll_cTableTrust);
        ll_cTableInsurance = root.findViewById(R.id.ll_cTableInsurance);
        ll_cInsuranceTable = root.findViewById(R.id.ll_cInsuranceTable);
        ll_cSecurityTable = root.findViewById(R.id.ll_cSecurityTable);
        ll_cTableSecurity = root.findViewById(R.id.ll_cTableSecurity);
        tv_corporateCFTable = root.findViewById(R.id.tv_corporateCFTable);
        tv_corporateCashFlowUnit = root.findViewById(R.id.tv_corporateCashFlowUnit);
        tv_corporateCreditUnit = root.findViewById(R.id.tv_corporateCreditUnit);
        tv_unit = root.findViewById(R.id.tv_unit);
        tv_cashFlowTable = root.findViewById(R.id.tv_cashFlowTable);
        tv_cfUnit = root.findViewById(R.id.tv_cfUnit);
        iv_capitalRaisingBack.setOnClickListener(view -> getActivity().finish());
        cl_group = root.findViewById(R.id.cl_group);
        tv_titleTrust = root.findViewById(R.id.tv_titleTrust);
        tv_titleInsurance = root.findViewById(R.id.tv_titleInsurance);
        tv_titleSecurity = root.findViewById(R.id.tv_titleSecurity);
        tv_titleOfTrust = root.findViewById(R.id.tv_titleOfTrust);
        tv_titleOfInsurance = root.findViewById(R.id.tv_titleOfInsurance);
        tv_titleOfSecurity = root.findViewById(R.id.tv_titleOfSecurity);
        mPresenter.getInfo();
        return root;
    }

    @Override
    public void setPresenter(CapitalRaisingInfoDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

//    public void showLoadingDialog() {
//        progressDialog = ProgressDialog.show(getContext(), "融资详情", "加载中，请稍后……");
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        }, 12500);
//    }

    //集团融资情况概览
    @Override
    public void setGroupCapitalRaisingOverviewInfo(String paragraph) {
        tv_groupCapitalRaisingOverviewInfo.setText(paragraph);
        //tv_groupCapitalRaisingOverviewInfo.setBackgroundColor(0xFFFFFF);
    }

    //集团贷款银行的授信情况
    @Override
    public void setGroupCreditInfo(String paragraph) {
        tv_blankCreditLineInfo.setText(paragraph);
        //tv_blankCreditLineInfo.setBackgroundColor(0xFFFFFF);
    }

    //集团债券融资情况
    @Override
    public void setBondCapitalRaisingInfo(String paragraph) {
        tv_bondCapitalRaisingInfo.setText(paragraph);
        //tv_bondCapitalRaisingInfo.setBackgroundColor(0xFFFFFF);
    }

    //集团有息负债情况
    @Override
    public void setDebtInfo(String paragraph) {
        tv_debtInfo.setText(paragraph);
        //tv_liabilityWithInterestInfo.setBackgroundColor(0xFFFFFF);
    }

    //集团股权融资情况
    @Override
    public void setEquityFinancing(String paragraph) {
        tv_equityFinancingInfo.setText(paragraph);
        //tv_equityFinancingInfo.setBackgroundColor(0xFFFFFF);
    }

    //集团资产管理计划情况
    @Override
    public void setAssetManagementPlanInfo(String paragraph) {
        tv_assetManagementPlanInfo.setText(paragraph);
        //tv_assetManagementPlanInfo.setBackgroundColor(0xFFFFFF);
    }

    //企业融资概览
    @Override
    public void setCorporateFinanceOverviewInfo(String paragraph) {
        tv_corporateFinanceOverviewInfo.setText(paragraph);
        //tv_corporateFinanceOverviewInfo.setBackgroundColor(0xFFFFFF);
    }


    //企业授信用信
    @Override
    public void setCorporateCreditInfo(String paragraph) {
        tv_creditLineInfo.setText(paragraph);
        //tv_creditLineInfo.setBackgroundColor(0xFFFFFF);
    }

    //企业债券信息
    public void setCorporateBondInfo(String paragraph) {
        tv_corporateBondInfo.setText(paragraph);
    }

    //企业股权融资
    public void setCorporateEquityFinancingInfo(String paragraph) {
        tv_corporateEquityFinancingInfo.setText(paragraph);
    }

    //企业有息债券
    public void setCorporateDebtInfo(String paragraph) {

        tv_corporateDebtInfo.setText(paragraph);
    }

    //企业资产管理计划情况
    public void setCorporateAssetManagementPlanInfo(String paragraph) {
        tv_corporateAssetManagementPlanInfo.setText(paragraph);
    }


    //集团主要合作情况表格
    @Override
    public void setCreditTable(List<String> list) {
        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_cTable);
            mt.setColumn(6);
            int weight[] = {1, 1, 1, 1, 1,1};
            mt.setWeight(weight);
            mt.add("银行名称");
            mt.add("授信额度");
            mt.add("已使用额度");
            mt.add("未使用额度");
            mt.add("币种");
            mt.add("授信到期日");
            for (int i = 0; i < list.size(); i++) {

                mt.add(list.get(i));
            }
        } else {
            tv_unit.setVisibility(View.GONE);
            tv_cTable.removeAllViews();
        }

    }

    @Override
    public void setBondTable(List<Map<String, String>> list1, List<Map<String, String>> list2, List<Map<String, String>> list3) {
        MyTable mt = new MyTable(tv_bTable);
        mt.setColumn(6);
        int weight[] = {1, 1, 1, 1, 1, 1};
        mt.setWeight(weight);
        mt.add("债务主体");
        mt.add("发行日期");
        mt.add("发行期限（月)");
        mt.add("发行规模");
        mt.add("主承销商");
        mt.add("种类");
        for (int i = 0; i < list1.size(); i++) {
            mt.add(list1.get(i).get("debt_subject"));
            mt.add(list1.get(i).get("list_date"));
            mt.add(list1.get(i).get("deadline"));
            mt.add(list1.get(i).get("total"));
            mt.add(list1.get(i).get("lead_underwriter"));
            mt.add(list1.get(i).get("classify"));

        }
        for (int i = 0; i < list2.size(); i++) {
            mt.add(list2.get(i).get("name"));
            mt.add(list2.get(i).get("date"));
            mt.add(list2.get(i).get("deadline"));
            mt.add(list2.get(i).get("total"));
            mt.add(list2.get(i).get("nothing"));
            mt.add(list2.get(i).get("classify"));

        }
        mt.add(list3.get(0).get("debt_subject"));
        mt.add(list3.get(0).get("list_date"));
        mt.add(list3.get(0).get("deadline"));
        mt.add(list3.get(0).get("total"));
        mt.add(list3.get(0).get("lead_underwriter"));
        mt.add(list3.get(0).get("classify"));

    }

    //有息债券表格
    @Override
    public void setDebtTable(List<String> list,String latest) {

        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_dTable);
            mt.setColumn(5);
            int weight1[] = {1, 2, 2, 2, 2};
            mt.setWeight(weight1);
            mt.add("年份");
            mt.add("2015");
            mt.add("2016");
            mt.add("2017");
            mt.add(latest);
            mt.setColumn(9);
            int weight2[] = {1, 1, 1, 1, 1, 1, 1, 1, 1};
            mt.setWeight(weight2);
            mt.add("");
            for (int i = 0; i < 4; i++) {
                mt.add("金额");
                mt.add("占总负债比例");
            }

            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));

            }
        } else {
            tv_dTable.removeAllViews();

        }
    }

    //现金流表格
    @Override
    public void setCashFlowTable(List<String> list,String latest) {
        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_cfTable);
            mt.setColumn(5);
            int weight[] = {3, 1, 1, 1, 1};
            mt.setWeight(weight);
            mt.add("年份");
            mt.add("2015");
            mt.add("2016");
            mt.add("2017");
            mt.add(latest);
            for (int i = 0; i < list.size(); i++) {

                mt.add(list.get(i));
            }
        } else {
            tv_cfUnit.setVisibility(View.GONE);
            tv_cfTable.removeAllViews();
            tv_cashFlowTable.setVisibility(View.GONE);
        }


    }

    //股权融资表格
    public void setFinancingTable(List<String> list) {
        if(list.size()!=0) {
            MyTable mt = new MyTable(tv_fTable);
            mt.setColumn(8);
            int weight[] = {2, 1, 1, 2, 1, 1, 1, 2};
            mt.setWeight(weight);
            mt.add("上市公司");
            mt.add("上市日期");
            mt.add("发行类型");
            mt.add("上市交易所");
            mt.add("发行股票数量（万股）");
            mt.add("发行价格（元)");
            mt.add("募集金额（亿元)");
            mt.add("主承销商");
            for (int i = 0; i < list.size(); i++) {

                mt.add(list.get(i));
            }
        }else{
            tv_fTable.removeAllViews();
        }
    }

    @Override
    public void setCorporateCreditTable(List<String> list) {
        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_corporateCreditTable);
            mt.setColumn(6);
            int weight[] = {1, 1, 1, 1, 1,1};
            mt.setWeight(weight);
            mt.add("银行名称");
            mt.add("授信额度");
            mt.add("已使用额度");
            mt.add("未使用额度");
            mt.add("币种");
            mt.add("授信到期日");
            for (int i = 0; i < list.size(); i++) {

                mt.add(list.get(i));
            }
        } else {
            tv_corporateCreditUnit.setVisibility(View.GONE);
            tv_corporateCreditTable.removeAllViews();
        }
    }

    @Override
    public void setCorporateBondTable(List<Map<String, String>> list1, List<Map<String, String>> list2, List<Map<String, String>> list3) {
        MyTable mt = new MyTable(tv_corporateBondTable);
        mt.setColumn(6);
        int weight[] = {1, 1, 1, 1, 1, 1};
        mt.setWeight(weight);
        mt.add("债务主体");
        mt.add("发行日期");
        mt.add("发行期限（月)");
        mt.add("发行规模");
        mt.add("主承销商");
        mt.add("种类");
        for (int i = 0; i < list1.size(); i++) {
            mt.add(list1.get(i).get("debt_subject"));
            mt.add(list1.get(i).get("list_date"));
            mt.add(list1.get(i).get("deadline"));
            mt.add(list1.get(i).get("total"));
            mt.add(list1.get(i).get("lead_underwriter"));
            mt.add(list1.get(i).get("classify"));

        }
        for (int i = 0; i < list2.size(); i++) {
            mt.add(list2.get(i).get("name"));
            mt.add(list2.get(i).get("date"));
            mt.add(list2.get(i).get("deadline"));
            mt.add(list2.get(i).get("total"));
            mt.add(list2.get(i).get("nothing"));
            mt.add(list2.get(i).get("classify"));

        }
        mt.add(list3.get(0).get("debt_subject"));
        mt.add(list3.get(0).get("list_date"));
        mt.add(list3.get(0).get("deadline"));
        mt.add(list3.get(0).get("total"));
        mt.add(list3.get(0).get("lead_underwriter"));
        mt.add(list3.get(0).get("classify"));

    }

    @Override
    public void setCorporateFinancingTable(List<String> list) {
        MyTable mt = new MyTable(tv_corporateFinancingTable);
        mt.setColumn(8);
        int weight[] = {2, 1, 1, 2, 1, 1, 1, 2};
        mt.setWeight(weight);
        mt.add("上市公司");
        mt.add("上市日期");
        mt.add("发行类型");
        mt.add("上市交易所");
        mt.add("发行股票数量（万股）");
        mt.add("发行价格（元)");
        mt.add("募集金额（亿元)");
        mt.add("主承销商");
        for (int i = 0; i < list.size(); i++) {

            mt.add(list.get(i));
        }
    }

    @Override
    public void setCorporateDebtTable(List<String> list,String latest) {

        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_corporateDebtTable);
            mt.setColumn(5);
            int weight1[] = {1, 2, 2, 2, 2};
            mt.setWeight(weight1);
            mt.add("年份");
            mt.add("2015");
            mt.add("2016");
            mt.add("2017");
            mt.add(latest);
            mt.setColumn(9);
            int weight2[] = {1, 1, 1, 1, 1, 1, 1, 1, 1};
            mt.setWeight(weight2);
            mt.add("");
            for (int i = 0; i < 4; i++) {
                mt.add("金额");
                mt.add("占总负债比例");
            }

            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));

            }
        } else {
            tv_corporateDebtTable.removeAllViews();
        }
    }

    public void setCorporateCashFlowTable(List<String> list,String latest) {
        if (list.size() != 0) {
            MyTable mt = new MyTable(tv_corporateCashFlowTable);
            mt.setColumn(5);
            int weight[] = {3, 1, 1, 1, 1};
            mt.setWeight(weight);
            mt.add("年份");
            mt.add("2015");
            mt.add("2016");
            mt.add("2017");
            mt.add(latest);
            for (int i = 0; i < list.size(); i++) {

                mt.add(list.get(i));
            }
        } else {
            tv_corporateCFTable.setVisibility(View.GONE);
            tv_corporateCashFlowTable.removeAllViews();
            tv_corporateCashFlowUnit.setVisibility(View.GONE);
        }
    }

    //设置chart基本属性
    @Override
    public void initTotalPriceChart(int valueSize) {

        //描述信息

        Description description = new Description();

        description.setText("单位:亿元");

        //设置描述信息

        mLineChartTotalPrice.setDescription(description);

        //设置没有数据时显示的文本

        mLineChartTotalPrice.setNoDataText("");

        //设置是否绘制chart边框的线

        mLineChartTotalPrice.setDrawBorders(true);

        //设置chart边框线颜色

        mLineChartTotalPrice.setBorderColor(Color.GRAY);

        //设置chart边框线宽度

        mLineChartTotalPrice.setBorderWidth(1f);

        //设置chart是否可以触摸

        mLineChartTotalPrice.setTouchEnabled(false);

        //设置是否可以拖拽

        mLineChartTotalPrice.setDragEnabled(false);

        //设置是否可以缩放 x和y，默认true

        mLineChartTotalPrice.setScaleEnabled(true);

        //设置是否可以通过双击屏幕放大图表。默认是true

        mLineChartTotalPrice.setDoubleTapToZoomEnabled(false);

        //设置chart动画

        mLineChartTotalPrice.animateXY(1000, 1000);


        //=========================设置图例=========================

        // 像"□ xxx"就是图例

        Legend legend = mLineChartTotalPrice.getLegend();

        //设置图例显示在chart那个位置 setPosition建议放弃使用了

        //设置垂直方向上还是下或中

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //设置水平方向是左边还是右边或中

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        //设置所有图例位置排序方向

        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        //设置图例的形状 有圆形、正方形、线

        legend.setForm(Legend.LegendForm.CIRCLE);

        //是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter

        legend.setWordWrapEnabled(true);


        //=======================设置X轴显示效果==================

        XAxis xAxis = mLineChartTotalPrice.getXAxis();

        //是否启用X轴

        xAxis.setEnabled(true);

        //是否绘制X轴线

        xAxis.setDrawAxisLine(true);

        //设置X轴上每个竖线是否显示

        xAxis.setDrawGridLines(true);

        //设置是否绘制X轴上的对应值(标签)

        xAxis.setDrawLabels(true);

        //设置X轴显示位置

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置竖线为虚线样式

        // xAxis.enableGridDashedLine(10f, 10f, 0f);

        //设置x轴标签数

        xAxis.setLabelCount(valueSize, true);

        //图表第一个和最后一个label数据不超出左边和右边的Y轴

        // xAxis.setAvoidFirstLastClipping(true);


        //设置限制线 12代表某个该轴某个值，也就是要画到该轴某个值上

        LimitLine limitLine = new LimitLine(12);

        //设置限制线的宽

        limitLine.setLineWidth(1f);

        //设置限制线的颜色

        limitLine.setLineColor(Color.RED);

        //设置基线的位置

        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        limitLine.setLabel("马丹我是基线，也可以叫我水位线");

        //设置限制线为虚线

        limitLine.enableDashedLine(10f, 10f, 0f);

        //左边Y轴添加限制线

        //axisLeft.addLimitLine(limitLine);


        //=================设置左边Y轴===============

        YAxis axisLeft = mLineChartTotalPrice.getAxisLeft();

        //是否启用左边Y轴

        axisLeft.setEnabled(true);

        //设置最小值（这里就按demo里固死的写）

        //axisLeft.setAxisMinimum(1);

        //设置最大值（这里就按demo里固死的写了）

        //axisLeft.setAxisMaximum(20);

        //设置横向的线为虚线

        axisLeft.enableGridDashedLine(10f, 10f, 0f);

        //axisLeft.setDrawLimitLinesBehindData(true);


        //====================设置右边的Y轴===============

        YAxis axisRight = mLineChartTotalPrice.getAxisRight();

        //是否启用右边Y轴

        axisRight.setEnabled(true);

        //设置最小值（这里按demo里的数据固死写了）

//        axisRight.setAxisMinimum(1);

        //设置最大值（这里按demo里的数据固死写了）

//        axisRight.setAxisMaximum(20);

        //设置横向的线为虚线

        axisRight.enableGridDashedLine(10f, 10f, 0f);


    }

    @Override
    public void initPriceRateChart(int valueSize) {

        //描述信息

        Description description = new Description();

        description.setText("单位:亿元");

        //设置描述信息

        mLinechartPriceRate.setDescription(description);

        //设置没有数据时显示的文本

        mLinechartPriceRate.setNoDataText("");

        //设置是否绘制chart边框的线

        mLinechartPriceRate.setDrawBorders(true);

        //设置chart边框线颜色

        mLinechartPriceRate.setBorderColor(Color.GRAY);

        //设置chart边框线宽度

        mLinechartPriceRate.setBorderWidth(1f);

        //设置chart是否可以触摸

        mLinechartPriceRate.setTouchEnabled(false);

        //设置是否可以拖拽

        mLinechartPriceRate.setDragEnabled(false);

        //设置是否可以缩放 x和y，默认true

        mLinechartPriceRate.setScaleEnabled(true);

        //设置是否可以通过双击屏幕放大图表。默认是true

        mLinechartPriceRate.setDoubleTapToZoomEnabled(false);

        //设置chart动画

        mLinechartPriceRate.animateXY(1000, 1000);


        //=========================设置图例=========================

        // 像"□ xxx"就是图例

        Legend legend = mLinechartPriceRate.getLegend();

        //设置图例显示在chart那个位置 setPosition建议放弃使用了

        //设置垂直方向上还是下或中

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        //设置水平方向是左边还是右边或中

        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        //设置所有图例位置排序方向

        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        //设置图例的形状 有圆形、正方形、线

        legend.setForm(Legend.LegendForm.CIRCLE);

        //是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter

        legend.setWordWrapEnabled(true);


        //=======================设置X轴显示效果==================

        XAxis xAxis = mLinechartPriceRate.getXAxis();

        //是否启用X轴

        xAxis.setEnabled(true);

        //是否绘制X轴线

        xAxis.setDrawAxisLine(true);

        //设置X轴上每个竖线是否显示

        xAxis.setDrawGridLines(true);

        //设置是否绘制X轴上的对应值(标签)

        xAxis.setDrawLabels(true);

        //设置X轴显示位置

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //设置竖线为虚线样式

        // xAxis.enableGridDashedLine(10f, 10f, 0f);

        //设置x轴标签数

        xAxis.setLabelCount(valueSize, true);

        //图表第一个和最后一个label数据不超出左边和右边的Y轴

        // xAxis.setAvoidFirstLastClipping(true);


        //设置限制线 12代表某个该轴某个值，也就是要画到该轴某个值上

        LimitLine limitLine = new LimitLine(12);

        //设置限制线的宽

        limitLine.setLineWidth(1f);

        //设置限制线的颜色

        limitLine.setLineColor(Color.RED);

        //设置基线的位置

        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        limitLine.setLabel("马丹我是基线，也可以叫我水位线");

        //设置限制线为虚线

        limitLine.enableDashedLine(10f, 10f, 0f);

        //左边Y轴添加限制线

        //axisLeft.addLimitLine(limitLine);


        //=================设置左边Y轴===============

        YAxis axisLeft = mLinechartPriceRate.getAxisLeft();

        //是否启用左边Y轴

        axisLeft.setEnabled(true);

        //设置最小值（这里就按demo里固死的写）

        //axisLeft.setAxisMinimum(1);

        //设置最大值（这里就按demo里固死的写了）

        //axisLeft.setAxisMaximum(20);

        //设置横向的线为虚线

        axisLeft.enableGridDashedLine(10f, 10f, 0f);

        //axisLeft.setDrawLimitLinesBehindData(true);


        //====================设置右边的Y轴===============

        YAxis axisRight = mLinechartPriceRate.getAxisRight();

        //是否启用右边Y轴

        axisRight.setEnabled(true);

        //设置最小值（这里按demo里的数据固死写了）

//        axisRight.setAxisMinimum(1);

        //设置最大值（这里按demo里的数据固死写了）

//        axisRight.setAxisMaximum(20);

        //设置横向的线为虚线

        axisRight.enableGridDashedLine(10f, 10f, 0f);


    }

    //设置数据
    @Override
    public void setLineChartTotalPriceData(ArrayList<Entry> yValues1, ArrayList<Entry> yValues2, ArrayList<Entry> yValues3, ArrayList<Entry> yValues4, ArrayList<String> xValues) {
        LineDataSet dataSet_0_1 = new LineDataSet(yValues1, "-1");
        dataSet_0_1.setColor(Color.rgb(255, 255, 0));// 折线显示颜色
        ILineDataSet dataSet1 = dataSet_0_1;
        LineDataSet dataSet_0_2 = new LineDataSet(yValues2, "1-3");
        dataSet_0_2.setColor(Color.rgb(135, 206, 235));// 折线显示颜色
        ILineDataSet dataSet2 = dataSet_0_2;
        LineDataSet dataSet_0_3 = new LineDataSet(yValues3, "3-5");
        dataSet_0_3.setColor(Color.rgb(0, 255, 0));// 折线显示颜色
        ILineDataSet dataSet3 = dataSet_0_3;
        LineDataSet dataSet_0_4 = new LineDataSet(yValues4, "5-");
        dataSet_0_4.setColor(Color.rgb(255, 0, 0));// 折线显示颜色
        ILineDataSet dataSet4 = dataSet_0_4;

        dataSet1.setHighlightEnabled(true);
        dataSet1.setValueTextColor(Color.rgb(255, 255, 0)); //数值显示的颜色
        dataSet1.setValueTextSize(8f);     //数值显示的大小
        //dataSet1.setColor(Color.rgb(255, 255, 0));// 折线显示颜色
        //dataSet1.setCircleColor(Color.rgb(89, 255, 0));// 圆形折点的颜色
        dataSet2.setHighlightEnabled(true);
        dataSet2.setValueTextColor(Color.rgb(135, 206, 235));
        dataSet2.setValueTextSize(8f);
        dataSet3.setHighlightEnabled(true);
        dataSet3.setValueTextColor(Color.rgb(0, 255, 0)); //数值显示的颜色
        dataSet3.setValueTextSize(8f);     //数值显示的大小
        dataSet4.setHighlightEnabled(true);
        dataSet4.setValueTextColor(Color.rgb(255, 0, 0)); //数值显示的颜色
        dataSet4.setValueTextSize(8f);     //数值显示的大小

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        //将数据加入dataSets
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);
        dataSets.add(dataSet4);
        LineData a = new LineData(dataSets);
        mLineChartTotalPrice.setData(a);
        mLineChartTotalPrice.invalidate();
        XAxis xAxis = mLineChartTotalPrice.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "" + (int) value;
            }
        });
    }

    //设置数据
    @Override
    public void setLineChartPriceRateData(ArrayList<Entry> yValues1, ArrayList<Entry> yValues2, ArrayList<Entry> yValues3, ArrayList<Entry> yValues4, ArrayList<String> xValues) {
        LineDataSet dataSet_0_1 = new LineDataSet(yValues1, "-1");
        dataSet_0_1.setColor(Color.rgb(255, 255, 0));// 折线显示颜色
        ILineDataSet dataSet1 = dataSet_0_1;
        LineDataSet dataSet_0_2 = new LineDataSet(yValues2, "1-3");
        dataSet_0_2.setColor(Color.rgb(135, 206, 235));// 折线显示颜色
        ILineDataSet dataSet2 = dataSet_0_2;
        LineDataSet dataSet_0_3 = new LineDataSet(yValues3, "3-5");
        dataSet_0_3.setColor(Color.rgb(0, 255, 0));// 折线显示颜色
        ILineDataSet dataSet3 = dataSet_0_3;
        LineDataSet dataSet_0_4 = new LineDataSet(yValues4, "5-");
        dataSet_0_4.setColor(Color.rgb(255, 0, 0));// 折线显示颜色
        ILineDataSet dataSet4 = dataSet_0_4;

        dataSet1.setHighlightEnabled(true);
        dataSet1.setValueTextColor(Color.rgb(255, 255, 0)); //数值显示的颜色
        dataSet1.setValueTextSize(8f);     //数值显示的大小
        //dataSet1.setColor(Color.rgb(255, 255, 0));// 折线显示颜色
        //dataSet1.setCircleColor(Color.rgb(89, 255, 0));// 圆形折点的颜色
        dataSet2.setHighlightEnabled(true);
        dataSet2.setValueTextColor(Color.rgb(135, 206, 235));
        dataSet2.setValueTextSize(8f);
        dataSet3.setHighlightEnabled(true);
        dataSet3.setValueTextColor(Color.rgb(0, 255, 0)); //数值显示的颜色
        dataSet3.setValueTextSize(8f);     //数值显示的大小
        dataSet4.setHighlightEnabled(true);
        dataSet4.setValueTextColor(Color.rgb(255, 0, 0)); //数值显示的颜色
        dataSet4.setValueTextSize(8f);     //数值显示的大小

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        //将数据加入dataSets
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);
        dataSets.add(dataSet4);
        LineData a = new LineData(dataSets);
        mLinechartPriceRate.setData(a);
        mLinechartPriceRate.invalidate();
        XAxis xAxis = mLinechartPriceRate.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "" + (int) value;
            }
        });
    }

    public void accessDenied() {
        Toast.makeText(getContext(), "权限不足！", Toast.LENGTH_SHORT).show();
        getActivity().finish();
        progressDialog.dismiss();
    }

    //集团信托资产管理计划汇总表
    public void setTrustTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTableForTrust mt = new MyScrollTableForTrust(ll_tableTrust);
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        } else {
            ll_trustTable.removeAllViews();
            tv_titleOfTrust.setVisibility(View.GONE);
        }


    }

    //集团保险资产管理计划汇总表
    public void setInsuranceTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTable mt = new MyScrollTable(ll_tableInsurance, "insurance");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        } else {
            tv_titleOfInsurance.setVisibility(View.GONE);
            ll_insuranceTable.removeAllViews();
        }

    }

    //集团证券资产管理计划汇总表
    public void setSecurityTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTable mt = new MyScrollTable(ll_tableSecurity, "security");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
            long now_c = System.currentTimeMillis();
            Log.e("集团最后一个表格执行时间", String.valueOf(now_c));
        } else {
            tv_titleOfSecurity.setVisibility(View.GONE);
            ll_securityTable.removeAllViews();
        }


    }

    //企业信托资产管理计划汇总表
    public void setCorporateTrustTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTableForTrust mt = new MyScrollTableForTrust(ll_cTableTrust);
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        } else {
            ll_cTrustTable.removeAllViews();
            tv_titleTrust.setVisibility(View.GONE);
        }
    }

    //企业保险资产管理计划汇总表
    public void setCorporateInsuranceTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTable mt = new MyScrollTable(ll_cTableInsurance, "insurance");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        } else {
            ll_cInsuranceTable.removeAllViews();
            tv_titleInsurance.setVisibility(View.GONE);
        }
    }

    //企业证券资产管理计划汇总表
    public void setCorporateSecurityTable(List<String> list) {
        if (list.size() != 0) {
            MyScrollTable mt = new MyScrollTable(ll_cTableSecurity, "security");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        } else {
            ll_cSecurityTable.removeAllViews();
            tv_titleSecurity.setVisibility(View.GONE);
        }

    }

    @Override
    public void removeView() {
        cl_group.removeAllViews();
    }

    @Override
    public void setProgressBar(boolean show) { //设置显示/取消显示进度条
        if (show) {
            progressDialog = new ProgressDialog(getContext()); // 创建ProgressDialog对象
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置进度条风格，风格为圆形，旋转的
            progressDialog.setIndeterminate(false); // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
            progressDialog.setCancelable(true); // 设置ProgressDialog 是否可以按退回键取消
            progressDialog.setTitle("融资详情");
            progressDialog.setMessage("正在加载中....");
            progressDialog.show(); // 让ProgressDialog显示
        } else {
            if (progressDialog != null) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel(); // 取消ProgressDialog显示
                    }
                }, 0);
            }
        }
    }

}


