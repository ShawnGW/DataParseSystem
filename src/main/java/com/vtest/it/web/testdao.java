package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.mesdao.GetMesConfig;
import com.vtest.it.dao.mesdao.GetSlotAndSequenceDAO;
import com.vtest.it.dao.systemdao.*;
import com.vtest.it.pojo.PropertiesOrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@Controller
@RequestMapping("/DAO")
public class testdao {
    private MesConfigDAO mesConfigDAO;
    private SiteBinSumToDAO siteBinSumToDAO;
    private VtComputerDAO vtComputerDAO;
    private PropertiesDAO propertiesDAO;
    private GetMesConfig getMesConfig;
    private GetDataSourceConfigDao getDataSourceConfigDao;
    private GetSlotAndSequenceDAO getSlotAndSequenceDAO;

    @Autowired
    public void setGetSlotAndSequenceDAO(GetSlotAndSequenceDAO getSlotAndSequenceDAO) {
        this.getSlotAndSequenceDAO = getSlotAndSequenceDAO;
    }

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @Autowired
    public void setGetMesConfig(GetMesConfig getMesConfig) {
        this.getMesConfig = getMesConfig;
    }

    @Autowired
    public void setPropertiesDAO(PropertiesDAO propertiesDAO) {
        this.propertiesDAO = propertiesDAO;
    }

    @Autowired
    public void setVtComputerDAO(VtComputerDAO vtComputerDAO) {
        this.vtComputerDAO = vtComputerDAO;
    }

    @Autowired
    public void setSiteBinSumToDAO(SiteBinSumToDAO siteBinSumToDAO) {
        this.siteBinSumToDAO = siteBinSumToDAO;
    }

    @Autowired
    public void setMesConfigDAO(MesConfigDAO mesConfigDAO) {
        this.mesConfigDAO = mesConfigDAO;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("waferId") String waferId)
    {
//
//        LinkedHashMap<String,String> properties=new LinkedHashMap<>();
//        LinkedList<PropertiesOrderBean> propertiesOrderBeans=propertiesDAO.getOrder();
//        for (PropertiesOrderBean propertiesOrderBean: propertiesOrderBeans) {
//            properties.put(propertiesOrderBean.getProperty(),"NA");
//        }
//        TestMesBean testMesBean=getMesConfig.getConfig();
//        System.out.println(testMesBean.getWhShelfLoc());
//        System.out.println(testMesBean.getWhName());
//        System.out.println(testMesBean.getPartNumber());
//     return JSON.toJSONString(getMesConfig.getBean(waferId,"CP1"));
     return  JSON.toJSONString(getMesConfig.getBean(waferId,"CP1"));
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String test2()
    {
        LinkedHashMap<String,String> properties=new LinkedHashMap<>();
        LinkedList<PropertiesOrderBean> propertiesOrderBeans=propertiesDAO.getOrder();
        for (PropertiesOrderBean propertiesOrderBean: propertiesOrderBeans) {
            properties.put(propertiesOrderBean.getProperty(),"NA");
        }
        return JSON.toJSONString(properties);
    }
}
