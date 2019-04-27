package com.demo.reborn.personalcenter;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.companydetail.CompanyDetailActivity;
import com.demo.reborn.data.json.Api1_Search_Users;
import com.demo.reborn.financialreport.FinancialReportActivity;
import com.demo.reborn.homepage.HomePageActivity;
import com.demo.reborn.opportunityabstract.OpportunityAbstractActivity;
import com.demo.reborn.registerpage.RegisterPageActivity;
import com.demo.reborn.registerpage.RegisterPageContract;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

import static com.demo.reborn.R.drawable.rectangle_bottom_border;
import static com.demo.reborn.historydetail.HistoryDetailPresenter.list;
import static com.google.common.base.Preconditions.checkNotNull;

public class PersonalCenterFragment extends Fragment implements PersonalCenterContract.View{

    private PersonalCenterContract.Presenter mPresenter;
    private TextView tv_personalCenter_fav;
    private TextView tv_personalCenter_message;
    private TextView tv_personalCenter_friends;
    //private TextView tv_personalCenter_intelligence;
    private TextView tv_personalCenter_realName;
    private TextView tv_personalCenter_gender;
    private TextView tv_personalCenter_occupation;
    private TextView tv_personalCenter_company;
    private TextView tv_personalCenter_position;
    private ListView lv_personalCenter_sharedList;
    private ScrollView sv_personalCenter_listScroll;
    private TextView tv_personalCenter_editInfo;
    private ImageView iv_personalCenter_avatar;
    private int currentPage = 1;
    private Button bt_personalCenter_logout;
    private ImageView iv_personalCenter_backIcon;

    protected List<Map<String, Object>> mList = new ArrayList<>();
    public String favorite ;
    private int page = 0;
    private static int status = 0;
    public View loadCompanyView;//加载视图
    public Toolbar toolbar;
    private String from;
    private FrameLayout fLayout_search_fav;
    private EditText et_search;
    private TextView tv_search;
    private String startTime;
    private String endTime;
    private String modifyInfoTime;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RelativeLayout rLayout_version;

    public static PersonalCenterFragment newInstance(){ return new PersonalCenterFragment(); }

    @Override
    public void setPresenter(PersonalCenterContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public void onResume(){
        super.onResume();
        mPresenter.subscribe();
        startTime = df.format(new Date());
    }

    public void onPause(){
        super.onPause();
        mPresenter.unsubscribe();
        endTime = df.format(new Date());
        mPresenter.setBehavior(startTime,endTime);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_personal_center, container, false);
        NavigationBar navigationBar = new NavigationBar((LinearLayout) root.findViewById(R.id.personalCenter_navigationBar));

        tv_personalCenter_fav = root.findViewById(R.id.tv_personalCenter_Fav);
        tv_personalCenter_friends = root.findViewById(R.id.tv_personalCenter_Friends);
        tv_personalCenter_message = root.findViewById(R.id.tv_personalCenter_Message);
        //tv_personalCenter_intelligence = root.findViewById(R.id.tv_personalCenter_Intelligence);
        tv_personalCenter_realName = root.findViewById(R.id.tv_personalCenter_realName);
        tv_personalCenter_gender = root.findViewById(R.id.tv_personalCenter_Gender);
        tv_personalCenter_occupation = root.findViewById(R.id.tv_personalCenter_Occupation);
        tv_personalCenter_company = root.findViewById(R.id.tv_personalCenter_Company);
        tv_personalCenter_position = root.findViewById(R.id.tv_personalCenter_Position);
        lv_personalCenter_sharedList = root.findViewById(R.id.lv_personalCenter_sharedList);
        sv_personalCenter_listScroll = root.findViewById(R.id.sv_personalCenter_listScroll);
        bt_personalCenter_logout = root.findViewById(R.id.bt_perosonCenter_logout);
        tv_personalCenter_editInfo = root.findViewById(R.id.tv_personalCenter_EditInfo);
        iv_personalCenter_avatar = root.findViewById(R.id.iv_personalCenter_Avatar);
        iv_personalCenter_backIcon = root.findViewById(R.id.iv_personalCenter_Back);
        loadCompanyView = inflater.inflate(R.layout.load_company, null); //加载刷新视图
        loadCompanyView.setVisibility(View.GONE);//设置刷新视图默认情况下是不可见的
        mPresenter.setPersonalInfo();
        mPresenter.displayFav();
        mPresenter.getFavouritePageInfo(page);
        rLayout_version = root.findViewById(R.id.rLayout_version);
        tv_personalCenter_fav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currentPage !=1) {
                    mPresenter.displayFav();
                    mPresenter.refreshList();
                    lv_personalCenter_sharedList.setAdapter(null);
                    page = 0;
                    mPresenter.getFavouritePageInfo(page);
                    currentPage = 1;
                }
            }
        });
        tv_personalCenter_friends.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currentPage != 2) {
                    mPresenter.displayFriends();
                    mPresenter.refreshList();
                    lv_personalCenter_sharedList.setAdapter(null);
                    page = 0;
                    mPresenter.getFriendsListPageInfo(page);
                    currentPage = 2;
                }
            }
        });
        tv_personalCenter_message.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(currentPage != 3) {
                    mPresenter.displayMessage();
                    mPresenter.refreshList();
                    lv_personalCenter_sharedList.setAdapter(null);
                    currentPage = 3;
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    TextView tv_version = new TextView(getContext());
                    tv_version.setText("version:1.36.b20190422");
                    tv_version.setTextSize(12);
                    tv_version.setTextColor(Color.rgb(160,160,160));
                    lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    tv_version.setLayoutParams(lp);
                    rLayout_version.addView(tv_version);
                }
            }
        });
