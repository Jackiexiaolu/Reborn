package com.demo.reborn.searchresult;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.OnDoubleClickListener;
import com.demo.reborn.R;
import com.demo.reborn.companydetail.CompanyDetailActivity;
import com.demo.reborn.financialreport.FinancialReportActivity;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.opportunityabstract.OpportunityAbstractActivity;
import com.demo.reborn.searchpage.SearchPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class SearchResultFragment extends Fragment implements SearchResultContract.View {

    private SearchResultContract.Presenter mPresenter;
    private ListView lv_company;
    private ScrollView sl_searchResult;
    public String favorite ;
    public int page = 0;
    private static int status = 0;
    public List<Map<String, Object>> mList = new ArrayList<>();
    public View loadCompanyView;//加载视图
    public Toolbar toolbar;
    private String from;
    private FrameLayout fLayout_search_fav;
    private EditText et_search;
    private TextView tv_search;
    private ProgressDialog progressDialog;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");





    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        //initList(mList);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_result, container, false);
        new NavigationBar(root.findViewById(R.id.ll_navigationBar));
        lv_company = root.findViewById(R.id.lv_company);
        sl_searchResult = root.findViewById(R.id.sl_searchResult);
        loadCompanyView = inflater.inflate(R.layout.load_company, null); //加载刷新视图
        loadCompanyView.setVisibility(View.GONE);//设置刷新视图默认情况下是不可见的
        lv_company.addFooterView(loadCompanyView, null, false);
        toolbar = root.findViewById(R.id.tb_financialhomepage);
        TextView tv_searchResult_title = root.findViewById(R.id.tv_searchResult_title);
        fLayout_search_fav = root.findViewById(R.id.fLayout_search_fav);
        et_search = root.findViewById(R.id.et_search);
        tv_search = root.findViewById(R.id.tv_search);


        //设置左侧NavigationIcon点击事件
        toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HomePageActivity.class);
            startActivity(intent);
        });

        from = mPresenter.getFromPage();
        if(from.equals("SearchPage")) {
            tv_searchResult_title.setText("搜索结果");
            status = 0;
            mPresenter.getPerPageInfo(page);
            fLayout_search_fav.removeAllViews();
        }
        else{
            tv_searchResult_title.setText("我收藏的公司");
            mPresenter.getFavouritePageInfo(page);
            status = 1;
            tv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   startActivity(new Intent(getContext(),SearchPageActivity.class));
                }
            });
            et_search.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    startActivity(new Intent(getContext(),SearchPageActivity.class));
                    return false;
                }

            });
        }

        tv_searchResult_title.setOnTouchListener(new OnDoubleClickListener(() -> {
            sl_searchResult.fullScroll(View.FOCUS_UP);
        }));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListener();
        jumpTo();
    }


    @Override
    public void setPresenter(SearchResultContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    public void initList(List<Map<String, Object>> list) {

        mList = list;
        if(list.size() == 0 && status==0 ){//搜索结果未搜到弹出提示
            new AlertDialog.Builder(getContext())
                    .setTitle("查找结果不存在")
                    .setMessage("无法找到该公司，请重试。")
                    .setPositiveButton("确定", null)
                    .show();
        }
        String[] from = {"companyName", "legalPerson","address"};
        int[] to = {R.id.tv_companyItem_companyName, R.id.tv_companyItem_companyLegalPerson,R.id.tv_companyItem_companyAddress};
        if( mPresenter.getFromPage().equals("SearchPage")) {
            final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.search_result_listview_item, from, to) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    ImageView iv_companyItem_star = view.findViewById(R.id.iv_companyItem_star);
                    Map<String,Object> map = mList.get(position);
                    favorite = map.get("flag").toString();
                    if(favorite.equals("1"))//此公司被收藏
                        iv_companyItem_star.setImageResource(R.drawable.ic_star_solid);
                    else
                        iv_companyItem_star.setImageResource(R.drawable.ic_star_border);

                    iv_companyItem_star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favorite = map.get("flag").toString();
                            if (favorite.equals("0")) {//未收藏
                                mPresenter.postAddCollect(map.get("c_id").toString(), map.get("companyName").toString(), "ANDROID",iv_companyItem_star,map);
                            } else {
                                mPresenter.postCancelCollect(map.get("c_id").toString(), map.get("companyName").toString(), "ANDROID",iv_companyItem_star,map);
                            }
                        }
                    });

                    return view;
                }
            };
            lv_company.setAdapter(adapter);
        }else{
            final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.search_result_listview_item, from, to) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    ImageView iv_companyItem_star = view.findViewById(R.id.iv_companyItem_star);
                    iv_companyItem_star.setImageResource(R.drawable.ic_star_solid);
                    Map<String,Object> map = mList.get(position);
                    map.put("flag","1");
                    iv_companyItem_star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favorite = map.get("flag").toString();
                            if (favorite.equals("0")) {//未收藏
                                mPresenter.postAddCollect(map.get("c_id").toString(), map.get("companyName").toString(), "ANDROID",iv_companyItem_star,map);
                            } else {
                                mPresenter.postCancelCollect(map.get("c_id").toString(), map.get("companyName").toString(), "ANDROID",iv_companyItem_star,map);
                            }
                        }
                    });

                    return view;
                }
            };
            lv_company.setAdapter(adapter);

        }



    }


