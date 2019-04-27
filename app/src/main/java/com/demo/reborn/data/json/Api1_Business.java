package com.demo.reborn.data.json;

import java.util.List;

public class Api1_Business {
    public class KeyBusiness3Year {
        public String date;
        public List<List<String>> data;
    }

    public String error;
    public String year;
    public String operate_rev;
    public String operate_rev_YOY;
    public String profit;
    public String profit_YOY;
    public List<KeyBusiness3Year> key_business_3_year;
    public List<List<String>> key_business_last;
}
