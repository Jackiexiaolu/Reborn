package com.demo.reborn.homepage;


import android.widget.ImageView;
import android.widget.TextView;

import com.demo.reborn.Judgement;
import com.demo.reborn.R;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_BaseInfo;
import com.demo.reborn.data.json.Api1_FinancingIndex;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;

public class HomePagePresenter implements HomePageContract.Presenter {

    private HomePageContract.View mView;
    private final FinancialDataRepository mData;
    Judgement authority = new Judgement();


    public HomePagePresenter(HomePageContract.View view) {
        mData = FinancialDataRepository.getINSTANCE();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        int[] drawable = {R.drawable.toutiao1, R.drawable.toutiao2,};
        String[] title = {"百度地震", "4月销售面积环比跌20%"};
        String[] content = {"百度宣布集团总裁兼首席运营官陆奇由于个人和家庭原因，无法继续全职在北京工作，将从7月起不再担任上述职务，但仍将继续担任集团....", "2018年4月，商品房销售面积、销售金额环比均出现20%左右的下降，同比去年面积微降4.1%，金额上涨5.8%，整体商品房均价依旧保持..."};

        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("picture", drawable[i]);
            map.put("title", title[i]);
            map.put("content", content[i]);
            list.add(map);
        }

        return list;

    }
    @Override
    public void doJump(String textview){
        mView.clickJump(textview);
    }
//    @Override
//    public void setLine(String textview){
//        mView.drawLine(textview);
//    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setFromPage(String from){
        mData.setFromPage(from);
    }


    public void LoadNews() {

//        mData.test().subscribe(new Observer<ResponseBody>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                try {
//                    title1=new String(responseBody.bytes(), "utf-8"));
//                    title2=new String(responseBody.bytes(), "utf-8"));
//                    content1=new String(responseBody.bytes(), "utf-8"));
//                    content2=new String(responseBody.bytes(), "utf-8"));
//                    pic1=new Image(responseBody.bytes(), "utf-8"));
//                    pic2=new Image(responseBody.bytes(), "utf-8"));
//                }
//                catch (Exception e){
//
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//            setNewsTitle(title1,title2)；
//            setNewsContent(content1,content2)；
//            setNewsPic(pic1,pic2)；{
//        });

    }







}
