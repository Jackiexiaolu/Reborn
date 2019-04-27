package com.demo.reborn;

import android.app.ProgressDialog;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.demo.reborn.util.MyApplication.getContext;

public class Judgement {


    public static ProgressDialog progressDialog;

    /**
     *  
     *      * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     *      *  
     *      * @param obj 
     *      * @return 
     *      
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null || obj.equals("None") || obj.equals("--") || obj.equals("null") || obj.equals("") || obj.equals("0") || obj.equals("-"))
            return true;
        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;
        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();
        if (obj instanceof Map)
            return ((Map) obj).isEmpty();
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;

                }
            }
            return empty;
        }
        return false;
    }

    //英文日期转为中文日期 Sat, 30 Jun 2018 00:00:00 GMT-> 2018年6月30日
    public static String date2String(String date) {

        StringBuilder result = new StringBuilder();
        String year = "";
        String month = "";
        String day = "";
        //String time = "";

        String[] timeStr = date.split(" ");

        for (int i = timeStr.length - 1; i > 0; i--) {
            //Log.d("split",i + timeStr[i]);
            if (i == 1) {
                day = timeStr[i];//day
            } else if (i == 2) {
                month = timeStr[i];//month
                switch (month) {
                    case "Jan":
                        month = "1";
                        break;
                    case "Feb":
                        month = "2";
                        break;
                    case "Mar":
                        month = "3";
                        break;
                    case "Apr":
                        month = "4";
                        break;
                    case "May":
                        month = "5";
                        break;
                    case "Jun":
                        month = "6";
                        break;
                    case "Jul":
                        month = "7";
                        break;
                    case "Aug":
                        month = "8";
                        break;
                    case "Sep":
                        month = "9";
                        break;
                    case "Oct":
                        month = "10";
                        break;
                    case "Nov":
                        month = "11";
                        break;
                    case "Dec":
                        month = "12";
                        break;
                }
            } else if (i == 3) {
                year = timeStr[i];//year
            }
        }
        result.append(year).append("年").append(month).append("月").append(day).append("日 ");
        return result.toString();
    }

    //金额单位显示统一化，不足一亿元显示万元，小数点后保留两位
    public static String unitFormat(String num) {
        double number;
        String result = "";
        DecimalFormat df = new DecimalFormat("0.00");
        if (!isNullOrEmpty(num)) {
//            Pattern p_ch= Pattern.compile("[\u4e00-\u9fa5]");
//            Matcher m_ch = p_ch.matcher(num);
            Pattern p_num = Pattern.compile("^-?[0-9]+(.[0-9]+)?$");
            Matcher m_num = p_num.matcher(num);
            if (m_num.find()) {//为数据格式（包括double和int型
                number = Double.parseDouble(num);
                if (Math.abs(number) < 100000000)//小于一亿元
                    result = df.format(number / 10000) + "万";
                else if (Math.abs(number) >= 100000000)
                    result = df.format(number / 100000000) + "亿";
            } else if (num.contains(",")) {//英文日期
                result = date2String(num);
            } else if (num.contains("%")) {//XX率
                number = Double.parseDouble(num.replace("%", ""));
                result = df.format(number) + "%";
            } else
                result = num;
        } else
            result = "-";
        return result;

    }

    /*

     * 格式化数字为千分位显示；

     * @param 要格式化的数字；

     * @return

     */

    public static String fmtMicrometer(String text) {
        DecimalFormat df = null;
        if (text.indexOf(".") > 0) {
            if (text.length() - text.indexOf(".") - 1 == 0) {
                df = new DecimalFormat("###,##0.");
            } else if (text.length() - text.indexOf(".") - 1 == 1) {
                df = new DecimalFormat("###,##0.0");
            } else {
                df = new DecimalFormat("###,##0.00");
            }
        } else {
            df = new DecimalFormat("###,##0");
        }
        double number = 0.0;
        try {
            number = Double.parseDouble(text);
        } catch (Exception e) {
            number = 0.0;
        }
        return df.format(number);
    }

    public static String trustScale(String num) {
        String result = "";
        if (!isNullOrEmpty(num)) {
            Pattern p_num = Pattern.compile("^-?[0-9]+(.[0-9]+)?$");
            Matcher m_num = p_num.matcher(num);
            if (m_num.find()) {//为数据格式（包括double和int型)
                result = num + "万元";
            } else
                result = num;
        }
        return result;
    }

    //小数点后两位
    public static String pointFormat(String num) {
        if (!isNullOrEmpty(num)) {
            DecimalFormat df = new DecimalFormat("0.00");
            return df.format(Double.parseDouble(num));
        } else
            return "";
    }

    public static String notNull(String str) {
        if (str == null || str.equals("None") || str.equals("--") || str.equals("") || str.equals("null")) {
            return "-";
        } else
            return str;
    }
}
