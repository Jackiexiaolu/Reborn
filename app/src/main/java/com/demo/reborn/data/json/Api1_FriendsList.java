package com.demo.reborn.data.json;

import java.util.List;

/**
 * Created by hanwenlong on 2019/4/25.
 */

public class Api1_FriendsList {


    public class Company {
        public String c_id;
        public String name;

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }
    }

    public String error;
    public List<Company> request_list;
    public String info;
}
