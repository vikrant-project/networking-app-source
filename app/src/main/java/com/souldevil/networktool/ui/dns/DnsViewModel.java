package com.souldevil.networktool.ui.dns;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.souldevil.networktool.data.db.AppDatabase;
import com.souldevil.networktool.data.db.DnsDao;
import com.souldevil.networktool.data.model.DnsRecord;

import java.util.List;

public class DnsViewModel extends AndroidViewModel {
    private DnsDao dnsDao;
    private LiveData<List<DnsRecord>> allDnsRecords;

    public DnsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        dnsDao = database.dnsDao();
        allDnsRecords = dnsDao.getAllDnsRecords();
    }

    public LiveData<List<DnsRecord>> getAllDnsRecords() {
        return allDnsRecords;
    }

    public LiveData<List<DnsRecord>> searchDnsRecords(String query) {
        return dnsDao.searchDnsRecords(query);
    }

    public void clearAll() {
        new Thread(() -> dnsDao.deleteAll()).start();
    }
}
