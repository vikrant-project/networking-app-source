package com.souldevil.networktool.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "connections")
public class ConnectionRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String appName;
    private String packageName;
    private String sourceIp;
    private String destIp;
    private int sourcePort;
    private int destPort;
    private String protocol;
    private long dataSent;
    private long dataReceived;
    private long timestamp;
    private boolean isActive;
    private int uid;

    public ConnectionRecord() {
        this.timestamp = System.currentTimeMillis();
        this.isActive = true;
        this.dataSent = 0;
        this.dataReceived = 0;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getSourceIp() { return sourceIp; }
    public void setSourceIp(String sourceIp) { this.sourceIp = sourceIp; }

    public String getDestIp() { return destIp; }
    public void setDestIp(String destIp) { this.destIp = destIp; }

    public int getSourcePort() { return sourcePort; }
    public void setSourcePort(int sourcePort) { this.sourcePort = sourcePort; }

    public int getDestPort() { return destPort; }
    public void setDestPort(int destPort) { this.destPort = destPort; }

    public String getProtocol() { return protocol; }
    public void setProtocol(String protocol) { this.protocol = protocol; }

    public long getDataSent() { return dataSent; }
    public void setDataSent(long dataSent) { this.dataSent = dataSent; }

    public long getDataReceived() { return dataReceived; }
    public void setDataReceived(long dataReceived) { this.dataReceived = dataReceived; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }
}