//        tv_personalCenter_intelligence.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                if(currentPage != 4) {
//                    mPresenter.displayIntelligence();
//                    mPresenter.refreshList();
//                    lv_personalCenter_sharedList.setAdapter(null);
//                    currentPage = 4;
//                    //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)test.getLayoutParams();
//                    //params.setMargins(0,0,0,30);
//                    //test.setLayoutParams(params);
//                }
//            }
//        });

        bt_personalCenter_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mPresenter.logout();
            }
        });

        tv_personalCenter_editInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                modifyInfoTime = df.format(new Date());
                mPresenter.setModifyPersonalInfo(modifyInfoTime);
                Intent intent = new Intent(getContext(),RegisterPageActivity.class);
                intent.putExtra("from","PersonalCenter");
                startActivity(intent);
            }
        });

        iv_personalCenter_backIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setListener();
        jumpTo();
    }

    public void setBottomBorder(int flag){
        switch(flag) {
            case 1:{
                tv_personalCenter_fav.setBackgroundResource(R.drawable.rectangle_without_border);
                tv_personalCenter_friends.setBackgroundResource(R.drawable.rectangle_bottom_border);
                tv_personalCenter_message.setBackgroundResource(R.drawable.rectangle_bottom_border);
                //tv_personalCenter_intelligence.setBackgroundResource(R.drawable.rectangle_bottom_border);
                break;
            }
            case 2:{
                tv_personalCenter_friends.setBackgroundResource(R.drawable.rectangle_without_border);
                tv_personalCenter_fav.setBackgroundResource(R.drawable.rectangle_bottom_border);
                tv_personalCenter_message.setBackgroundResource(R.drawable.rectangle_bottom_border);
                //tv_personalCenter_intelligence.setBackgroundResource(R.drawable.rectangle_bottom_border);
                break;
            }
            case 3:{
                tv_personalCenter_message.setBackgroundResource(R.drawable.rectangle_without_border);
                tv_personalCenter_fav.setBackgroundResource(R.drawable.rectangle_bottom_border);
                tv_personalCenter_friends.setBackgroundResource(R.drawable.rectangle_bottom_border);
               // tv_personalCenter_intelligence.setBackgroundResource(R.drawable.rectangle_bottom_border);
                break;
            }
            case 4: {
                //tv_personalCenter_intelligence.setBackgroundResource(R.drawable.rectangle_without_border);
                tv_personalCenter_fav.setBackgroundResource(R.drawable.rectangle_bottom_border);
                tv_personalCenter_friends.setBackgroundResource(R.drawable.rectangle_bottom_border);
                tv_personalCenter_message.setBackgroundResource(R.drawable.rectangle_bottom_border);
                break;
            }
        }
    }

    public void initList(List<Map<String, Object>> list) {
        mList = list;
        String[] from = {"companyName", "legalPerson","address"};
        int[] to = {R.id.tv_companyItem_companyName, R.id.tv_companyItem_companyLegalPerson,R.id.tv_companyItem_companyAddress};
            final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.search_result_listview_item, from, to) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    final View view = super.getView(position, convertView, parent);
                    final ImageView iv_companyItem_star = view.findViewById(R.id.iv_companyItem_star);
                    iv_companyItem_star.setImageResource(R.drawable.ic_star_solid);
                    final Map<String,Object> map = mList.get(position);
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
            lv_personalCenter_sharedList.setAdapter(adapter);
    }



    public void initListFriends(List<Map<String, Api1_Search_Users.UserInfo>> list) {
        //initListFriends_Response();
        String[] from = {"real_name", "department"};
        int[] to = {R.id.friends_name, R.id.friends_content};
        final SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.fragment_personal_center_friends_listview_item, from, to) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
              final View view = super.getView(position, convertView, parent);
                ListView listView = (ListView) view.findViewById(R.id.lv_personalCenter_sharedList);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Toast.makeText(getActivity(),"ceshi",Toast.LENGTH_LONG).show();

                    }
                });
                return view;
            }
        };
        lv_personalCenter_sharedList.setAdapter(adapter);
    }



     public void setListener() {
//        sv_personalCenter_listScroll.setOnTouchListener((v, event) -> {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_MOVE: {
//                    break;
//                }
//                case MotionEvent.ACTION_DOWN: {
//                    break;
//                }
//                case MotionEvent.ACTION_UP: {
//                    //当文本的measureheight 等于scroll滚动的长度+scroll的height
//                    if (sv_personalCenter_listScroll.getChildAt(0).getMeasuredHeight() <= sv_personalCenter_listScroll.getScrollY() + sv_personalCenter_listScroll.getHeight()) {
//                        //当滑倒最底端时，加载视图出现（正在加载。。。）
//                        loadCompanyView.setVisibility(View.VISIBLE);
//                        onLoadCompanyInfo();//加载接下来的十条数据
//
//                    }
//                    break;
//                }
//            }
//            return false;
//        });
    }

    public void onLoadCompanyInfo() {

        if(mPresenter.hasMoreInfo(page)==0) {//如果数据加载完则移除加载视图
            loadCompanyView.setVisibility(View.GONE);//刷新界面不可见
            lv_personalCenter_sharedList.removeFooterView(loadCompanyView);
        }
        else {//若数据未加载完
            page++;//请求下面一页
            mPresenter.getFavouritePageInfo(page);
        }


    }

    public void jumpTo() {
        lv_personalCenter_sharedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Map<String,Object> map = mList.get(position);
                String c_id = map.get("c_id").toString();
                mPresenter.setCompanyId(c_id);
                Intent intentSearch = new Intent(getContext(), CompanyDetailActivity.class);
                intentSearch.putExtra("favourite", "1");
                startActivity(intentSearch);
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

    public void logoutJump(){
        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(),HomePageActivity.class);
        startActivity(intent);
    }

    public void setRealName(String realName){
        tv_personalCenter_realName.setText(realName);
    }

    public void setGender(String gender){
        tv_personalCenter_gender.setText(gender);
    }

    public void setOccupation(String occupation){
        tv_personalCenter_occupation.setText(occupation);
    }

    public void setCompany(String company){
        tv_personalCenter_company.setText(company);
    }

    public void setPosition(String position){
        tv_personalCenter_position.setText(position);
    }

}
