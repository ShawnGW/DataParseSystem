package com.vtest.it.rawdataBean;

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

    public RawdataInitBean()
    {
        this.properties=new LinkedHashMap<>();
        this.dataProperties=new LinkedHashMap<>();
        this.siteBinSum=new HashMap<>();
        this.primarySiteBinSum=new HashMap<>();
        this.retestSiteBinSum=new HashMap<>();
        this.primaryTestDieMap=new HashMap<>();
        this.reTestDieMap=new HashMap<>();
        this.testDieMap=new HashMap<>();
        this.skipAndMarkDieMap=new HashMap<>();
    }

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
}
