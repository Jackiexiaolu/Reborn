package com.demo.reborn;

import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.loginpage.LoginPageActivity;
import com.demo.reborn.loginorregister.LoginOrRegisterActivity;
import com.demo.reborn.managerinfodetail.ManagerInfoDetailActivity;
import com.demo.reborn.personalcenter.PersonalCenterActivity;
import com.demo.reborn.productionmanagementdetail.ProductionManagementDetailActivity;
import com.demo.reborn.registerpage.RegisterPageActivity;

import com.demo.reborn.searchresult.SearchResultActivity;

public class NavigationBar{

    private static int status = 1;
    private final FinancialDataRepository mData;
    private final int RED = 0xFFE63C28;
    private final int DARKGRAY = 0xFFA0A0A0;
    Judgement authority = new Judgement();

    public NavigationBar(LinearLayout currentLinearLayout){
        mData = FinancialDataRepository.getINSTANCE();
        DisplayMetrics dm = currentLinearLayout.getResources().getDisplayMetrics();
        int width = dm.widthPixels;

        TextView textViewHomepage = new TextView(currentLinearLayout.getContext());
        textViewHomepage.setText("首页");
        textViewHomepage.setTextSize(20);
        textViewHomepage.setGravity(Gravity.CENTER);
        textViewHomepage.setId(R.id.navigationBar_textView_homePage);

        TextView textViewFinancialReport = new TextView(currentLinearLayout.getContext());
        textViewFinancialReport.setText("融报");
        textViewFinancialReport.setTextSize(20);
        textViewFinancialReport.setGravity(Gravity.CENTER);
        textViewFinancialReport.setId(R.id.navigationBar_textView_financialReport);

        TextView textViewBusinessOpportunity = new TextView(currentLinearLayout.getContext());
        textViewBusinessOpportunity.setText("商机");
        textViewBusinessOpportunity.setTextSize(20);
        textViewBusinessOpportunity.setGravity(Gravity.CENTER);
        textViewBusinessOpportunity.setId(R.id.navigationBar_textView_businessOpportunity);

        TextView textViewMine = new TextView(currentLinearLayout.getContext());
        textViewMine.setText("我的");
        textViewMine.setTextSize(20);
        textViewMine.setGravity(Gravity.CENTER);
        textViewMine.setId(R.id.navigationBar_textView_mine);

        if(currentLinearLayout.getContext().toString().contains("SearchResultActivity")){
            switch (mData.getFromPage()){
                case "SearchPage":
                    status = 1;
                    break;
                case "FinancialReport":
                    status = 2;
                    break;
                case "Business":
                    status = 3;
                    break;
                default:
                    status = 1;
            }
        }

        switch (status){
            case 1:
                textViewHomepage.setTextColor(RED);
                textViewFinancialReport.setTextColor(DARKGRAY);
                textViewBusinessOpportunity.setTextColor(DARKGRAY);
                textViewMine.setTextColor(DARKGRAY);
                break;
            case 2:
                textViewHomepage.setTextColor(DARKGRAY);
                textViewFinancialReport.setTextColor(RED);
                textViewBusinessOpportunity.setTextColor(DARKGRAY);
                textViewMine.setTextColor(DARKGRAY);
                break;
            case 3:
                textViewHomepage.setTextColor(DARKGRAY);
                textViewFinancialReport.setTextColor(DARKGRAY);
                textViewBusinessOpportunity.setTextColor(RED);
                textViewMine.setTextColor(DARKGRAY);
                break;
            case 4:
                textViewHomepage.setTextColor(DARKGRAY);
                textViewFinancialReport.setTextColor(DARKGRAY);
                textViewBusinessOpportunity.setTextColor(DARKGRAY);
                textViewMine.setTextColor(RED);
                break;
        }

        currentLinearLayout.addView(textViewHomepage, width / 4, LinearLayout.LayoutParams.MATCH_PARENT);
        currentLinearLayout.addView(textViewFinancialReport, width / 4, LinearLayout.LayoutParams.MATCH_PARENT);
        currentLinearLayout.addView(textViewBusinessOpportunity, width / 4, LinearLayout.LayoutParams.MATCH_PARENT);
        currentLinearLayout.addView(textViewMine, width / 4, LinearLayout.LayoutParams.MATCH_PARENT);



        textViewHomepage.setOnClickListener(v -> {
            if(textViewHomepage.getCurrentTextColor() != RED) {
                status = 1;
                currentLinearLayout.getContext().startActivity(new Intent(currentLinearLayout.getContext(), HomePageActivity.class));
            }
        });

        textViewFinancialReport.setOnClickListener(v -> {
            if(textViewFinancialReport.getCurrentTextColor() != RED) {
                status = 2;
                Intent intentFinancial = new Intent(currentLinearLayout.getContext(), SearchResultActivity.class);
                mData.setFromPage("FinancialReport");
                currentLinearLayout.getContext().startActivity(intentFinancial);
            }
        });

        textViewBusinessOpportunity.setOnClickListener(v -> {
            if(textViewBusinessOpportunity.getCurrentTextColor() != RED) {
                status = 3;
                Intent intentBusiness = new Intent(currentLinearLayout.getContext(), SearchResultActivity.class);
                mData.setFromPage("Business");
                currentLinearLayout.getContext().startActivity(intentBusiness);
            }
        });

        textViewMine.setOnClickListener(v -> {
            if(textViewMine.getCurrentTextColor() != RED) {
                status = 4;
                currentLinearLayout.getContext().startActivity(new Intent(currentLinearLayout.getContext(), LoginPageActivity.class));
            }
        });
    }

    public void setStatus(int num){
        status = num;
    }

    public int getStatue(){return status;}
}
