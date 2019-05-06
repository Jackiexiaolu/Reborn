package com.demo.reborn.financinginfodetail;

import android.util.Log;
import android.view.textservice.SpellCheckerInfo;

import com.demo.reborn.data.FinancialDataRepository;
import com.demo.reborn.data.json.Api1_FinancialStatement;
import com.demo.reborn.data.json.Api1_post_common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.demo.reborn.Judgement.date2String;
import static com.demo.reborn.Judgement.isNullOrEmpty;
import static com.demo.reborn.Judgement.notNull;
import static com.demo.reborn.Judgement.pointFormat;
import static com.demo.reborn.Judgement.unitFormat;

public class FinancingInfoDetailPresenter implements FinancingInfoDetailContract.Presenter {

    private final FinancingInfoDetailContract.View mView;
    private final FinancialDataRepository mData;

    int columnNum = 0;
    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }


    public FinancingInfoDetailPresenter(FinancingInfoDetailContract.View view) {
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
    public void getInfo() {


        mData.get_Api1_FinancialStatement(mData.getCompanyId(), "2017")
                .subscribe(new Observer<Response<Api1_FinancialStatement>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<Api1_FinancialStatement> api1_financialStatement) {
                        DecimalFormat df = new DecimalFormat("0.00");
                        String temp_summary = "";
                        if (notNull(api1_financialStatement.body().is_a_h).equals("1")) {
                            if (isNullOrEmpty(api1_financialStatement.body().net_profit)) {
                                temp_summary = "该公司暂无公开财务情况";

                            } else {
                                temp_summary = "     截至" + notNull(api1_financialStatement.body().year) + "，公司总资产" + unitFormat(api1_financialStatement.body().sum_asset) + "元，总负债" + unitFormat(api1_financialStatement.body().sum_debt) + "元，";
                                temp_summary += "所有者权益" + unitFormat(api1_financialStatement.body().sum_owners_equity) + "元，资产负债率" + unitFormat(api1_financialStatement.body().asset_debt_ratio) + "。";
                                temp_summary += notNull(api1_financialStatement.body().year) + "实现营业收入" + unitFormat(api1_financialStatement.body().operate_rev) + "元，同比";
                                if (notNull(api1_financialStatement.body().operate_rev_YOY).contains("-"))
                                    temp_summary += "减少" + (isNullOrEmpty(api1_financialStatement.body().operate_rev_YOY)?"-":unitFormat(api1_financialStatement.body().operate_rev_YOY.substring(1))) + "，实现净润";
                                else
                                    temp_summary += "增长" + unitFormat(api1_financialStatement.body().operate_rev_YOY) + "，实现净润";
                                temp_summary += unitFormat(api1_financialStatement.body().net_profit) + "元，同比";
                                if (notNull(api1_financialStatement.body().net_profit_YOY).contains("-"))
                                    temp_summary += "减少" + (isNullOrEmpty(api1_financialStatement.body().net_profit_YOY)?"-":unitFormat(api1_financialStatement.body().net_profit_YOY.substring(1))) + "。";
                                else
                                    temp_summary += "增长" + unitFormat(api1_financialStatement.body().net_profit_YOY) + "。";
                            }
                        } else {
                            if (isNullOrEmpty(api1_financialStatement.body().net_profit)) {
                                temp_summary = "该公司暂无公开财务情况";

                            } else {
                                temp_summary = "   截至" + notNull(api1_financialStatement.body().year) + "，公司总资产" + pointFormat(api1_financialStatement.body().sum_asset) + "亿元，总负债" + pointFormat(api1_financialStatement.body().sum_debt) + "亿元，";
                                temp_summary += "所有者权益" + pointFormat(api1_financialStatement.body().sum_owners_equity) + "亿元，资产负债率" + unitFormat(api1_financialStatement.body().asset_debt_ratio) + "。";
                                temp_summary += notNull(api1_financialStatement.body().year) + "实现营业收入" + pointFormat(api1_financialStatement.body().operate_rev) + "亿元，同比";
                                if (notNull(api1_financialStatement.body().operate_rev_YOY).contains("-"))
                                    temp_summary += "减少" + (isNullOrEmpty(api1_financialStatement.body().operate_rev_YOY)?"-":unitFormat(api1_financialStatement.body().operate_rev_YOY.substring(1))) + "，实现净润";
                                else
                                    temp_summary += "增长" + unitFormat(api1_financialStatement.body().operate_rev_YOY) + "，实现净润";
                                temp_summary += pointFormat(api1_financialStatement.body().net_profit) + "亿元，同比";
                                if (notNull(api1_financialStatement.body().net_profit_YOY).contains("-"))
                                    temp_summary += "减少" + (isNullOrEmpty(api1_financialStatement.body().net_profit_YOY)?"-":unitFormat(api1_financialStatement.body().net_profit_YOY.substring(1))) + "。";
                                else
                                    temp_summary += "增长" + unitFormat(api1_financialStatement.body().net_profit_YOY) + "。";
                            }

                        }
                        mView.setTopSummary(temp_summary);
//                //偿债能力分析
//                String temp_debt_ability = "";
//                if (isNullOrEmpty(api1_financialStatement.body().statement_last.date))
//                    temp_debt_ability = "非上市公司";
//                else {
//
//                    temp_debt_ability = "    " + api1_financialStatement.body().statement_last.date.substring(0, 4) + "年合并报表显示，";
//                    temp_debt_ability += "公司资产总额" + fmtMicrometer(df.format(Double.parseDouble(api1_financialStatement.body().statement_last.sum_asset) / 10000)) + "万元，";
//                    temp_debt_ability += "其中流动资产占比" + df.format((Double.parseDouble(api1_financialStatement.body().statement_last.curr_asset) / Double.parseDouble(api1_financialStatement.body().statement_last.sum_asset)) * 100) + "%，";
//                    temp_debt_ability += "其余主要为长期股权投资、固定资产和在建工程，资产构成总体较为稳定。公司近年来积极扩张发展的战略，使资产规模增长速度较快，同时也让公司的资产负债率有所上升。其余主要为长期股权投资、固定资产和在建工程，资产构成总体较为稳定。公司近年来积极扩张发展的战略，使资产规模增长速度较快，同时也让公司的资产负债率有所上升。";
//                    temp_debt_ability += "截止" + api1_financialStatement.body().statement_last.date.substring(0, 4) + "年末，公司负债总额" + fmtMicrometer(df.format(Double.parseDouble(api1_financialStatement.body().statement_last.sum_liab) / 10000)) + "万元，资产负债率" + api1_financialStatement.body().statement_2_year.get(0).asset_debt_ratio + "，高于行业平均水平" + "xx%。";
//                    temp_debt_ability += "公司流动比率和速动比率近年来总体呈改善势态，截至" + api1_financialStatement.body().statement_last.date.substring(0, 4) + "年末，流动比率" + api1_financialStatement.body().statement_last.current_ratio + "，速动比率" + api1_financialStatement.body().statement_last.quick_ratio + "，低于行业平均值" + "xx。";
//                    temp_debt_ability += "总体而言，公司长、短期债务偿还能力一般。\n";
//
//                }
//                mView.setDebtPaymentAbility(temp_debt_ability);
//
//                //营运能力分析
//                String temp_operation = "";
//                if (isNullOrEmpty(api1_financialStatement.body().statement_last.date))
//                    temp_operation = "非上市公司";
//                else {
//
//                    temp_operation = "    截至" + api1_financialStatement.body().statement_last.date.substring(0, 4) + "年末，";
//                    temp_operation += "公司的应收账款周转率" + api1_financialStatement.body().statement_last.account_rec_turnover + "优于行业平均值" + "（xx），";
//                    temp_operation += "存货周转率" + api1_financialStatement.body().statement_last.inventory_turnover + "低于行业平均值" + "（xx）,";
//                    temp_operation += "总资产周转率" + api1_financialStatement.body().statement_last.total_asset_turnover + "低于行业平均值" + "（xx）。";
//                    temp_operation += "公司营运能力指标总体较为稳健，经营情况正常。\n";
//
//
//                }
//                mView.setOperationAbility(temp_operation);
//
//                //盈利能力分析
//                String temp_profit;
//                if (isNullOrEmpty(api1_financialStatement.body().statement_last.date))
//                    temp_profit = "非上市公司";
//                else {
//
//                    temp_profit = "    截至" + api1_financialStatement.body().statement_last.date.substring(0, 4) + "年末，";
//                    temp_profit += "公司实现主营业务收入" + df.format(Double.parseDouble(api1_financialStatement.body().statement_last.operate_rev) / 100000000) + "亿元，";
//                    temp_profit += "利润总额" + df.format(Double.parseDouble(api1_financialStatement.body().statement_last.sum_profit) / 100000000) + "亿元，";
//                    temp_profit += "净利润" + df.format(Double.parseDouble(api1_financialStatement.body().statement_last.net_profit) / 100000000) + "亿元。";
//                    temp_profit += "总体而言，近年来公司销售规模和利润规模随资产规模的增长稳中有升。近年来公司净资产收益率和总资产报酬率均高于行业平均值。受主营业务特点的影响，主营业务利润率略低于行业平均值。公司盈利情况总体较好。";
//
//
//                }
//                mView.setProfitInfo(temp_profit);
//
//                //现金流分析
//                String temp_cash = "";
//                if (isNullOrEmpty(api1_financialStatement.body().statement_last.net_cash_flow))
//                    temp_cash = "非上市公司";
//                else {
//                    temp_cash = "   近三年，公司现金净流量为";
//                    if (Double.parseDouble(api1_financialStatement.body().statement_last.net_cash_flow) >= 0)
//                        temp_cash += "正,";
//                    else temp_cash += "负，";
//                    temp_cash += "投资性现金净流量近三年为";
//                    if (Double.parseDouble(api1_financialStatement.body().statement_last.net_inv_cash_flow) >= 0)
//                        temp_cash += "正，";
//                    else temp_cash += "负，";
//                    temp_cash += "流出金额总体较大，反映出公司持续保持对外扩张力度；投资性现金净流量近三年均为";
//                    if (Double.parseDouble(api1_financialStatement.body().statement_last.net_fin_cash_flow) >= 0)
//                        temp_cash += "正，";
//                    else temp_cash += "负，";
//                    temp_cash += "是由于公司为满足投资需求而保持了较大的筹资规模。";
//                }
//                mView.setCashFlow(temp_cash);
//
//                //综述
//                String temp_final = "    综上所述，该公司经营情况较好，资产规模持续扩大，财务结构稳定，具备一定的偿债能力；公司盈利水平保持稳定；融资能力较强。";
//
//                mView.setFinalSummary(temp_final);

                        //表格介绍内容
                        String temp_preList = "";
                        if (notNull(api1_financialStatement.body().is_a_h).equals("1")) {
                            int year = api1_financialStatement.body().statement_2_year.size();
                            if (year != 0 && !isNullOrEmpty(api1_financialStatement.body().statement_last.date)) {//最新一期和最近三年都有数据
                                temp_preList += api1_financialStatement.body().name + "提供的" + (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(year - 1).date) ? "-" : api1_financialStatement.body().statement_2_year.get(year - 1).date.substring(0, 4)) + "-" + api1_financialStatement.body().statement_last.date.substring(0, 4) +
                                        "年合并报表，具体情况如下表：\n";
                            }else if(year != 0 && isNullOrEmpty(api1_financialStatement.body().statement_last.date)){//最近三年有数据，最新一期没有数据
                                temp_preList += api1_financialStatement.body().name + "提供的" + (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(year - 1).date) ? "-" : api1_financialStatement.body().statement_2_year.get(year - 1).date.substring(0, 4)) + "-" + api1_financialStatement.body().statement_2_year.get(0).date.substring(0, 4) +
                                        "年合并报表，具体情况如下表：\n";
                            }
                        } else {
                            int year = api1_financialStatement.body().statement_2_year.size();
                            if (year != 0 && !isNullOrEmpty(api1_financialStatement.body().statement_last.report_date)) {
                                temp_preList += api1_financialStatement.body().name + "提供的" + (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(year - 1).report_date) ? "-" : date2String(api1_financialStatement.body().statement_2_year.get(year - 1).report_date).substring(0, 4)) + "-" + date2String(api1_financialStatement.body().statement_last.report_date).substring(0, 4) +
                                        "年合并报表，具体情况如下表：\n";
                            }else if(year != 0 && isNullOrEmpty(api1_financialStatement.body().statement_last.report_date)){//最近三年有数据，最新一期没有数据
                                temp_preList += api1_financialStatement.body().name + "提供的" + (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(year - 1).report_date) ? "-" : api1_financialStatement.body().statement_2_year.get(year - 1).report_date.substring(0, 4)) + "-" + api1_financialStatement.body().statement_2_year.get(0).report_date.substring(0, 4) +
                                        "年合并报表，具体情况如下表：\n";
                            }
                        }

                        mView.setPreList(temp_preList);

                        //表格下方提示
                        String temp_attention = "";
                        if (temp_preList.equals(""))
                            temp_attention = "";
                        else {
                            temp_attention = "注：\n" +
                                    "1.本表中的行业平均值以国资委统计评价局制定的《企业绩效评价标准值2018》为准，各年度指标计算方法与该书相应指标的计算方法保持一致。\n";
                            if(!isNullOrEmpty(api1_financialStatement.body().industry))
                                temp_attention += "2.本表中的行业平均值为" + api1_financialStatement.body().industry + "平均值。\n";
                        }
                        mView.setAttention(temp_attention);

                        //表格
                        List<String> list = new ArrayList<>();//表格内容
                        if (notNull(api1_financialStatement.body().is_a_h).equals("1")) {//非港股
                            List<String> list_item = new ArrayList<>();
                            int year = api1_financialStatement.body().statement_2_year.size();
                            if (!isNullOrEmpty(api1_financialStatement.body().statement_last.date)) {
                                columnNum = year + 3;
                                mView.setChartColumn(columnNum);//有最新一期
                            } else {
                                columnNum = year + 2;
                                mView.setChartColumn(columnNum);//无最新一期
                                Log.e("column", String.valueOf(columnNum));
                            }
                            if( year==0 && isNullOrEmpty(api1_financialStatement.body().statement_last.date)){
                                list.add("");
                            }
                            else {
                                list.add("科目");
                                for (int i = api1_financialStatement.body().statement_2_year.size() - 1; i >= 0; i--)
                                    list.add(isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(i).date)? "-":api1_financialStatement.body().statement_2_year.get(i).date.substring(0, 4) + "年" +
                                            (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(i).date)? "-":api1_financialStatement.body().statement_2_year.get(i).date.substring(5, 7) + "月"));
                                if (!isNullOrEmpty(api1_financialStatement.body().statement_last.date))
                                    list.add(api1_financialStatement.body().statement_last.date.substring(0, 4) + "年" + api1_financialStatement.body().statement_last.date.substring(5, 7) + "月");
                                list.add("行业平均值");
                                String[] name = {"资产总额", "流动资产", "货币资金", "应收账款", "其他应收账款", "预付账款", "存货", "非流动资产", "固定资产", "在建工程", "长期股权投资", "无形资产", "负债总额", "流动负债", "短期借款", "应付票据", "应付账款",
                                        "预收账款", "其他应付账款", "一年内到期非流动负债", "非流动负债", "长期借款", "应付债券", "长期应付债券", "所有者权益", "母公司所有者权益", "主营业收入",
                                        "主营业成本", "税金及附加", "销售费用", "管理费用", "财务费用", "营业利润", "利润总额", "净利润", "归属母公司的净利润", "经营性现金净流量", "投资性现金净流量", "筹资性现金净流量", "现金净流量",
                                        "资产负债率", "流动比率", "速动比率", "应收账款周转率", "存货周转率", "总资产周转率", "主营业务利润", "净资产收益率", "总资产报酬率",
                                        "营业增长率", "总资产增长率", "净利润增长率"};
                                Log.e("name_length", String.valueOf(name.length));

                                //前三年
                                for (int i = api1_financialStatement.body().statement_2_year.size() - 1; i >= 0; i--) {
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_asset));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).curr_asset));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).monetary_fund));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).account_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).other_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).advance_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).inventory));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_non_curr_ass));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).fixed_ass));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).constructiong_project));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).lt_equity_inv));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).intangble_ass));//无形资产
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_liab));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_curr_liab));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).st_borrow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).bill_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).account_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).advance_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).other_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).non_curr_liab_one_year));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_non_liab));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).lt_borrow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).bond_pay));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).lt_account_pay));//长期应付债券
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_she_equity));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_parent_equity));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).operate_rev));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).operate_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).operate_tax));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sale_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).manage_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).fin_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).operate_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).sum_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).net_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).parent_net_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).net_operate_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).net_inv_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).net_fin_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_2_year.get(i).net_cash_flow));
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).asset_debt_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).current_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).quick_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).account_rec_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).inventory_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).main_business_profit_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).net_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).business_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).net_profit_growth_rate);
                                }
