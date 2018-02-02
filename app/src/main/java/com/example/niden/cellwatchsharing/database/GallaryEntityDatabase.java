package com.example.niden.cellwatchsharing.database;

import java.util.List;

/**
 * Created by niden on 26-Jan-18.
 */

public class GallaryEntityDatabase {
    public List<String> fileNameList;
    public List<String> fileDoneList;

    public GallaryEntityDatabase(List<String> fileNameList, List<String> fileDoneList) {
        this.fileNameList = fileNameList;
        this.fileDoneList = fileDoneList;
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public List<String> getFileDoneList() {
        return fileDoneList;
    }

    public void setFileDoneList(List<String> fileDoneList) {
        this.fileDoneList = fileDoneList;
    }
}
