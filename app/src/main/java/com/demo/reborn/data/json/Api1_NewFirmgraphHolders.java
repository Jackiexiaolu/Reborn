package com.demo.reborn.data.json;

        import java.util.List;

public class Api1_NewFirmgraphHolders {
    public class Holders{
        public String holder;
        public String rate;
        public String c_id;
    }

    public class Second_Holder_Info{
        public String second_holder_name;
        public String second_holder_rate;
    }


    public int error;
    public String group_name;
    public List<Holders> holders;
    public Second_Holder_Info second_holder_info;
    public int situation;

}
