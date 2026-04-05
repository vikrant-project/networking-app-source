package com.souldevil.networktool.ui.pcap;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.souldevil.networktool.data.repository.PcapRepository;

import java.io.File;
import java.util.List;

public class PcapViewModel extends AndroidViewModel {
    private PcapRepository repository;
    private MutableLiveData<List<File>> pcapFiles = new MutableLiveData<>();

    public PcapViewModel(@NonNull Application application) {
        super(application);
        repository = new PcapRepository(application);
        loadPcapFiles();
    }

    public MutableLiveData<List<File>> getPcapFiles() {
        return pcapFiles;
    }

    public void loadPcapFiles() {
        new Thread(() -> {
            List<File> files = repository.getAllPcapFiles();
            pcapFiles.postValue(files);
        }).start();
    }

    public void deletePcapFile(File file) {
        new Thread(() -> {
            repository.deletePcapFile(file);
            loadPcapFiles();
        }).start();
    }

    public void deleteAllPcapFiles() {
        new Thread(() -> {
            repository.deleteAllPcapFiles();
            loadPcapFiles();
        }).start();
    }
}
