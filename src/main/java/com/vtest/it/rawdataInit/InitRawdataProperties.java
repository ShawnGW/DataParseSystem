package com.vtest.it.rawdataInit;

import com.vtest.it.dao.PropertiesDAO;
import com.vtest.it.pojo.PropertiesOrderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;

@Service
public class InitRawdataProperties {
    private PropertiesDAO propertiesDAO;

    @Autowired
    public void setPropertiesDAO(PropertiesDAO propertiesDAO) {
        this.propertiesDAO = propertiesDAO;
    }

    public LinkedHashMap<String,String> getProperties()
    {
        LinkedHashMap<String,String> properties=new LinkedHashMap<>();
        LinkedList<PropertiesOrderBean> propertiesOrderBeans=propertiesDAO.getOrder();
        for (PropertiesOrderBean propertiesOrderBean: propertiesOrderBeans) {
            properties.put(propertiesOrderBean.getProperty(),"NA");
        }
        return  properties;
    }
}
