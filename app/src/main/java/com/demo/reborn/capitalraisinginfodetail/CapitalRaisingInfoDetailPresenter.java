package com.demo.reborn.capitalraisinginfodetail;

import android.os.Debug;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.reborn.Credit;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_FinancingGroupInfo0729;
import com.demo.reborn.data.json.Api1_FinancingInfo0729;
import com.demo.reborn.data.json.Api1_post_common;
import com.github.mikephil.charting.data.Entry;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Nullable;

import io.reactivex.Notification;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.trustScale;
import static com.demo.reborn.Judgement.unitFormat;

public class CapitalRaisingInfoDetailPresenter implements CapitalRaisingInfoDetailContract.Presenter {
    public class deadLineFinancing {
        String deadLine;
        float financing;
        String year;

        public deadLineFinancing(String deadLine, float financing, String year) {
            this.deadLine = deadLine;
            this.financing = financing;
            this.year = year;
        }
    }

    public int MaxValueSize(int a1, int a2, int a3, int a4) {
        if (a1 >= a2 && a1 >= a3 && a1 >= a4)
            return a1;
        if (a2 >= a1 && a2 >= a3 && a2 >= a4)
            return a2;
        if (a3 >= a1 && a3 >= a2 && a3 >= a4)
            return a3;
        if (a4 >= a1 && a4 >= a2 && a4 >= a3)
            return a4;
        return 0;
    }

    private final CapitalRaisingInfoDetailContract.View mView;
    private final FinancialDataRepository mData;
    public long statTime_g = 0;
    public long statTime_c = 0;
    public long statTime = 0;

