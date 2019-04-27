package com.demo.reborn.managerinfodetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.financialreport.FinancialReportActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class ManagerInfoDetailFragment extends Fragment implements ManagerInfoDetailContract.View {

    private ManagerInfoDetailContract.Presenter mPresenter;
    private ListView lv_managerInfo;
    private ImageView iv_managerInfoDetailBack;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static ManagerInfoDetailFragment newInstance() {
        return new ManagerInfoDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_manager_info_detail, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        lv_managerInfo = root.findViewById(R.id.lv_managerInfo);
        iv_managerInfoDetailBack = root.findViewById(R.id.iv_managerInfoDetailBack);
        iv_managerInfoDetailBack.setOnClickListener(view -> getActivity().finish());
        mPresenter.loadInfo();
        return root;
    }
    @Override
    public void setPresenter(ManagerInfoDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void initList(List<Map<String,Object>> list){
        String[] from = {"managerName","position","introductionInfo"};
        int[] to = {R.id.tv_managerName, R.id.tv_position,R.id.tv_introductionInfo};
        final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.fragment_manager_info_detail_listview_item, from, to);

        lv_managerInfo.setAdapter(adapter);


    }

}


