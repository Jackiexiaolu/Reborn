package com.demo.reborn.searchpage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.reborn.NavigationBar;
import com.demo.reborn.R;
import com.demo.reborn.searchresult.SearchResultActivity;
import com.demo.reborn.homepage.HomePageActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SearchPageFragment extends Fragment implements SearchPageContract.View{
    private SearchPageContract.Presenter mPresenter;
    private EditText  et_search;
    private TextView tv_tip;
    private TextView tv_record;
    private ListView lv_record;
    private ImageView iv_clear;
    private ImageView iv_backhomepage;
    private String searchResult;
    long time;


    public static SearchPageFragment newInstance() {
        return new SearchPageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_page, container, false);

        //控件绑定
        et_search = root.findViewById(R.id.et_search);
        tv_tip = root.findViewById(R.id.tv_tip);
        lv_record = root.findViewById(R.id.lv_record);
        iv_clear = root. findViewById(R.id.iv_clear);
        iv_backhomepage = root.findViewById(R.id.iv_backhomepage);



        setKeyEvent();
        showRecord();
        setListRecord();
        clearRecord();



        iv_backhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomePageActivity.class);
                startActivity(intent);
            }
        });

        return root;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.queryData("",lv_record);
    }

    @Override
    public void setPresenter(SearchPageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 对软键盘的搜索键的监听 & 若是新记录保存在本地缓存中
     * 调用时刻：点击键盘上的搜索键时
     */
    @Override
    public void setKeyEvent() {

        et_search.setOnKeyListener(new View.OnKeyListener() {//监听手机键盘的事件
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    searchResult=et_search.getText().toString().trim();//trim作用为去掉空格
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mPresenter.setSearchRecord(searchResult,df.format(new Date()));
                    boolean Var = mPresenter.hasData(searchResult);
                    if (!Var) {//若搜索记录不存在且不为空则保存至数据库
                        mPresenter.insertData(searchResult);
                        mPresenter.queryData("",lv_record);
                    }
                    //若搜索记录存在则更新搜索记录
                    else{
                        mPresenter.renewData(searchResult);
                    }
                    Intent intent = new Intent(getContext(), SearchResultActivity.class);
                    mPresenter.setFromPage("SearchPage");
                    mPresenter.setSearchResult(searchResult);//将搜索结果传入搜索结果页面
                    startActivity(intent);
                }
                return false;
            }

        });



}

    /**
     * 对本文搜索框的实时监听，实时显示搜索记录
     * 调用时刻：在文本框有输入时
     */
    @Override
    public void showRecord() {

              et_search.addTextChangedListener(new TextWatcher() {
                  @Override
                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                  }

                  @Override
                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                  }

                  @Override
                  public void afterTextChanged(Editable editable) {//搜索框监听到信息输入后，根据字段长度不同显示文本
                      if (editable.toString().trim().length() == 0) {
                          tv_tip.setText("搜索历史");
                      } else {
                          tv_tip.setText("");
                      }
                      String tempName = et_search.getText().toString();
                      // 根据tempName去调接口
                      mPresenter.queryData(tempName,lv_record);
                      //mPresenter.getInfo(tempName,lv_record);


                  }
              });

          }

    /**
     * 对已经显示的搜索记录的点击跳转
     * 调用时刻：点击搜索记录跳转时
     */
    @Override
    public void setListRecord(){

        lv_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_record = view.findViewById(R.id.tv_record);
                String name = tv_record .getText().toString();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mPresenter.setSearchRecord(name,df.format(new Date()));
                et_search.setText(name);
                mPresenter.renewData(name);
                //Toast.makeText(getContext(), "clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), SearchResultActivity.class);
                mPresenter.setFromPage("SearchPage");
                mPresenter.setSearchResult(name);
                startActivity(intent);
            }
        });



        }




    /**
     * 清空搜索历史记录，清空本地缓存文件
     * 调用时刻：点击右边删除图标
     */
    @Override
    public void clearRecord()
    {
        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.deleteData();
                // 模糊搜索空字符 = 显示所有的搜索历史（此时是没有搜索记录的）
                mPresenter.queryData("",lv_record);
            }
        });
    }


}
