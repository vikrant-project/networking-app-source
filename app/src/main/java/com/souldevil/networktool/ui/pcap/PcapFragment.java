package com.souldevil.networktool.ui.pcap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.souldevil.networktool.R;

public class PcapFragment extends Fragment {
    private PcapViewModel viewModel;
    private RecyclerView recyclerView;
    private TextView tvEmpty;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pcap, container, false);
        
        recyclerView = root.findViewById(R.id.pcap_recycler_view);
        tvEmpty = root.findViewById(R.id.tv_empty);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        viewModel = new ViewModelProvider(this).get(PcapViewModel.class);
        viewModel.getPcapFiles().observe(getViewLifecycleOwner(), files -> {
            if (files == null || files.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
            }
        });
        
        return root;
    }
}
