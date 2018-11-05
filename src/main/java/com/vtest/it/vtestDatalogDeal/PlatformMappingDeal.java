package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.pojo.propertiescheckItemBean.DataParseIssueBean;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlatformMappingDeal {
    public static final String  regex="CP[1-9]{1}";
    protected HashMap<String,String> getResultMap(String platform)
    {
        HashMap<String,String> resultMap=new HashMap<>();
        resultMap.put("customCode","NA");
        resultMap.put("device","NA");
        resultMap.put("passBins","NA");
        resultMap.put("lot", "NA");
        resultMap.put("waferId", "NA");
        resultMap.put("cpStep", "NA");
        resultMap.put("resource",platform);
        return  resultMap;
    }
    protected boolean checkCpProcess(String cpProcess)
    {
        Pattern pattern=Pattern.compile(regex);
        Matcher matcher=pattern.matcher(cpProcess);
        if (matcher.find())
        {
            return  true;
        }
        return  false;
    }
    protected DataParseIssueBean getDatabean(HashMap<String,String> waferInfor, String issueType, int level, String issuePath, String descripth)
    {
        DataParseIssueBean dataParseIssueBean=new DataParseIssueBean();
        dataParseIssueBean.setCustomCode(waferInfor.get("customCode"));
        dataParseIssueBean.setDevice(waferInfor.get("device"));
        dataParseIssueBean.setLotId(waferInfor.get("lot"));
        dataParseIssueBean.setCpStep(waferInfor.get("cpStep"));
        dataParseIssueBean.setWaferNo(waferInfor.get("waferId"));
        dataParseIssueBean.setResource(waferInfor.get("resource"));
        dataParseIssueBean.setIssueType(issueType);
        dataParseIssueBean.setIssuLevel(level);
        dataParseIssueBean.setIssuePath(issuePath);
        dataParseIssueBean.setIssueDescription(descripth);
        dataParseIssueBean.setDealFlag(0);
        return  dataParseIssueBean;
    }
}
