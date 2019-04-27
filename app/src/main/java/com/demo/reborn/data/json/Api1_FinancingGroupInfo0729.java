package com.demo.reborn.data.json;

import java.util.List;

public class Api1_FinancingGroupInfo0729 {
    public class CreditTotal {
        public String currency;
        public String amount;
        public String used;
        public String unused;
        public String time;
    }

    public class CreditDetail {
        public String name;
        public String bank;
        public String currency;
        public String amount;
        public String used;
        public String unused;
        public String limit_date;
        public String time;
    }

    public class Classify {
        public String classify;
        public String count;
        public String rest;
        public String total;
    }

    public class BondTotal {
        public String rest;
        public String total;
        public String avg_deadline;
        public String avg_inter_rate;
        public List<Classify> classify;
        public List<Subject> subject;
    }

    public class Subject {
        public String subject;
        public String rest;
        public String total;
    }

    public class BondDetail {
        public String debt_subject;
        public String s_id;
        public String s_name;
        public String s_short_name;
        public String init_face_value;
        public String total;
        public String rest;
        public String inter_type;
        public String inter_rate;
        public String deadline;
        public String list_date;
        public String list_address;
        public String classify;
        public String lead_underwriter;
    }

    public class Rating {
        public String date;
        public String organ;
        public String rating;
        public String move;
    }

    public class Debt {
        public String total;
        public List<String> last;
        public List<List<String>> three_years;
    }

    public class ShareFinancingTotal {
        public String listed_num;
        public List<String> listed;
        public String total;
        public List<List<String>> detail;
    }

    public class ShareFinancingDetail {
        public String s_id;
        public String s_name;
        public String list_date;
        public String address;
        public String lead_underwriter;
        public List<List<String>> detail;
    }


    public class Trust{
        public String count;
        public String total;
        public List<trust_Detail> detail;
    }
    public class trust_Detail{
        public String comp_name;
        public String company_name;
        public String t_name;
        public String estab_dt;
        public String t_scale;
        public String t_deadline;
        public String appli_way;
        public String in_distr;
        public String trustee;
        public String custodian;
        public String bank;
        public String t_manag;
        public String f_used;
    }
    public class Insurance{
        public String count;
        public List<insuranceDetail> detail;
    }
    public class insuranceDetail{
        public String comp_name;
        public String insur_ass_name;
        public String reg_number;
        public String ann_date;
    }
    public class Asset {
        public String total;
        public String count;
        public List<assetDetail> detail;
    }
    public class assetDetail{
        public String prod_name;
        public String man_name;
        public String coll_name;
        public String company_name;

    }
    public String error;
    public List<CreditTotal> credit_total;
    public List<CreditDetail> credit_detail;
    public BondTotal bond_total;
    //public List<Subject> subject;
    public List<BondDetail> bond_detail;
    public Rating rating;
    public Debt debt;
    public ShareFinancingTotal share_financing_total;
    public List<ShareFinancingDetail> share_financing_detail;
    public Trust trust;
    public Insurance insurance;
    public Asset asset;
    public String group_name;
}
