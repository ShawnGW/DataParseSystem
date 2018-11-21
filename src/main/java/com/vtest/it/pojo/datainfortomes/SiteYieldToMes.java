package com.vtest.it.pojo.datainfortomes;

import java.io.Serializable;
import java.util.HashMap;

public class SiteYieldToMes implements Serializable {
    private String lot;
    private String cpStep;
    private String waferId;
    private HashMap<String,String> siteYieldSummary;

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getCpStep() {
        return cpStep;
    }

    public void setCpStep(String cpStep) {
        this.cpStep = cpStep;
    }

    public String getWaferId() {
        return waferId;
    }

    public void setWaferId(String waferId) {
        this.waferId = waferId;
    }

    public HashMap<String, String> getSiteYieldSummary() {
        return siteYieldSummary;
    }

    public void setSiteYieldSummary(HashMap<String, String> siteYieldSummary) {
        this.siteYieldSummary = siteYieldSummary;
    }
}
