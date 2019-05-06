package com.demo.reborn.financialreport;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.demo.reborn.Credit;
import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_FinancingIndex;
import com.demo.reborn.data.json.Api1_post_common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static com.demo.reborn.Judgement.fmtMicrometer;
import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.pointFormat;
import static com.demo.reborn.Judgement.unitFormat;

public class FinancialReportPresenter implements FinancialReportContract.Presenter {

    private final FinancialReportContract.View mView;
    private final FinancialDataRepository mData;
    private int max = 0;

    private String company_name = "";


    public Bitmap apply(@NonNull ResponseBody responseBody) {
        return BitmapFactory.decodeStream(responseBody.byteStream());
    }


    public FinancialReportPresenter(FinancialReportContract.View view) {
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

    @Override
    public void doJump(String textview) {
        mView.clickJump(textview);
    }

    @Override
    public void destroyEarth() {
        //新接口
        mData.get_Api1_FinancingIndex(mData.getCompanyId()).subscribe(new Observer<Response<Api1_FinancingIndex>>() {


            @Override
            public void onSubscribe(Disposable d) {
                if (mData == null || mData.getCompanyId() == null)
                    Log.d("test", "mData == null");
                else
                    Log.d("test", mData.getCompanyId());

            }

            @Override

            public void onNext(Response<Api1_FinancingIndex> api1_financingIndex) {

                List<Map<String, Object>> product_list = new ArrayList<>();
                List<Map<String, Object>> location_list = new ArrayList<>();
                //公司基本情况+历史沿革
                mView.setCompanyName(notNull(api1_financingIndex.body().name));
                company_name = api1_financingIndex.body().name;
                mView.setLegalPerson(notNull(api1_financingIndex.body().legal_name));
                mView.setGroupName(notNull(api1_financingIndex.body().group_name));
                mView.setHistory(notNull(api1_financingIndex.body().estab_date));
                //股东信息
                mView.setShareholder(notNull(api1_financingIndex.body().first_holder_name), notNull(api1_financingIndex.body().first_holder_rate));
                //高管信息
                String temp_managers = "";
                for (int i = 0; i < api1_financingIndex.body().managers.size(); i++) {
                    if (i < api1_financingIndex.body().managers.size() - 1)
                        temp_managers += api1_financingIndex.body().managers.get(i).name + "、";
                    else
                        temp_managers += api1_financingIndex.body().managers.get(i).name + "等";
                }
                if (api1_financingIndex.body().managers.size() == 0)
                    temp_managers = "该公司暂无高管信息";
                mView.setManager(temp_managers);
                //生产经营情况
                String temp_business = "";
                if (api1_financingIndex.body().business.key_business_1_year.data.size() == 0 || api1_financingIndex.body().business.key_business_1_year.data.get(0).size() == 0)
                    temp_business = "";
                else {
                    temp_business = "公司主营业务分为";
                    for (int i = 0; i < api1_financingIndex.body().business.key_business_1_year.data.size(); i++) {
                        if (api1_financingIndex.body().business.key_business_1_year.data.get(i).size() != 0) {
                            if (i < api1_financingIndex.body().business.key_business_1_year.data.size() - 1 && notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(1)).equals("产品"))
                                temp_business += notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(2)) + "、";
                            else if (api1_financingIndex.body().business.key_business_1_year.data.size() != 0 && i == api1_financingIndex.body().business.key_business_1_year.data.size() - 1 && notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(1)).equals("产品"))
                                temp_business += notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(2));
                        }
                    }
                    temp_business += "等几个板块。";
                    //计算最大业务板块
                    for (int j = 0; j < api1_financingIndex.body().business.key_business_1_year.data.size(); j++) {
                        if (api1_financingIndex.body().business.key_business_1_year.data.get(j).size() != 0)
                            if (api1_financingIndex.body().business.key_business_1_year.data.get(j).get(1).equals("产品") && !isNullOrEmpty(api1_financingIndex.body().business.key_business_1_year.data.get(j).get(4))) {
                                if (Double.parseDouble(api1_financingIndex.body().business.key_business_1_year.data.get(max).get(4).replace("%", "")) < Double.parseDouble(api1_financingIndex.body().business.key_business_1_year.data.get(j).get(4).replace("%", "")))
                                    max = j;
                            }
                    }
                    if (api1_financingIndex.body().business.key_business_1_year.data.get(max).size() != 0) {
                        temp_business += "主营业务中" + api1_financingIndex.body().business.key_business_1_year.data.get(max).get(2) + "占较大业务板块,";
                        temp_business += "占比" + unitFormat(api1_financingIndex.body().business.key_business_1_year.data.get(max).get(4)) + "。";
                    }
                }
                if (isNullOrEmpty(api1_financingIndex.body().business.year) && isNullOrEmpty(api1_financingIndex.body().business.operate_rev))
                    temp_business += "该公司暂无生产经营情况";//年份和营业额都没有数据，则整段表达不显示
                else {
                    temp_business += api1_financingIndex.body().business.year + "实现营业额" + unitFormat(api1_financingIndex.body().business.operate_rev) +
                            "元人民币，";
                    temp_business += "同比";
                    if (api1_financingIndex.body().business.operate_rev_YOY.contains("-"))
                        temp_business += "减少" + unitFormat(api1_financingIndex.body().business.operate_rev_YOY.substring(1)) + "；实现了利润总额" + unitFormat(api1_financingIndex.body().business.profit) +
                                "元人民币，同比";
                    else
                        temp_business += "增长" + unitFormat(api1_financingIndex.body().business.operate_rev_YOY) + "；实现了利润总额" + unitFormat(api1_financingIndex.body().business.profit) +
                                "元人民币，同比";
                    if (api1_financingIndex.body().business.profit_YOY.contains("-"))
                        temp_business += "减少" + unitFormat(api1_financingIndex.body().business.profit_YOY.substring(1)) + "。";
                    else
                        temp_business += "增长" + unitFormat(api1_financingIndex.body().business.profit_YOY) + "。";
                }

                mView.setBusinessInfo(temp_business);
                //生产经营情况表格产品
                for (int i = 0; i < api1_financingIndex.body().business.key_business_1_year.data.size(); i++) {
                    if (api1_financingIndex.body().business.key_business_1_year.data.get(i).size() != 0) {
                        if (notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(1)).equals("产品")) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", unitFormat(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(2)));
                            map.put("rate", unitFormat(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(4)));
                            product_list.add(map);
                        } else if (notNull(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(1)).equals("地区")) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("name", unitFormat(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(2)));
                            map.put("rate", unitFormat(api1_financingIndex.body().business.key_business_1_year.data.get(i).get(4)));
                            location_list.add(map);
                        }
                    }
                }

                mView.setProductList(product_list);
                mView.setLocationList(location_list);
                //财务情况
                String temp_financingState = "";
                if (!isNullOrEmpty(api1_financingIndex.body().financialstatement.year)) {
                    if (api1_financingIndex.body().is_a_h.equals("1")) {
                        temp_financingState += "截止" + api1_financingIndex.body().financialstatement.year + "，公司总资产" + unitFormat(api1_financingIndex.body().financialstatement.sum_asset) +
                                "元，总负债" + unitFormat(api1_financingIndex.body().financialstatement.sum_debt) + "元，所有者权益" +
                                unitFormat(api1_financingIndex.body().financialstatement.sum_owners_equity) + "元，资产负债率" + api1_financingIndex.body().financialstatement.asset_debt_ratio + "。" +
                                api1_financingIndex.body().financialstatement.year + "实现营业额收入" + unitFormat(api1_financingIndex.body().financialstatement.operate_rev) + "元，";
                        if (notNull(api1_financingIndex.body().financialstatement.operate_rev_YOY).contains("-"))
                            temp_financingState += "同比减少" + (isNullOrEmpty(api1_financingIndex.body().financialstatement.operate_rev_YOY)?"-":unitFormat(api1_financingIndex.body().financialstatement.operate_rev_YOY.substring(1))) + "，实现净润";
                        else
                            temp_financingState += "同比增长" + unitFormat(api1_financingIndex.body().financialstatement.operate_rev_YOY) + "，实现净润";
                        temp_financingState += unitFormat(api1_financingIndex.body().financialstatement.net_profit) + "元，";

                        if (notNull(api1_financingIndex.body().financialstatement.net_profit_YOY).contains("-"))
                            temp_financingState += "同比减少" + (isNullOrEmpty(api1_financingIndex.body().financialstatement.net_profit_YOY)?"-":unitFormat(api1_financingIndex.body().financialstatement.net_profit_YOY.substring(1))) + "。";
                        else
                            temp_financingState += "同比增长" + unitFormat(api1_financingIndex.body().financialstatement.net_profit_YOY) + "。";
                    } else {
                        temp_financingState += "截止" + api1_financingIndex.body().financialstatement.year + "，公司总资产" + pointFormat(api1_financingIndex.body().financialstatement.sum_asset) +
                                "亿元，总负债" + pointFormat(api1_financingIndex.body().financialstatement.sum_debt) + "亿元，所有者权益" +
                                pointFormat(api1_financingIndex.body().financialstatement.sum_owners_equity) + "亿元，资产负债率" + api1_financingIndex.body().financialstatement.asset_debt_ratio + "。" +
                                api1_financingIndex.body().financialstatement.year + "实现营业额收入" + pointFormat(api1_financingIndex.body().financialstatement.operate_rev) + "亿元，";

                        if (api1_financingIndex.body().financialstatement.operate_rev_YOY.contains("-"))
                            temp_financingState += "同比减少" + (isNullOrEmpty(api1_financingIndex.body().financialstatement.operate_rev_YOY)?"-":unitFormat(api1_financingIndex.body().financialstatement.operate_rev_YOY.substring(1)))+ "，实现净润";
                        else
                            temp_financingState += "同比增长" + unitFormat(api1_financingIndex.body().financialstatement.operate_rev_YOY) + "，实现净润";
                        temp_financingState += pointFormat(api1_financingIndex.body().financialstatement.net_profit) + "亿元";

                        if (api1_financingIndex.body().financialstatement.net_profit_YOY.contains("-"))
                            temp_financingState += "同比减少" + (isNullOrEmpty(api1_financingIndex.body().financialstatement.net_profit_YOY)?"-":unitFormat(api1_financingIndex.body().financialstatement.net_profit_YOY.substring(1))) + "。";
                        else
                            temp_financingState += "同比增长" + unitFormat(api1_financingIndex.body().financialstatement.net_profit_YOY) + "。";


                    }
                } else
                    temp_financingState = "该公司暂无财务情况";
                mView.setFinancingInfo(temp_financingState);
                //融资情况
                List<Credit> list_currency = new ArrayList<>();
                for (int i = 0; i < api1_financingIndex.body().credit_total.size(); i++) {
                    if (!isNullOrEmpty(api1_financingIndex.body().credit_total.get(i).currency)) {
                        if (notNull(api1_financingIndex.body().credit_total.get(i).currency).equals("人民币元") || notNull(api1_financingIndex.body().credit_total.get(i).currency).equals("人民币")) {
                            list_currency.add(new Credit(1, api1_financingIndex.body().credit_total.get(i).currency,
                                    api1_financingIndex.body().credit_total.get(i).used,
                                    api1_financingIndex.body().credit_total.get(i).amount, api1_financingIndex.body().credit_total.get(i).unused));
                        } else if (notNull(api1_financingIndex.body().credit_total.get(i).currency).equals("美元")) {
                            list_currency.add(new Credit(2, api1_financingIndex.body().credit_total.get(i).currency,
                                    api1_financingIndex.body().credit_total.get(i).used, api1_financingIndex.body().credit_total.get(i).amount,
                                    api1_financingIndex.body().credit_total.get(i).unused));
                        } else if (notNull(api1_financingIndex.body().credit_total.get(i).currency).equals("欧元")) {
                            list_currency.add(new Credit(3, api1_financingIndex.body().credit_total.get(i).currency,
                                    api1_financingIndex.body().credit_total.get(i).used,
                                    api1_financingIndex.body().credit_total.get(i).amount, api1_financingIndex.body().credit_total.get(i).unused));
                        } else if (notNull(api1_financingIndex.body().credit_total.get(i).currency).equals("日元")) {
                            list_currency.add(new Credit(4, api1_financingIndex.body().credit_total.get(i).currency,
                                    api1_financingIndex.body().credit_total.get(i).used,
                                    api1_financingIndex.body().credit_total.get(i).amount, api1_financingIndex.body().credit_total.get(i).unused));
                        } else {
                            list_currency.add(new Credit(5, api1_financingIndex.body().credit_total.get(i).currency,
                                    api1_financingIndex.body().credit_total.get(i).used,
                                    api1_financingIndex.body().credit_total.get(i).amount, api1_financingIndex.body().credit_total.get(i).unused));
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
                String temp_financing = "";
                DecimalFormat df = new DecimalFormat("0.00");
                if (api1_financingIndex.body().credit_total.size() != 0) {
                    temp_financing += "企业";
                    for (Credit credit : list_currency) {
                        temp_financing += credit.getCurrency() + "总授信" + (isNullOrEmpty(credit.getAmount()) ? "-" : df.format(Double.parseDouble(credit.getAmount()))) + "亿元,用信";
                        temp_financing += isNullOrEmpty(credit.getUsed()) ? "-" : df.format(Double.parseDouble(credit.getUsed())) + "亿元。";


                    }

                    if (api1_financingIndex.body().debt.last.size() != 0) {
                        temp_financing += "根据公开信息显示，该企业存量债券" + (isNullOrEmpty(api1_financingIndex.body().bond_total.total) ? "0" : df.format(Double.parseDouble(api1_financingIndex.body().bond_total.total))) + "亿元，短期借款" + (api1_financingIndex.body().debt.last.get(1) == null ? "" : df.format(Double.parseDouble(api1_financingIndex.body().debt.last.get(1)) / 100000000)) + "亿元，长期借款" +
                                (api1_financingIndex.body().debt.last.get(4) == null ? "" : df.format(Double.parseDouble(api1_financingIndex.body().debt.last.get(4)) / 100000000)) + "亿元。";
                    }
                } else
                    temp_financing += "该公司暂无公开数据。";
                mView.setRaisingInfo(temp_financing);

                List<String> list = new ArrayList<>();
                list.add(isNullOrEmpty(api1_financingIndex.body().is_bond) ? "0" : api1_financingIndex.body().is_bond);
                list.add(isNullOrEmpty(api1_financingIndex.body().is_gov) ? "0" : api1_financingIndex.body().is_gov);
                list.add(isNullOrEmpty(api1_financingIndex.body().is_listed) ? "0" : api1_financingIndex.body().is_listed);
                list.add(isNullOrEmpty(api1_financingIndex.body().is_loc) ? "0" : api1_financingIndex.body().is_loc);
                list.add(isNullOrEmpty(api1_financingIndex.body().is_state_owned) ? "0" : api1_financingIndex.body().is_state_owned);
                mView.setTags(list);
            }

            @Override
            public void onError(Throwable e) {
                mView.errorShow();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void showPDF() {
        String path = Environment.getExternalStorageDirectory() + File.separator + company_name + "融资报告.pdf";
        final boolean[] success = {false};
        mData.get_Pdf(mData.getCompanyId(), "pdf").subscribe(new Observer<Response<ResponseBody>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mView.showProgressBar(0);
            }

            @Override
            public void onNext(Response<ResponseBody> response) {
//                mView.setPdf(response.body());

                assert response.body() != null;
                try {
                    File file = new File(path);  //File.createTempFile("testPDF", ".pdf", (Environment.getExternalStorageDirectory()));
                    if (file.exists()) {
                        file.delete();
                    }
                    OutputStream os = new FileOutputStream(file);
                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    InputStream is = response.body().byteStream();
                    mView.showProgressBar(is.available());
                    if (is.available() > 0) {
                        int i = 1;
                        while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                            os.write(buffer, 0, bytesRead);
                            mView.setProgressBar(i * 8192);
                            i++;
                        }
                        success[0] = true;
                    } else {
                        mView.errorShow("该公司PDF尚未生成，请稍后");
                    }
                    is.close();
                } catch (Exception e) {
                    onError(e);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.errorShow("未开通文件读写权限！\n请开通后重启该应用");
            }

            @Override
            public void onComplete() {
                mView.hideProgressBar();
                if (success[0]) {
                    mView.openPDF(path);
                }
            }
        });
    }

    @Override
    public void setClickPDF(String time) {
        mData.post_Click_Search("2", mData.getCompanyId(), time).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                Log.e("info", api1_post_common.body().info);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void setBehavior(String startTime, String endTime) {
        mData.post_Browse_Recode("3", "2", "1", mData.getCompanyId(), startTime, endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if (api1_post_common.headers().get("Set-Cookie") != null) {
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_融报摘要", api1_post_common.body().info);

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
