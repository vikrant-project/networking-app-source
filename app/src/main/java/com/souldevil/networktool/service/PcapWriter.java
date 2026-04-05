package com.souldevil.networktool.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Writes packets in PCAP format (libpcap)
 */
public class PcapWriter {
    private FileOutputStream outputStream;
    private File pcapFile;
    private boolean headerWritten = false;

    // PCAP Global Header constants
    private static final int PCAP_MAGIC_NUMBER = 0xA1B2C3D4;
    private static final short VERSION_MAJOR = 2;
    private static final short VERSION_MINOR = 4;
    private static final int THISZONE = 0;
    private static final int SIGFIGS = 0;
    private static final int SNAPLEN = 65535;
    private static final int NETWORK = 101; // LINKTYPE_RAW (raw IP packets)

    public PcapWriter(File file) throws IOException {
        this.pcapFile = file;
        this.outputStream = new FileOutputStream(file);
        writeGlobalHeader();
    }

    private void writeGlobalHeader() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
        buffer.putInt(PCAP_MAGIC_NUMBER);
        buffer.putShort(VERSION_MAJOR);
        buffer.putShort(VERSION_MINOR);
        buffer.putInt(THISZONE);
        buffer.putInt(SIGFIGS);
        buffer.putInt(SNAPLEN);
        buffer.putInt(NETWORK);
        
        outputStream.write(buffer.array());
        headerWritten = true;
    }

    public synchronized void writePacket(byte[] packetData) throws IOException {
        if (!headerWritten || packetData == null || packetData.length == 0) {
            return;
        }

        long timestamp = System.currentTimeMillis();
        int tsSec = (int) (timestamp / 1000);
        int tsUsec = (int) ((timestamp % 1000) * 1000);

        // Write packet record header (16 bytes)
        ByteBuffer header = ByteBuffer.allocate(16);
        header.order(ByteOrder.LITTLE_ENDIAN);
        
        header.putInt(tsSec);           // timestamp seconds
        header.putInt(tsUsec);          // timestamp microseconds
        header.putInt(packetData.length); // number of octets captured
        header.putInt(packetData.length); // actual length of packet
        
        outputStream.write(header.array());
        outputStream.write(packetData);
    }

    public void close() throws IOException {
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
    }

    public File getPcapFile() {
        return pcapFile;
    }

    public long getFileSize() {
        return pcapFile.length();
    }
}
