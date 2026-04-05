package com.souldevil.networktool.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.souldevil.networktool.data.db.AppDatabase;
import com.souldevil.networktool.data.db.ConnectionDao;
import com.souldevil.networktool.data.model.ConnectionRecord;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionRepository {
    private ConnectionDao connectionDao;
    private LiveData<List<ConnectionRecord>> allConnections;
    private ExecutorService executorService;

    public ConnectionRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        connectionDao = database.connectionDao();
        allConnections = connectionDao.getAllConnections();
        executorService = Executors.newFixedThreadPool(2);
    }

    public void insert(ConnectionRecord connection) {
        executorService.execute(() -> connectionDao.insert(connection));
    }

    public void update(ConnectionRecord connection) {
        executorService.execute(() -> connectionDao.update(connection));
    }

    public void delete(ConnectionRecord connection) {
        executorService.execute(() -> connectionDao.delete(connection));
    }

    public void deleteAll() {
        executorService.execute(() -> connectionDao.deleteAll());
    }

    public LiveData<List<ConnectionRecord>> getAllConnections() {
        return allConnections;
    }

    public LiveData<List<ConnectionRecord>> getActiveConnections() {
        return connectionDao.getActiveConnections();
    }

    public LiveData<List<ConnectionRecord>> getConnectionsByPackage(String packageName) {
        return connectionDao.getConnectionsByPackage(packageName);
    }

    public LiveData<List<ConnectionRecord>> getConnectionsByProtocol(String protocol) {
        return connectionDao.getConnectionsByProtocol(protocol);
    }
}
