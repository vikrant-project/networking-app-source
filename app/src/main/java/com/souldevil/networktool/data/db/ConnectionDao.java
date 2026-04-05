package com.souldevil.networktool.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.souldevil.networktool.data.model.ConnectionRecord;

import java.util.List;

@Dao
public interface ConnectionDao {
    @Insert
    long insert(ConnectionRecord connection);

    @Update
    void update(ConnectionRecord connection);

    @Delete
    void delete(ConnectionRecord connection);

    @Query("SELECT * FROM connections ORDER BY timestamp DESC")
    LiveData<List<ConnectionRecord>> getAllConnections();

    @Query("SELECT * FROM connections WHERE isActive = 1 ORDER BY timestamp DESC")
    LiveData<List<ConnectionRecord>> getActiveConnections();

    @Query("SELECT * FROM connections WHERE packageName = :packageName ORDER BY timestamp DESC")
    LiveData<List<ConnectionRecord>> getConnectionsByPackage(String packageName);

    @Query("DELETE FROM connections")
    void deleteAll();

    @Query("SELECT * FROM connections WHERE protocol = :protocol ORDER BY timestamp DESC")
    LiveData<List<ConnectionRecord>> getConnectionsByProtocol(String protocol);

    @Query("SELECT SUM(dataSent) FROM connections")
    long getTotalDataSent();

    @Query("SELECT SUM(dataReceived) FROM connections")
    long getTotalDataReceived();
}
