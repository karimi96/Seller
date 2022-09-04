package com.karimi.seller.widget.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.karimi.seller.R;

import java.util.ArrayList;

public class CubicChart extends RelativeLayout {
    private LineChart chart;
    private TextView title;

    public CubicChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.cubicline_chart, this);


        title = findViewById(R.id.title_chart);

        chart = findViewById(R.id.chart1);
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(Color.WHITE);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);
        setData();

        XAxis x = chart.getXAxis();
        x.setEnabled(false);

        YAxis y = chart.getAxisRight();
//        y.setTypeface(tfLight);
        y.setLabelCount(6, false);
        y.setTextColor(R.color.black);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(true);
        y.setGridColor(Color.parseColor("#0D7D7AED"));
        y.enableGridDashedLine(15f, 5f, 0f);
        y.setAxisLineColor(Color.WHITE);
        y.setGridLineWidth(2f);
        y.setYOffset(-7f);






//        chart.getAxisRight().setEnabled(true);
        chart.getAxisLeft().setEnabled(false);

        chart.getLegend().setEnabled(false);


        chart.animateXY(2000, 2000);

//        chart.getAxisRight().setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return String.format("%.2f $",value);
//
//            }
//        });

        // don't forget to refresh the drawing
        chart.invalidate();

    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();

//        for (int i = 0; i < count; i++) {
//            float val = (float) (Math.random() * (range + 1)) + 20;
        values.add(new Entry(1, 100));
        values.add(new Entry(2, 200));
        values.add(new Entry(3, 500));
        values.add(new Entry(4, 140));
        values.add(new Entry(5, 300));
        values.add(new Entry(6, 200));
        values.add(new Entry(7, 100));
        values.add(new Entry(8, 300));
            /*values.add(new Entry(9, 700));
            values.add(new Entry(10, 100));
            values.add(new Entry(11, 200));
            values.add(new Entry(12, 300));
            values.add(new Entry(13, 500));
            values.add(new Entry(14, 100));
            values.add(new Entry(15, 400));
            values.add(new Entry(16, 600));
            values.add(new Entry(17, 700));
            values.add(new Entry(18, 200));
            values.add(new Entry(19, 400));
            values.add(new Entry(20, 100));
            values.add(new Entry(21, 300));
            values.add(new Entry(22, 500));
            values.add(new Entry(23, 200));
            values.add(new Entry(24, 600));
            values.add(new Entry(25, 100));
            values.add(new Entry(26, 400));
            values.add(new Entry(27, 300));
            values.add(new Entry(28, 200));
            values.add(new Entry(29, 100));
            values.add(new Entry(30, 500));*/
//        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);

            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this.getContext(), R.drawable.item_fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            set1.setDrawCircles(false);
            set1.setLineWidth(2f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
//            set1.setHighLightColor(R.color.black);
            set1.setColor(Color.parseColor("#5855B6"));
//            set1.setFillColor(Color.WHITE);
//            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
//            data.setValueTypeface(tfLight);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            chart.setData(data);
        }
    }


    public void setTitle(String title1){
        title.setText(title1);
    }


}
