package com.souldevil.networktool.ui.connections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.souldevil.networktool.R;
import com.souldevil.networktool.data.model.ConnectionRecord;
import com.souldevil.networktool.utils.FileUtils;
import com.souldevil.networktool.utils.ProtocolUtils;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsAdapter extends RecyclerView.Adapter<ConnectionsAdapter.ViewHolder> {
    private List<ConnectionRecord> connections = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_connection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConnectionRecord connection = connections.get(position);
        holder.bind(connection);
    }

    @Override
    public int getItemCount() {
        return connections.size();
    }

    public void setConnections(List<ConnectionRecord> connections) {
        this.connections = connections;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppName;
        TextView tvProtocol;
        TextView tvConnection;
        TextView tvData;
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvAppName = itemView.findViewById(R.id.tv_app_name);
            tvProtocol = itemView.findViewById(R.id.tv_protocol);
            tvConnection = itemView.findViewById(R.id.tv_connection);
            tvData = itemView.findViewById(R.id.tv_data);
            tvTime = itemView.findViewById(R.id.tv_time);
        }

        void bind(ConnectionRecord connection) {
            tvAppName.setText(connection.getAppName() != null ? connection.getAppName() : "Unknown");
            tvProtocol.setText(connection.getProtocol());
            tvProtocol.setTextColor(ProtocolUtils.getProtocolColor(connection.getProtocol()));
            
            String connStr = connection.getSourceIp() + ":" + connection.getSourcePort() +
                    " → " + connection.getDestIp() + ":" + connection.getDestPort();
            tvConnection.setText(connStr);
            
            String dataStr = "↑ " + FileUtils.formatFileSize(connection.getDataSent()) +
                    "  ↓ " + FileUtils.formatFileSize(connection.getDataReceived());
            tvData.setText(dataStr);
            
            tvTime.setText(FileUtils.formatTimestamp(connection.getTimestamp()));
        }
    }
}
