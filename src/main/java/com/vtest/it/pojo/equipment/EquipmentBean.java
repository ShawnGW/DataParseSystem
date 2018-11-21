package com.vtest.it.pojo.equipment;

import java.io.Serializable;

public class EquipmentBean implements Serializable {
    private String customerCode;
    private String device;
    private String lotId;
    private String cpStep;
    private String waferNo;
    private String testerId;
    private String proberId;
    private String cardId;
    private int firstTouchDown;
    private long firstTimeRange;
    private double firstTestRate;
    private int retestTouchDown;
    private long retestTimeRange;
    private double retestRate;
    private long singleTestTime;
    private long testTime;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getCpStep() {
        return cpStep;
    }

    public void setCpStep(String cpStep) {
        this.cpStep = cpStep;
    }

    public String getWaferNo() {
        return waferNo;
    }

    public void setWaferNo(String waferNo) {
        this.waferNo = waferNo;
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId;
    }

    public String getProberId() {
        return proberId;
    }

    public void setProberId(String proberId) {
        this.proberId = proberId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public int getFirstTouchDown() {
        return firstTouchDown;
    }

    public void setFirstTouchDown(int firstTouchDown) {
        this.firstTouchDown = firstTouchDown;
    }

    public long getFirstTimeRange() {
        return firstTimeRange;
    }

    public void setFirstTimeRange(long firstTimeRange) {
        this.firstTimeRange = firstTimeRange;
    }

    public double getFirstTestRate() {
        return firstTestRate;
    }

    public void setFirstTestRate(double firstTestRate) {
        this.firstTestRate = firstTestRate;
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

    public double getRetestRate() {
        return retestRate;
    }

    public void setRetestRate(double retestRate) {
        this.retestRate = retestRate;
    }

    public long getSingleTestTime() {
        return singleTestTime;
    }

    public void setSingleTestTime(long singleTestTime) {
        this.singleTestTime = singleTestTime;
    }

    public long getTestTime() {
        return testTime;
    }

    public void setTestTime(long testTime) {
        this.testTime = testTime;
    }
}
