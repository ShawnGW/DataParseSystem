package com.vtest.it.rawdatafterdeal;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;

import java.util.ArrayList;
import java.util.HashMap;

public class DealWithBin0 extends AbstractRawDataAfterDeal {
    @Override
    public void deal(RawdataInitBean rawdataInitBean) {
        ArrayList<Integer> osBinArray = new ArrayList<>();
        try {
            String[] osBins = rawdataInitBean.getProperties().get("OS Bins").trim().split(",");
            for (String osBin : osBins) {
                osBinArray.add(Integer.valueOf(osBin));
            }
        } catch (Exception e) {
            osBinArray.add(5);
        }
        Boolean flag = false;
        HashMap<Integer, HashMap<Integer, Integer>> binSummary = rawdataInitBean.getSiteBinSum();
        for (Integer site : binSummary.keySet()) {
            if (binSummary.get(site).keySet().contains(0)) {
                flag = true;
            }
        }
        if (!flag) {
            return;
        }
        String osbinFirst=osBinArray.get(0).toString();
        HashMap<String,String> testDieMap=rawdataInitBean.getTestDieMap();
        for (String key:testDieMap.keySet()) {
            if (testDieMap.get(key).equals("   0   0   0"))
            {
                testDieMap.put(key,String.format("%4s", osbinFirst)+String.format("%4s", osbinFirst)+String.format("%4s", 0));
            }
        }
    }
}
