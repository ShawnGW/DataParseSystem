package com.vtest.it.pojo;

import java.io.Serializable;

public class RawdataModifyBean implements Serializable {
    private String fileName;
    private String status;
    private String reason;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
