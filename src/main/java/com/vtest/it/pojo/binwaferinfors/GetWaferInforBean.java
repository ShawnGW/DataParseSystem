package com.vtest.it.pojo.binwaferinfors;

import java.io.Serializable;
import java.util.Date;

public class GetWaferInforBean implements Serializable {
    private String customCode;
    private String device;
    private String lotId;
    private String cpStep;
    private String waferNo;
    private String oprator;
    private String testerProgram;
    private String testerId;
    private String proberId;
    private String proberCardId;
    private String pidName;
    private String pidVersion;
    private Date startTime;
    private int grossDie;
    private int passDie;
    private int failDie;
    private double yield;
    private Date endTime;
    private int mapCols;
    private int mapRows;
    private int minX;
    private int minY;

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

    public String getOprator() {
        return oprator;
    }

    public void setOprator(String oprator) {
        this.oprator = oprator;
    }

    public String getTesterProgram() {
        return testerProgram;
    }

    public void setTesterProgram(String testerProgram) {
        this.testerProgram = testerProgram;
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

    public String getProberCardId() {
        return proberCardId;
    }

    public void setProberCardId(String proberCardId) {
        this.proberCardId = proberCardId;
    }

    public String getPidName() {
        return pidName;
    }

    public void setPidName(String pidName) {
        this.pidName = pidName;
    }

    public String getPidVersion() {
        return pidVersion;
    }

    public void setPidVersion(String pidVersion) {
        this.pidVersion = pidVersion;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public int getGrossDie() {
        return grossDie;
    }

    public void setGrossDie(int grossDie) {
        this.grossDie = grossDie;
    }

    public int getPassDie() {
        return passDie;
    }

    public void setPassDie(int passDie) {
        this.passDie = passDie;
    }

    public int getFailDie() {
        return failDie;
    }

    public void setFailDie(int failDie) {
        this.failDie = failDie;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getMapCols() {
        return mapCols;
    }

    public void setMapCols(int mapCols) {
        this.mapCols = mapCols;
    }

    public int getMapRows() {
        return mapRows;
    }

    public void setMapRows(int mapRows) {
        this.mapRows = mapRows;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }
}
