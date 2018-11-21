package com.vtest.it.pojo;

import java.io.Serializable;

public class MarkAndSkipToPassBean implements Serializable {
    private String customCode;
    private String device;

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
