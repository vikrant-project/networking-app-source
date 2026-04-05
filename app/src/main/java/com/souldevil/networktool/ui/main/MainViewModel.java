package com.souldevil.networktool.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.souldevil.networktool.data.model.ConnectionRecord;
import com.souldevil.networktool.data.repository.ConnectionRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private ConnectionRepository repository;
    private LiveData<List<ConnectionRecord>> allConnections;
    private MutableLiveData<Boolean> isCapturing = new MutableLiveData<>(false);

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new ConnectionRepository(application);
        allConnections = repository.getAllConnections();
    }

    public LiveData<List<ConnectionRecord>> getAllConnections() {
        return allConnections;
    }

    public MutableLiveData<Boolean> getIsCapturing() {
        return isCapturing;
    }

    public void setCapturing(boolean capturing) {
        isCapturing.setValue(capturing);
    }
}
