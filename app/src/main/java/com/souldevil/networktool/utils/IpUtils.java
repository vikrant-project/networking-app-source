package com.souldevil.networktool.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class IpUtils {
    
    public static String ipv4ToString(byte[] bytes) {
        if (bytes == null || bytes.length != 4) {
            return "0.0.0.0";
        }
        return String.format("%d.%d.%d.%d",
                bytes[0] & 0xFF,
                bytes[1] & 0xFF,
                bytes[2] & 0xFF,
                bytes[3] & 0xFF);
    }

    public static String ipv4ToString(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip & 0xFF),
                (ip >> 8 & 0xFF),
                (ip >> 16 & 0xFF),
                (ip >> 24 & 0xFF));
    }

    public static String ipv6ToString(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            return "::";
        }
        try {
            InetAddress addr = InetAddress.getByAddress(bytes);
            return addr.getHostAddress();
        } catch (UnknownHostException e) {
            return "::";
        }
    }

    public static int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24) |
               ((bytes[offset + 1] & 0xFF) << 16) |
               ((bytes[offset + 2] & 0xFF) << 8) |
               (bytes[offset + 3] & 0xFF);
    }

    public static short bytesToShort(byte[] bytes, int offset) {
        return (short) (((bytes[offset] & 0xFF) << 8) |
                        (bytes[offset + 1] & 0xFF));
    }

    public static boolean isPrivateIp(String ip) {
        if (ip == null) return false;
        
        String[] parts = ip.split("\\.");
        if (parts.length != 4) return false;
        
        try {
            int first = Integer.parseInt(parts[0]);
            int second = Integer.parseInt(parts[1]);
            
            // 10.0.0.0/8
            if (first == 10) return true;
            
            // 172.16.0.0/12
            if (first == 172 && second >= 16 && second <= 31) return true;
            
            // 192.168.0.0/16
            if (first == 192 && second == 168) return true;
            
            // 127.0.0.0/8 (localhost)
            if (first == 127) return true;
            
        } catch (NumberFormatException e) {
            return false;
        }
        
        return false;
    }
}
