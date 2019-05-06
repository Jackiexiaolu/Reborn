package com.demo.reborn.data.json;

import java.util.List;

public class Api1_Business_Opportunity {

    public class TableData{
        public List<List<String>> info;
        public List<String> row_name;
        public String error;
    }

    public class TableCount{
        public TableData tableData;
        public String tableName;
    }

    public class Datas{
        public String suggest_content;
        public List<TableCount> table;
    }

    public class TableList{
        public List<TableData> table_data;
        public String table_name;
    }

    public class Info{
        public String title;
        public List<Datas> datas;
        public List<TableList> table_list;
    }

    public int error;
    public List<Info> opportunity_info;
}
