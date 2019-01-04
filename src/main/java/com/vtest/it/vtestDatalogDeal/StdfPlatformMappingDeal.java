package com.vtest.it.vtestDatalogDeal;

import com.alibaba.fastjson.JSON;
import com.vtest.it.MappingParseTools.StdfTesterMappingParse;
import com.vtest.it.RawdataGenerate.GeneratePrimaryAndReTestMap;
import com.vtest.it.RawdataGenerate.GenerateRawdata;
import com.vtest.it.RawdataGenerate.InitMesConfigToRawdataProperties;
import com.vtest.it.dao.testermapperdao.TesterDataDAO;
import com.vtest.it.dao.vtmesdao.VtMesConfigDAO;
import com.vtest.it.dao.vtmesdao.VtSiteYieldToMes;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.excelModel.SiteInforReport;
import com.vtest.it.mesinfors.StdfTouchDownWrite;
import com.vtest.it.mesinfors.WaferIdBinSummaryWrite;
import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.pojo.datainfortomes.SiteYieldToMes;
import com.vtest.it.pojo.equipment.EquipmentBean;
import com.vtest.it.pojo.propertiescheckItemBean.DataParseIssueBean;
import com.vtest.it.rawdatacheck.CheckIfInforToMes;
import com.vtest.it.rawdatacheck.RawDataCheck;
import com.vtest.it.rawdatafterdeal.GenerateEquipmentInforBean;
import com.vtest.it.rawdatafterdeal.GenerateWaferInforBean;
import com.vtest.it.rawdatafterdeal.RawDataDeal;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import com.vtest.it.tools.GetOrder;
import com.vtest.it.tools.TimeCheck;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

@Service
public class StdfPlatformMappingDeal extends PlatformMappingDeal {
    private GetDataSourceConfigDao dataSourceConfig;
    private TimeCheck timeCheck;
    private InitMesConfigToRawdataProperties initMesConfigToRawdataProperties;
    private RawDataDeal rawDataDeal;
    private VtMesConfigDAO vtMesConfigDAO;
    private TesterDataDAO testerDataDAO;
    private GenerateEquipmentInforBean generateEquipmentInforBean;
    private GenerateWaferInforBean generateWaferInforBean;
    private GenerateRawdata generateRawdata;
    private CheckIfInforToMes checkIfInforToMes;
    private RawDataCheck rawDataCheck;
    private WaferIdBinSummaryWrite waferIdBinSummaryWrite;
    private StdfTouchDownWrite stdfTouchDownWrite;
    private VtSiteYieldToMes vtSiteYieldToMes;
    private GetOrder getOrder;
    private StdfTesterMappingParse stdfTesterMappingParse;
    private DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal;
    private GeneratePrimaryAndReTestMap generatePrimaryAndReTestMap;
    private SiteInforReport siteInforReport;

    @Autowired
    public void setSiteInforReport(SiteInforReport siteInforReport) {
        this.siteInforReport = siteInforReport;
    }

    @Autowired
    public void setGeneratePrimaryAndReTestMap(GeneratePrimaryAndReTestMap generatePrimaryAndReTestMap) {
        this.generatePrimaryAndReTestMap = generatePrimaryAndReTestMap;
    }

    @Autowired
    public void setDatalogBackupAndRawDataDeal(DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal) {
        this.datalogBackupAndRawDataDeal = datalogBackupAndRawDataDeal;
    }

    @Autowired
    public void setStdfTesterMappingParse(StdfTesterMappingParse stdfTesterMappingParse) {
        this.stdfTesterMappingParse = stdfTesterMappingParse;
    }

    @Autowired
    public void setGetOrder(GetOrder getOrder) {
        this.getOrder = getOrder;
    }

    @Autowired
    public void setVtSiteYieldToMes(VtSiteYieldToMes vtSiteYieldToMes) {
        this.vtSiteYieldToMes = vtSiteYieldToMes;
    }

    @Autowired
    public void setStdfTouchDownWrite(StdfTouchDownWrite stdfTouchDownWrite) {
        this.stdfTouchDownWrite = stdfTouchDownWrite;
    }

    @Autowired
    public void setWaferIdBinSummaryWrite(WaferIdBinSummaryWrite waferIdBinSummaryWrite) {
        this.waferIdBinSummaryWrite = waferIdBinSummaryWrite;
    }

