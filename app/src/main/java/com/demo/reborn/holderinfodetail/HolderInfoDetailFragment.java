package com.demo.reborn.holderinfodetail;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.reborn.MyPieChart;
import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

//import com.demo.reborn.ownershipstructure.OwnershipStructureActivity;


public class HolderInfoDetailFragment extends Fragment implements HolderInfoDetailContract.View {

    private HolderInfoDetailContract.Presenter mPresenter;

    private TextView tv_holderInfoDetailCompanyType;
    private TextView tv_holderInfoDetailHolderListHolderName;
    private TextView tv_holderInfoDetailHolderListStockCode;
    private TextView tv_holderInfoDetailOwnershipStructure;
    private ImageView iv_holderInfoDetailBack;
    private PieChart holderPieChart;
    String  startTime;
    String  endTime;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<Uri> images=new ArrayList<>();
    Context context;
    private ImageView image1;
    public Uri getUriFromDrawableRes(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id);
        return Uri.parse(path);
    }



    public static HolderInfoDetailFragment newInstance() {
        return new HolderInfoDetailFragment();
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
        View root = inflater.inflate(R.layout.fragment_holder_info_detail, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));

        tv_holderInfoDetailCompanyType = root.findViewById(R.id.tv_holderInfoDetailCompanyType);
        tv_holderInfoDetailHolderListHolderName = root.findViewById(R.id.tv_holderInfoDetailHolderListHolderName);
        tv_holderInfoDetailHolderListStockCode = root.findViewById(R.id.tv_holderInfoDetailHolderListStockCode);
        tv_holderInfoDetailOwnershipStructure = root.findViewById(R.id.tv_holderInfoDetailOwnershipStructure);
        iv_holderInfoDetailBack = root.findViewById(R.id.iv_holderInfoDetailBack);
        holderPieChart = root.findViewById(R.id.holderPieChart);
        context=this.getContext();


        tv_holderInfoDetailOwnershipStructure.setOnClickListener(view -> startActivity(new Intent(getContext(), com.demo.reborn.ownershipstructure.OwnershipStructureActivity.class)));

        iv_holderInfoDetailBack.setOnClickListener(view -> getActivity().finish());

        mPresenter.loadInfo();

        return root;
    }

    @Override
    public void setPresenter(HolderInfoDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setCompanyType(String type){
        tv_holderInfoDetailCompanyType.setText(type);
        tv_holderInfoDetailCompanyType.setBackgroundColor(0xfff0f0f0);
    }

    @Override
    public void setHolderName(String name){
        tv_holderInfoDetailHolderListHolderName.setText(name);
        tv_holderInfoDetailHolderListHolderName.setBackgroundColor(0xfff0f0f0);
    }

    @Override
    public void setStockCode(ArrayList<String> codes) {
        String str_codes = "";
        for(int i = 0; i < codes.size(); i++) {
            str_codes += codes.get(i) + "\n";
        }
        tv_holderInfoDetailHolderListStockCode.setText(str_codes);
        tv_holderInfoDetailHolderListStockCode.setBackgroundColor(0xfff0f0f0);
    }

    @Override
    public void setPieChartData(ArrayList<PieEntry> tempEntries){
        MyPieChart mpc = new MyPieChart(holderPieChart);
        mpc.setCenterText("股东占比");
        mpc.setData(tempEntries);
    }


}
