package com.souldevil.networktool.ui.stats;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.souldevil.networktool.R;
import com.souldevil.networktool.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class StatsFragment extends Fragment {
    private StatsViewModel viewModel;
    private LineChart chart;
    private TextView tvTotalSent;
    private TextView tvTotalReceived;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        
        chart = root.findViewById(R.id.traffic_chart);
        tvTotalSent = root.findViewById(R.id.tv_total_sent);
        tvTotalReceived = root.findViewById(R.id.tv_total_received);
        
        setupChart();
        
        viewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        viewModel.getTotalDataSent().observe(getViewLifecycleOwner(), sent -> {
            tvTotalSent.setText("↑ " + FileUtils.formatFileSize(sent));
        });
        
        viewModel.getTotalDataReceived().observe(getViewLifecycleOwner(), received -> {
            tvTotalReceived.setText("↓ " + FileUtils.formatFileSize(received));
        });
        
        return root;
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        
        List<Entry> entries = new ArrayList<>();
        LineDataSet dataSet = new LineDataSet(entries, "Traffic");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.WHITE);
        
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}
