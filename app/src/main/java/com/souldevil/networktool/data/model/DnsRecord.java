package com.souldevil.networktool.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dns_records")
public class DnsRecord {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String domain;
    private String resolvedIp;
    private String queryType;
    private int ttl;
    private long timestamp;
    private boolean isBlocked;
    private String appPackageName;

    public DnsRecord() {
        this.timestamp = System.currentTimeMillis();
        this.isBlocked = false;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public String getResolvedIp() { return resolvedIp; }
    public void setResolvedIp(String resolvedIp) { this.resolvedIp = resolvedIp; }

    public String getQueryType() { return queryType; }
    public void setQueryType(String queryType) { this.queryType = queryType; }

    public int getTtl() { return ttl; }
    public void setTtl(int ttl) { this.ttl = ttl; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    public String getAppPackageName() { return appPackageName; }
    public void setAppPackageName(String appPackageName) { this.appPackageName = appPackageName; }
}
