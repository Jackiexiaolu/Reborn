package com.demo.reborn.data.json;

import java.util.List;

public class Api1_FinancingInfo {
    public class CreditAmount {
        public String currency;
        public String amount;
        public String used;
        public String unused;
    }

    public class CreditDetail {
        public String time;
        public String bank;
        public String currency;
        public String amount;
        public String used;
        public String unused;
    }

    public class Bond {
        public String debt_subject;
        public String s_id;
        public String s_name;
        public String s_short_name;
        public String init_face_value;
        public String total;
        public String inter_type;
        public String inter_rate;
        public String deadline;
        public String list_date;
        public String classify;
        public String lead_underwriter;
    }

    public String error;
    public String g_id;
    public List<CreditAmount> credit_amount;
    public List<CreditDetail> credit_detail;
    public String organ;
    public String date;
    public String rating;
    public String move;
    public List<Bond> bonds;
    public List<List<String>> debt_info_3_year;
    public String debt_info_last;
}
