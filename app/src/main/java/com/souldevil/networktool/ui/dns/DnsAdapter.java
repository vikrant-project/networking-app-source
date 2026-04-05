package com.souldevil.networktool.ui.dns;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.souldevil.networktool.R;
import com.souldevil.networktool.data.model.DnsRecord;
import com.souldevil.networktool.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DnsAdapter extends RecyclerView.Adapter<DnsAdapter.ViewHolder> {
    private List<DnsRecord> dnsRecords = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dns, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DnsRecord record = dnsRecords.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return dnsRecords.size();
    }

    public void setDnsRecords(List<DnsRecord> records) {
        this.dnsRecords = records;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDomain;
        TextView tvResolvedIp;
        TextView tvQueryType;
        TextView tvTime;

        ViewHolder(View itemView) {
            super(itemView);
            tvDomain = itemView.findViewById(R.id.tv_domain);
            tvResolvedIp = itemView.findViewById(R.id.tv_resolved_ip);
            tvQueryType = itemView.findViewById(R.id.tv_query_type);
            tvTime = itemView.findViewById(R.id.tv_time);
        }

        void bind(DnsRecord record) {
            tvDomain.setText(record.getDomain());
            tvResolvedIp.setText(record.getResolvedIp());
            tvQueryType.setText(record.getQueryType());
            tvTime.setText(FileUtils.formatTimestamp(record.getTimestamp()));
        }
    }
}
