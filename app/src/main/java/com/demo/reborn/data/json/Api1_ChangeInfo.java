package com.demo.reborn.data.json;

import java.util.List;

public class Api1_ChangeInfo {
    public class ChangeInfo {
        public String time;
        public String item;
        public String before;
        public String after;
    }

    public String error;
    public List<ChangeInfo> changeinfos;
}
