package com.demo.reborn.ownershipstructure;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.Judgement;
import com.demo.reborn.MyScrollView;
import com.demo.reborn.R;
import com.demo.reborn.companydetail.CompanyDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class OwnershipStructureFragment extends Fragment implements OwnershipStructureContract.View {
    private OwnershipStructureContract.Presenter mPresenter;
    private int densityDPI;
    private int Width;

    private final int RED = 0xFFE63C28;
    private boolean isGroup = false;

    private ConstraintLayout cl_ownershipStructure_rootView;
    private ConstraintLayout cl_ownershipStructure_lastItemCompany;
    private MyScrollView sv_ownershipStructure;
    private ListView lv_ownershipStructure_fatherList;
    private ListView lv_ownershipStructure_childList;
    private LinearLayout ll_ownershipStructure_secondHolder;
    private LinearLayout ll_ownershipStructure_fatherList;
    private LinearLayout ll_ownershipStructure_groupCompany;
    private ImageView iv_ownershipStructureBack;
    private Button btn_ownershipStructure_return;
    private TextView tv_ownershipStructure_currentCompany;
    private TextView tv_ownershipStructure_currentCompanyTop;
    private TextView tv_ownershipStructure_groupCompany;
    private TextView tv_ownershipStructure_lastItemCompany;
    private TextView tv_ownershipStructure_lastItemCompanyRate;
    private TextView tv_ownershipStructure_line1;
    private TextView tv_ownershipStructure_line2;
    private TextView tv_ownershipStructure_line3;
    private TextView tv_ownershipStructure_line4;
    private TextView tv_ownershipStructure_line5;
    private TextView tv_ownershipStructure_line6;
    private TextView tv_ownershipStructure_line7;
    private TextView expand;
    private TextView retract;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static OwnershipStructureFragment newInstance() {
        return new OwnershipStructureFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ownership_structure, container, false);
        densityDPI  = getResources().getDisplayMetrics().densityDpi;
        Width = getResources().getDisplayMetrics().widthPixels;

        cl_ownershipStructure_rootView = root.findViewById(R.id.cl_ownershipStructure_rootView);
        cl_ownershipStructure_lastItemCompany = root.findViewById(R.id.cl_ownershipStructure_lastItemCompany);
        sv_ownershipStructure = root.findViewById(R.id.sv_ownershipStructure);
        ll_ownershipStructure_secondHolder = root.findViewById(R.id.ll_ownershipStructure_secondHolder);
        ll_ownershipStructure_fatherList = root.findViewById(R.id.ll_ownershipStructure_fatherList);
        ll_ownershipStructure_groupCompany = root.findViewById(R.id.ll_ownershipStructure_groupCompany);
        lv_ownershipStructure_fatherList = root.findViewById(R.id.lv_ownershipStructure_fatherList);
        lv_ownershipStructure_childList = root.findViewById(R.id.lv_ownershipStructure_childList);
        iv_ownershipStructureBack = root.findViewById(R.id.iv_ownershipStructureBack);
        btn_ownershipStructure_return = root.findViewById(R.id.btn_ownershipStructure_return);
        tv_ownershipStructure_currentCompany = root.findViewById(R.id.tv_ownershipStructure_currentCompany);
        tv_ownershipStructure_currentCompanyTop = root.findViewById(R.id.tv_ownershipStructure_currentCompanyTop);
        tv_ownershipStructure_currentCompanyTop.setAlpha(0);
        tv_ownershipStructure_groupCompany = root.findViewById(R.id.tv_ownershipStructure_groupCompany);
        tv_ownershipStructure_lastItemCompany = root.findViewById(R.id.tv_ownershipStructure_lastItemCompany);
        tv_ownershipStructure_lastItemCompanyRate = root.findViewById(R.id.tv_ownershipStructure_lastItemCompanyRate);
        tv_ownershipStructure_line1 = root.findViewById(R.id.tv_ownershipStructure_line1);
        tv_ownershipStructure_line2 = root.findViewById(R.id.tv_ownershipStructure_line2);
        tv_ownershipStructure_line3 = root.findViewById(R.id.tv_ownershipStructure_line3);
        tv_ownershipStructure_line4 = root.findViewById(R.id.tv_ownershipStructure_line4);
        tv_ownershipStructure_line5 = root.findViewById(R.id.tv_ownershipStructure_line5);
        tv_ownershipStructure_line6 = root.findViewById(R.id.tv_ownershipStructure_line6);
        tv_ownershipStructure_line7 = root.findViewById(R.id.tv_ownershipStructure_line7);

        init(mPresenter.getCompanyId());

        btn_ownershipStructure_return.setOnClickListener(v -> {
            refresh(mPresenter.getCompanyId());
        });

        iv_ownershipStructureBack.setOnClickListener(view -> getActivity().finish());
        sv_ownershipStructure.setOnScrollListener(new MyScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(!isGroup) {
                    if (oldScrollY > tv_ownershipStructure_currentCompany.getY()) {
                        if (scrollY > 0) {

                            tv_ownershipStructure_currentCompanyTop.setAlpha(1);
                            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tv_ownershipStructure_currentCompanyTop.getLayoutParams();

                            int width = tv_ownershipStructure_currentCompany.getWidth();
                            params.setMarginStart(getPixels(30) - (oldScrollY - (int) tv_ownershipStructure_currentCompany.getY()));
                            tv_ownershipStructure_currentCompanyTop.setWidth(width + (Width - width - getPixels(30)) / getPixels(30) * (oldScrollY - (int) tv_ownershipStructure_currentCompany.getY()));
                            tv_ownershipStructure_currentCompanyTop.setLayoutParams(params);
                        }
                    } else {
                        tv_ownershipStructure_currentCompanyTop.setAlpha(0);
                    }
                }
            }
        });



        tv_ownershipStructure_currentCompanyTop.setOnClickListener(v -> {
            if(tv_ownershipStructure_currentCompanyTop.getAlpha() == 1){
                sv_ownershipStructure.scrollTo(0, (int) tv_ownershipStructure_currentCompany.getY() - getPixels(5));
                tv_ownershipStructure_currentCompanyTop.setAlpha(0);
            }
        });

        return root;
    }

    @Override
    public void setPresenter(OwnershipStructureContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private void init(String company_id) {
        if(Judgement.isNullOrEmpty(company_id)){
            Toast.makeText(getContext(),"暂无该家公司信息！",Toast.LENGTH_SHORT).show();
        }
        else {
            mPresenter.refreshCompany(company_id);
        }
    }

    private void refresh(String company_id){
        if(Judgement.isNullOrEmpty(company_id)){
            Toast.makeText(getContext(),"暂无该家公司信息！",Toast.LENGTH_SHORT).show();
        }
        else {
            mPresenter.setCompanyId(company_id);
            Intent intentSearch = new Intent(getContext(), CompanyDetailActivity.class);
            intentSearch.putExtra("favourite", "0");
            startActivity(intentSearch);
        }
    }

    @Override
    public void refreshList(String company_id){
        if(!tv_ownershipStructure_currentCompany.getText().equals("该公司信息尚未完善")) {
            mPresenter.refreshFatherList(company_id);
            mPresenter.refreshChildList(company_id);
            tv_ownershipStructure_currentCompanyTop.setAlpha(0);
        }
    }

    @Override
    public void setCurrentCompany(String companyName) {
        tv_ownershipStructure_currentCompany.setAlpha(1);
        tv_ownershipStructure_currentCompany.setText(companyName);
        tv_ownershipStructure_currentCompanyTop.setText(companyName);
        ll_ownershipStructure_groupCompany.setAlpha(1);
    }

    @Override
    public void setFatherList(List<Map<String, Object>> list){
        tv_ownershipStructure_line2.setAlpha(1);
        tv_ownershipStructure_line3.setAlpha(1);
        tv_ownershipStructure_line7.setAlpha(0);
        ll_ownershipStructure_fatherList.removeAllViews();
        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) lv_ownershipStructure_fatherList.getLayoutParams();
        lp.setMargins(getPixels(15), getPixels(40), getPixels(140), 0);
        isGroup = false;

        if(list.size() <= 3) {
            showAllFatherList(list);
        }
        else {
            List<Map<String, Object>> emptyList = new ArrayList<>();
            showAllFatherList(emptyList);

            //一级子公司
            LinearLayout ll_firstLevelCompany = new LinearLayout(ll_ownershipStructure_fatherList.getContext());
            ll_firstLevelCompany.setOrientation(LinearLayout.HORIZONTAL);

            TextView red = new TextView(ll_firstLevelCompany.getContext());
            red.setBackgroundColor(RED);
            ll_firstLevelCompany.addView(red, getPixels(4), LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout linearLayout_firstLevelCompany = new LinearLayout(ll_ownershipStructure_fatherList.getContext());
            linearLayout_firstLevelCompany.setOrientation(LinearLayout.VERTICAL);

            TextView firstText = new TextView(linearLayout_firstLevelCompany.getContext());
            firstText.setText(list.get(0).get("company").toString());
            firstText.setTextColor(0xFF000000);
            firstText.setTextSize(13);
            firstText.setBackgroundColor(0xFFFFFFFF);
            firstText.setPadding(getPixels(12), getPixels(3), getPixels(12), 0);
            linearLayout_firstLevelCompany.addView(firstText, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView firstRate = new TextView(linearLayout_firstLevelCompany.getContext());
            firstRate.setText(list.get(0).get("rate").toString());
            firstRate.setTextColor(0xFFA0A0A0);
            firstRate.setTextSize(9);
            firstRate.setBackgroundColor(0xFFFFFFFF);
            firstRate.setPadding(getPixels(12), 0, getPixels(12), getPixels(3));
            linearLayout_firstLevelCompany.addView(firstRate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_firstLevelCompany.addView(linearLayout_firstLevelCompany, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ll_ownershipStructure_fatherList.addView(ll_firstLevelCompany);
            ll_firstLevelCompany.setOnClickListener(v -> {
                refresh(list.get(0).get("company_id").toString());
            });

            //加号
            expand = new TextView(ll_ownershipStructure_fatherList.getContext());
            expand.setText("+");
            expand.setTextColor(RED);
            expand.setTextSize(17);
            expand.setGravity(Gravity.CENTER);
            expand.setBackgroundResource(R.drawable.textview_border);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getPixels(30),getPixels(30));
            params.setMargins(0,getPixels(10), 0, getPixels(10));
            expand.setLayoutParams(params);
            ll_ownershipStructure_fatherList.addView(expand);
            expand.setOnClickListener(v -> {
                ll_ownershipStructure_fatherList.removeAllViews();

                final Map<String, Object> lastItem = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                showAllFatherList(list);

                retract = new TextView(ll_ownershipStructure_fatherList.getContext());
                retract.setText("-");
                retract.setTextColor(RED);
                retract.setTextSize(17);
                retract.setGravity(Gravity.CENTER);
                retract.setBackgroundResource(R.drawable.textview_border);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(getPixels(30),getPixels(30));
                params2.setMargins(0,getPixels(10), 0, getPixels(10));
                retract.setLayoutParams(params2);
                ll_ownershipStructure_fatherList.addView(retract);
                retract.setOnClickListener(v2 -> {
                    list.add(lastItem);
                    setFatherList(list);
                });

                LinearLayout ll_lastLevelCompany = new LinearLayout(ll_ownershipStructure_fatherList.getContext());
                ll_lastLevelCompany.setOrientation(LinearLayout.HORIZONTAL);

                TextView red2 = new TextView(ll_lastLevelCompany.getContext());
                red2.setBackgroundColor(RED);
                ll_lastLevelCompany.addView(red2, getPixels(4), LinearLayout.LayoutParams.MATCH_PARENT);

                LinearLayout linearLayout_lastLevelCompany = new LinearLayout(ll_lastLevelCompany.getContext());
                linearLayout_lastLevelCompany.setOrientation(LinearLayout.VERTICAL);

                TextView lastText = new TextView(linearLayout_lastLevelCompany.getContext());
                lastText.setText(lastItem.get("company").toString());
                lastText.setTextColor(0xFF000000);
                lastText.setTextSize(13);
                lastText.setBackgroundColor(0xFFFFFFFF);
                lastText.setPadding(getPixels(12), getPixels(3), getPixels(12), 0);
                linearLayout_lastLevelCompany.addView(lastText, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView lastRate = new TextView(linearLayout_lastLevelCompany.getContext());
                lastRate.setText(lastItem.get("rate").toString());
                lastRate.setTextColor(0xFFA0A0A0);
                lastRate.setTextSize(9);
                lastRate.setBackgroundColor(0xFFFFFFFF);
                lastRate.setPadding(getPixels(12), 0, getPixels(12), getPixels(3));
                linearLayout_lastLevelCompany.addView(lastRate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll_lastLevelCompany.addView(linearLayout_lastLevelCompany, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                ll_ownershipStructure_fatherList.addView(ll_lastLevelCompany);
                ll_ownershipStructure_fatherList.setOnClickListener(v3 -> {
                    refresh(lastItem.get("company_id").toString());
                });
            });

            //上一级子公司
            LinearLayout ll_lastLevelCompany = new LinearLayout(ll_ownershipStructure_fatherList.getContext());
            ll_lastLevelCompany.setOrientation(LinearLayout.HORIZONTAL);

            TextView red2 = new TextView(ll_lastLevelCompany.getContext());
            red2.setBackgroundColor(RED);
            ll_lastLevelCompany.addView(red2, getPixels(4), LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout linearLayout_lastLevelCompany = new LinearLayout(ll_lastLevelCompany.getContext());
            linearLayout_lastLevelCompany.setOrientation(LinearLayout.VERTICAL);

            TextView lastText = new TextView(linearLayout_lastLevelCompany.getContext());
            lastText.setText(list.get(list.size() - 1).get("company").toString());
            lastText.setTextColor(0xFF000000);
            lastText.setTextSize(13);
            lastText.setBackgroundColor(0xFFFFFFFF);
            lastText.setPadding(getPixels(12), getPixels(3), getPixels(12), 0);
            linearLayout_lastLevelCompany.addView(lastText, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            TextView lastRate = new TextView(linearLayout_lastLevelCompany.getContext());
            lastRate.setText(list.get(list.size() - 1).get("rate").toString());
            lastRate.setTextColor(0xFFA0A0A0);
            lastRate.setTextSize(9);
            lastRate.setBackgroundColor(0xFFFFFFFF);
            lastRate.setPadding(getPixels(12), 0, getPixels(12), getPixels(3));
            linearLayout_lastLevelCompany.addView(lastRate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_lastLevelCompany.addView(linearLayout_lastLevelCompany, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ll_ownershipStructure_fatherList.addView(ll_lastLevelCompany);
            ll_ownershipStructure_fatherList.setOnClickListener(v -> {
                refresh(list.get(list.size() - 1).get("company_id").toString());
            });
        }
        sv_ownershipStructure.scrollTo(0,0);
    }

    private void showAllFatherList(List<Map<String, Object>> list){
        String from[] = {"company", "rate"};
        int to[] = {R.id.tv_ownershipStructure_listItemCompany, R.id.tv_ownershipStructure_listItemCompanyRate};
        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.fragment_ownership_structure_father_list_item, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final View view = super.getView(position, convertView, parent);
                view.setOnClickListener(v -> {
                    refresh(list.get(position).get("company_id").toString());
                });
                return view;
            }
        };
        lv_ownershipStructure_fatherList.setAdapter(adapter);
    }

    @Override
    public void setChildList(List<Map<String, Object>> list){
        if(list.size() > 0) {
            final Map<String, Object> lastItem = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            tv_ownershipStructure_lastItemCompany.setText(lastItem.get("company").toString());
            tv_ownershipStructure_lastItemCompanyRate.setText(lastItem.get("rate").toString());
            cl_ownershipStructure_lastItemCompany.setOnClickListener(v -> {
                if(!tv_ownershipStructure_lastItemCompany.getText().equals("无子公司")) {
                    refresh(lastItem.get("company_id").toString());
                }
            });
        }
        if(list.size() == 0){
            tv_ownershipStructure_lastItemCompany.setText("无子公司");
            tv_ownershipStructure_lastItemCompanyRate.setText("");
        }
        String from[] = {"company", "rate"};
        int to[] = {R.id.tv_ownershipStructure_listItemCompany, R.id.tv_ownershipStructure_listItemCompanyRate};
        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, R.layout.fragment_ownership_structure_child_list_item, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final View view = super.getView(position, convertView, parent);
                view.setOnClickListener(v -> {
                    if(tv_ownershipStructure_currentCompanyTop.getAlpha() == 1) {
                        if (position - lv_ownershipStructure_childList.getFirstVisiblePosition() > 2) {
                            refresh(list.get(position).get("company_id").toString());
                        }
                    }
                    else{
                        refresh(list.get(position).get("company_id").toString());
                    }
                });
                return view;
            }
        };
        lv_ownershipStructure_childList.setAdapter(adapter);
        sv_ownershipStructure.scrollTo(0,0);
    }

    @Override
    public void setGroupCompany(String name){
        tv_ownershipStructure_groupCompany.setAlpha(1);
        tv_ownershipStructure_groupCompany.setText(name);
    }

    @Override
    public void setSecondHolder(String name, String r){
        if(name.equals("")){
            removeSecondHolder();
            return;
        }

        tv_ownershipStructure_line4.setAlpha(1);
        tv_ownershipStructure_line5.setAlpha(1);
        tv_ownershipStructure_line6.setAlpha(1);

        TextView red = new TextView(ll_ownershipStructure_secondHolder.getContext());
        red.setBackgroundColor(RED);

        LinearLayout linearLayout = new LinearLayout(ll_ownershipStructure_secondHolder.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView company = new TextView(ll_ownershipStructure_secondHolder.getContext());
        company.setText(name);
        company.setTextColor(0xff000000);
        company.setPadding(getPixels(12), getPixels(3), getPixels(12), 0);
        company.setTextSize(15);
        linearLayout.addView(company, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView rate = new TextView(ll_ownershipStructure_secondHolder.getContext());
        rate.setText(r);
        rate.setTextColor(0xffa0a0a0);
        rate.setPadding(getPixels(12), 0, getPixels(12), 3);
        rate.setTextSize(9);
        linearLayout.addView(rate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ll_ownershipStructure_secondHolder.addView(red, getPixels(4), LinearLayout.LayoutParams.MATCH_PARENT);
        ll_ownershipStructure_secondHolder.addView(linearLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void removeSecondHolder(){
        tv_ownershipStructure_line4.setAlpha(0);
        tv_ownershipStructure_line5.setAlpha(0);
        tv_ownershipStructure_line6.setAlpha(0);

        ll_ownershipStructure_secondHolder.removeAllViews();
    }

    @Override
    public void removeGroup(){
        removeSecondHolder();
        tv_ownershipStructure_line2.setAlpha(0);
        tv_ownershipStructure_line3.setAlpha(0);
        tv_ownershipStructure_line7.setAlpha(1);
        ll_ownershipStructure_groupCompany.setAlpha(0);
        tv_ownershipStructure_currentCompany.setAlpha(0);

        tv_ownershipStructure_currentCompanyTop.setAlpha(1);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) tv_ownershipStructure_currentCompanyTop.getLayoutParams();
        params.setMarginStart(0);
        tv_ownershipStructure_currentCompanyTop.setWidth(Width);
        tv_ownershipStructure_currentCompanyTop.setLayoutParams(params);

        showAllFatherList(new ArrayList<>());
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) lv_ownershipStructure_fatherList.getLayoutParams();
        params1.setMargins(0, 0, 0, 0);

        isGroup = true;
        sv_ownershipStructure.scrollTo(0,0);
    }

    private int getPixels(int dp){
        return dp * densityDPI / 160;
    }
}
