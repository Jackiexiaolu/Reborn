package com.demo.reborn.data.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Api1_FinancingIndex {
    //债券类
    public class Classify{
        public String classify;
        public String count;
        public String rest;
        public String total;
    }
    public class BondTotal{
        public String avg_deadline;
        public String avg_inter_rate;
        public List<Classify> classify;
        public String rest;
        public String total;
    }
    //财务类
    public class KeyBusiness1Year{
        public String date;
        public List<List<String>> data;
    }
    public class Business{
        public KeyBusiness1Year key_business_1_year;
        public String operate_rev;
        public String operate_rev_YOY;
        public String profit;
        public String profit_YOY;
        public String year;
    }
    //用信情况类
    public class CrediTotal{
        public String amount;
        public String currency;
        public String unused;
        public String used;
    }
    //债务类
    public class Debt_Index{
        public List<String> last;
        public String total;
    }
    //融资情况类
    public class FinancialStatement{
        public String asset_debt_ratio;
        public String net_profit;
        public String net_profit_YOY;
        public String operate_rev;
        public String operate_rev_YOY;
        public String sum_asset;
        public String sum_debt;
        public String sum_owners_equity;
        public String year;
    }
    //高管类
    public class Managers{
        public String name;
        public String post;
        @SerializedName("abstract")
        public String cv;
    }
    //实例
    public BondTotal bond_total;
    public Business business;
    public List<CrediTotal> credit_total;
    public Debt_Index debt;
    public String error;
    public String estab_date;
    public FinancialStatement financialstatement;
    public String first_holder_name;
    public String first_holder_rate;
    public String legal_name;
    public List<Managers> managers;
    public String name;
    public String is_bond;
    public String is_listed;
    public String is_gov;
    public String is_loc;
    public String group_name;
    public String is_a_h;
    public String is_state_owned;
    public int flag;
}