    @Autowired
    public void setRawDataCheck(RawDataCheck rawDataCheck) {
        this.rawDataCheck = rawDataCheck;
    }

    @Autowired
    public void setCheckIfInforToMes(CheckIfInforToMes checkIfInforToMes) {
        this.checkIfInforToMes = checkIfInforToMes;
    }

    @Autowired
    public void setGenerateRawdata(GenerateRawdata generateRawdata) {
        this.generateRawdata = generateRawdata;
    }

    @Autowired
    public void setGenerateWaferInforBean(GenerateWaferInforBean generateWaferInforBean) {
        this.generateWaferInforBean = generateWaferInforBean;
    }

    @Autowired
    public void setGenerateEquipmentInforBean(GenerateEquipmentInforBean generateEquipmentInforBean) {
        this.generateEquipmentInforBean = generateEquipmentInforBean;
    }

    @Autowired
    public void setTesterDataDAO(TesterDataDAO testerDataDAO) {
        this.testerDataDAO = testerDataDAO;
    }

    @Autowired
    public void setVtMesConfigDAO(VtMesConfigDAO vtMesConfigDAO) {
        this.vtMesConfigDAO = vtMesConfigDAO;
    }

    @Autowired
    public void setRawDataDeal(RawDataDeal rawDataDeal) {
        this.rawDataDeal = rawDataDeal;
    }

    @Autowired
    public void setTimeCheck(TimeCheck timeCheck) {
        this.timeCheck = timeCheck;
    }

