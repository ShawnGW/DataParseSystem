package com.vtest.it.pojo;

import java.io.Serializable;

public class MesProperties implements Serializable {
    private String host;
    private String initUrl;
    private String acode;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getInitUrl() {
        return initUrl;
    }

    public void setInitUrl(String initUrl) {
        this.initUrl = initUrl;
    }

    public String getAcode() {
        return acode;
    }

    public void setAcode(String acode) {
        this.acode = acode;
    }
}
