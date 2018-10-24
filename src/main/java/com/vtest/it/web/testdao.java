package com.vtest.it.web;

import com.alibaba.fastjson.JSON;
import com.vtest.it.dao.testermapperdao.TesterDataDAO;
import com.vtest.it.dao.vtmesdao.VtMesConfigDAO;
import com.vtest.it.dao.vtmesdao.VtMesSlotAndSequenceDAO;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.mvcDieBean;
import com.vtest.it.pojo.propertiescheckItemBean.DataParseIssueBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/DAO")
public class testdao {
    private GetDataSourceConfigDao getDataSourceConfigDao;
    private TesterDataDAO proberDataDAO;
    private VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO;
    private VtMesConfigDAO vtMesConfigDAO;

    @Autowired
    public void setVtMesSlotAndSequenceDAO(VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO) {
        this.vtMesSlotAndSequenceDAO = vtMesSlotAndSequenceDAO;
    }
    @Autowired
    public void setVtMesConfigDAO(VtMesConfigDAO vtMesConfigDAO) {
        this.vtMesConfigDAO = vtMesConfigDAO;
    }

    @Autowired
    public void setProberDataDAO(TesterDataDAO proberDataDAO) {
        this.proberDataDAO = proberDataDAO;
    }

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
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
//        BinWaferInforBean binWaferInforBean=new BinWaferInforBean();
//        binWaferInforBean.setCustomerCode("AMC");
//        binWaferInforBean.setDevice("GXLX_2");
//        binWaferInforBean.setLotId("FA89-9932");
//        binWaferInforBean.setCpStep("CP1");
//        binWaferInforBean.setWaferNo("FA899932-01");
//        binWaferInforBean.setOprator("V033");
//        binWaferInforBean.setTesterProgram("TTDSBDSNLSD_V0.1");
//        binWaferInforBean.setTesterId("TTV-02");
//        binWaferInforBean.setProberId("PTS-01");
//        binWaferInforBean.setProberCardId("PIUDS-EE-02");
//        binWaferInforBean.setPidName("AMCSDSSD-1.1.1");
//        binWaferInforBean.setPidVersion("V2.2");
//        binWaferInforBean.setStartTime(new Date());
//        binWaferInforBean.setGrossDie(10000);
//        binWaferInforBean.setPassDie(9000);
//        binWaferInforBean.setFailDie(1000);
//        binWaferInforBean.setYield(90.00);
//        binWaferInforBean.setEndTime(new Date());
//        binWaferInforBean.setCheckStatus(0);
//        binWaferInforBean.setMapCols(100);
//        binWaferInforBean.setMapRows(80);
//        binWaferInforBean.setMinX(-1);
//        binWaferInforBean.setMinY(0);
//        binWaferInforBean.setOthersParams("udhsiaiuda:dsada:da90dsa0da:dsadas");
//        proberDataDAO.insertWaferInforToBinWaferSummary(binWaferInforBean);
//
//        HashMap<Integer,HashMap<Integer,Integer>> siteMap=new HashMap<>();
//        HashMap<Integer,Integer> binMap=new HashMap<>();
//        binMap.put(1,10);
//        binMap.put(2,11);
//        binMap.put(3,12);
//        binMap.put(4,13);
//        HashMap<Integer,Integer> binMap1=new HashMap<>();
//        binMap1.put(1,10);
//        binMap1.put(2,11);
//        binMap1.put(3,12);
//        binMap1.put(4,13);
//        HashMap<Integer,Integer> binMap2=new HashMap<>();
//        binMap2.put(1,10);
//        binMap2.put(2,11);
//        binMap2.put(3,12);
//        binMap2.put(4,13);
//        siteMap.put(0,binMap);
//        siteMap.put(1,binMap1);
//        siteMap.put(2,binMap2);
//        ArrayList<Integer> arrayList=new ArrayList<>();
//        arrayList.add(1);
//        arrayList.add(2);
//        proberDataDAO.deleteSiteInforToBinInfoSummary("AMC","GXLX_2","FA89-9932","CP1","FA899932-01");
//        proberDataDAO.insertSiteInforToBinInfoSummary("AMC","GXLX_2","FA89-9932","CP1","FA899932-01",siteMap,"P",arrayList);
//
//        ArrayList<FailDieBean> arrayList1=new ArrayList<>();
//        FailDieBean failDieBean=new FailDieBean();
//        failDieBean.setxCoordinate(1);
//        failDieBean.setyCoordinate(2);
//        failDieBean.setBinNumber(2);
//        failDieBean.setSiteId(0);
//
//        FailDieBean failDieBean1=new FailDieBean();
//        failDieBean1.setxCoordinate(2);
//        failDieBean1.setyCoordinate(3);
//        failDieBean1.setBinNumber(2);
//        failDieBean1.setSiteId(0);
//
//        FailDieBean failDieBean2=new FailDieBean();
//        failDieBean2.setxCoordinate(3);
//        failDieBean2.setyCoordinate(4);
//        failDieBean2.setBinNumber(2);
//        failDieBean2.setSiteId(0);
//
//        arrayList1.add(failDieBean);
//        arrayList1.add(failDieBean1);
//        arrayList1.add(failDieBean2);
//
//        proberDataDAO.insertFailDieToBinInfo("AMC","GXLX_2","FA89-9932","CP1","FA899932-01",arrayList1);
        ArrayList<mvcDieBean> items=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                mvcDieBean die=new mvcDieBean();
                die.setX(i);
                die.setY(j);
                die.setZ(0);
                die.setHardbin(i/10);
                die.setSoftbin(i/10);
                die.setSite(i/8);
                die.setName("Bin"+i/10);
                items.add(die);
            }
        }
     return  JSON.toJSONString(items);
    }
    @RequestMapping("/test2")
    @ResponseBody
    public String test2()
    {
        DataParseIssueBean systemIssueBean=new DataParseIssueBean();
        systemIssueBean.setCustomCode("AMC");
        systemIssueBean.setDevice("GXLX2");
        systemIssueBean.setLotId("FA78-9876");
        systemIssueBean.setCpStep("CP1");
        systemIssueBean.setWaferNo("FA789876-10");
        systemIssueBean.setIssueType("data check");
        systemIssueBean.setIssuLevel(5);
        systemIssueBean.setIssuePath("/errors/xxx");
        systemIssueBean.setIssueDescription("test");
        systemIssueBean.setDealFlag(0);

        DataParseIssueBean systemIssueBean2=new DataParseIssueBean();
        systemIssueBean2.setCustomCode("AMC");
        systemIssueBean2.setDevice("GXLX2");
        systemIssueBean2.setLotId("FA78-9876");
        systemIssueBean2.setCpStep("CP1");
        systemIssueBean2.setWaferNo("FA789876-09");
        systemIssueBean2.setIssueType("data check");
        systemIssueBean2.setIssuLevel(4);
        systemIssueBean2.setIssuePath("/errors/xxx");
        systemIssueBean2.setIssueDescription("test");
        systemIssueBean2.setDealFlag(0);

        ArrayList<DataParseIssueBean> arrayList=new ArrayList<>();
        arrayList.add(systemIssueBean);
        arrayList.add(systemIssueBean2);
        getDataSourceConfigDao.dataErrorsRecord(arrayList);
        return JSON.toJSONString(getDataSourceConfigDao.getCheckItemList());
    }
}
