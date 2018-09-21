package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.MesConfigDAO;
import com.vtest.it.dao.PropertiesDAO;
import com.vtest.it.dao.SiteBinSumToDAO;
import com.vtest.it.dao.VtComputerDAO;
import com.vtest.it.pojo.PropertiesOrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String test()
    {
        LinkedHashMap<String,String> properties=new LinkedHashMap<>();
        LinkedList<PropertiesOrderBean> propertiesOrderBeans=propertiesDAO.getOrder();
        for (PropertiesOrderBean propertiesOrderBean: propertiesOrderBeans) {
            properties.put(propertiesOrderBean.getProperty(),"NA");
        }
     return JSON.toJSONString(properties);
    }
}
