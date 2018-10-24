package com.vtest.it.rawdatainformationBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RawdataInitBean implements Serializable{
    private LinkedHashMap<String,String> properties;
    private LinkedHashMap<String,String> dataProperties;
    private HashMap<Integer,HashMap<Integer,Integer>> siteBinSum;
    private HashMap<Integer,HashMap<Integer,Integer>> primarySiteBinSum;
    private HashMap<Integer,HashMap<Integer,Integer>> retestSiteBinSum;
    private HashMap<String,String> primaryTestDieMap;
    private HashMap<String,String> reTestDieMap;
    private HashMap<String,String> testDieMap;
    private HashMap<String,String> skipAndMarkDieMap;
    private Integer primaryTouchDownTimes;
    private Long primaryTouchDownDuringTime;
    private Integer reTestTouchDownTimes;
    private Long reTestTouchDownDuringTime;
    private long singleTouchDownDuringTime;
    private double reTestRate;
    private double primaryRate;
    private long testDuringTime;

    public LinkedHashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(LinkedHashMap<String, String> properties) {
        this.properties = properties;
    }

    public LinkedHashMap<String, String> getDataProperties() {
        return dataProperties;
    }

    public void setDataProperties(LinkedHashMap<String, String> dataProperties) {
        this.dataProperties = dataProperties;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getSiteBinSum() {
        return siteBinSum;
    }

    public void setSiteBinSum(HashMap<Integer, HashMap<Integer, Integer>> siteBinSum) {
        this.siteBinSum = siteBinSum;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getPrimarySiteBinSum() {
        return primarySiteBinSum;
    }

    public void setPrimarySiteBinSum(HashMap<Integer, HashMap<Integer, Integer>> primarySiteBinSum) {
        this.primarySiteBinSum = primarySiteBinSum;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getRetestSiteBinSum() {
        return retestSiteBinSum;
    }

    public void setRetestSiteBinSum(HashMap<Integer, HashMap<Integer, Integer>> retestSiteBinSum) {
        this.retestSiteBinSum = retestSiteBinSum;
    }

    public HashMap<String, String> getTestDieMap() {
        return testDieMap;
    }

    public void setTestDieMap(HashMap<String, String> testDieMap) {
        this.testDieMap = testDieMap;
    }

    public HashMap<String, String> getPrimaryTestDieMap() {
        return primaryTestDieMap;
    }

    public void setPrimaryTestDieMap(HashMap<String, String> primaryTestDieMap) {
        this.primaryTestDieMap = primaryTestDieMap;
    }

    public HashMap<String, String> getReTestDieMap() {
        return reTestDieMap;
    }

    public void setReTestDieMap(HashMap<String, String> reTestDieMap) {
        this.reTestDieMap = reTestDieMap;
    }

    public HashMap<String, String> getSkipAndMarkDieMap() {
        return skipAndMarkDieMap;
    }

    public void setSkipAndMarkDieMap(HashMap<String, String> skipAndMarkDieMap) {
        this.skipAndMarkDieMap = skipAndMarkDieMap;
    }
    public Integer getPrimaryTouchDownTimes() {
        return primaryTouchDownTimes;
    }

    public void setPrimaryTouchDownTimes(Integer primaryTouchDownTimes) {
        this.primaryTouchDownTimes = primaryTouchDownTimes;
    }

    public Long getPrimaryTouchDownDuringTime() {
        return primaryTouchDownDuringTime;
    }

    public void setPrimaryTouchDownDuringTime(Long primaryTouchDownDuringTime) {
        this.primaryTouchDownDuringTime = primaryTouchDownDuringTime;
    }

    public Integer getReTestTouchDownTimes() {
        return reTestTouchDownTimes;
    }

    public void setReTestTouchDownTimes(Integer reTestTouchDownTimes) {
        this.reTestTouchDownTimes = reTestTouchDownTimes;
    }

    public Long getReTestTouchDownDuringTime() {
        return reTestTouchDownDuringTime;
    }

    public void setReTestTouchDownDuringTime(Long reTestTouchDownDuringTime) {
        this.reTestTouchDownDuringTime = reTestTouchDownDuringTime;
    }

    public long getSingleTouchDownDuringTime() {
        return singleTouchDownDuringTime;
    }

    public void setSingleTouchDownDuringTime(long singleTouchDownDuringTime) {
        this.singleTouchDownDuringTime = singleTouchDownDuringTime;
    }

    public double getReTestRate() {
        return reTestRate;
    }

    public void setReTestRate(double reTestRate) {
        this.reTestRate = reTestRate;
    }

    public double getPrimaryRate() {
        return primaryRate;
    }

    public void setPrimaryRate(double primaryRate) {
        this.primaryRate = primaryRate;
    }

    public long getTestDuringTime() {
        return testDuringTime;
    }

    public void setTestDuringTime(long testDuringTime) {
        this.testDuringTime = testDuringTime;
    }
}
