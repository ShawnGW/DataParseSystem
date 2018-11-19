package com.vtest.it.pojo.binwaferinfors;

public class waferYieldBean {
    private String customCode;
    private String device;
    private String lotId;
    private String cpStep;
    private String waferNo;
    private Integer siteId;
    private Integer softBinNo;
    private Integer binCount;
    private Boolean passFail;

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

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getSoftBinNo() {
        return softBinNo;
    }

    public void setSoftBinNo(Integer softBinNo) {
        this.softBinNo = softBinNo;
    }

    public Integer getBinCount() {
        return binCount;
    }

    public void setBinCount(Integer binCount) {
        this.binCount = binCount;
    }

    public Boolean getPassFail() {
        return passFail;
    }

    public void setPassFail(Boolean passFail) {
        this.passFail = passFail;
    }
}
