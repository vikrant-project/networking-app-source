package com.souldevil.networktool.ui.stats;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class StatsViewModel extends AndroidViewModel {
    private MutableLiveData<Long> uploadSpeed = new MutableLiveData<>(0L);
    private MutableLiveData<Long> downloadSpeed = new MutableLiveData<>(0L);
    private MutableLiveData<Long> totalDataSent = new MutableLiveData<>(0L);
    private MutableLiveData<Long> totalDataReceived = new MutableLiveData<>(0L);

    public StatsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Long> getUploadSpeed() {
        return uploadSpeed;
    }

    public MutableLiveData<Long> getDownloadSpeed() {
        return downloadSpeed;
    }

    public MutableLiveData<Long> getTotalDataSent() {
        return totalDataSent;
    }

    public MutableLiveData<Long> getTotalDataReceived() {
        return totalDataReceived;
    }

    public void updateStats(long sent, long received) {
        totalDataSent.setValue(sent);
        totalDataReceived.setValue(received);
    }
}
