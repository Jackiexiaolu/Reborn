package com.demo.reborn.data.json;

import java.util.List;

/**
 * Created by hanwenlong on 2019/4/25.
 */

public class Api1_Receive_Friends_Message {

    public class message {
        public String content;//消息内容
        public String name;
        public String time;//發送時間
    }
    public String error;
    public List<message> message_list;
    public String info;
}
