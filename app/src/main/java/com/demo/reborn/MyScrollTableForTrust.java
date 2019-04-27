package com.demo.reborn;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MyScrollTableForTrust {
    private int rowNum;
    private LinearLayout currentLinearLayout;
    private LinearLayout columnUnit;


    private int num_of_row = 1;
    private int num_of_column = 1;

    public MyScrollTableForTrust(LinearLayout ll) {
        currentLinearLayout = ll;
        rowNum = 13;

    }

    public void add(String s){
        if(num_of_row == 1){
            columnUnit = new LinearLayout(currentLinearLayout.getContext());
            columnUnit.setOrientation(LinearLayout.VERTICAL);
            columnUnit.setBackgroundColor(0xffa0a0a0);
        }

        TextView textView = new TextView(currentLinearLayout.getContext());
        textView.setText(s);
        textView.setWidth(1500);
        textView.setHeight(600);
        textView.setGravity(Gravity.CENTER);

        if(num_of_column%2 == 1)       textView.setBackgroundColor(0xffe6e6e6);      //单数行(gery)
        else                           textView.setBackgroundColor(0xfff0f0f0);      //双数行(light)

        columnUnit.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        num_of_row++;

        if(num_of_row <= rowNum){
            TextView horizonDivider = new TextView(currentLinearLayout.getContext());
            horizonDivider.setBackgroundColor(0xffa0a0a0);
            horizonDivider.setText("          ");
            columnUnit.addView(horizonDivider, LinearLayout.LayoutParams.MATCH_PARENT, 3);
        }
        else if(num_of_row > rowNum){
            currentLinearLayout.addView(columnUnit, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            num_of_column++;
            num_of_row = 1;
            TextView verticalDivider = new TextView(currentLinearLayout.getContext());
            verticalDivider.setBackgroundColor(0xffa0a0a0);
            currentLinearLayout.addView(verticalDivider,3, LinearLayout.LayoutParams.MATCH_PARENT);
        }

//        TextView textView = new TextView(currentLinearLayout.getContext());
//        textView.setText(s);
//        textView.setWidth(80);
//        textView.setGravity(Gravity.CENTER);
//
//        columnUnit = new LinearLayout(currentLinearLayout.getContext());
//        columnUnit.setOrientation(LinearLayout.VERTICAL);
//        columnUnit.setBackgroundColor(0xffa0a0a0);
//
//        columnUnit.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        currentLinearLayout.addView(columnUnit, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void add(int i){
        add(String.valueOf(i));
    }

    public void add(double d){
        add(String.valueOf(d));
    }

    private String fmtMicrometer(String text)
    {
        DecimalFormat df = null;
        if(text.indexOf(".") > 0)
        {
            if(text.length() - text.indexOf(".")-1 == 0)
            {
                df = new DecimalFormat("###,##0.");
            }else if(text.length() - text.indexOf(".")-1 == 1)
            {
                df = new DecimalFormat("###,##0.0");
            }else
            {
                df = new DecimalFormat("###,##0.00");
            }
        }else
        {
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
}
