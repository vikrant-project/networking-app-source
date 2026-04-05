package com.souldevil.networktool.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.souldevil.networktool.data.model.DnsRecord;

import java.util.List;

@Dao
public interface DnsDao {
    @Insert
    long insert(DnsRecord dnsRecord);

    @Delete
    void delete(DnsRecord dnsRecord);

    @Query("SELECT * FROM dns_records ORDER BY timestamp DESC")
    LiveData<List<DnsRecord>> getAllDnsRecords();

    @Query("SELECT * FROM dns_records WHERE domain LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    LiveData<List<DnsRecord>> searchDnsRecords(String query);

    @Query("DELETE FROM dns_records")
    void deleteAll();

    @Query("SELECT * FROM dns_records WHERE isBlocked = 1 ORDER BY timestamp DESC")
    LiveData<List<DnsRecord>> getBlockedDomains();
}
