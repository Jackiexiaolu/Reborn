package com.demo.reborn.data.json;

import java.util.List;

public class Api1_FinancialStatement {
    public class Statement2Year {
        //a股
        public String date;
        public String sum_asset;
        public String curr_asset;
        public String monetary_fund;
        public String account_rec;
        public String other_rec;
        public String advance_pay;
        public String inventory;
        public String fixed_ass;
        public String sum_liab;
        public String sum_curr_liab;
        public String st_borrow;
        public String bill_pay;
        public String account_pay;
        public String advance_rec;
        public String other_pay;
        public String lt_borrow;
        public String sum_she_equity;
        public String sum_parent_equity;
        public String operate_rev;
        public String operate_exp;
        public String operate_tax;
        public String operate_profit;
        public String sum_profit;
        public String net_profit;
        public String parent_net_profit;
        public String net_operate_cash_flow;
        public String net_inv_cash_flow;
        public String net_fin_cash_flow;
        public String net_cash_flow;
        public String asset_debt_ratio;
        public String current_ratio;
        public String quick_ratio;
        public String account_rec_turnover;
        public String inventory_turnover;
        public String total_asset_turnover;
        public String main_business_profit_ratio;
        public String net_asset_return_ratio;
        public String total_asset_return_ratio;
        public String business_growth_rate;
        public String total_asset_growth_rate;
        public String net_profit_growth_rate;
        public String sum_non_curr_ass;
        public String constructiong_project;
        public String lt_equity_inv;
        public String intangble_ass;
        public String non_curr_liab_one_year;
        public String sum_non_liab;
        public String bond_pay;
        public String lt_account_pay;
        public String sale_exp;
        public String manage_exp;
        public String fin_exp;
        //港股
        public String report_date;
        public String b_id;
        public String cash_equ;
        public String tran_fin_ass;
        public String other_short_term_ass;
        //        public String account_rec;
//        public String other_rec;
//        public String inventory;
        public String other_curr_ass;
        public String sum_curr_ass;
        public String fixed_ass_net;
        public String inv_property;
        public String constructoing_project;
        public String other_fixed_ass;
        public String equity_inv;
        public String hold_maturity_inv;
        public String saled_inv;
        public String other_long_term_inv;
        public String goodwill_intangl_ass;
        public String other_non_curr_ass;
        //        public String sum_non_curr_ass;
        public String sum_ass;
        //        public String account_pay;
        public String tran_fin_liab;
        public String expire_liab;
        public String other_curr_liab;
        //        public String lt_borrow;
        public String other_non_liab;
        //        public String sum_non_liab;
//        public String sum_liab;
        public String equity;
        public String pre_stock;
        //        public String sum_she_equity;
        public String sum_liab_equity;
        //        public String net_profit;
        public String depre_amort;
        public String oper_capital_change;
        public String other_non_cash_change;
        public String net_oper_cash_flow;
        public String capital_exp;
        public String sale_fixed_ass_cash_flow;
        public String inv_add;
        public String inv_reduce;
        public String other_net_inv_cash_flow;
        //        public String net_inv_cash_flow;
        public String debt_add;
        public String debt_reduce;
        public String equity_add;
        public String equity_reduce;
        public String equity_int_sun;
        public String other_net_fin_cash_flow;
        //        public String net_fin_cash_flow;
        public String rate_change;
        public String other_cah_adjust;
        public String ni_cash_equi;
        public String net_cash_equi_begin;
        public String net_cash_equi_end;
        public String short_name;
        public String update_time;
        public String data_source;
        public String tot_oper_rev;
        public String oper_rev;
        public String other_oper_rev;
        public String tot_oper_exp;
        public String oper_cost;
        public String oper_exp;
        public String gross_profit;
        public String sale_admin_exp;
        public String material_exp;
        public String employee_salary;
        public String r_d_cost;
        public String other_oper_exp_sum;
        public String operate_profig;
        public String int_cost;
        public String int_income;
        public String equity_inv_loss_benifit;
        public String pre_tax_profit;
        public String income_tax;
//        public String sum_curr_liab;
//        public String net_cash_flow;
//        public String asset_debt_ratio;
//        public String current_ratio;
//        public String quick_ratio;
//        public String account_rec_turnover;
//        public String inventory_turnover;
//        public String total_asset_turnover;
//        public String main_business_profit_ratio;
//        public String net_asset_return_ratio;
//        public String total_asset_return_ratio;
//        public String business_growth_rate;
//        public String total_asset_growth_rate;
//        public String net_profit_growth_rate;


    }

    public class info {
        public String industry;
        public String source;
        public String time;
        public List<List<String>> indexs;
    }

    public class AverageInfo {
        public String error;
        public info info;

    }

    public AverageInfo industry_avg_info;
    public String error;
    public String year;
    public String sum_asset;
    public String sum_debt;
    public String sum_owners_equity;
    public String asset_debt_ratio;
    public String operate_rev;
    public String operate_rev_YOY;
    public String net_profit;
    public String net_profit_YOY;
    public List<Statement2Year> statement_2_year;
    public Statement2Year statement_last;
    public String name;
    public String industry;
    public String is_a_h;//0是港股

}
