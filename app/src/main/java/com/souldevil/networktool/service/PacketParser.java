package com.souldevil.networktool.service;

import com.souldevil.networktool.data.model.PacketInfo;
import com.souldevil.networktool.utils.IpUtils;
import com.souldevil.networktool.utils.ProtocolUtils;

import java.nio.ByteBuffer;

/**
 * Parses raw IP packets and extracts packet information
 */
public class PacketParser {

    public static PacketInfo parse(byte[] packet) {
        if (packet == null || packet.length < 20) {
            return null;
        }

        PacketInfo info = new PacketInfo();
        info.setRawData(packet);
        info.setPayloadSize(packet.length);

        try {
            // Parse IP header (first byte contains version and header length)
            int versionAndIHL = packet[0] & 0xFF;
            int version = (versionAndIHL >> 4) & 0x0F;
            int headerLength = (versionAndIHL & 0x0F) * 4;

            if (version == 4) {
                parseIPv4(packet, info, headerLength);
            } else if (version == 6) {
                parseIPv6(packet, info);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return info;
    }

    private static void parseIPv4(byte[] packet, PacketInfo info, int headerLength) {
        if (packet.length < headerLength) return;

        // Protocol (byte 9)
        int protocol = packet[9] & 0xFF;
        info.setProtocol(ProtocolUtils.getProtocolName(protocol));

        // Source IP (bytes 12-15)
        byte[] srcIp = new byte[4];
        System.arraycopy(packet, 12, srcIp, 0, 4);
        info.setSourceIp(IpUtils.ipv4ToString(srcIp));

        // Destination IP (bytes 16-19)
        byte[] dstIp = new byte[4];
        System.arraycopy(packet, 16, dstIp, 0, 4);
        info.setDestIp(IpUtils.ipv4ToString(dstIp));

        // Parse transport layer
        if (protocol == ProtocolUtils.PROTOCOL_TCP && packet.length > headerLength + 4) {
            parseTCP(packet, info, headerLength);
        } else if (protocol == ProtocolUtils.PROTOCOL_UDP && packet.length > headerLength + 4) {
            parseUDP(packet, info, headerLength);
        }
    }

    private static void parseIPv6(byte[] packet, PacketInfo info) {
        if (packet.length < 40) return;

        // Next header / Protocol (byte 6)
        int protocol = packet[6] & 0xFF;
        info.setProtocol(ProtocolUtils.getProtocolName(protocol));

        // Source IP (bytes 8-23)
        byte[] srcIp = new byte[16];
        System.arraycopy(packet, 8, srcIp, 0, 16);
        info.setSourceIp(IpUtils.ipv6ToString(srcIp));

        // Destination IP (bytes 24-39)
        byte[] dstIp = new byte[16];
        System.arraycopy(packet, 24, dstIp, 0, 16);
        info.setDestIp(IpUtils.ipv6ToString(dstIp));

        // Parse transport layer (IPv6 header is always 40 bytes)
        if (protocol == ProtocolUtils.PROTOCOL_TCP && packet.length > 44) {
            parseTCP(packet, info, 40);
        } else if (protocol == ProtocolUtils.PROTOCOL_UDP && packet.length > 44) {
            parseUDP(packet, info, 40);
        }
    }

    private static void parseTCP(byte[] packet, PacketInfo info, int offset) {
        // Source port (2 bytes)
        int srcPort = ((packet[offset] & 0xFF) << 8) | (packet[offset + 1] & 0xFF);
        info.setSourcePort(srcPort);

        // Destination port (2 bytes)
        int dstPort = ((packet[offset + 2] & 0xFF) << 8) | (packet[offset + 3] & 0xFF);
        info.setDestPort(dstPort);

        // Check for common protocols
        if (ProtocolUtils.isDnsPort(dstPort) || ProtocolUtils.isDnsPort(srcPort)) {
            info.setProtocol("DNS");
        } else if (ProtocolUtils.isHttpsPort(dstPort) || ProtocolUtils.isHttpsPort(srcPort)) {
            info.setProtocol("HTTPS");
        } else if (ProtocolUtils.isHttpPort(dstPort) || ProtocolUtils.isHttpPort(srcPort)) {
            info.setProtocol("HTTP");
        }
    }

    private static void parseUDP(byte[] packet, PacketInfo info, int offset) {
        // Source port (2 bytes)
        int srcPort = ((packet[offset] & 0xFF) << 8) | (packet[offset + 1] & 0xFF);
        info.setSourcePort(srcPort);

        // Destination port (2 bytes)
        int dstPort = ((packet[offset + 2] & 0xFF) << 8) | (packet[offset + 3] & 0xFF);
        info.setDestPort(dstPort);

        // Check for DNS
        if (ProtocolUtils.isDnsPort(dstPort) || ProtocolUtils.isDnsPort(srcPort)) {
            info.setProtocol("DNS");
        }
    }
}
