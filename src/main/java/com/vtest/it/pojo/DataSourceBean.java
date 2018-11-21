package com.vtest.it.pojo;

import java.io.Serializable;

public class DataSourceBean implements Serializable {
    private String sourcePath;
    private String backupSourcePath;
    private String backupSourcePathByCp;
    private String rawdataPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getBackupSourcePath() {
        return backupSourcePath;
    }

    public void setBackupSourcePath(String backupSourcePath) {
        this.backupSourcePath = backupSourcePath;
    }

    public String getBackupSourcePathByCp() {
        return backupSourcePathByCp;
    }

    public void setBackupSourcePathByCp(String backupSourcePathByCp) {
        this.backupSourcePathByCp = backupSourcePathByCp;
    }

    public String getRawdataPath() {
        return rawdataPath;
    }

    public void setRawdataPath(String rawdataPath) {
        this.rawdataPath = rawdataPath;
    }
}
