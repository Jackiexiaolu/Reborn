package com.demo.reborn.historydetail;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static com.google.common.base.Preconditions.checkNotNull;

public class HistoryDetailFragment extends Fragment implements HistoryDetailContract.View {

    private HistoryDetailContract.Presenter mPresenter;
    public ListView lv_changeInformation;
    private ImageView iv_hisrotyDetailBack;
    private Handler mHandler;

    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static HistoryDetailFragment newInstance() {
        return new HistoryDetailFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        startTime  = df.format(new Date());
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
        View root = inflater.inflate(R.layout.fragment_history_detail, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        lv_changeInformation = root.findViewById(R.id.lv_changeInformation);
        iv_hisrotyDetailBack = root.findViewById(R.id.iv_hisrotyDetailBack);
        iv_hisrotyDetailBack.setOnClickListener(view -> getActivity().finish());
        mPresenter.loadInfo();

        return root;
    }

    //初始化listview
    public void initList(List<Map<String, Object>> list) {

        String[] from = {"changeDate", "changeType", "beforeChange", "afterChange"};
        int[] to = {R.id.tv_changeDateInfo, R.id.tv_changeTypeInfo, R.id.tv_beforeChangeInfo, R.id.tv_afterChangeInfo};
        final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.fragment_history_detail_listview_item, from, to);
        lv_changeInformation.setAdapter(adapter);

    }

    @Override
    public void setPresenter(HistoryDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
