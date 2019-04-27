package com.demo.reborn.data.json;

import java.util.List;

public class Api1_Holders {
    public class Code{
        public String a_code;
        public String b_code;
        public String h_code;
        public String n_code;
        public String nas_code;
        public String x_code;
    }

    public class Holder {
        public String name;
        public String rate;
        public String c_id;
        public Code codes;
    }

    public String error;
    public String name;
    public String type;
    public String act_control;
    public Holder holder;
    public List<Holder> holders;
}
