package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/GetTesterStatus")
public class GetTesterStatus {
    private GetDataSourceConfigDao getDataSourceConfigDao;

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @ResponseBody
    @RequestMapping(value = "/status",produces ="text/html;charset=UTF-8")
    public String getStatus()
    {
        ArrayList<BinWaferInforBean> status=getDataSourceConfigDao.getTesterStatus();
        return JSON.toJSONString(status);
    }

    @RequestMapping("/getTesterStatusList")
    @ResponseBody
    public String getTesterStatusList(@RequestParam("tester") String tester) {
        return JSON.toJSONString(getDataSourceConfigDao.getTesterStatusList(tester));
    }
    @RequestMapping("/getTesterStatusSingle")
    @ResponseBody
    public String getTesterStatusSingle(@RequestParam("tester") String tester) {
        return JSON.toJSONString(getDataSourceConfigDao.getTesterStatusSingle(tester));
    }
}
