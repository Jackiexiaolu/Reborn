package com.demo.reborn.financialreport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.baseinfodetail.BaseInfoDetailActivity;
import com.demo.reborn.capitalraisinginfodetail.CapitalRaisingInfoDetailActivity;
import com.demo.reborn.financinginfodetail.FinancingInfoDetailActivity;
import com.demo.reborn.historydetail.HistoryDetailActivity;
import com.demo.reborn.holderinfodetail.HolderInfoDetailActivity;
import com.demo.reborn.managerinfodetail.ManagerInfoDetailActivity;
import com.demo.reborn.productionmanagementdetail.ProductionManagementDetailActivity;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.google.common.base.Preconditions.checkNotNull;


public class FinancialReportFragment extends Fragment implements FinancialReportContract.View {

    private FinancialReportContract.Presenter mPresenter;

    private ConstraintLayout cl_financialReport;
    private ScrollView sv_financialReport;
    private TextView listHead01;
    private TextView listHead02;
    private ListView mListView01;
    private ListView mListView02;
    private TextView titleText;
    private TextView basicInfoCompanyName;
    private TextView basicInfoLegalPerson;
    private TextView stockHolderShareholder;
    private TextView stockHolderPercent;
    private TextView history;
    private TextView manager;
    private TextView businessInfo;
    private TextView financingInfo;
    private TextView raisingInfo;
    private LinearLayout tv_table;
    private LinearLayout lLayout_tag;
    private TextView basicInfoGroupName;
    private TextView tv_secondTitleText;
    private TextView basicInfoDetail;
    private TextView stockHolderDetail;
    private TextView historyDetail;
    private TextView managerDetail;
    private TextView businessInfoDetail;
    private TextView financingInfoDetail;
    private TextView raisingInfoDetail;
    private Button btn_pdf;
    private PDFView pdfView;
    private ImageView iv_close;
    private ProgressDialog progressDialog;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String  startTime;
    String  endTime;

    public static FinancialReportFragment newInstance() {
        return new FinancialReportFragment();
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
        View root = inflater.inflate(R.layout.fragment_financial_report, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        cl_financialReport = root.findViewById(R.id.cl_financialReport);
        titleText = root.findViewById(R.id.tv_titleText);
        basicInfoCompanyName = root.findViewById(R.id.basicInfoCompanyName);
        basicInfoLegalPerson = root.findViewById(R.id.basicInfoLegalPerson);
        stockHolderShareholder = root.findViewById(R.id.stockHolderShareholder);
        stockHolderPercent = root.findViewById(R.id.stockHolderPercent);
        history = root.findViewById(R.id.history);
        manager = root.findViewById(R.id.manager);
        businessInfo = root.findViewById(R.id.businessInfo);
        financingInfo = root.findViewById(R.id.financingInfo);
        raisingInfo = root.findViewById(R.id.raisingInfo);
        listHead01 = root.findViewById(R.id.listHead01);
        listHead02 = root.findViewById(R.id.listHead02);
        mListView01 = root.findViewById(R.id.businessList01);
        mListView02 = root.findViewById(R.id.businessList02);
        lLayout_tag = root.findViewById(R.id.lLayout_tag);
        basicInfoGroupName = root.findViewById(R.id.basicInfoGroupName);
        tv_secondTitleText = root.findViewById(R.id.tv_secondTitleText);
        btn_pdf = root.findViewById(R.id.btn_pdf);
        sv_financialReport = root.findViewById(R.id.sv_financialReport);
        iv_close = root.findViewById(R.id.iv_close);
        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setTitle("正在生成pdf");

        basicInfoDetail = root.findViewById(R.id.basicInfoDetail);
        basicInfoDetail.setOnClickListener(view -> mPresenter.doJump("basicInfo"));

        stockHolderDetail = root.findViewById(R.id.stockHolderDetail);
        stockHolderDetail.setOnClickListener(view -> mPresenter.doJump("stockHolder"));

        historyDetail = root.findViewById(R.id.historyDetail);
        historyDetail.setOnClickListener(view -> mPresenter.doJump("history"));

        managerDetail = root.findViewById(R.id.managerDetail);
        managerDetail.setOnClickListener(view -> mPresenter.doJump("manager"));

        businessInfoDetail = root.findViewById(R.id.businessInfoDetail);
        businessInfoDetail.setOnClickListener(view -> mPresenter.doJump("production"));

        financingInfoDetail = root.findViewById(R.id.financingInfoDetail);
        financingInfoDetail.setOnClickListener(view -> mPresenter.doJump("financing"));

        raisingInfoDetail = root.findViewById(R.id.raisingInfoDetail);
        raisingInfoDetail.setOnClickListener(view -> mPresenter.doJump("capital"));

        ImageView iv_financialReport_back = root.findViewById(R.id.iv_financialReport_back);
        iv_financialReport_back.setOnClickListener(view -> getActivity().finish());

        pdfView = root.findViewById(R.id.pdfView);

        btn_pdf.setOnClickListener(v -> {
            mPresenter.setClickPDF(df.format(new Date()));
            if(btn_pdf.getText().equals("生 成 P D F 融 资 报 告")) {
                mPresenter.showPDF();
            }
            else{
                hidePDF();
            }
        });

        mPresenter.destroyEarth();
        return root;
    }

    @Override
    public void setPresenter(FinancialReportContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void setCompanyName(String company){
        titleText.setText(company);
        ViewTreeObserver vto = titleText.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                titleText.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                titleText.getHeight();
                double w0=titleText.getWidth();//控件宽度
                double w1=titleText.getPaint().measureText(titleText.getText().toString());//文本宽度
                if( w1>=w0 && company.contains("有限公司")) {//需要换行
                    if(company.indexOf("有限公司") == company.length()-4){
                        titleText.setText(company.replace("有限公司", ""));
                        tv_secondTitleText.setText("有限公司");
                    }else{
                        tv_secondTitleText.setVisibility(View.GONE);
                        titleText.setText(company);
                    }

                }
                else{
                    tv_secondTitleText.setVisibility(View.GONE);
                    titleText.setText(company);
                }
                double w3;
                if(company.indexOf("有限公司") == company.length()-4) {
                    w3 = titleText.getPaint().measureText(company.replace("有限公司", ""));
                    if(w3 > w0) {
                        titleText.setText(company);
                        tv_secondTitleText.setVisibility(View.GONE);
                    }
                }
            }
        });

