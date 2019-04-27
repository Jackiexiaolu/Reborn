package com.demo.reborn;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MyTable{

    private int columnNum;
    private LinearLayout currentLinearLayout;
    private int weightArray[];
    public LinearLayout rowUnit;
    private int width = 0;

    private int num_of_row = 1;
    private int num_of_column = 1;

    public MyTable(LinearLayout ll) {
        currentLinearLayout = ll;
    }

    public MyTable(LinearLayout ll, int widthPx){
        currentLinearLayout = ll;
        width = widthPx;
    }

    public void setColumn(int n){
        columnNum = n;
    }

    public void setWeight(int weight[]){
        if(weight.length >= columnNum){
            int totalWeight = 0;
            if(width == 0) {
                width = currentLinearLayout.getMeasuredWidth();
                if (width == 0) {
                    DisplayMetrics dm = new DisplayMetrics();
                    width = dm.widthPixels;
                }
                if (width == 0) {
                    DisplayMetrics dm = currentLinearLayout.getResources().getDisplayMetrics();
                    width = dm.widthPixels;
                }
            }
            weightArray = new int[columnNum];
            for(int i = 0; i < columnNum; i++){
                totalWeight += weight[i];
            }
            for(int i = 0; i < columnNum; i++){
                weightArray[i] = weight[i] * width / totalWeight;
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void add(String s){
        if(num_of_column == 1){
            rowUnit = new LinearLayout(currentLinearLayout.getContext());
            rowUnit.setOrientation(LinearLayout.HORIZONTAL);
            rowUnit.setBackgroundColor(0xffa0a0a0);
            if(num_of_row == 1)         rowUnit.setBackgroundColor(0xffa0a0a0);
            else if(num_of_row%2 == 1)  rowUnit.setBackgroundColor(0xffe6e6e6);
            else                        rowUnit.setBackgroundColor(0xfff0f0f0);
        }

        TextView textView = new TextView(currentLinearLayout.getContext());
        textView.setText(s);
        textView.setWidth(weightArray[num_of_column - 1]);
        textView.setGravity(Gravity.CENTER);
        if(num_of_row == 1) {
            textView.setBackgroundColor(0xffa0a0a0);      //表头(dark)
            textView.setTextColor(0xffffffff);
        }
        else if(num_of_row%2 == 1)  textView.setBackgroundColor(0xffe6e6e6);      //单数行(gery)
        else                        textView.setBackgroundColor(0xfff0f0f0);      //双数行(light)

        rowUnit.addView(textView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        num_of_column++;

        if(num_of_column <= columnNum){
            TextView verticalDivider = new TextView(currentLinearLayout.getContext());
            verticalDivider.setBackgroundColor(0xffa0a0a0);
            verticalDivider.setText("          ");
            rowUnit.addView(verticalDivider,3, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        else {
            currentLinearLayout.addView(rowUnit, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            num_of_row++;
            num_of_column = 1;
            TextView horizonDivider = new TextView(currentLinearLayout.getContext());
            horizonDivider.setBackgroundColor(0xffa0a0a0);
            currentLinearLayout.addView(horizonDivider, LinearLayout.LayoutParams.MATCH_PARENT,3);
        }
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
