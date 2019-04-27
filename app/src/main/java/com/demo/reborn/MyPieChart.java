package com.demo.reborn;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MyPieChart {

    private PieChart currentPieChart;

    public MyPieChart(PieChart mPieChart){
        currentPieChart = new PieChart(mPieChart.getContext());
        currentPieChart = mPieChart;
        currentPieChart.setDescription(new Description());
        //是否使用百分比
        currentPieChart.setUsePercentValues(true);
        //圆环距离屏幕上下左右的距离
        currentPieChart.setExtraOffsets(20f,5f,20f,15f);
        //是否显示原圆环中间的洞
        currentPieChart.setDrawHoleEnabled(true);
        //设置中间洞的颜色
        currentPieChart.setHoleColor(Color.WHITE);
        //设置圆环透明度及半径
        currentPieChart.setTransparentCircleColor(Color.YELLOW);
        currentPieChart.setTransparentCircleAlpha(110);
        currentPieChart.setTransparentCircleRadius(46f);
        //设置中间洞的半径
        currentPieChart.setHoleRadius(45f);
        //表上不显示文字
        currentPieChart.setDrawSliceText(false);
        //是否显示洞中间文本
        currentPieChart.setDrawCenterText(true);
        //触摸是否可以旋转以及松手之后的度数
        currentPieChart.setRotationAngle(20);
        currentPieChart.setRotationEnabled(false);
        //设置比例块
        Legend mLegend = currentPieChart.getLegend();
        //比例块字体大小
        mLegend.setFormSize(12f);
        //设置换行
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        mLegend.setTextColor(Color.BLACK);
    }

    public void setCenterText(String text){
        //设置圆环中间的文字
        currentPieChart.setCenterText(text);
    }

    public void setData(ArrayList<PieEntry> data){
        PieDataSet dataSet = new PieDataSet(data,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        //数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for(int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for(int c :ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for(int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for(int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //数据是否外部显示
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //
        PieData pieData=new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.BLACK);
        currentPieChart.setData(pieData);
        currentPieChart.highlightValue(null);
        //刷新
        currentPieChart.invalidate();
    }
}
