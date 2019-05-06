package com.demo.reborn.data.json;

import java.util.List;

public class Api1_BaseInfo {
    public class Info {
        public String s_name;
        public String s_id;
        public String address;
        public String date;
    }
    public class code{
        public String a_code;
        public String a_name;
        public String b_code;
        public String b_name;
        public String h_code;
        public String h_name;
        public String n_code;
        public String n_name;
        public String nas_code;
        public String nas_name;
        public String x_code;
        public String x_name;

    }

    public String error;
    public String name;
    public String s_name;
    public String estab_date;
    public String money;
    public String legal_name;
    public String reg_address;
    public String logo;
    public String top_world;
    public String top_china;
    public List<String>  main_business;
    public List<Info> list_info;
    public code codes;
    public String group_name;
}
