package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.pojo.mvcDieBean;
import com.vtest.it.rawdataParse.ParseRawdataNew;
import com.vtest.it.rawdatacheck.CheckIfInforToMes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;

@Controller
@RequestMapping("/GetWaferMap")
public class GetWaferMap {
    private ParseRawdataNew parseRawdataNew;
    private CheckIfInforToMes checkIfInforToMes;

    @Autowired
    public void setCheckIfInforToMes(CheckIfInforToMes checkIfInforToMes) {
        this.checkIfInforToMes = checkIfInforToMes;
    }

    @Autowired
    public void setParseRawdataNew(ParseRawdataNew parseRawdataNew) {
        this.parseRawdataNew = parseRawdataNew;
    }

    @RequestMapping("/Mapping")
    @ResponseBody
    public String getMap(@RequestParam("customer") String code, @RequestParam("device") String device, @RequestParam("lot") String lot, @RequestParam("cp") String cp, @RequestParam("wafer") String waferId) {
        if (checkIfInforToMes.check(code, device)) {
            ArrayList<mvcDieBean> result = parseRawdataNew.getMap(new File("/server212/RawData/TesterRawdatabackup/" + code + "/" + device + "/" + lot + "/" + cp + "/" + waferId + ".raw"));
            return JSON.toJSONString(result);
        } else {
            ArrayList<mvcDieBean> result = parseRawdataNew.getMap(new File("/server212/RawData/ProberRawdatabackup/" + code + "/" + device + "/" + lot + "/" + cp + "/" + waferId + ".raw"));
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping("/PrimaryOrRetestMap")
    @ResponseBody
    public String getPrimaryOrRetestMap(@RequestParam("customer") String code, @RequestParam("device") String device, @RequestParam("lot") String lot, @RequestParam("cp") String cp, @RequestParam("wafer") String waferId, @RequestParam("type") String type) {
        if (type.equals("P")) {
            ArrayList<mvcDieBean> result = parseRawdataNew.getPrimaryMap(new File("/server212/RawData/RawdataDetails/" + code + "/" + device + "/" + lot + "/" + cp + "/" + waferId + ".dtl"));
            return JSON.toJSONString(result);
        } else {
            ArrayList<mvcDieBean> result = parseRawdataNew.getRetestMap(new File("/server212/RawData/RawdataDetails/" + code + "/" + device + "/" + lot + "/" + cp + "/" + waferId + ".dtl"));
            return JSON.toJSONString(result);
        }
    }
}