public void setListener()
{
    sl_searchResult.setOnTouchListener((v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                //当文本的measureheight 等于scroll滚动的长度+scroll的height
                if (sl_searchResult.getChildAt(0).getMeasuredHeight() <= sl_searchResult.getScrollY() + sl_searchResult.getHeight()) {
                        //当滑倒最底端时，加载视图出现（正在加载。。。）
                        loadCompanyView.setVisibility(View.VISIBLE);
                        onLoadCompanyInfo();//加载接下来的十条数据

                    }
                break;
            }
        }
        return false;
    });
}




public void onLoadCompanyInfo()
    {

        if(mPresenter.hasMoreInfo(page)==0) {//如果数据加载完则移除加载视图
            loadCompanyView.setVisibility(View.GONE);//刷新界面不可见
            lv_company.removeFooterView(loadCompanyView);
        }
        else {//若数据未加载完
            page++;//请求下面一页
            if(mPresenter.getFromPage().equals("SearchPage"))
                mPresenter.getPerPageInfo(page);
            else
                mPresenter.getFavouritePageInfo(page);
        }


    }


    public void jumpTo()
    {
        lv_company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String,Object> map = mList.get(position);
                String c_id = map.get("c_id").toString();
                mPresenter.setCompanyId(c_id);
                mPresenter.setSearchClick(df.format(new Date()));
                switch (from){
                    case "SearchPage":
                        Intent intentSearch = new Intent(getContext(), CompanyDetailActivity.class);
                        intentSearch.putExtra("favourite", map.get("flag").toString());
                        startActivity(intentSearch);
                        break;
                    case "FinancialReport":
                        Intent intentFinancial = new Intent(getContext(), FinancialReportActivity.class);
                        startActivity(intentFinancial);
                        break;
                    case "Business":
                        Intent intentBusiness = new Intent(getContext(), OpportunityAbstractActivity.class);
                        startActivity(intentBusiness);
                        break;
                }
            }
        });
    }



    @Override
    public void collectSuccess(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_solid);
        map.put("flag","1");


    }

    @Override
    public void collectFailure(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "收藏失败！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_border);
        map.put("flag","0");


    }

    @Override
    public void cancelSuccess(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "取消收藏成功！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_border);
        map.put("flag","0");

    }

    @Override
    public void cancelFailure(ImageView imageView,Map<String,Object> map){
        Toast.makeText(getContext(), "取消收藏失败！", Toast.LENGTH_SHORT).show();
        imageView.setImageResource(R.drawable.ic_star_solid);
        map.put("flag","1");

    }

    public void loginFirst(){
        Toast.makeText(getContext(),"请先登陆！",Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }




}




