package com.souldevil.networktool.data.model;

public class PacketInfo {
    private String sourceIp;
    private String destIp;
    private int sourcePort;
    private int destPort;
    private String protocol;
    private int payloadSize;
    private long timestamp;
    private String appPackageName;
    private int uid;
    private byte[] rawData;

    public PacketInfo() {
        this.timestamp = System.currentTimeMillis();
    }

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

    public int getPayloadSize() { return payloadSize; }
    public void setPayloadSize(int payloadSize) { this.payloadSize = payloadSize; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public String getAppPackageName() { return appPackageName; }
    public void setAppPackageName(String appPackageName) { this.appPackageName = appPackageName; }

    public int getUid() { return uid; }
    public void setUid(int uid) { this.uid = uid; }

    public byte[] getRawData() { return rawData; }
    public void setRawData(byte[] rawData) { this.rawData = rawData; }
}
