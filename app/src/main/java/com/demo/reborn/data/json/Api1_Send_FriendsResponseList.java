package com.demo.reborn.data.json;

import java.util.List;

/**
 * Created by hanwenlong on 2019/4/25.
 */

public class Api1_Send_FriendsResponseList {

    public class Company {
        public String c_id;
        public String name;
    }
    public String error;
    public List<Company> request_list;
    public String info;
}

