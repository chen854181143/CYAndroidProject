package com.chenyang.androidproject.activity;

import android.graphics.Color;

import com.chenyang.androidproject.R;
import com.chenyang.androidproject.common.MyActivity;
import com.chenyang.androidproject.utils.mpandroidchart.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MPAndroidChartStudentOneActivity extends MyActivity {

    @BindView(R.id.lineChart)
    LineChart mLineChart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mp_android_chart_student_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_test_a_bar;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<String> monthData=new ArrayList<String>();
        monthData.add("一月份");
        monthData.add("二月份");
        monthData.add("三月份");
        monthData.add("四月份");
        monthData.add("五月份");
        monthData.add("六月份");
        monthData.add("七月份");
        monthData.add("八月份");
        monthData.add("九月份");
        monthData.add("十月份");
        monthData.add("十一月份");
        monthData.add("十二月份");
        XAxis xAxis = mLineChart.getXAxis();//得到X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setGranularity(1f);//因为此图有缩放功能，X轴,Y轴可设置可缩放
        xAxis.setLabelCount(6, true);//设置X轴的刻度数量
//        xAxis.setAxisMinimum(1f);//最小值
//        xAxis.setAxisMaximum(12f);//最大值
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return monthData.get((int) value); //mList为存有月份的集合
            }
        });

        YAxis leftYAxis = mLineChart.getAxisLeft();
        YAxis rightYAxis = mLineChart.getAxisRight();
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setAxisMaximum(100f);
        rightYAxis.setAxisMinimum(0f);
        rightYAxis.setAxisMaximum(100f);
        leftYAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + "%";
            }
        });
//        rightYAxis.setEnabled(false); //右侧Y轴不显示
        rightYAxis.setGranularity(1f);
        rightYAxis.setLabelCount(11,false);
        rightYAxis.setTextColor(Color.BLUE); //文字颜色
        rightYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色

        LimitLine limitLine = new LimitLine(95,"高限制性"); //得到限制线
        limitLine.setLineWidth(1f); //宽度
        limitLine.setTextSize(10f);
        limitLine.setTextColor(Color.RED);  //颜色
        limitLine.setLineColor(Color.BLUE);
        rightYAxis.addLimitLine(limitLine); //Y轴添加限制线

        Legend legend = mLineChart.getLegend();
        legend.setTextColor(Color.RED); //设置Legend 文本颜色
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setEnabled(false);

        /*Description description = new Description();
        description.setEnabled(false);
        mLineChart.setDescription(description);*/

        Description description = new Description();
        description.setText("X轴描述");
        description.setTextColor(Color.RED);
        mLineChart.setDescription(description);

        mLineChart.setDrawBorders(true);//显示边界
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
//        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        //设置显示值的字体大小
        lineDataSet.setValueTextSize(0f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        MyMarkerView mv = new MyMarkerView(this);
        mLineChart.setMarkerView(mv);

        //取消曲线显示的值为整数
        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int IValue = (int) value;
                return String.valueOf(IValue);
            }
        });
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
    }
}
