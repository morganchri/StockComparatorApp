package edu.neu.team28finalproject;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

public class GraphViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout linearLayout;
    public Button[] btn = new Button[5];
    public int[] btn_id = {R.id.oneDay, R.id.fiveDay, R.id.oneMonth, R.id.sixMonth, R.id.Year};
    public LineChart chart;

    public GraphViewHolder(@NonNull View graphView) {
        super(graphView);
        linearLayout = graphView.findViewById(R.id.linLayout);
        for(int i = 0; i < btn.length; i++) {
            btn[i] = graphView.findViewById(btn_id[i]);
        }
        btn[4].setTextColor(Color.MAGENTA);
        chart = graphView.findViewById(R.id.chart1);
    }

    public void bindThisData(GraphViewObj graphToBind) {
        addChart(chart, graphToBind.getEntries());
    }

    public void addChart(LineChart chart, LineData data) {
        chart.getDescription().setEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setTouchEnabled(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setViewPortOffsets(10,0,10,0);
        chart.getLegend().setEnabled(false);
        chart.setData(data);
        chart.notifyDataSetChanged();
    }
}
