package com.souldevil.networktool.ui.connections;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.souldevil.networktool.data.model.ConnectionRecord;
import com.souldevil.networktool.data.repository.ConnectionRepository;

import java.util.List;

public class ConnectionsViewModel extends AndroidViewModel {
    private ConnectionRepository repository;
    private LiveData<List<ConnectionRecord>> connections;
    private MutableLiveData<String> filterProtocol = new MutableLiveData<>();

    public ConnectionsViewModel(@NonNull Application application) {
        super(application);
        repository = new ConnectionRepository(application);
        connections = repository.getAllConnections();
    }

    public LiveData<List<ConnectionRecord>> getConnections() {
        return connections;
    }

    public LiveData<List<ConnectionRecord>> getActiveConnections() {
        return repository.getActiveConnections();
    }

    public void setFilterProtocol(String protocol) {
        filterProtocol.setValue(protocol);
        if (protocol == null || protocol.isEmpty()) {
            connections = repository.getAllConnections();
        } else {
            connections = repository.getConnectionsByProtocol(protocol);
        }
    }

    public void clearAll() {
        repository.deleteAll();
    }
}
