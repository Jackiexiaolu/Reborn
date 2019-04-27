package com.demo.reborn.productionmanagementdetail;

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

import com.demo.reborn.MyScrollTable;
import com.demo.reborn.MyTable;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportActivity;
import com.demo.reborn.preregisterpage.PreRegisterPageActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductionManagementDetailFragment extends Fragment implements  ProductionManagementDetailContract.View{

    private ProductionManagementDetailContract.Presenter mPresenter;
    private TextView tv_productionManagementInfo;
    private TextView tv_eachModuleProductionManagementInfo;
    private ImageView iv_produtionManagementBack;

    private LinearLayout ll_tableProduct;
    private LinearLayout ll_tableArea;

    private LinearLayout ll_pTableProduct;
    private LinearLayout ll_pTableArea;

    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static ProductionManagementDetailFragment newInstance() {
        return new ProductionManagementDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_production_management_detail, container, false);
        NavigationBar navigationBar = new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        tv_productionManagementInfo = root.findViewById(R.id.tv_productionManagementInfo);
        //tv_eachModuleProductionManagementInfo = root.findViewById(R.id.tv_eachModuleProductionManagementInfo);
        iv_produtionManagementBack = root.findViewById(R.id.iv_productionManagementBack);

        ll_tableProduct = root.findViewById(R.id.ll_tableProduct);
        ll_tableArea = root.findViewById(R.id.ll_tableArea);
        ll_pTableProduct = root.findViewById(R.id.ll_pTableProduct);
        ll_pTableArea = root.findViewById(R.id.ll_pTableArea);

        iv_produtionManagementBack.setOnClickListener(view -> getActivity().finish());
        mPresenter.getInfo();

        return root;
    }

    @Override
    public void setPresenter(ProductionManagementDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    // 生产经营情况
    @Override
    public void setBusinessInfo(String businessInfo){
        tv_productionManagementInfo.setText(businessInfo);
        //tv_productionManagementInfo.setBackgroundColor(0xFFFFFF);
    }

//    //各板块经营情况
//    public void setEachBusinessInfo(String businessInfo){
//        tv_eachModuleProductionManagementInfo.setText(businessInfo);
//    }

    @Override
    public void setProductTable(List<String> list){
        if(list.size()!=0) {
            MyScrollTable mt = new MyScrollTable(ll_tableProduct,"production");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        }else{
            ll_pTableProduct.removeAllViews();
        }
    }

    @Override
    public void setAreaTable(List<String> list){
        if(list.size()!=0) {
            MyScrollTable mt = new MyScrollTable(ll_tableArea,"production");
            for (int i = 0; i < list.size(); i++) {
                mt.add(list.get(i));
            }
        }else{
            ll_pTableArea.removeAllViews();
        }

    }
}
