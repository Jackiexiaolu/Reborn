package com.demo.reborn.data.json;

import java.util.List;

public class Api1_IndustryAverage {

    public  class info{
        public String industry;
        public String source;
        public String time;
        public List<List<String>> indexs;
    }

    public String error;
    public info info;

}
