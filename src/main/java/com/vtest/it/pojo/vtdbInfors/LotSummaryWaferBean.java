package com.vtest.it.pojo.vtdbInfors;

import java.util.ArrayList;

public class LotSummaryWaferBean {
    private String waferNo;
    private Integer grossDie;
    private Integer passDie;
    private Integer failDie;
    private Double yield;
    private ArrayList<Integer> binSummary;

    public String getWaferNo() {
        return waferNo;
    }

    public void setWaferNo(String waferNo) {
        this.waferNo = waferNo;
    }

    public Integer getGrossDie() {
        return grossDie;
    }

    public void setGrossDie(Integer grossDie) {
        this.grossDie = grossDie;
    }

    public Integer getPassDie() {
        return passDie;
    }

    public void setPassDie(Integer passDie) {
        this.passDie = passDie;
    }

    public Integer getFailDie() {
        return failDie;
    }

    public void setFailDie(Integer failDie) {
        this.failDie = failDie;
    }

    public Double getYield() {
        return yield;
    }

    public void setYield(Double yield) {
        this.yield = yield;
    }

    public ArrayList<Integer> getBinSummary() {
        return binSummary;
    }

    public void setBinSummary(ArrayList<Integer> binSummary) {
        this.binSummary = binSummary;
    }
}
