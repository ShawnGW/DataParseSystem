package com.vtest.it.pojo.datainfortomes;

import java.util.HashMap;

public class BinSummaryToMes {
    private String lot;
    private String cpStep;
    private String waferId;
    private HashMap<Integer,Integer> binSummary;

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

    public HashMap<Integer, Integer> getBinSummary() {
        return binSummary;
    }

    public void setBinSummary(HashMap<Integer, Integer> binSummary) {
        this.binSummary = binSummary;
    }
}
