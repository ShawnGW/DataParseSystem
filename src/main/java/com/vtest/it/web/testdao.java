package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.mesdao.GetMesConfig;
import com.vtest.it.dao.mesdao.GetSlotAndSequenceDAO;
import com.vtest.it.dao.systemdao.*;
import com.vtest.it.dao.testermapperdao.TesterDataDAO;
import com.vtest.it.pojo.PropertiesOrderBean;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.pojo.binwaferinfors.FailDieBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
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


    /**
     *
     *
     */
    private TesterDataDAO proberDataDAO;

    @Autowired
    public void setProberDataDAO(TesterDataDAO proberDataDAO) {
        this.proberDataDAO = proberDataDAO;
    }

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

//
        BinWaferInforBean binWaferInforBean=new BinWaferInforBean();
        binWaferInforBean.setCustomerCode("AMC");
        binWaferInforBean.setDevice("GXLX_2");
        binWaferInforBean.setLotId("FA89-9932");
        binWaferInforBean.setCpStep("CP1");
        binWaferInforBean.setWaferNo("FA899932-01");
        binWaferInforBean.setOprator("V033");
        binWaferInforBean.setTesterProgram("TTDSBDSNLSD_V0.1");
        binWaferInforBean.setTesterId("TTV-02");
        binWaferInforBean.setProberId("PTS-01");
        binWaferInforBean.setProberCardId("PIUDS-EE-02");
        binWaferInforBean.setPidName("AMCSDSSD-1.1.1");
        binWaferInforBean.setPidVersion("V2.2");
        binWaferInforBean.setStartTime(new Date());
        binWaferInforBean.setGrossDie(10000);
        binWaferInforBean.setPassDie(9000);
        binWaferInforBean.setFailDie(1000);
        binWaferInforBean.setYield(90.00);
        binWaferInforBean.setEndTime(new Date());
        binWaferInforBean.setCheckStatus(0);
        binWaferInforBean.setMapCols(100);
        binWaferInforBean.setMapRows(80);
        binWaferInforBean.setMinX(-1);
        binWaferInforBean.setMinY(0);
        binWaferInforBean.setOthersParams("udhsiaiuda:dsada:da90dsa0da:dsadas");
        proberDataDAO.insertWaferInforToBinWaferSummary(binWaferInforBean);

        HashMap<Integer,HashMap<Integer,Integer>> siteMap=new HashMap<>();
        HashMap<Integer,Integer> binMap=new HashMap<>();
        binMap.put(1,10);
        binMap.put(2,11);
        binMap.put(3,12);
        binMap.put(4,13);
        HashMap<Integer,Integer> binMap1=new HashMap<>();
        binMap1.put(1,10);
        binMap1.put(2,11);
        binMap1.put(3,12);
        binMap1.put(4,13);
        HashMap<Integer,Integer> binMap2=new HashMap<>();
        binMap2.put(1,10);
        binMap2.put(2,11);
        binMap2.put(3,12);
        binMap2.put(4,13);
        siteMap.put(0,binMap);
        siteMap.put(1,binMap1);
        siteMap.put(2,binMap2);
        ArrayList<Integer> arrayList=new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        proberDataDAO.deleteSiteInforToBinInfoSummary("AMC","GXLX_2","FA89-9932","CP1","FA899932-01");
        proberDataDAO.insertSiteInforToBinInfoSummary("AMC","GXLX_2","FA89-9932","CP1","FA899932-01",siteMap,"P",arrayList);

        ArrayList<FailDieBean> arrayList1=new ArrayList<>();
        FailDieBean failDieBean=new FailDieBean();
        failDieBean.setxCoordinate(1);
        failDieBean.setyCoordinate(2);
        failDieBean.setBinNumber(2);
        failDieBean.setSiteId(0);

        FailDieBean failDieBean1=new FailDieBean();
        failDieBean1.setxCoordinate(2);
        failDieBean1.setyCoordinate(3);
        failDieBean1.setBinNumber(2);
        failDieBean1.setSiteId(0);

        FailDieBean failDieBean2=new FailDieBean();
        failDieBean2.setxCoordinate(3);
        failDieBean2.setyCoordinate(4);
        failDieBean2.setBinNumber(2);
        failDieBean2.setSiteId(0);

        arrayList1.add(failDieBean);
        arrayList1.add(failDieBean1);
        arrayList1.add(failDieBean2);

        proberDataDAO.insertFailDieToBinInfo("AMC","GXLX_2","FA89-9932","CP1","FA899932-01",arrayList1);

        ArrayList<String> items=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("test_json_xixi"+i);
        }
     return  JSON.toJSONString(items);
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