    public CapitalRaisingInfoDetailPresenter(CapitalRaisingInfoDetailContract.View view) {
        mData = FinancialDataRepository.getINSTANCE();
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    public void getInfo() {

        mData.get_Api1_FinancingInfo0729(mData.getCompanyId())
                .subscribe(new Observer<Response<Api1_FinancingInfo0729>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.setProgressBar(true);
                        statTime = System.currentTimeMillis();
                        Log.e("企业融资情况开始时间", " is " + statTime + "ms");
                    }

                    @Override
                    public void onNext(@NonNull Response<Api1_FinancingInfo0729> api1_financingInfo0729) {
//                Log.d("gid", api1_financingInfo0729.body().g_id);
                        //集团信息
                        statTime_c = System.currentTimeMillis();
                        Log.e("企业融资情况开始时间next", " is " + statTime_c + "ms");
                        if (notNull(api1_financingInfo0729.body().error).equals("0")) {
                            if (!isNullOrEmpty(api1_financingInfo0729.body().g_name) || !isNullOrEmpty(api1_financingInfo0729.body().g_id)) {
                                if (isNullOrEmpty(api1_financingInfo0729.body().g_id))
                                    api1_financingInfo0729.body().g_id = "";
                                if (isNullOrEmpty(api1_financingInfo0729.body().g_name))
                                    api1_financingInfo0729.body().g_name = "";
                                mData.get_Api1_FinancingGroupInfo0729(api1_financingInfo0729.body().g_id, api1_financingInfo0729.body().g_name)
                                        .subscribe(new Observer<Response<Api1_FinancingGroupInfo0729>>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                statTime_g = System.currentTimeMillis();
                                                Log.e("集团融资情况开始时间", "time is " + statTime_g + "ms");
                                                Log.e("g_id", api1_financingInfo0729.body().g_id);
                                                Log.d("g_name", api1_financingInfo0729.body().g_name);

                                            }

                                            @Override
                                            public void onNext(@NonNull Response<Api1_FinancingGroupInfo0729> api1_financingGroupInfo0729) {
                                                //Debug.startMethodTracing("tracefilename");
                                                if (notNull(api1_financingGroupInfo0729.body().error).equals("0")) {
                                                    // Log.d("error", api1_financingGroupInfo0729.body().error);
                                                    //币种排序
                                                    List<Credit> list_currency = new ArrayList<>();
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().credit_total.size(); i++) {
                                                        if (!isNullOrEmpty(api1_financingGroupInfo0729.body().credit_total.get(i).currency)) {
                                                            if (notNull(api1_financingGroupInfo0729.body().credit_total.get(i).currency).equals("人民币元") || notNull(api1_financingGroupInfo0729.body().credit_total.get(i).currency).equals("人民币")) {
                                                                list_currency.add(new Credit(1, api1_financingGroupInfo0729.body().credit_total.get(i).currency,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).used,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).amount, api1_financingGroupInfo0729.body().credit_total.get(i).unused));
                                                            } else if (notNull(api1_financingGroupInfo0729.body().credit_total.get(i).currency).equals("美元")) {
                                                                list_currency.add(new Credit(2, api1_financingGroupInfo0729.body().credit_total.get(i).currency,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).used, api1_financingGroupInfo0729.body().credit_total.get(i).amount,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).unused));
                                                            } else if (notNull(api1_financingGroupInfo0729.body().credit_total.get(i).currency).equals("欧元")) {
                                                                list_currency.add(new Credit(3, api1_financingGroupInfo0729.body().credit_total.get(i).currency,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).used,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).amount, api1_financingGroupInfo0729.body().credit_total.get(i).unused));
                                                            } else if (notNull(api1_financingGroupInfo0729.body().credit_total.get(i).currency).equals("日元")) {
                                                                list_currency.add(new Credit(4, api1_financingGroupInfo0729.body().credit_total.get(i).currency,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).used,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).amount, api1_financingGroupInfo0729.body().credit_total.get(i).unused));
                                                            } else {
                                                                list_currency.add(new Credit(5, api1_financingGroupInfo0729.body().credit_total.get(i).currency,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).used,
                                                                        api1_financingGroupInfo0729.body().credit_total.get(i).amount, api1_financingGroupInfo0729.body().credit_total.get(i).unused));
                                                            }
                                                        }

                                                    }
                                                    //实现排序
                                                    Collections.sort(list_currency, new Comparator<Credit>() {
                                                        @Override
                                                        public int compare(Credit u1, Credit u2) {
                                                            if (u1.getPriority() < u2.getPriority()) {
                                                                //return -1:即为正序排序
                                                                return -1;
                                                            } else if (u1.getPriority() == u2.getPriority()) {
                                                                return 0;
                                                            } else {
                                                                //return 1: 即为倒序排序
                                                                return 1;
                                                            }
                                                        }
                                                    });

                                                    //融资情况概览
                                                    String temp_financing = "";
                                                    DecimalFormat df = new DecimalFormat("0.00");
                                                    DecimalFormat decimalFormat = new DecimalFormat("0");
                                                    if (!isNullOrEmpty(api1_financingGroupInfo0729.body().credit_total)) {
                                                        temp_financing += notNull(api1_financingGroupInfo0729.body().group_name);
                                                        for (Credit credit : list_currency) {
                                                            temp_financing += credit.getCurrency() + "总授信" + (isNullOrEmpty(credit.getAmount()) ? "-" : df.format(Double.parseDouble(credit.getAmount()))) + "亿元，未使用的授信额度为";
                                                            temp_financing += isNullOrEmpty(credit.getUnused()) ? "-" : df.format(Double.parseDouble(credit.getUnused())) + "亿元；";
                                                        }
                                                    }
                                                    //无总授信信息就不显示
                                                    int count = 0;//银行机构数量
                                                    if (api1_financingGroupInfo0729.body().debt.last.size() != 0) {

                                                        temp_financing += "根据公开信息显示，" + notNull(api1_financingGroupInfo0729.body().group_name) + "及其下属公司合计存量债券" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.total))) + "亿元，短期借款" + (isNullOrEmpty(api1_financingGroupInfo0729.body().debt.last.get(1)) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().debt.last.get(1)) / 100000000)) + "亿元，长期借款" +
                                                                (isNullOrEmpty(api1_financingGroupInfo0729.body().debt.last.get(4)) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().debt.last.get(4)) / 100000000)) + "亿元，发行股票募集资金" + (isNullOrEmpty(api1_financingGroupInfo0729.body().share_financing_total.total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().share_financing_total.total) / 10000)) + "亿元。";
                                                    }
                                                    List<String> bank = new ArrayList<>();

                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().credit_detail.size(); i++) {
                                                        if (i == 10) break;
                                                        if (!notNull(api1_financingGroupInfo0729.body().credit_detail.get(i).bank).equals("合计"))
                                                            bank.add(api1_financingGroupInfo0729.body().credit_detail.get(i).bank);
                                                    }
                                                    if (bank.size() != 0) {
                                                        temp_financing += "该集团合作的前10名金融机构为：";
                                                        for (int i = 0; i < bank.size(); i++) {
                                                            if (i < bank.size() - 1)
                                                                temp_financing += bank.get(i) + "、";
                                                            else if (i == bank.size() - 1)
                                                                temp_financing += bank.get(i) + "。";
                                                        }
                                                    }
                                                    mView.setGroupCapitalRaisingOverviewInfo(temp_financing);


                                                    //贷款银行的授信情况
                                                    SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日");
                                                    long now = System.currentTimeMillis();
                                                    String view_time = sd.format(now);

                                                    String temp_credit = "";
                                                    List<Map<String, String>> currency_credit = new ArrayList<>();
                                                    if (api1_financingGroupInfo0729.body().credit_total.size() != 0)
                                                        temp_credit += "截至" + api1_financingGroupInfo0729.body().credit_total.get(0).time + "日，" + notNull(api1_financingGroupInfo0729.body().group_name) + "主要合作银行的";
                                                    for (Credit credit : list_currency) {

                                                        temp_credit += credit.getCurrency() + "授信总额" + (isNullOrEmpty(credit.getAmount()) ? "-" : df.format(Double.parseDouble(credit.getAmount()))) + "亿元，";
                                                    }
                                                    if (api1_financingGroupInfo0729.body().credit_total.size() != 0)
                                                        temp_credit += "合作的银行主要情况如下：\n";
                                                    mView.setGroupCreditInfo(temp_credit);


                                                    //集团主要合作情况表格

                                                    List<String> list_group_credit = new ArrayList<>();
                                                    List<String> currency = new ArrayList<>();
                                                    List<Map<String, String>> total = new ArrayList<>();//用来存储所有的合计条目
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().credit_detail.size() && !isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).bank); i++) {
                                                        if (!notNull(api1_financingGroupInfo0729.body().credit_detail.get(i).bank).equals("合计") && !isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).currency)) {
                                                            if (currency.size() == 0) {
                                                                currency.add(api1_financingGroupInfo0729.body().credit_detail.get(i).currency);
                                                            } else {
                                                                for (int j = 0; j < currency.size(); j++) {
                                                                    if (notNull(currency.get(j)).equals(api1_financingGroupInfo0729.body().credit_detail.get(i).currency)) {
                                                                        break;
                                                                    } else {
                                                                        if (j == currency.size() - 1) {
                                                                            currency.add(api1_financingGroupInfo0729.body().credit_detail.get(i).currency);
                                                                        }
                                                                    }

                                                                }
                                                            }
                                                        } else if (api1_financingGroupInfo0729.body().credit_detail.get(i).bank.equals("合计") && !isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).currency)) {
                                                            Map<String, String> item_total = new HashMap<>();
                                                            item_total.put("bank", "小计");
                                                            item_total.put("amount", isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).amount) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).amount)));
                                                            item_total.put("used", isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).used) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).used)));
                                                            item_total.put("unused", isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).unused) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).unused)));
                                                            item_total.put("currency", api1_financingGroupInfo0729.body().credit_detail.get(i).currency);
                                                            item_total.put("limit_date", api1_financingGroupInfo0729.body().credit_detail.get(i).limit_date);
                                                            total.add(item_total);
                                                        }
                                                    }
                                                    for (int k = 0; k < currency.size(); k++)
                                                        Log.e("currency", String.valueOf(currency.size()));
                                                    for (int n = 0; n < currency.size(); n++) {//n用来标识币种的个数
                                                        for (int i = 0; i < api1_financingGroupInfo0729.body().credit_detail.size(); i++) {
                                                            if (!api1_financingGroupInfo0729.body().credit_detail.get(i).bank.equals("合计") && notNull(api1_financingGroupInfo0729.body().credit_detail.get(i).currency).equals(currency.get(n))) {
                                                                list_group_credit.add(api1_financingGroupInfo0729.body().credit_detail.get(i).bank);
                                                                list_group_credit.add(isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).amount) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).amount)));
                                                                list_group_credit.add(isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).used) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).used)));
                                                                list_group_credit.add(isNullOrEmpty(api1_financingGroupInfo0729.body().credit_detail.get(i).unused) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().credit_detail.get(i).unused)));
                                                                list_group_credit.add(api1_financingGroupInfo0729.body().credit_detail.get(i).currency);
                                                                list_group_credit.add(api1_financingGroupInfo0729.body().credit_detail.get(i).limit_date);
                                                            }
                                                        }
                                                        for (int s = 0; s < total.size(); s++) {
                                                            Map<String, String> map = total.get(s);
                                                            if (map.get("currency").equals(currency.get(n))) {
                                                                list_group_credit.add(map.get("bank"));
                                                                list_group_credit.add(map.get("amount"));
                                                                list_group_credit.add(map.get("used"));
                                                                list_group_credit.add(map.get("unused"));
                                                                list_group_credit.add(map.get("currency"));
                                                                list_group_credit.add(map.get("limit_date"));
                                                            }
                                                        }
                                                    }
                                                    if (currency.size() == 0 && total.size() != 0) { //只有合计的情况
                                                        for (int s = 0; s < total.size(); s++) {
                                                            Map<String, String> map = total.get(s);
                                                            list_group_credit.add(map.get("bank"));
                                                            list_group_credit.add(map.get("amount"));
                                                            list_group_credit.add(map.get("used"));
                                                            list_group_credit.add(map.get("unused"));
                                                            list_group_credit.add(map.get("currency"));
                                                            list_group_credit.add(map.get("limit_date"));
                                                        }
                                                    }

                                                    mView.setCreditTable(list_group_credit);
                                                    //债券融资情况
                                                    String temp_bond = "";
                                                    if (notNull(api1_financingGroupInfo0729.body().rating.organ).equals(""))
                                                        temp_bond += "";
                                                    else
                                                        temp_bond = "经" + api1_financingGroupInfo0729.body().rating.organ + "评定,申请人的" + (isNullOrEmpty(api1_financingGroupInfo0729.body().rating.date) ? "-" : api1_financingGroupInfo0729.body().rating.date.substring(0, 4)) + "年主体信用等级为" + notNull(api1_financingGroupInfo0729.body().rating.rating) + "。较上年主体信用评级" + notNull(api1_financingGroupInfo0729.body().rating.move) + "。";
                                                    if (!isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.total)) {
                                                        temp_bond += "\n截至" + view_time + "日，集团及其子公司尚处于存续期债券合计为" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.total))) + "亿元，平均期限" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.avg_deadline) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.avg_deadline))) + "月，平均票面利率" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.avg_inter_rate) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.avg_inter_rate))) + "%。集团及其子公司处在存续期内的";
                                                        if (api1_financingGroupInfo0729.body().bond_total.classify.size() != 0) {
                                                            for (int i = 0; i < api1_financingGroupInfo0729.body().bond_total.classify.size(); i++) {
                                                                if (i < api1_financingGroupInfo0729.body().bond_total.classify.size() - 1 && !isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.classify.get(i).classify))
                                                                    temp_bond += api1_financingGroupInfo0729.body().bond_total.classify.get(i).classify + api1_financingGroupInfo0729.body().bond_total.classify.get(i).count + "只，" + "金额共" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total))) + "亿元；";
                                                                else if (i == api1_financingGroupInfo0729.body().bond_total.classify.size() - 1)
                                                                    temp_bond += api1_financingGroupInfo0729.body().bond_total.classify.get(i).classify + api1_financingGroupInfo0729.body().bond_total.classify.get(i).count + "只，" + "金额共" + (isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total))) + "亿元。具体情况如下表：\n";

                                                            }
                                                        }
                                                        mView.setBondCapitalRaisingInfo(temp_bond);

                                                        //债券表格
                                                        List<Map<String, String>> list3 = new ArrayList<>();
                                                        if (api1_financingGroupInfo0729.body().bond_detail.size() != 0) {
                                                            for (int i = 0; i < api1_financingGroupInfo0729.body().bond_detail.size(); i++) {
                                                                Map<String, String> map1 = new HashMap<>();
                                                                map1.put("debt_subject", api1_financingGroupInfo0729.body().bond_detail.get(i).debt_subject);
                                                                map1.put("list_date", api1_financingGroupInfo0729.body().bond_detail.get(i).list_date);
                                                                map1.put("deadline", isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(i).deadline) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_detail.get(i).deadline)));
                                                                map1.put("total", isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_detail.get(i).total)));
                                                                map1.put("lead_underwriter", api1_financingGroupInfo0729.body().bond_detail.get(i).lead_underwriter);
                                                                map1.put("classify", api1_financingGroupInfo0729.body().bond_detail.get(i).classify);
                                                                list3.add(map1);
                                                            }
                                                        }
                                                        List<Map<String, String>> list4 = new ArrayList<>();
                                                        for (int i = 0; i < api1_financingGroupInfo0729.body().bond_total.classify.size(); i++) {
                                                            Map<String, String> map1 = new HashMap<>();
                                                            map1.put("name", "小计");
                                                            map1.put("date", "-");
                                                            map1.put("deadline", "-");
                                                            map1.put("total", isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.classify.get(i).total)));
                                                            map1.put("nothing", "-");
                                                            map1.put("classify", api1_financingGroupInfo0729.body().bond_total.classify.get(i).classify);
                                                            list4.add(map1);
                                                        }

                                                        List<Map<String, String>> list5 = new ArrayList<>();
                                                        Map<String, String> map1 = new HashMap<>();
                                                        map1.put("debt_subject", "合计");
                                                        map1.put("list_date", "-");
                                                        map1.put("deadline", isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.avg_deadline) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.avg_deadline)));
                                                        map1.put("total", isNullOrEmpty(api1_financingGroupInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().bond_total.total)));
                                                        map1.put("lead_underwriter", "-");
                                                        map1.put("rest", "-");
                                                        list5.add(map1);
                                                        mView.setBondTable(list3, list4, list5);
                                                    } else {
                                                        temp_bond = "本公司无公开债券信息";
                                                        mView.setBondCapitalRaisingInfo(temp_bond);
                                                    }


                                                    //有息负债情况
                                                    String temp_debt = "";
                                                    if (!isNullOrEmpty(api1_financingGroupInfo0729.body().debt.total))
                                                        temp_debt = "根据公开信息显示，在" + (isNullOrEmpty(api1_financingGroupInfo0729.body().debt.last) ? "" : api1_financingGroupInfo0729.body().debt.last.get(0).substring(0, 4)) + "年，该集团有息负债共" + df.format(Double.parseDouble(api1_financingGroupInfo0729.body().debt.total) / 100000000) + "亿元。";
                                                    else temp_debt = "该集团无公开有息债券信息";
                                                    if (api1_financingGroupInfo0729.body().debt.three_years.size() != 0)
                                                        temp_debt += "近三年及最近一期有息债务具体情况如下表:\n";
                                                    mView.setDebtInfo(temp_debt);

                                                    //有息债券表格一

                                                    List<String> list = new ArrayList<>();
                                                    String latest = "";
                                                    String[] name = {"短期借款", "应付票据", "一年内到期的非流动负债", "长期借款", "应付债款", "应付租赁款", "合计"};
                                                    if (api1_financingGroupInfo0729.body().debt.three_years.size() != 0) {
                                                        for (int i = 0; i < 7; i++) {
                                                            for (int j = 0; j < 2; j++) {
                                                                if (j == 0)
                                                                    list.add(name[i]);
                                                                else {
                                                                    int size = api1_financingGroupInfo0729.body().debt.three_years.size();
                                                                    for (int k = size - 1; k >= 0; k--) {
                                                                        if (i == 6)
                                                                            list.add(unitFormat(api1_financingGroupInfo0729.body().debt.three_years.get(k).get(20)));
                                                                        else
                                                                            list.add(unitFormat(api1_financingGroupInfo0729.body().debt.three_years.get(k).get(i + 1)));
                                                                        if (i == 6)
                                                                            //list.add("");
                                                                            list.add(unitFormat(api1_financingGroupInfo0729.body().debt.three_years.get(k).get(21)));
                                                                        else {
                                                                            list.add(api1_financingGroupInfo0729.body().debt.three_years.get(k).get(i + 14));
                                                                        }

                                                                    }
                                                                    if (size == 1) {
                                                                        list.add("-");
                                                                        list.add("-");
                                                                        list.add("-");
                                                                        list.add("-");
                                                                    } else if (size == 2) {
                                                                        list.add("-");
                                                                        list.add("-");
                                                                    }
//
                                                                    if (api1_financingGroupInfo0729.body().debt.last.size() == 0) {
                                                                        list.add("-");
                                                                        list.add("-");
                                                                    } else {
                                                                        if (i == 6) {
                                                                            list.add(unitFormat(api1_financingGroupInfo0729.body().debt.last.get(19)));
                                                                        } else {
                                                                            list.add(unitFormat(api1_financingGroupInfo0729.body().debt.last.get(i + 1)));
                                                                        }
                                                                        if (i == 6)
                                                                            list.add(api1_financingGroupInfo0729.body().debt.last.get(20));
                                                                        else
                                                                            list.add(api1_financingGroupInfo0729.body().debt.last.get(i + 13));

                                                                        latest = notNull(api1_financingGroupInfo0729.body().debt.last.get(0));

                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }
                                                    mView.setDebtTable(list, latest);


                                                    //现金流表格
                                                    List<String> list_cash = new ArrayList<>();
                                                    String[] name_cash = {"取得借款收到的现金", "发行债券收到的现金", "偿还债务支付的现金", "分配股利支付的现金", "净额"};
                                                    if (api1_financingGroupInfo0729.body().debt.three_years.size() != 0) {
                                                        for (int i = 0; i < 5; i++) {
                                                            for (int j = 0; j < 2; j++) {
                                                                if (j == 0)
                                                                    list_cash.add(name_cash[i]);
                                                                else {
                                                                    int size = api1_financingGroupInfo0729.body().debt.three_years.size();
                                                                    for (int k = size - 1; k >= 0; k--) {
                                                                        if (api1_financingGroupInfo0729.body().debt.three_years.get(k).get(8 + i) != null)
                                                                            list_cash.add(df.format(Double.parseDouble(api1_financingGroupInfo0729.body().debt.three_years.get(k).get(8 + i)) / 100000000));
                                                                        else
                                                                            list_cash.add("-");
                                                                    }

                                                                    if (size == 1) {
                                                                        list_cash.add("-");
                                                                        list_cash.add("-");
                                                                    } else if (size == 2) {
                                                                        list_cash.add("-");
                                                                    }
                                                                    if (api1_financingGroupInfo0729.body().debt.last.size() == 0)
                                                                        list_cash.add("-");
                                                                    else {
                                                                        if (api1_financingGroupInfo0729.body().debt.last.get(i + 8) == null)
                                                                            list_cash.add("-");
                                                                        else
                                                                            list_cash.add(df.format(Double.parseDouble(api1_financingGroupInfo0729.body().debt.last.get(8 + i)) / 100000000));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    mView.setCashFlowTable(list_cash,latest);

                                                    List<Float> total_1 = new ArrayList<>();
                                                    List<Float> total_2 = new ArrayList<>();
                                                    List<Float> total_3 = new ArrayList<>();
                                                    List<Float> total_4 = new ArrayList<>();
                                                    List<Float> price_1 = new ArrayList<>();  //价格即是票面利率
                                                    List<Float> price_2 = new ArrayList<>();
                                                    List<Float> price_3 = new ArrayList<>();
                                                    List<Float> price_4 = new ArrayList<>();
                                                    int minYear = 0;
                                                    int maxYear = 0;
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().bond_detail.size(); i++) {
                                                        if (!isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(i).list_date)) {
                                                            int year = Integer.parseInt(api1_financingGroupInfo0729.body().bond_detail.get(i).list_date.substring(0, 4));
                                                            if (minYear == 0) minYear = year;
                                                            if (maxYear == 0) maxYear = year;
                                                            if (maxYear < year) maxYear = year;
                                                            if (minYear > year) minYear = year;
                                                        }
                                                    }
                                                    for (int i = minYear; i <= maxYear; i++) {
                                                        int num_1 = 0, num_2 = 0, num_3 = 0, num_4 = 0;
                                                        float total_year1 = 0, total_year2 = 0, total_year3 = 0, total_year4 = 0;
                                                        float price_year1 = 0, price_year2 = 0, price_year3 = 0, price_year4 = 0;
                                                        for (int j = 0; j < api1_financingGroupInfo0729.body().bond_detail.size(); j++) {
                                                            if (!isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).list_date) && !isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).deadline)) {
                                                                int year = Integer.parseInt(api1_financingGroupInfo0729.body().bond_detail.get(j).list_date.substring(0, 4));
                                                                if (year == i) {
                                                                    if (Double.parseDouble(api1_financingGroupInfo0729.body().bond_detail.get(j).deadline) / 12 <= 1 && !isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate)) {
                                                                        total_year1 += isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).total) ? 0 : Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).total);
                                                                        price_year1 += Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate);
                                                                        num_1++;
                                                                    } else if (Double.parseDouble(api1_financingGroupInfo0729.body().bond_detail.get(j).deadline) / 12 <= 3 && !isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate)) {
                                                                        total_year2 += isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).total) ? 0 : Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).total);
                                                                        price_year2 += Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate);
                                                                        num_2++;
                                                                    } else if (Double.parseDouble(api1_financingGroupInfo0729.body().bond_detail.get(j).deadline) / 12 <= 5 && !isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate)) {
                                                                        total_year3 += isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).total) ? 0 : Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).total);
                                                                        price_year3 += Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate);
                                                                        num_3++;
                                                                    } else if (!isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate)) {
                                                                        total_year4 += isNullOrEmpty(api1_financingGroupInfo0729.body().bond_detail.get(j).total) ? 0 : Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).total);
                                                                        price_year4 += Float.parseFloat(api1_financingGroupInfo0729.body().bond_detail.get(j).inter_rate);
                                                                        num_4++;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        if (num_1 == 0) {
                                                            total_1.add(0f);
                                                            price_1.add(0f);
                                                        } else {
                                                            total_1.add(total_year1 / num_1);
                                                            price_1.add(price_year1 / num_1);
                                                        }
                                                        if (num_2 == 0) {
                                                            total_2.add(0f);
                                                            price_2.add(0f);
                                                        } else {
                                                            total_2.add(total_year2 / num_2);
                                                            price_2.add(price_year2 / num_2);
                                                        }
                                                        if (num_3 == 0) {
                                                            total_3.add(0f);
                                                            price_3.add(0f);
                                                        } else {
                                                            total_3.add(total_year3 / num_3);
                                                            price_3.add(price_year3 / num_3);
                                                        }
                                                        if (num_4 == 0) {
                                                            total_4.add(0f);
                                                            price_4.add(0f);
                                                        } else {
                                                            total_4.add(total_year4 / num_4);
                                                            price_4.add(price_year4 / num_4);
                                                        }
                                                    }
                                                    ArrayList<String> xValues = new ArrayList<>();
                                                    for (int i = 0; i < 5; i++) {
                                                        // x轴显示的数据，这里默认使用数字下标显示
                                                        xValues.add((i) + ":00");
                                                    }
                                                    ArrayList<Entry> yValues1 = new ArrayList<>();
                                                    ArrayList<Entry> yValues2 = new ArrayList<>();
                                                    ArrayList<Entry> yValues3 = new ArrayList<>();
                                                    ArrayList<Entry> yValues4 = new ArrayList<>();

                                                    for (int i = 0; i < total_1.size(); i++) {
                                                        if (total_1.get(i) != 0.0)
                                                            yValues1.add(new Entry((float) (minYear + i), total_1.get(i)));
                                                        else
                                                            yValues1.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_2.size(); i++) {
                                                        if (total_2.get(i) != 0.0)
                                                            yValues2.add(new Entry((float) (minYear + i), total_2.get(i)));
                                                        else
                                                            yValues2.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_3.size(); i++) {
                                                        if (total_3.get(i) != 0.0)
                                                            yValues3.add(new Entry((float) (minYear + i), total_3.get(i)));
                                                        else
                                                            yValues3.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_4.size(); i++) {
                                                        if (total_4.get(i) != 0.0)
                                                            yValues4.add(new Entry((float) (minYear + i), total_4.get(i)));
                                                        else
                                                            yValues4.add(new Entry((float) (minYear + i), 0f));
                                                    }


                                                    if (yValues1.size() == 0) {
                                                        yValues1.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues2.size() == 0) {
                                                        yValues2.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues3.size() == 0) {
                                                        yValues3.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues4.size() == 0) {
                                                        yValues4.add(new Entry((float) 2015, 0));
                                                    }
                                                    mView.initTotalPriceChart(MaxValueSize(yValues1.size(), yValues2.size(), yValues3.size(), yValues4.size()));
                                                    mView.setLineChartTotalPriceData(yValues1, yValues2, yValues3, yValues4, xValues);

                                                    yValues1 = new ArrayList<>();
                                                    yValues2 = new ArrayList<>();
                                                    yValues3 = new ArrayList<>();
                                                    yValues4 = new ArrayList<>();
                                                    for (int i = 0; i < total_1.size(); i++) {
                                                        if (total_1.get(i) != 0.0)
                                                            yValues1.add(new Entry((float) (minYear + i), price_1.get(i)));
                                                        else
                                                            yValues1.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_2.size(); i++) {
                                                        if (total_2.get(i) != 0.0)
                                                            yValues2.add(new Entry((float) (minYear + i), price_2.get(i)));
                                                        else
                                                            yValues2.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_3.size(); i++) {
                                                        if (total_3.get(i) != 0.0)
                                                            yValues3.add(new Entry((float) (minYear + i), price_3.get(i)));
                                                        else
                                                            yValues3.add(new Entry((float) (minYear + i), 0f));
                                                    }
                                                    for (int i = 0; i < total_4.size(); i++) {
                                                        if (total_4.get(i) != 0.0)
                                                            yValues4.add(new Entry((float) (minYear + i), price_4.get(i)));
                                                        else
                                                            yValues4.add(new Entry((float) (minYear + i), 0f));
                                                    }


                                                    if (yValues1.size() == 0) {
                                                        yValues1.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues2.size() == 0) {
                                                        yValues2.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues3.size() == 0) {
                                                        yValues3.add(new Entry((float) 2015, 0));
                                                    }
                                                    if (yValues4.size() == 0) {
                                                        yValues4.add(new Entry((float) 2015, 0));
                                                    }
                                                    mView.initPriceRateChart(MaxValueSize(yValues1.size(), yValues2.size(), yValues3.size(), yValues4.size()));
                                                    mView.setLineChartPriceRateData(yValues1, yValues2, yValues3, yValues4, xValues);


                                                    //股权融资情况
                                                    String temp_sharing_financing = "";
                                                    if (!notNull(api1_financingGroupInfo0729.body().share_financing_total.listed_num).equals("0")) {
                                                        temp_sharing_financing = "该集团旗下共有" + notNull(api1_financingGroupInfo0729.body().share_financing_total.listed_num) + "家上市公司，" + "分别为";
                                                        for (int i = 0; i < api1_financingGroupInfo0729.body().share_financing_total.listed.size(); i++) {
                                                            if (i < api1_financingGroupInfo0729.body().share_financing_total.listed.size() - 1)
                                                                temp_sharing_financing += notNull(api1_financingGroupInfo0729.body().share_financing_total.listed.get(i)) + "、";
                                                            else if (i == api1_financingGroupInfo0729.body().share_financing_total.listed.size() - 1)
                                                                temp_sharing_financing += notNull(api1_financingGroupInfo0729.body().share_financing_total.listed.get(i)) + "。";
                                                        }
                                                        if (!isNullOrEmpty(api1_financingGroupInfo0729.body().share_financing_total.total)) {
                                                            temp_sharing_financing += "根据公开信息显示，集团旗下上市公司合计通过股票市场融资" + df.format(Double.parseDouble(api1_financingGroupInfo0729.body().share_financing_total.total) / 10000) + "亿元。";
                                                            for (int i = 0; i < api1_financingGroupInfo0729.body().share_financing_total.detail.size(); i++) {
                                                                if (i < api1_financingGroupInfo0729.body().share_financing_total.detail.size() - 1)
                                                                    temp_sharing_financing += "其中" + notNull(api1_financingGroupInfo0729.body().share_financing_total.detail.get(i).get(0)) + "类型股票募集" + df.format(Double.parseDouble(api1_financingGroupInfo0729.body().share_financing_total.detail.get(i).get(1)) / 10000) + "亿元，";
                                                                else if (i == api1_financingGroupInfo0729.body().share_financing_total.detail.size() - 1)
                                                                    temp_sharing_financing += "其中" + notNull(api1_financingGroupInfo0729.body().share_financing_total.detail.get(i).get(0)) + "类型股票募集" + df.format(Double.parseDouble(api1_financingGroupInfo0729.body().share_financing_total.detail.get(i).get(1)) / 10000) + "亿元，股票募集资金情况如下表：\n";

                                                            }
                                                        }
                                                        mView.setEquityFinancing(temp_sharing_financing);


                                                        //股权融资表格
                                                        List<String> list_financing = new ArrayList<>();

                                                        for (int i = 0; i < api1_financingGroupInfo0729.body().share_financing_detail.size(); i++) {

                                                            for (int j = 0; j < api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.size(); j++) {
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).s_name);
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(1));
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(0));
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).address);
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(2));
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(3));
                                                                list_financing.add(isNullOrEmpty(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(4)) ? "-" : df.format(Double.parseDouble(api1_financingGroupInfo0729.body().share_financing_detail.get(i).detail.get(j).get(4)) / 10000));
                                                                list_financing.add(api1_financingGroupInfo0729.body().share_financing_detail.get(i).lead_underwriter);
                                                            }

                                                        }
                                                        mView.setFinancingTable(list_financing);

                                                    } else {
                                                        temp_sharing_financing = "该公司无公开股权融资情况";
                                                        mView.setEquityFinancing(temp_sharing_financing);
                                                    }


                                                    //资产管理计划情况
                                                    String temp_plan = "";
                                                    int trust_count = 0;
                                                    int asset_count = 0;
                                                    int insurance_count = 0;
                                                    if (isNullOrEmpty(api1_financingGroupInfo0729.body().trust.count))
                                                        trust_count = 0;
                                                    else
                                                        trust_count = Integer.parseInt(api1_financingGroupInfo0729.body().trust.count);
                                                    if (isNullOrEmpty(api1_financingGroupInfo0729.body().insurance.count))
                                                        insurance_count = 0;
                                                    else
                                                        insurance_count = Integer.parseInt(api1_financingGroupInfo0729.body().insurance.count);
                                                    if (isNullOrEmpty(api1_financingGroupInfo0729.body().asset.count))
                                                        asset_count = 0;
                                                    else
                                                        asset_count = Integer.parseInt(api1_financingGroupInfo0729.body().asset.count);
                                                    int count_plan = trust_count + insurance_count + asset_count;
                                                    if (count_plan == 0)
                                                        temp_plan = "该公司暂无公开资产管理计划";
                                                    else {
                                                        temp_plan = "截至" + view_time + "根据公开数据及相关大数据显示，" + notNull(api1_financingGroupInfo0729.body().group_name) + "及其下属公司作为最终融资方共发行资产管理计划" +
                                                                count_plan + "支，涉及金额" + notNull(api1_financingGroupInfo0729.body().trust.total) + "，其中信托资管计划" + api1_financingGroupInfo0729.body().trust.count + "支，涉及金额" + notNull(api1_financingGroupInfo0729.body().trust.total) + "；保险资管计划" + api1_financingGroupInfo0729.body().insurance.count + "支。证券资管计划" + api1_financingGroupInfo0729.body().asset.count + "支，具体如下表：";
                                                    }
                                                    mView.setAssetManagementPlanInfo(temp_plan);

                                                    //信托表
                                                    List<String> list_trust = new ArrayList<>();
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().trust.detail.size(); i++) {
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).comp_name);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).company_name);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).t_name);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).estab_dt);
                                                        list_trust.add(trustScale(api1_financingGroupInfo0729.body().trust.detail.get(i).t_scale));
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).t_deadline);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).appli_way);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).in_distr);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).trustee);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).custodian);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).bank);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).t_manag);
                                                        list_trust.add(api1_financingGroupInfo0729.body().trust.detail.get(i).f_used);
                                                    }
                                                    mView.setTrustTable(list_trust);
                                                    //保险表
                                                    List<String> list_insurance = new ArrayList<>();
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().insurance.detail.size(); i++) {
                                                        list_insurance.add(api1_financingGroupInfo0729.body().insurance.detail.get(i).comp_name);
                                                        list_insurance.add(api1_financingGroupInfo0729.body().insurance.detail.get(i).insur_ass_name);
                                                        list_insurance.add(api1_financingGroupInfo0729.body().insurance.detail.get(i).reg_number);
                                                        list_insurance.add(api1_financingGroupInfo0729.body().insurance.detail.get(i).ann_date);
                                                    }
                                                    mView.setInsuranceTable(list_insurance);

                                                    //证券表
                                                    List<String> list_security = new ArrayList<>();
                                                    for (int i = 0; i < api1_financingGroupInfo0729.body().asset.detail.size(); i++) {
                                                        list_security.add(api1_financingGroupInfo0729.body().asset.detail.get(i).company_name);
                                                        list_security.add(api1_financingGroupInfo0729.body().asset.detail.get(i).prod_name);
                                                        list_security.add(api1_financingGroupInfo0729.body().asset.detail.get(i).man_name);
                                                        list_security.add(api1_financingGroupInfo0729.body().asset.detail.get(i).coll_name);
                                                    }
                                                    mView.setSecurityTable(list_security);
                                                    long now_g = System.currentTimeMillis();
                                                    Log.e("集团结束时间", String.valueOf(now_g));
                                                } else if (api1_financingGroupInfo0729.body().error.equals("503")) {
                                                    mView.removeView();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.d("error", e.toString());
                                                mView.setProgressBar(false);
                                            }

                                            @Override
                                            public void onComplete() {
                                                mView.setProgressBar(false);
                                                long now_g = System.currentTimeMillis();
                                                Log.e("集团结束时间", String.valueOf(now_g));
                                                Log.e("集团时间间隔", "timeDiff in rb is " + (now_g - statTime_g) + "ms");
                                                //Debug.stopMethodTracing();
                                            }

                                        });
                            }//gid和gname都为null
                            else {
                                mView.removeView();
                                Log.e("degug", "gid 和gname都为空");
                                mView.setProgressBar(false);
                            }//g_id和g_name都为空

                            //企业信息
                            //币种排序
                            List<Credit> list_currency = new ArrayList<>();
                            for (int i = 0; i < api1_financingInfo0729.body().credit_total.size(); i++) {
                                if (!isNullOrEmpty(api1_financingInfo0729.body().credit_total.get(i).currency)) {
                                    if (notNull(api1_financingInfo0729.body().credit_total.get(i).currency).equals("人民币元") || notNull(api1_financingInfo0729.body().credit_total.get(i).currency).equals("人民币")) {
                                        list_currency.add(new Credit(1, api1_financingInfo0729.body().credit_total.get(i).currency,
                                                api1_financingInfo0729.body().credit_total.get(i).used,
                                                api1_financingInfo0729.body().credit_total.get(i).amount, api1_financingInfo0729.body().credit_total.get(i).unused));
                                    } else if (notNull(api1_financingInfo0729.body().credit_total.get(i).currency).equals("美元")) {
                                        list_currency.add(new Credit(2, api1_financingInfo0729.body().credit_total.get(i).currency,
                                                api1_financingInfo0729.body().credit_total.get(i).used, api1_financingInfo0729.body().credit_total.get(i).amount,
                                                api1_financingInfo0729.body().credit_total.get(i).unused));
                                    } else if (notNull(api1_financingInfo0729.body().credit_total.get(i).currency).equals("欧元")) {
                                        list_currency.add(new Credit(3, api1_financingInfo0729.body().credit_total.get(i).currency,
                                                api1_financingInfo0729.body().credit_total.get(i).used,
                                                api1_financingInfo0729.body().credit_total.get(i).amount, api1_financingInfo0729.body().credit_total.get(i).unused));
                                    } else if (notNull(api1_financingInfo0729.body().credit_total.get(i).currency).equals("日元")) {
                                        list_currency.add(new Credit(4, api1_financingInfo0729.body().credit_total.get(i).currency,
                                                api1_financingInfo0729.body().credit_total.get(i).used,
                                                api1_financingInfo0729.body().credit_total.get(i).amount, api1_financingInfo0729.body().credit_total.get(i).unused));
                                    } else {
                                        list_currency.add(new Credit(5, api1_financingInfo0729.body().credit_total.get(i).currency,
                                                api1_financingInfo0729.body().credit_total.get(i).used,
                                                api1_financingInfo0729.body().credit_total.get(i).amount, api1_financingInfo0729.body().credit_total.get(i).unused));
                                    }
                                }

                            }
                            //实现排序
                            Collections.sort(list_currency, new Comparator<Credit>() {
                                @Override
                                public int compare(Credit u1, Credit u2) {
                                    if (u1.getPriority() < u2.getPriority()) {
                                        //return -1:即为正序排序
                                        return -1;
                                    } else if (u1.getPriority() == u2.getPriority()) {
                                        return 0;
                                    } else {
                                        //return 1: 即为倒序排序
                                        return 1;
                                    }
                                }
                            });
                            //企业融资概览
                            String temp_financing = "";
                            DecimalFormat df = new DecimalFormat("0.00");
                            DecimalFormat decimalFormat = new DecimalFormat("0");
                            if (api1_financingInfo0729.body().credit_total.size() != 0) {
                                temp_financing += notNull(api1_financingInfo0729.body().name);
                                for (Credit credit : list_currency) {
                                    temp_financing += credit.getCurrency() + "总授信" + (isNullOrEmpty(credit.getAmount()) ? "-" : df.format(Double.parseDouble(credit.getAmount()))) + "亿元，";
                                    temp_financing += "用信" + (isNullOrEmpty(credit.getUsed()) ? "-" : df.format(Double.parseDouble(credit.getUsed()))) + "亿元。";

                                }

                            }

                            if (api1_financingInfo0729.body().debt.last.size() != 0 && !isNullOrEmpty(api1_financingInfo0729.body().share_financing_total.total))
                                temp_financing += "根据公开信息显示，该企业合计存量债券" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.total))) + "亿元，短期借款" + (isNullOrEmpty(api1_financingInfo0729.body().debt.last.get(1)) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().debt.last.get(1)) / 100000000)) + "亿元，长期借款" +
                                        (isNullOrEmpty(api1_financingInfo0729.body().debt.last.get(4)) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().debt.last.get(4)) / 100000000)) + "亿元，发行股票募集资金" + (isNullOrEmpty(api1_financingInfo0729.body().share_financing_total.total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().share_financing_total.total) / 10000)) + "亿元。";

                            mView.setCorporateFinanceOverviewInfo(temp_financing);


                            // //授信用信
                            SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd");
                            long now = System.currentTimeMillis();
                            String view_time = sd.format(now);
                            String temp_credit = "";
                            if (api1_financingInfo0729.body().credit_total.size() != 0) {
                                temp_credit = "截至" + api1_financingInfo0729.body().credit_total.get(0).time + "日，申请人主要合作银行的";
                                for (Credit credit : list_currency) {
                                    temp_credit += credit.getCurrency() + "总授信" + (isNullOrEmpty(credit.getAmount()) ? "-" : df.format(Double.parseDouble(credit.getAmount()))) + "亿元，";
                                    temp_credit += "未使用的授信总额" + (isNullOrEmpty(credit.getUnused()) ? "-" : df.format(Double.parseDouble(credit.getUnused()))) + "亿元。";

                                }
                                temp_credit += "合作的主要银行情况如下表：\n";
                            } else
                                temp_credit = "该公司无公开授信用信";
                            mView.setCorporateCreditInfo(temp_credit);


                            //授信情况表格
                            List<String> list_credit = new ArrayList<>();
                            List<String> currency = new ArrayList<>();
                            List<Map<String, String>> total = new ArrayList<>();//用来存储所有的合计条目
                            for (int i = 0; i < api1_financingInfo0729.body().credit_detail.size() && !isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).bank); i++) {
                                if (!api1_financingInfo0729.body().credit_detail.get(i).bank.equals("合计") && !isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).currency)) {
                                    if (currency.size() == 0) {
                                        currency.add(api1_financingInfo0729.body().credit_detail.get(i).currency);
                                    } else {
                                        for (int j = 0; j < currency.size(); j++) {
                                            if (currency.get(j).equals(api1_financingInfo0729.body().credit_detail.get(i).currency)) {
                                                break;
                                            } else {
                                                if (j == currency.size() - 1) {
                                                    currency.add(api1_financingInfo0729.body().credit_detail.get(i).currency);
                                                }
                                            }

                                        }
                                    }
                                } else if (api1_financingInfo0729.body().credit_detail.get(i).bank.equals("合计") && !isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).currency)) {
                                    Map<String, String> item_total = new HashMap<>();
                                    item_total.put("bank", "小计");
                                    item_total.put("amount", isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).amount) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).amount)));
                                    item_total.put("used", isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).used) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).used)));
                                    item_total.put("unused", isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).unused) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).unused)));
                                    item_total.put("currency", api1_financingInfo0729.body().credit_detail.get(i).currency);
                                    item_total.put("limit_date", api1_financingInfo0729.body().credit_detail.get(i).limit_date);
                                    total.add(item_total);
                                }
                            }
                            for (int k = 0; k < currency.size(); k++)
                                Log.e("currency", String.valueOf(currency.size()));
                            for (int n = 0; n < currency.size(); n++) {//n用来标识币种的个数
                                for (int i = 0; i < api1_financingInfo0729.body().credit_detail.size(); i++) {
                                    if (!api1_financingInfo0729.body().credit_detail.get(i).bank.equals("合计") && notNull(api1_financingInfo0729.body().credit_detail.get(i).currency).equals(currency.get(n))) {
                                        list_credit.add(api1_financingInfo0729.body().credit_detail.get(i).bank);
                                        list_credit.add(isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).amount) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).amount)));
                                        list_credit.add(isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).used) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).used)));
                                        list_credit.add(isNullOrEmpty(api1_financingInfo0729.body().credit_detail.get(i).unused) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().credit_detail.get(i).unused)));
                                        list_credit.add(api1_financingInfo0729.body().credit_detail.get(i).currency);
                                        list_credit.add(api1_financingInfo0729.body().credit_detail.get(i).limit_date);
                                    }
                                }
                                for (int s = 0; s < total.size(); s++) {
                                    Map<String, String> map = total.get(s);
                                    if (map.get("currency").equals(currency.get(n))) {
                                        list_credit.add(map.get("bank"));
                                        list_credit.add(map.get("amount"));
                                        list_credit.add(map.get("used"));
                                        list_credit.add(map.get("unused"));
                                        list_credit.add(map.get("currency"));
                                        list_credit.add(map.get("limit_date"));
                                    }
                                }
                            }
                            if (currency.size() == 0 && total.size() != 0) { //只有合计的情况
                                for (int s = 0; s < total.size(); s++) {
                                    Map<String, String> map = total.get(s);
                                    list_credit.add(map.get("bank"));
                                    list_credit.add(map.get("amount"));
                                    list_credit.add(map.get("used"));
                                    list_credit.add(map.get("unused"));
                                    list_credit.add(map.get("currency"));
                                    list_credit.add(map.get("limit_date"));
                                }
                            }

                            mView.setCorporateCreditTable(list_credit);


                            //债券信息
                            String temp_bond = "";
                            if (isNullOrEmpty(api1_financingInfo0729.body().rating.organ))
                                temp_bond += "";
                            else
                                temp_bond = "经" + api1_financingInfo0729.body().rating.organ + "评定,申请人的" + (isNullOrEmpty(api1_financingInfo0729.body().rating.date) ? "-" : api1_financingInfo0729.body().rating.date.substring(0, 4)) + "年主体信用等级为" + notNull(api1_financingInfo0729.body().rating.rating) + "。较上年主体信用评级" + notNull(api1_financingInfo0729.body().rating.move) + "。";
                            if (!isNullOrEmpty(api1_financingInfo0729.body().bond_total.total)) {
                                temp_bond += "\n截至" + view_time + "日，该企业尚处于存续期内债券合计" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.total))) + "亿元，平均期限" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.avg_deadline) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.avg_deadline))) + "月，平均票面利率" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.avg_inter_rate) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.avg_inter_rate))) + "%。集团及其子公司处在存续期内的";
                                if (api1_financingInfo0729.body().bond_total.classify.size() != 0) {
                                    for (int i = 0; i < api1_financingInfo0729.body().bond_total.classify.size(); i++) {
                                        if (i < api1_financingInfo0729.body().bond_total.classify.size() - 1 && !isNullOrEmpty(api1_financingInfo0729.body().bond_total.classify.get(i).classify))
                                            temp_bond += api1_financingInfo0729.body().bond_total.classify.get(i).classify + api1_financingInfo0729.body().bond_total.classify.get(i).count + "只，" + "金额共" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.classify.get(i).total))) + "亿元；";
                                        else if (i == api1_financingInfo0729.body().bond_total.classify.size() - 1)
                                            temp_bond += api1_financingInfo0729.body().bond_total.classify.get(i).classify + api1_financingInfo0729.body().bond_total.classify.get(i).count + "只，" + "金额共" + (isNullOrEmpty(api1_financingInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.classify.get(i).total))) + "亿元。具体情况如下表：\n";

                                    }
                                }
                                mView.setCorporateBondInfo(temp_bond);

                                //债券表格
                                List<Map<String, String>> list3 = new ArrayList<>();
                                for (int i = 0; i < api1_financingInfo0729.body().bond_detail.size(); i++) {
                                    Map<String, String> map1 = new HashMap<>();
                                    map1.put("debt_subject", api1_financingInfo0729.body().bond_detail.get(i).debt_subject);
                                    map1.put("list_date", api1_financingInfo0729.body().bond_detail.get(i).list_date);
                                    map1.put("deadline", isNullOrEmpty(api1_financingInfo0729.body().bond_detail.get(i).deadline) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_detail.get(i).deadline)));
                                    map1.put("total", isNullOrEmpty(api1_financingInfo0729.body().bond_detail.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_detail.get(i).total)));
                                    map1.put("lead_underwriter", api1_financingInfo0729.body().bond_detail.get(i).lead_underwriter);
                                    map1.put("classify", api1_financingInfo0729.body().bond_detail.get(i).classify);
                                    list3.add(map1);
                                }
                                List<Map<String, String>> list4 = new ArrayList<>();
                                for (int i = 0; i < api1_financingInfo0729.body().bond_total.classify.size(); i++) {
                                    Map<String, String> map1 = new HashMap<>();
                                    map1.put("name", "小计");
                                    map1.put("date", "-");
                                    map1.put("deadline", "-");
                                    map1.put("total", isNullOrEmpty(api1_financingInfo0729.body().bond_total.classify.get(i).total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.classify.get(i).total)));
                                    map1.put("nothing", "-");
                                    map1.put("classify", api1_financingInfo0729.body().bond_total.classify.get(i).classify);
                                    list4.add(map1);
                                }

                                List<Map<String, String>> list5 = new ArrayList<>();
                                Map<String, String> map1 = new HashMap<>();
                                map1.put("debt_subject", "合计");
                                map1.put("list_date", "-");
                                map1.put("deadline", (isNullOrEmpty(api1_financingInfo0729.body().bond_total.avg_deadline) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.avg_deadline))));
                                map1.put("total", (isNullOrEmpty(api1_financingInfo0729.body().bond_total.total) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().bond_total.total))));
                                map1.put("lead_underwriter", "-");
                                map1.put("rest", "-");
                                list5.add(map1);
                                mView.setCorporateBondTable(list3, list4, list5);
                            } else {
                                temp_bond = "该公司无公开债券信息";
                                mView.setCorporateBondInfo(temp_bond);
                            }


                            //企业股权融资
                            String temp_sharing_financing = "";
                            if (!isNullOrEmpty(api1_financingInfo0729.body().share_financing_total.total)) {
                                temp_sharing_financing = "根据公开信息显示，企业合计通过股票市场融资";
                                temp_sharing_financing += df.format(Double.parseDouble(api1_financingInfo0729.body().share_financing_total.total) / 10000) + "亿元。";
                            }


                            //股权融资情况
                            List<String> list_financing = new ArrayList<>();
                            if (api1_financingInfo0729.body().share_financing_detail.size() != 0) {
                                temp_sharing_financing += "股票募集资金情况如下：";
                                for (int i = 0; i < api1_financingInfo0729.body().share_financing_detail.size(); i++) {
                                    for (int j = 0; j < api1_financingInfo0729.body().share_financing_detail.get(i).detail.size(); j++) {
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).s_name);
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(1));
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(0));
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).address);
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(2));
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(3));
                                        list_financing.add(isNullOrEmpty(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(4)) ? "-" : df.format(Double.parseDouble(api1_financingInfo0729.body().share_financing_detail.get(i).detail.get(j).get(4)) / 10000));
                                        list_financing.add(api1_financingInfo0729.body().share_financing_detail.get(i).lead_underwriter);

                                    }


                                }
                                mView.setCorporateFinancingTable(list_financing);
                            }
                            if (isNullOrEmpty(api1_financingInfo0729.body().share_financing_total.total) && api1_financingInfo0729.body().share_financing_detail.size() == 0)
                                temp_sharing_financing += "该公司暂无公开股权融资信息";
                            mView.setCorporateEquityFinancingInfo(temp_sharing_financing);


                            //企业有息债券
                            String temp_debt = "";
                            if (!isNullOrEmpty(api1_financingInfo0729.body().debt.total))
                                temp_debt = "根据公开数据显示，在" + (isNullOrEmpty(api1_financingInfo0729.body().debt.last) ? "" : api1_financingInfo0729.body().debt.last.get(0).substring(0, 4)) + "年,该企业有息负债共" + unitFormat(api1_financingInfo0729.body().debt.total) + "元。";
                            else
                                temp_debt = "该企业无公开有息债券信息";
                            if (api1_financingInfo0729.body().debt.three_years.size() != 0)
                                temp_debt += "近三年及最近一期有息债务具体情况如下表:\n";

                            mView.setCorporateDebtInfo(temp_debt);

                            //有息负债表
                            List<String> list = new ArrayList<>();
                            String latest = "";
                            String[] name = {"短期借款", "应付票据", "一年内到期的非流动负债", "长期借款", "应付债款", "应付租赁款", "合计"};
                            if (api1_financingInfo0729.body().debt.three_years.size() != 0) {
                                for (int i = 0; i < 7; i++) {
                                    for (int j = 0; j < 2; j++) {
                                        if (j == 0)
                                            list.add(name[i]);
                                        else {
                                            int size = api1_financingInfo0729.body().debt.three_years.size();
                                            for (int k = size - 1; k >= 0; k--) {
                                                if (i == 6)
                                                    list.add(unitFormat(api1_financingInfo0729.body().debt.three_years.get(k).get(20)));
                                                else
                                                    list.add(unitFormat(api1_financingInfo0729.body().debt.three_years.get(k).get(i + 1)));
                                                if (i == 6)
                                                    list.add(api1_financingInfo0729.body().debt.three_years.get(k).get(21));
                                                else {
                                                    list.add(api1_financingInfo0729.body().debt.three_years.get(k).get(i + 14));
                                                }
                                            }
                                            if (size == 1) {
                                                list.add("-");
                                                list.add("-");
                                                list.add("-");
                                                list.add("-");
                                            } else if (size == 2) {
                                                list.add("-");
                                                list.add("-");
                                            }

                                            if (api1_financingInfo0729.body().debt.last.size() == 0) {
                                                list.add("-");
                                                list.add("-");
                                            } else {
                                                if (i == 6)
                                                    list.add(unitFormat(api1_financingInfo0729.body().debt.last.get(19)));
                                                else
                                                    list.add(unitFormat(api1_financingInfo0729.body().debt.last.get(i + 1)));
                                                if (i == 6)
                                                    list.add(api1_financingInfo0729.body().debt.last.get(20));
                                                else {
                                                    list.add(api1_financingInfo0729.body().debt.last.get(i + 13));
                                                }
                                                latest = notNull(api1_financingInfo0729.body().debt.last.get(0));
                                            }
                                        }

                                    }
                                }
                            }
                            mView.setCorporateDebtTable(list,latest);


                            //现金流表
                            List<String> list_cash = new ArrayList<>();
                            String[] name_cash = {"取得借款收到的现金", "发行债券收到的现金", "偿还债务支付的现金", "分配股利支付的现金", "净额"};
                            if (api1_financingInfo0729.body().debt.three_years.size() != 0) {
                                for (int i = 0; i < 5; i++) {
                                    for (int j = 0; j < 2; j++) {
                                        if (j == 0)
                                            list_cash.add(name_cash[i]);
                                        else {
                                            int size = api1_financingInfo0729.body().debt.three_years.size();
                                            for (int k = size - 1; k >= 0; k--) {
                                                if (api1_financingInfo0729.body().debt.three_years.get(k).get(8 + i) != null)
                                                    list_cash.add(df.format(Double.parseDouble(api1_financingInfo0729.body().debt.three_years.get(k).get(8 + i)) / 100000000));
                                                else
                                                    list_cash.add("-");
                                            }

                                            if (size == 1) {
                                                list_cash.add("-");
                                                list_cash.add("-");
                                            } else if (size == 2) {
                                                list_cash.add("-");
                                            }
                                            if (api1_financingInfo0729.body().debt.last.size() == 0)
                                                list_cash.add("-");
                                            else {
                                                if (api1_financingInfo0729.body().debt.last.get(i + 8) == null)
                                                    list_cash.add("-");
                                                else
                                                    list_cash.add(df.format(Double.parseDouble(api1_financingInfo0729.body().debt.last.get(8 + i)) / 100000000));
                                            }

                                        }
                                    }
                                }

                            }
                            mView.setCorporateCashFlowTable(list_cash,latest);


                            //资产管理计划

                            String temp_plan = "";
                            int trust_count = 0;
                            int asset_count = 0;
                            int insurance_count = 0;
                            if (isNullOrEmpty(api1_financingInfo0729.body().trust.count))
                                trust_count = 0;
                            else
                                trust_count = Integer.parseInt(api1_financingInfo0729.body().trust.count);
                            if (isNullOrEmpty(api1_financingInfo0729.body().insurance.count))
                                insurance_count = 0;
                            else
                                insurance_count = Integer.parseInt(api1_financingInfo0729.body().insurance.count);
                            if (isNullOrEmpty(api1_financingInfo0729.body().asset.count))
                                asset_count = 0;
                            else
                                asset_count = Integer.parseInt(api1_financingInfo0729.body().asset.count);
                            int count = trust_count + insurance_count + asset_count;
                            if (count == 0)
                                temp_plan = "该公司暂无公开资产管理计划";
                            else {
                                temp_plan = "截至" + view_time + "根据公开数据及相关大数据显示，" + notNull(api1_financingInfo0729.body().name) + "作为最终融资方共发行资产管理计划" +
                                        count + "支，涉及金额" + notNull(api1_financingInfo0729.body().trust.total) + "；信托资管计划" + api1_financingInfo0729.body().trust.count + "支。保险资管计划" + api1_financingInfo0729.body().insurance.count +
                                        "支。证券资管计划" + api1_financingInfo0729.body().asset.count + "支，具体如下表：";
                            }
                            mView.setCorporateAssetManagementPlanInfo(temp_plan);
                            List<String> list_trust = new ArrayList<>();
                            for (int i = 0; i < api1_financingInfo0729.body().trust.detail.size(); i++) {
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).comp_name);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).company_name);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).t_name);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).estab_dt);
                                list_trust.add(trustScale(api1_financingInfo0729.body().trust.detail.get(i).t_scale));
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).t_deadline);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).appli_way);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).in_distr);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).trustee);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).custodian);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).bank);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).t_manag);
                                list_trust.add(api1_financingInfo0729.body().trust.detail.get(i).f_used);
                            }
                            mView.setCorporateTrustTable(list_trust);
                            //保险表
                            List<String> list_insurance = new ArrayList<>();
                            for (int i = 0; i < api1_financingInfo0729.body().insurance.detail.size(); i++) {
                                list_insurance.add(api1_financingInfo0729.body().insurance.detail.get(i).comp_name);
                                list_insurance.add(api1_financingInfo0729.body().insurance.detail.get(i).insur_ass_name);
                                list_insurance.add(api1_financingInfo0729.body().insurance.detail.get(i).reg_number);
                                list_insurance.add(api1_financingInfo0729.body().insurance.detail.get(i).ann_date);
                            }
                            mView.setCorporateInsuranceTable(list_insurance);

                            //证券表
                            List<String> list_security = new ArrayList<>();
                            for (int i = 0; i < api1_financingInfo0729.body().asset.detail.size(); i++) {
                                list_security.add(api1_financingInfo0729.body().asset.detail.get(i).company_name);
                                list_security.add(api1_financingInfo0729.body().asset.detail.get(i).prod_name);
                                list_security.add(api1_financingInfo0729.body().asset.detail.get(i).man_name);
                                list_security.add(api1_financingInfo0729.body().asset.detail.get(i).coll_name);
                            }
                            mView.setCorporateSecurityTable(list_security);
                            long now_c = System.currentTimeMillis();
                            Log.e("企业结束时间next", String.valueOf(now_c));
                        } else if (

                                notNull(api1_financingInfo0729.body().error).

                                        equals("400")) {
                            mView.accessDenied();
                        }

//
                    }


                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e("error_c", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        long now_c = System.currentTimeMillis();
                        Log.e("企业结束时间", String.valueOf(now_c));
                        Log.e("企业时间间隔", "timeDiff in rb is " + (now_c - statTime) + "ms");
                    }
                });
    }

    @Override
    public void setBehavior(String startTime, String endTime) {
        mData.post_Browse_Recode("3", "2", "11", mData.getCompanyId(), startTime, endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if (api1_post_common.headers().get("Set-Cookie") != null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_融资详情", api1_post_common.body().info);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}