        basicInfoCompanyName.setText(company);
        basicInfoCompanyName.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setLegalPerson(String name){
        basicInfoLegalPerson.setText(name);
        basicInfoLegalPerson.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setGroupName(String name){
        basicInfoGroupName.setText(name);
        basicInfoGroupName.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setShareholder(String company, String percent){
        stockHolderShareholder.setText(company);
        stockHolderShareholder.setBackgroundColor(0xFFFFFF);
        if(!isNullOrEmpty(percent))
            stockHolderPercent.setText(percent + "%");
        else
            stockHolderPercent.setText(percent);
        stockHolderPercent.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setHistory(String paragraph){
        if(!paragraph.equals("暂无数据"))
            paragraph = "成立于" + paragraph;
        history.setText(paragraph);
        history.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setManager(String names) {
        manager.setText(names);
        manager.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setBusinessInfo(String paragraph){
        businessInfo.setText(paragraph);
        businessInfo.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setProductList(List<Map<String,Object>> productList){
        if(productList.size() == 0) {
            listHead01.setHeight(0);
            listHead01.setVisibility(View.GONE);
            return;
        }else {
            String[] from = {"name", "rate"};
            int[] to = {R.id.listProduct, R.id.listPercent};
            final SimpleAdapter adapter = new SimpleAdapter(getContext(), productList, R.layout.fragment_financial_report_list_item, from, to);
            mListView01.setAdapter(adapter);
        }
    }

    @Override
    public void setLocationList(List<Map<String,Object>> locationList){
        if(locationList.size() == 0) {
            listHead02.setHeight(0);
            listHead02.setVisibility(View.GONE);
            return;
        }else {
            String[] from = {"name", "rate"};
            int[] to = {R.id.listProduct, R.id.listPercent};
            final SimpleAdapter adapter = new SimpleAdapter(getContext(), locationList, R.layout.fragment_financial_report_list_item, from, to);
            mListView02.setAdapter(adapter);
        }

    }

    @Override
    public void setFinancingInfo(String paragraph){
        financingInfo.setText(paragraph);
        financingInfo.setBackgroundColor(0xFFFFFF);
    }

    @Override
    public void setRaisingInfo(String paragraph) {
        raisingInfo.setText(paragraph);
        raisingInfo.setBackgroundColor(0xFFFFFF);
    }

    //点击跳转
    @Override
    public void clickJump(String textview){
        switch (textview) {
            case "basicInfo":   { startActivity(new Intent(getContext(), BaseInfoDetailActivity.class));            break;}
            case "stockHolder": { startActivity(new Intent(getContext(), HolderInfoDetailActivity.class));          break;}
            case "history":     { startActivity(new Intent(getContext(), HistoryDetailActivity.class));             break;}
            case "manager":     { startActivity(new Intent(getContext(), ManagerInfoDetailActivity.class));         break;}
            case "production":  { startActivity(new Intent(getContext(), ProductionManagementDetailActivity.class));break;}
            case "financing":   { startActivity(new Intent(getContext(), FinancingInfoDetailActivity.class));       break;}
            case "capital":     { startActivity(new Intent(getContext(), CapitalRaisingInfoDetailActivity.class));  break;
            }

        }
    }


    public void setTags(List<String> list){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        if (list.get(0).equals("1")) {
            TextView tv_tagOne = new TextView(getContext());
            tv_tagOne.setText("发债");
            tv_tagOne.setTextSize(12);
            tv_tagOne.setTextColor(Color.rgb(250,250,250));
            tv_tagOne.setBackgroundResource(R.drawable.tag_one);
            lLayout_tag.addView(tv_tagOne);
        }
        if(list.get(1).equals("1")){
            TextView tv_tagTwo = new TextView(getContext());
            tv_tagTwo.setText("国有资本运营平台");
            tv_tagTwo.setTextSize(12);
            tv_tagTwo.setTextColor(Color.rgb(250,250,250));
            tv_tagTwo.setBackgroundResource(R.drawable.tag_two);
            lp.setMarginStart(20);
            tv_tagTwo.setLayoutParams(lp);
            lLayout_tag.addView(tv_tagTwo);

        }
        if(list.get(2).equals("1")){
            TextView tv_tagThree = new TextView(getContext());
            tv_tagThree.setText("上市");
            tv_tagThree.setTextSize(12);
            tv_tagThree.setTextColor(Color.rgb(250,250,250));
            tv_tagThree.setBackgroundResource(R.drawable.tag_three);
            lp.setMarginStart(20);
            tv_tagThree.setLayoutParams(lp);
            lLayout_tag.addView(tv_tagThree);

        }
        if(list.get(3).equals("1")){
            TextView tv_tagFour = new TextView(getContext());
            tv_tagFour.setText("地方资本运营平台");
            tv_tagFour.setTextSize(12);
            tv_tagFour.setTextColor(Color.rgb(250,250,250));
            tv_tagFour.setBackgroundResource(R.drawable.tag_four);
            lp.setMarginStart(20);
            tv_tagFour.setLayoutParams(lp);
            lLayout_tag.addView(tv_tagFour);

        }

        if(list.get(4).equals("1")){
            TextView tv_tagFive = new TextView(getContext());
            tv_tagFive.setText("国有企业");
            tv_tagFive.setTextSize(12);
            tv_tagFive.setTextColor(Color.rgb(250,250,250));
            tv_tagFive.setBackgroundResource(R.drawable.tag_five);
            lp.setMarginStart(20);
            tv_tagFive.setLayoutParams(lp);
            lLayout_tag.addView(tv_tagFive);

        }
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

    @Override
    public void errorShow(String e){
        Toast.makeText(getContext(), e, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean showProgressBar(int max){
        if(max == 0){
            return false;
        }
        progressDialog.show();
        return true;
    }

    @Override
    public void setProgressBar(int progress){
        progressDialog.show();
//        progressDialog.setProgress(progress);
    }

    @Override
    public void hideProgressBar(){
        progressDialog.hide();
    }

    @Override
    public void openPDF(String path){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(path));
        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getContext(), "不能打开pdf文件\n请安装相关程序", Toast.LENGTH_LONG).show();
        }
    }

//    @Override
//    public void setPdf(ResponseBody r){
//        pdfView.setAlpha(1);
//        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) pdfView.getLayoutParams();
//        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
//        pdfView.setLayoutParams(params);
//        sv_financialReport.setAlpha(0);
//        iv_close.setAlpha(1f);
//        iv_close.setOnClickListener(v -> {
//            if(iv_close.getAlpha() == 1) {
//                hidePDF();
//            }
//        });
//        InputStream i = r.byteStream();
//        pdfView.fromStream(i).load();
//        sv_financialReport.scrollTo(0,0);
//    }

    private void hidePDF(){
        pdfView.setAlpha(0);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) pdfView.getLayoutParams();
        params.height = 0;
        pdfView.setLayoutParams(params);
        iv_close.setAlpha(0f);
        sv_financialReport.setAlpha(1);
        sv_financialReport.scrollTo(0,0);
    }
}