//
                                //最近一年
                                if (!isNullOrEmpty(api1_financialStatement.body().statement_last.date)) {
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_asset));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.curr_asset));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.monetary_fund));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.account_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.other_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.advance_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.inventory));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_non_curr_ass));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.fixed_ass));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.constructiong_project));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.lt_equity_inv));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.intangble_ass));//无形资产
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_liab));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_curr_liab));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.st_borrow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.bill_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.account_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.advance_rec));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.other_pay));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.non_curr_liab_one_year));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_non_liab));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.lt_borrow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.bond_pay));//
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.lt_account_pay));//长期应付债券
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_she_equity));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_parent_equity));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.operate_rev));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.operate_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.operate_tax));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sale_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.manage_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.fin_exp));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.operate_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.sum_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.net_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.parent_net_profit));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.net_operate_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.net_inv_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.net_fin_cash_flow));
                                    list_item.add(unitFormat(api1_financialStatement.body().statement_last.net_cash_flow));
                                    list_item.add(api1_financialStatement.body().statement_last.asset_debt_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.current_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.quick_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.account_rec_turnover);
                                    list_item.add(api1_financialStatement.body().statement_last.inventory_turnover);
                                    list_item.add(api1_financialStatement.body().statement_last.total_asset_turnover);
                                    list_item.add(api1_financialStatement.body().statement_last.main_business_profit_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.net_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.total_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_last.business_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_last.total_asset_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_last.net_profit_growth_rate);
                                }

                                //行业平均值的填充
                                for (int i = 0; i < 40; i++)
                                    list_item.add("-");

                                if (!isNullOrEmpty(api1_financialStatement.body().industry_avg_info.info)) {
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(11).get(3));//资产负债率
                                    list_item.add("-");//流动比率？？
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(13).get(3));//速动比率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(7).get(3));//应收账款周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(22).get(3));//存货周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(6).get(3));//总资产周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(2).get(3));//主营业务利润率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(1).get(3));//净资产收益率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(0).get(3));//总资产报酬率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(17).get(3));//营业增长率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(20).get(3));//总资产增长率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(19).get(3));//净利润增长率
                                } else {
                                    for (int i = 0; i < 12; i++)
                                        list_item.add("-");

                                }

                                int k = 0;
                                for (int i = 0; i < name.length; i++)
                                    for (int j = 0; j < 2; j++) {
                                        if (j == 0)//是科目那一列
                                        {
                                            list.add(name[i]);
                                        } else {
                                            if (k < name.length) {
                                                for (int t = 0; t < columnNum -1 ; t++) {
                                                    list.add(list_item.get(k + name.length * t));
                                                }
                                                k++;
                                            }
                                        }
                                    }
                            }
                            mView.setNewTable(list);
                        } else {//港股
                            List<String> list_item = new ArrayList<>();
                            int year = api1_financialStatement.body().statement_2_year.size();
                            if (!isNullOrEmpty(api1_financialStatement.body().statement_last.report_date)) {
                                columnNum = year + 3;
                                mView.setChartColumn(columnNum);//有最新一期
                            } else {
                                columnNum = year + 2;
                                mView.setChartColumn(columnNum);//无最新一期
                            }
                            if(year ==0 && isNullOrEmpty(api1_financialStatement.body().statement_last.date))
                                list.add("");
                            else {
                                list.add("科目");
                                for (int i = api1_financialStatement.body().statement_2_year.size() - 1; i >= 0; i--)
                                    list.add(isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(i).report_date)?"-":date2String(api1_financialStatement.body().statement_2_year.get(i).report_date).substring(0, 4) + "年" + (isNullOrEmpty(api1_financialStatement.body().statement_2_year.get(i).report_date)?"":date2String(api1_financialStatement.body().statement_2_year.get(i).report_date).substring(5, 7)) + "月");
                                if (!isNullOrEmpty(api1_financialStatement.body().statement_last.report_date))
                                    list.add(date2String(api1_financialStatement.body().statement_last.report_date).substring(0, date2String(api1_financialStatement.body().statement_last.report_date).indexOf("月") + 1));
                                list.add("行业平均值");
                                String[] name = {"证券代码", "现金及现金等价物", "交易性金融资产", "其他短期投资", "应收账款及票据", "其它应收款", "存货", "其他流动资产", "流动资产合计", "固定资产净值", "投资物业", "在建工程", "其他固定资产", "权益性投资", "持有至到期投资",
                                        "可供出售投资", "其他长期投资", "商誉及无形资产", "其他非流动资产", "非流动资产合计", "总资产", "应付账款及票据", "交易性金融负债", "短期借贷及长期借贷当期到期部分", "其他流动负债",
                                        "长期借贷", "其他非流动负债", "非流动负债合计", "总负债", "普通股股本", "优先股", "股东权益合计", "总负债及总权益", "净利润", "折旧与摊销", "营运资本变动", "其他非现金调整", "经营活动产生的现金流量净额",
                                        "资本性支出", "出售固定资产收到的现金", "投资增加", "投资减少", "其他投资活动产生的现金流量净额", "投资活动产生的现金流量净额", "债务增加", "债务减少", "股本增加", "股本减少", "支付的股利合计", "其他筹资活动产生的现金流量净额",
                                        "筹资活动产生的现金流量净额", "汇率变动影响", "其他现金流量调整", "现金及现金等价物净增加额", "现金及现金等价物期初余额", "现金及现金等价物期末余额", "证券简称", "更新时间", "总营业收入", "主营收入", "其他营业收入",
                                        "总营业支", "营业成本", "营业开支", "毛利", "销售、行政及一般费用", "材料及相关费用", "员工薪酬", "研发费用", "其他营业费用合计", "营业利润", "利息支出", "利息收入", "权益性投资损益", "除税前利润", "所得税", "流动负债合计",
                                        "现金净流量", "资产负债率", "流动比率", "速动比率", "应收账款周转率", "存货周转率", "总资产周转率", "主营业务利润率", "净资产收益率", "总资产报酬率", "营业增长率", "总资产增长率",
                                        "净利润增长率"};
                                Log.e("name_length", String.valueOf(name.length));

                                //前三年


                                for (int i = api1_financialStatement.body().statement_2_year.size() - 1; i >= 0; i--) {
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).b_id);
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).cash_equ));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).tran_fin_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_short_term_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).account_rec));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_rec));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).inventory));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_curr_ass));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_curr_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).fixed_ass_net));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).inv_property));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).constructiong_project));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_fixed_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity_inv));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).hold_maturity_inv));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).saled_inv));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_long_term_inv));//其他长期投资
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).goodwill_intangl_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_non_curr_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_non_curr_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_ass));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).account_pay));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).tran_fin_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).expire_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_curr_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).lt_borrow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_non_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_non_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).pre_stock));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_she_equity));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_liab_equity));//
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_profit));//净利润
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).depre_amort));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).oper_capital_change));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_non_cash_change));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_oper_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).capital_exp));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sale_fixed_ass_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).inv_add));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).inv_reduce));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_net_inv_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_inv_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).debt_add));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).debt_reduce));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity_add));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity_reduce));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity_int_sun));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_net_fin_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_fin_cash_flow));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).rate_change));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_cah_adjust));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).ni_cash_equi));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_cash_equi_begin));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_cash_equi_end));
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).short_name);
                                    list_item.add(date2String(api1_financialStatement.body().statement_2_year.get(i).update_time));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).tot_oper_rev));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).oper_rev));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_oper_rev));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).tot_oper_exp));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).oper_cost));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).oper_exp));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).gross_profit));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sale_admin_exp));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).material_exp));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).employee_salary));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).r_d_cost));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).other_oper_exp_sum));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).operate_profig));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).int_cost));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).int_income));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).equity_inv_loss_benifit));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).pre_tax_profit));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).income_tax));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).sum_curr_liab));
                                    list_item.add(pointFormat(api1_financialStatement.body().statement_2_year.get(i).net_cash_flow));
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).asset_debt_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).current_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).quick_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).account_rec_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).inventory_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_turnover);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).main_business_profit_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).net_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_return_ratio);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).business_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).total_asset_growth_rate);
                                    list_item.add(api1_financialStatement.body().statement_2_year.get(i).net_profit_growth_rate);

                                }
