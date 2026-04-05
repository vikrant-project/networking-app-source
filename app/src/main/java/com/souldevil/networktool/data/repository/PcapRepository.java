package com.souldevil.networktool.data.repository;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PcapRepository {
    private Context context;

    public PcapRepository(Context context) {
        this.context = context;
    }

    public File getPcapDirectory() {
        File dir;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            // Android 10+ use scoped storage
            dir = new File(context.getExternalFilesDir(null), "captures");
        } else {
            // Android 9 use external storage
            dir = new File(Environment.getExternalStorageDirectory(), "SoulDevil/captures");
        }
        
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public List<File> getAllPcapFiles() {
        File dir = getPcapDirectory();
        File[] files = dir.listFiles((d, name) -> name.endsWith(".pcap"));
        
        if (files != null) {
            return Arrays.asList(files);
        }
        return new ArrayList<>();
    }

    public boolean deletePcapFile(File file) {
        return file.delete();
    }

    public void deleteAllPcapFiles() {
        List<File> files = getAllPcapFiles();
        for (File file : files) {
            file.delete();
        }
    }
}
