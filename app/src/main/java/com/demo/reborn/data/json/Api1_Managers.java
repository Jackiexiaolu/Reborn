package com.demo.reborn.data.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Api1_Managers {
    public class Manager {
        public String name;
        public String post;
        @SerializedName("abstract")
        public String cv;
    }

    public String error;
    public List<Manager> managers;
}
