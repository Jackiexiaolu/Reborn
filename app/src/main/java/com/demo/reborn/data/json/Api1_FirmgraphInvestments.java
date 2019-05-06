package com.demo.reborn.data.json;

import java.util.List;

public class Api1_FirmgraphInvestments {
    public class Investment {
        public String code;
        public String name;
        public double rate;
    }

    public int error;
    public List<Investment> investments;
}
