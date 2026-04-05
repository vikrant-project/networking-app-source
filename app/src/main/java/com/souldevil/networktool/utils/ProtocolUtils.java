package com.souldevil.networktool.utils;

public class ProtocolUtils {
    
    // IP Protocol Numbers
    public static final int PROTOCOL_ICMP = 1;
    public static final int PROTOCOL_TCP = 6;
    public static final int PROTOCOL_UDP = 17;

    public static String getProtocolName(int protocolNumber) {
        switch (protocolNumber) {
            case PROTOCOL_ICMP:
                return "ICMP";
            case PROTOCOL_TCP:
                return "TCP";
            case PROTOCOL_UDP:
                return "UDP";
            default:
                return "Unknown (" + protocolNumber + ")";
        }
    }

    public static int getProtocolColor(String protocol) {
        if (protocol == null) return 0xFF888888;
        
        switch (protocol.toUpperCase()) {
            case "TCP":
                return 0xFF2196F3; // Blue
            case "UDP":
                return 0xFF4CAF50; // Green
            case "DNS":
                return 0xFFFF9800; // Orange
            case "HTTPS":
            case "TLS":
                return 0xFF9C27B0; // Purple
            case "HTTP":
                return 0xFF03A9F4; // Light Blue
            case "ICMP":
                return 0xFFFFC107; // Amber
            default:
                return 0xFF888888; // Gray
        }
    }

    public static boolean isDnsPort(int port) {
        return port == 53;
    }

    public static boolean isHttpPort(int port) {
        return port == 80 || port == 8080;
    }

    public static boolean isHttpsPort(int port) {
        return port == 443 || port == 8443;
    }

    public static String getServiceName(int port) {
        switch (port) {
            case 20:
            case 21:
                return "FTP";
            case 22:
                return "SSH";
            case 23:
                return "Telnet";
            case 25:
                return "SMTP";
            case 53:
                return "DNS";
            case 80:
                return "HTTP";
            case 110:
                return "POP3";
            case 143:
                return "IMAP";
            case 443:
                return "HTTPS";
            case 3306:
                return "MySQL";
            case 5432:
                return "PostgreSQL";
            case 6379:
                return "Redis";
            case 8080:
                return "HTTP-Alt";
            case 8443:
                return "HTTPS-Alt";
            default:
                return String.valueOf(port);
        }
    }
}
