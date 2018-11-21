package com.vtest.it.pojo.datainfortomes;

import java.io.Serializable;

public class TouchDownInforToMes implements Serializable {
    private String lot;
    private String cpStep;
    private String waferId;
    private int primaryTouchDown;
    private long primaryTimeRange;
    private int retestTouchDown;
    private long retestTimeRange;
    private long singleTestTime;
    private double retestRate;
    private long totalTestTime;

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

    public int getPrimaryTouchDown() {
        return primaryTouchDown;
    }

    public void setPrimaryTouchDown(int primaryTouchDown) {
        this.primaryTouchDown = primaryTouchDown;
    }

    public long getPrimaryTimeRange() {
        return primaryTimeRange;
    }

    public void setPrimaryTimeRange(long primaryTimeRange) {
        this.primaryTimeRange = primaryTimeRange;
    }

    public int getRetestTouchDown() {
        return retestTouchDown;
    }

    public void setRetestTouchDown(int retestTouchDown) {
        this.retestTouchDown = retestTouchDown;
    }

    public long getRetestTimeRange() {
        return retestTimeRange;
    }

    public void setRetestTimeRange(long retestTimeRange) {
        this.retestTimeRange = retestTimeRange;
    }

    public long getSingleTestTime() {
        return singleTestTime;
    }

    public void setSingleTestTime(long singleTestTime) {
        this.singleTestTime = singleTestTime;
    }

    public double getRetestRate() {
        return retestRate;
    }

    public void setRetestRate(double retestRate) {
        this.retestRate = retestRate;
    }

    public long getTotalTestTime() {
        return totalTestTime;
    }

    public void setTotalTestTime(long totalTestTime) {
        this.totalTestTime = totalTestTime;
    }
}
