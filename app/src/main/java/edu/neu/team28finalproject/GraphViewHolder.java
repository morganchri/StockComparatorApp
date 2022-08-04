package edu.neu.team28finalproject;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class GraphViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout linearLayout;
    public Button day1;
    public Button day5;
    public Button month;
    public Button month6;
    public Button year;
    public Button customDate;
    public LineChart chart;

    public GraphViewHolder(@NonNull View graphView) {
        super(graphView);
        linearLayout = graphView.findViewById(R.id.linLayout);
        day1 = graphView.findViewById(R.id.oneDay);
        day5 = graphView.findViewById(R.id.fiveDay);
        month = graphView.findViewById(R.id.oneMonth);
        month6 = graphView.findViewById(R.id.sixMonth);
        year = graphView.findViewById(R.id.Year);
        customDate = graphView.findViewById(R.id.customDate);
        chart = (LineChart) graphView.findViewById(R.id.chart1);
    }

    public void bindThisData(GraphViewObj graphToBind) {
        addChart(chart, graphToBind.getEntries());
    }

    public void addChart(LineChart chart, LineData data) {
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);

        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

        chart.setBackgroundColor(Color.WHITE);
        chart.setViewPortOffsets(10,0,10,0);
        //chart.setVisibleYRange(0, 100,chart.get);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        //chart.notifyDataSetChanged();
    }


}
