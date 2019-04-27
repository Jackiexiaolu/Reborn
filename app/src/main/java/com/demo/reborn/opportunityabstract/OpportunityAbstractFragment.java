package com.demo.reborn.opportunityabstract;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.MyTable;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.businessopportunity.BusinessOpportunityActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class OpportunityAbstractFragment extends Fragment implements OpportunityAbstractContract.View{

    private OpportunityAbstractContract.Presenter mPresenter;

    private final int RED = 0xFFE63C28;
    private final int DARKGREY = 0xffa0a0a0;
    private final int GREY = 0xffe0e0e0;
    private int densityDPI;
    private int screenWidth;
    private int screenHeight;
    private TextView tv_businessOpportunity_Title;
    private ImageView iv_businessOpportunity_back;
    private LinearLayout ll_businessOpportunity;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @NonNull
    public static OpportunityAbstractFragment newInstance() {return new OpportunityAbstractFragment(); }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_opportunity_abstract, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        densityDPI  = getResources().getDisplayMetrics().densityDpi;
        screenWidth  = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        tv_businessOpportunity_Title = root.findViewById(R.id.tv_businessOpportunity_Title);
        iv_businessOpportunity_back = root.findViewById(R.id.iv_businessOpportunity_back);
        ll_businessOpportunity = root.findViewById(R.id.ll_businessOpportunity);

        iv_businessOpportunity_back.setOnClickListener(view -> getActivity().finish());



        mPresenter.loadInfo();
        return root;
    }

    @Override
    public void setPresenter(OpportunityAbstractContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setCompanyName(String companyName){
        tv_businessOpportunity_Title.setText(companyName + "商机");
    }

    @Override
    public void setTitle(String title){
        LinearLayout linearLayout = new LinearLayout(ll_businessOpportunity.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView rectangle = new TextView(ll_businessOpportunity.getContext());
        rectangle.setBackgroundColor(RED);
        rectangle.setTextSize(17);
        linearLayout.addView(rectangle, getPixels(3), LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams rectangleParams = (LinearLayout.LayoutParams) rectangle.getLayoutParams();
        rectangleParams.setMargins(getPixels(20), getPixels(20), 0, getPixels(5));
        rectangle.setLayoutParams(rectangleParams);

        TextView textView = new TextView(ll_businessOpportunity.getContext());
        textView.setTextSize(17);
        textView.setTextColor(0xFF000000);
        textView.setText(title);
        linearLayout.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams textViewParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        textViewParams.setMargins(getPixels(7), getPixels(20), 0, getPixels(5));
        textView.setLayoutParams(textViewParams);

        TextView detail = new TextView(ll_businessOpportunity.getContext());
        detail.setTextSize(14);
        detail.setTextColor(RED);
        detail.setText("详情");
        detail.setTextDirection(View.TEXT_DIRECTION_RTL);
        linearLayout.addView(detail, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams detailParams = (LinearLayout.LayoutParams) detail.getLayoutParams();
        detailParams.setMarginEnd(getPixels(20));
        detailParams.setMargins(0, getPixels(20), getPixels(15), getPixels(5));
        detail.setLayoutParams(detailParams);
        setListener(detail, textView);

        ll_businessOpportunity.addView(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView divider = new TextView(ll_businessOpportunity.getContext());
        divider.setBackgroundColor(DARKGREY);
        ll_businessOpportunity.addView(divider, LinearLayout.LayoutParams.MATCH_PARENT, getPixels(1));
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) divider.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        params.setMargins(0, 0, 0, getPixels(10));
        divider.setLayoutParams(params);
    }

    @Override
    public void setParagraph(String paragraph){
        TextView textView = new TextView(ll_businessOpportunity.getContext());
        textView.setText(paragraph);
        textView.setTextSize(17);
        textView.setTextColor(DARKGREY);
        ll_businessOpportunity.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        textView.setLayoutParams(params);
    }

    @Override
    public void setChart(ArrayList<String> strings){
        LinearLayout linearLayout = new LinearLayout(ll_businessOpportunity.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        int width = screenWidth - getPixels(40);
        MyTable mt = new MyTable(linearLayout, width);
        mt.setColumn(5);
        int weight[] = {1,1,1,1,1};
        mt.setWeight(weight);
        for(int i = 0; i < strings.size(); i++){
            mt.add(strings.get(i));
        }
        ll_businessOpportunity.addView(linearLayout);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.setMarginStart(getPixels(20));
        params.setMarginEnd(getPixels(20));
        params.setMargins(0, getPixels(10), 0, getPixels(10));
        linearLayout.setLayoutParams(params);
    }

    private int getPixels(int dp){
        return dp * densityDPI / 160;
    }

    private void setListener(TextView detail, TextView textView){
        detail.setOnClickListener(v -> {
            mPresenter.setBusinessOpportunityTag(textView.getText().toString());
            startActivity(new Intent(getContext(), BusinessOpportunityActivity.class));
        });
    }

    public void accessDenied(){
        Toast.makeText(getContext(),"权限不足！",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}
