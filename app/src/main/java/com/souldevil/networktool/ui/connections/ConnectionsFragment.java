package com.souldevil.networktool.ui.connections;

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

public class ConnectionsFragment extends Fragment {
    private ConnectionsViewModel viewModel;
    private RecyclerView recyclerView;
    private ConnectionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_connections, container, false);
        
        recyclerView = root.findViewById(R.id.connections_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new ConnectionsAdapter();
        recyclerView.setAdapter(adapter);
        
        viewModel = new ViewModelProvider(this).get(ConnectionsViewModel.class);
        viewModel.getConnections().observe(getViewLifecycleOwner(), connections -> {
            adapter.setConnections(connections);
        });
        
        return root;
    }
}