    @Autowired
    public void setDataSourceConfig(GetDataSourceConfigDao dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    @Autowired
    public void setInitMesConfigToRawdataProperties(InitMesConfigToRawdataProperties initMesConfigToRawdataProperties) {
        this.initMesConfigToRawdataProperties = initMesConfigToRawdataProperties;
    }

    public void deal() {
        DataSourceBean dataSourceConfigBean = dataSourceConfig.getConfig("STDF");
        File dataSource = new File(dataSourceConfigBean.getSourcePath());
        File[] files = dataSource.listFiles();
        if (files.length > 0) {
            for (File customerCodeFile : files) {
                if (checkDirectory(customerCodeFile)) {
                    File[] deviceFiles = customerCodeFile.listFiles();
                    for (File deviceFile : deviceFiles) {
                        if (checkDirectory(deviceFile)) {
                            File[] lotFiles = deviceFile.listFiles();
                            for (File lotFile : lotFiles) {
                                if (checkDirectory(lotFile)) {
                                    String lotNum = lotFile.getName();
                                    File[] cpFiles = lotFile.listFiles();
                                    for (File cpFile : cpFiles) {
                                        if (checkDirectory(cpFile)) {
                                            String cpProcess = cpFile.getName();

                                            File[] waferIdFiles = cpFile.listFiles();
                                            for (File waferIdFile : waferIdFiles) {
                                                if (checkDirectory(waferIdFile)) {
                                                    try {
                                                        String waferId = waferIdFile.getName();
                                                        File[] waferIdTexts = waferIdFile.listFiles();
                                                        if (!timeCheck.check(waferIdFile, 60)) {
                                                            continue;
                                                        }
                                                        ArrayList<File> waferIdOrderList = getOrder.Order(waferIdTexts);
                                                        HashMap<String, String> resultMap = getResultMap("STDF");
                                                        ArrayList<DataParseIssueBean> issueBeans = new ArrayList<>();
                                                        resultMap.put("lot", lotNum);
                                                        resultMap.put("waferId", waferId);
                                                        resultMap.put("cpStep", cpProcess);
                                                        if (!checkCpProcess(cpProcess)) {
                                                            dealException(dataSourceConfigBean, waferIdTexts, resultMap, issueBeans, "there are error in cpProcess", "cp_process", 5);
                                                            continue;
                                                        }
                                                        RawdataInitBean rawdataInitBean = new RawdataInitBean();
                                                        try {
                                                            stdfTesterMappingParse.get(waferIdOrderList, rawdataInitBean);
                                                        } catch (Exception e) {
                                                            dealException(dataSourceConfigBean, waferIdTexts, resultMap, issueBeans, "there are error in file coding", "code_error", 5);
                                                            continue;
                                                        }
                                                        MesConfigBean mesConfigBean = vtMesConfigDAO.getBean(waferId, cpProcess);
                                                        if (null == mesConfigBean) {
                                                            dealException(dataSourceConfigBean, waferIdTexts, resultMap, issueBeans, "can't find this wafer in mes system", "call_proc", 5);
                                                            continue;
                                                        }
                                                        initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean, mesConfigBean);
                                                        try {
                                                            rawDataDeal.Deal(rawdataInitBean);
                                                        } catch (Exception e) {
                                                            dealException(dataSourceConfigBean, waferIdTexts, resultMap, issueBeans, "Rawdata deal Chains Fail", "deal_bean", 5);
                                                            continue;
                                                        }
                                                        try {
                                                            generatePrimaryAndReTestMap.generate(rawdataInitBean);
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        File rawFile = generateRawdata.generate(rawdataInitBean);
                                                        rawDataCheck.check(rawFile, issueBeans);
                                                        for (DataParseIssueBean issueBean : issueBeans) {
                                                            if (issueBean.getIssuLevel() == 99) {
                                                                continue;
                                                            }
                                                        }
                                                        if (issueBeans.size() > 0) {
                                                            dataSourceConfig.dataErrorsRecord(issueBeans);
                                                        }

                                                        LinkedHashMap<String, String> properties = rawdataInitBean.getProperties();
                                                        resultMap.put("customCode", properties.get("Customer Code"));
                                                        resultMap.put("device", properties.get("Device Name"));
                                                        resultMap.put("passBins", properties.get("Pass Bins"));
                                                        resultMap.put("osBins", properties.get("OS Bins"));
                                                        try {
                                                            dataToVtDB(rawdataInitBean, resultMap);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                            continue;
                                                        }
                                                        try {
                                                            datalogBackupAndRawDataDeal.rawdataDeal(dataSourceConfigBean.getRawdataPath(), rawFile, resultMap);
                                                        } catch (Exception e) {
                                                            continue;
                                                        }
                                                        try {
                                                            for (int i = 0; i < waferIdTexts.length; i++) {
                                                                FileUtils.forceDelete(waferIdTexts[i]);
                                                            }
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    } catch (Exception e) {
                                                        // TODO: handle exception
                                                        e.printStackTrace();
                                                    }
                                                    try {
                                                        FileUtils.deleteDirectory(waferIdFile);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkDirectory(File file) {
        if (file.isDirectory() && file.listFiles().length > 0) {
            return true;
        } else {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void dealException(DataSourceBean dataSourceConfigBean, File[] waferIdTexts, HashMap<String, String> resultMap, ArrayList<DataParseIssueBean> issueBeans, String exceptionMessage, String issueType, int level) {
        DataParseIssueBean dataParseIssueBean = getDatabean(resultMap, issueType, level, "NA", exceptionMessage);
        issueBeans.add(dataParseIssueBean);
        dataSourceConfig.dataErrorsRecord(issueBeans);
        try {
            for (int i = 0; i < waferIdTexts.length; i++) {
                FileUtils.forceDelete(waferIdTexts[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dataToVtDB(RawdataInitBean rawdataInitBean, HashMap<String, String> resultMap) {
        ArrayList<Integer> passBinArray = new ArrayList<>();
        ArrayList<Integer> osBinArray = new ArrayList<>();
        String[] passBins = resultMap.get("passBins").split(",");
        for (int i = 0; i < passBins.length; i++) {
            passBinArray.add(Integer.valueOf(passBins[i]));
        }
        String[] osBins = resultMap.get("osBins").split(",");
        for (int i = 0; i < osBins.length; i++) {
            osBinArray.add(Integer.valueOf(osBins[i]));
        }
        String customerCode = resultMap.get("customCode");
        String device = resultMap.get("device");
        String lotNum = resultMap.get("lot");
        String cpProcess = resultMap.get("cpStep");
        String waferId = resultMap.get("waferId");

        ArrayList<Integer> passBinsArray = new ArrayList<>();
        for (int i = 0; i < passBins.length; i++) {
            passBinsArray.add(Integer.valueOf(passBins[i]));
        }
        testerDataDAO.deleteSiteInforToBinInfoSummary(customerCode, device, lotNum, cpProcess, waferId);
        testerDataDAO.insertSiteInforToBinInfoSummary(customerCode, device, lotNum, cpProcess, waferId, rawdataInitBean.getPrimarySiteBinSum(), "P", passBinsArray);
        if (rawdataInitBean.getRetestSiteBinSum().size() > 0) {
            testerDataDAO.insertSiteInforToBinInfoSummary(customerCode, device, lotNum, cpProcess, waferId, rawdataInitBean.getRetestSiteBinSum(), "R", passBinsArray);
        }
        testerDataDAO.insertSiteInforToBinInfoSummary(customerCode, device, lotNum, cpProcess, waferId, rawdataInitBean.getSiteBinSum(), "F", passBinsArray);

//        ArrayList<FailDieBean> failDies=new ArrayList<>();
//        HashMap<String,String> testDieMap=rawdataInitBean.getTestDieMap();
//        Set<String> coordinateSet=testDieMap.keySet();
//        for (String coordinate : coordinateSet) {
//            Integer softbin=Integer.valueOf(testDieMap.get(coordinate).substring(0,4).trim());
//            if (!passBinsArray.contains(softbin))
//            {
//                Integer coordianteX=Integer.valueOf(coordinate.substring(0,4).trim());
//                Integer coordianteY=Integer.valueOf(coordinate.substring(4).trim());
//                FailDieBean failDieBean=new FailDieBean();
//                failDieBean.setxCoordinate(coordianteX);
//                failDieBean.setyCoordinate(coordianteY);
//                failDieBean.setSiteId(0);
//                failDieBean.setBinNumber(softbin);
//                failDies.add(failDieBean);
//            }
//        }
//        testerDataDAO.insertFailDieToBinInfo(customerCode,device,lotNum,cpProcess,waferId,failDies);

        BinWaferInforBean binWaferInforBean = new BinWaferInforBean();
        generateWaferInforBean.generate(rawdataInitBean, binWaferInforBean);

        testerDataDAO.insertWaferInforToBinWaferSummary(binWaferInforBean);

        EquipmentBean equipmentBean = new EquipmentBean();
        generateEquipmentInforBean.generate(rawdataInitBean, equipmentBean);
        testerDataDAO.insertEquipmentInforToeqCardSummary(equipmentBean);

        if (customerCode.equals("AMC") || customerCode.equals("ASH") || customerCode.equals("YFN") || customerCode.equals("FJT") || customerCode.equals("HUT")) {
            try {
                siteInforReport.write(customerCode, device, lotNum, cpProcess, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (checkIfInforToMes.check(customerCode, device)) {
            SiteYieldToMes siteYieldToMes = new SiteYieldToMes();
            siteYieldToMes.setLot(lotNum);
            siteYieldToMes.setCpStep(cpProcess);
            siteYieldToMes.setWaferId(waferId);
            HashMap<String, String> siteOsAndPassMap = new HashMap<>();
            HashMap<Integer, HashMap<Integer, Integer>> siteBinMap = rawdataInitBean.getSiteBinSum();
            Set<Integer> sites = siteBinMap.keySet();
            for (Integer site : sites) {
                HashMap<Integer, Integer> binMap = siteBinMap.get(site);
                Set<Integer> binSet = binMap.keySet();
                Integer totalBin = 0;
                Integer totalPassBin = 0;
                Integer totalOSBin = 0;
                for (Integer bin : binSet) {
                    Integer binValue = binMap.get(bin);
                    totalBin += binValue;
                    if (passBinArray.contains(bin)) {
                        totalPassBin += binValue;
                    }
                    if (osBinArray.contains(bin)) {
                        totalOSBin += binValue;
                    }
                }
                siteOsAndPassMap.put("Site" + site, String.format("%.2f", (double) (totalPassBin * 100) / totalBin) + "," + String.format("%.2f", (double) (totalOSBin * 100) / totalBin));
            }
            siteYieldToMes.setSiteYieldSummary(siteOsAndPassMap);
            HashMap<String, String> siteInfor = new HashMap<>();
            siteInfor.put("infor", JSON.toJSONString(siteYieldToMes));
            waferIdBinSummaryWrite.write(resultMap, rawdataInitBean);
            stdfTouchDownWrite.write(resultMap, rawdataInitBean);
            vtSiteYieldToMes.siteYieldToMes(siteInfor);
        }
    }
}