//                                if (api1_financialStatement.body().statement_2_year.size() == 2) {
//                                    for (int i = 0; i < 89; i++)
//                                        list_item.add("-");
//                                } else if (api1_financialStatement.body().statement_2_year.size() == 1) {
//                                    for (int i = 0; i < 89 * 2; i++)
//                                        list_item.add("-");
//
//                                }
                                //最近一年
                                list_item.add(api1_financialStatement.body().statement_last.b_id);
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.cash_equ));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.tran_fin_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_short_term_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.account_rec));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_rec));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.inventory));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_curr_ass));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_curr_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.fixed_ass_net));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.inv_property));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.constructiong_project));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_fixed_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity_inv));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.hold_maturity_inv));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.saled_inv));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_long_term_inv));//其他长期投资
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.goodwill_intangl_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_non_curr_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_non_curr_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_ass));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.account_pay));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.tran_fin_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.expire_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_curr_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.lt_borrow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_non_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_non_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.pre_stock));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_she_equity));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_liab_equity));//
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_profit));//净利润
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.depre_amort));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.oper_capital_change));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_non_cash_change));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_oper_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.capital_exp));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sale_fixed_ass_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.inv_add));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.inv_reduce));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_net_inv_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_inv_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.debt_add));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.debt_reduce));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity_add));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity_reduce));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity_int_sun));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_net_fin_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_fin_cash_flow));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.rate_change));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_cah_adjust));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.ni_cash_equi));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_cash_equi_begin));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_cash_equi_end));
                                list_item.add(api1_financialStatement.body().statement_last.short_name);
                                list_item.add(date2String(api1_financialStatement.body().statement_last.update_time));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.tot_oper_rev));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.oper_rev));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_oper_rev));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.tot_oper_exp));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.oper_cost));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.oper_exp));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.gross_profit));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sale_admin_exp));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.material_exp));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.employee_salary));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.r_d_cost));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.other_oper_exp_sum));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.operate_profig));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.int_cost));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.int_income));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.equity_inv_loss_benifit));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.pre_tax_profit));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.income_tax));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.sum_curr_liab));
                                list_item.add(pointFormat(api1_financialStatement.body().statement_last.net_cash_flow));
                                list_item.add(api1_financialStatement.body().statement_last.asset_debt_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.current_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.quick_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.account_rec_turnover);
                                list_item.add(api1_financialStatement.body().statement_last.inventory_turnover);
                                list_item.add(api1_financialStatement.body().statement_last.total_asset_turnover);
                                list_item.add(api1_financialStatement.body().statement_last.main_business_profit_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.net_asset_return_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.total_asset_return_ratio);
                                list_item.add(api1_financialStatement.body().statement_last.business_growth_rate);
                                list_item.add(api1_financialStatement.body().statement_last.total_asset_growth_rate);
                                list_item.add(api1_financialStatement.body().statement_last.net_profit_growth_rate);


                                //行业平均值的填充
                                for (int i = 0; i < 78; i++)
                                    list_item.add("-");

                                if (!isNullOrEmpty(api1_financialStatement.body().industry_avg_info.info)) {
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(11).get(3));//资产负债率
                                    list_item.add("-");//流动比率？？
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(13).get(3));//速动比率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(7).get(3));//应收账款周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(22).get(3));//存货周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(6).get(3));//总资产周转率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(2).get(3));//主营业务利润率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(1).get(3));//净资产收益率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(0).get(3));//总资产报酬率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(17).get(3));//营业增长率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(20).get(3));//总资产增长率
                                    list_item.add(api1_financialStatement.body().industry_avg_info.info.indexs.get(19).get(3));//净利润增长率
                                } else {
                                    for (int i = 0; i < 12; i++)
                                        list_item.add("-");

                                }

                                int k = 0;
                                for (int i = 0; i < name.length; i++)
                                    for (int j = 0; j < 2; j++) {
                                        if (j == 0)//是科目那一列
                                        {
                                            list.add(name[i]);
                                        } else {
                                            if (k < name.length) {
                                                for (int t = 0; t < columnNum -1 ; t++) {
                                                    list.add(list_item.get(k + name.length * t));
                                                }
                                                k++;
                                            }
                                        }
                                    }
//                                int k = 0;
//                                for (int i = 0; i < 89; i++) {
//                                    for (int j = 0; j < 2; j++) {
//                                        if (j == 0) {
//                                            list.add(name[i]);
//                                        } else {
//                                            if (k < 89) {
//                                                list.add(list_item.get(k));
//                                                list.add(list_item.get(k + 89));
//                                                list.add(list_item.get(k + 89 * 2));
//                                                list.add(list_item.get(k + 89 * 3));
//                                                list.add(list_item.get(k + 89 * 4));
//                                                k++;
//                                            }
//                                        }
//                                    }
//                                }
                            }
                            mView.setNewTable(list);
                        }

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
    public void setBehavior(String startTime,String endTime) {
        mData.post_Browse_Recode("3","2","10",mData.getCompanyId(),startTime,endTime).subscribe(new Observer<Response<Api1_post_common>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<Api1_post_common> api1_post_common) {
                if(api1_post_common.headers().get("Set-Cookie") != null){
                    mData.checkSession(api1_post_common.headers().get("Set-Cookie"));
                }
                Log.e("info_财务详情",api1_post_common.body().info);

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
