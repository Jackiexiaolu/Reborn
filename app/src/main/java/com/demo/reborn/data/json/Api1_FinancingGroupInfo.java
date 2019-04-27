package com.demo.reborn.data.json;

import java.util.List;

public class Api1_FinancingGroupInfo {
    public class Credit {
        public String name;
        public String credit_detail;
    }

    public class Debt {
        public String name;
        public String debt_info_3_year;
        public String debt_info_last;
    }

    public String error;
    public List<Credit> credit;
    public String bonds;
    public List<Debt> debts;
}
