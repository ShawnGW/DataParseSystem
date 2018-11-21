package com.vtest.it.pojo.binwaferinfors;

import java.io.Serializable;

public class waferIdInforBean implements Serializable {
    private String waferId;
    private String params;

    public String getWaferId() {
        return waferId;
    }

    public void setWaferId(String waferId) {
        this.waferId = waferId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
