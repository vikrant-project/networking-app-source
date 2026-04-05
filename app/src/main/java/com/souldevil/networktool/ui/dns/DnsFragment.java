package com.souldevil.networktool.ui.dns;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.souldevil.networktool.R;

public class DnsFragment extends Fragment {
    private DnsViewModel viewModel;
    private RecyclerView recyclerView;
    private DnsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dns, container, false);
        
        recyclerView = root.findViewById(R.id.dns_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new DnsAdapter();
        recyclerView.setAdapter(adapter);
        
        viewModel = new ViewModelProvider(this).get(DnsViewModel.class);
        viewModel.getAllDnsRecords().observe(getViewLifecycleOwner(), dnsRecords -> {
            adapter.setDnsRecords(dnsRecords);
        });
        
        return root;
    }
}
