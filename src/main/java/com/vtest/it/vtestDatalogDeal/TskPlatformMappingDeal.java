package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.MappingParseTools.TskProberMappingParse;
import com.vtest.it.RawdataGenerate.GenerateRawdata;
import com.vtest.it.RawdataGenerate.InitMesConfigToRawdataProperties;
import com.vtest.it.dao.probermapperdao.ProberDataDAO;
import com.vtest.it.dao.vtmesdao.VtMesConfigDAO;
import com.vtest.it.dao.vtmesdao.VtMesSlotAndSequenceDAO;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.mesinfors.WaferIdBinSummaryWrite;
import com.vtest.it.mestools.SlotModify;
import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.pojo.binwaferinfors.FailDieBean;
import com.vtest.it.pojo.propertiescheckItemBean.DataParseIssueBean;
import com.vtest.it.rawdatacheck.CheckIfInforToMes;
import com.vtest.it.rawdatacheck.RawDataCheck;
import com.vtest.it.rawdatafterdeal.GenerateWaferInforBean;
import com.vtest.it.rawdatafterdeal.RawDataDeal;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
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
public class TskPlatformMappingDeal extends PlatformMappingDeal{
    private GetDataSourceConfigDao dataSourceConfig;
    private TimeCheck timeCheck;
    private SlotModify slotModify;
    private TskProberMappingParse tskProberMappingParse;
    private InitMesConfigToRawdataProperties initMesConfigToRawdataProperties;
    private RawDataDeal rawDataDeal;
    private VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO;
    private VtMesConfigDAO vtMesConfigDAO;
    private ProberDataDAO proberDataDAO;
    private GenerateWaferInforBean generateWaferInforBean;
    private GenerateRawdata generateRawdata;
    private CheckIfInforToMes checkIfInforToMes;
    private RawDataCheck rawDataCheck;
    private WaferIdBinSummaryWrite waferIdBinSummaryWrite;
    private DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal;

    @Autowired
    public void setDatalogBackupAndRawDataDeal(DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal) {
        this.datalogBackupAndRawDataDeal = datalogBackupAndRawDataDeal;
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
    public void setProberDataDAO(ProberDataDAO proberDataDAO) {
        this.proberDataDAO = proberDataDAO;
    }
    @Autowired
    public void setVtMesSlotAndSequenceDAO(VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO) {
        this.vtMesSlotAndSequenceDAO = vtMesSlotAndSequenceDAO;
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
    public void setSlotModify(SlotModify slotModify) {
        this.slotModify = slotModify;
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
    public void setTskProberMappingParse(TskProberMappingParse tskProberMappingParse) {
        this.tskProberMappingParse = tskProberMappingParse;
    }
    @Autowired
    public void setInitMesConfigToRawdataProperties(InitMesConfigToRawdataProperties initMesConfigToRawdataProperties) {
        this.initMesConfigToRawdataProperties = initMesConfigToRawdataProperties;
    }
    public void deal()
    {
        DataSourceBean dataSourceConfigBean=dataSourceConfig.getConfig("TSK");
        File dataSource=new File(dataSourceConfigBean.getSourcePath());
        File[] files=dataSource.listFiles();
        for (File lot: files) {
            try {
                if (lot.isDirectory())
                {
                    if (timeCheck.check(lot,60))
                    {
                        try {
                            File destDir=new File(dataSourceConfigBean.getBackupSourcePath());
                            if (!destDir.exists())
                            {
                                destDir.mkdirs();
                            }
                            FileUtils.copyDirectoryToDirectory(lot,destDir);
                            String lotNum=lot.getName();
                            SlotAndSequenceConfigBean slotAndSequenceConfigBean=vtMesSlotAndSequenceDAO.getConfig(lotNum);
                            File[] wafers=lot.listFiles();
                            for (File wafer :wafers) {
                                try {
                                    HashMap<String,String> resultMap=getResultMap("TSK");
                                    resultMap.put("lot", lotNum);
                                    ArrayList<DataParseIssueBean> issueBeans=new ArrayList<>();
                                    String waferId=wafer.getName().substring(4);
                                    if (slotAndSequenceConfigBean.getReadType().equals("SLOT"));
                                    {
                                        waferId=slotModify.modify(slotAndSequenceConfigBean.getSequence(),waferId);
                                    }
                                    resultMap.put("waferId", waferId);
                                    RawdataInitBean rawdataInitBean=new RawdataInitBean();
                                    try {
                                        tskProberMappingParse.Get(wafer,Integer.valueOf(slotAndSequenceConfigBean.getGpibBin()),rawdataInitBean);
                                    } catch (Exception e) {
                                        dealException(dataSourceConfigBean,wafer,resultMap,issueBeans,"there are error in file coding","code_error",5);
                                        continue;
                                    }
                                    String cpProcess=rawdataInitBean.getDataProperties().get("CP Process");
                                    resultMap.put("cpStep", cpProcess);
                                    if (!checkCpProcess(cpProcess))
                                    {
                                        dealException(dataSourceConfigBean,wafer,resultMap,issueBeans,"there are error in cpProcess","cp_process",5);
                                        continue;
                                    }
                                    MesConfigBean mesConfigBean= vtMesConfigDAO.getBean(waferId,cpProcess);
                                    if (null==mesConfigBean)
                                    {
                                        dealException(dataSourceConfigBean,wafer,resultMap,issueBeans,"can't find this wafer in mes system","call_proc",5);
                                        continue;
                                    }
                                    initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean,mesConfigBean);
                                    try {
                                        rawDataDeal.Deal(rawdataInitBean);
                                    } catch (Exception e) {
                                        dealException(dataSourceConfigBean,wafer,resultMap,issueBeans,e.getMessage(),"deal_bean",5);
                                        continue;
                                    }
                                    File rawFile= generateRawdata.generate(rawdataInitBean);
                                    rawDataCheck.check(rawFile,issueBeans);
                                    for (DataParseIssueBean issueBean: issueBeans) {
                                        if (issueBean.getIssuLevel()==99)
                                        {
                                            continue;
                                        }
                                    }
                                    if (issueBeans.size()>0)
                                    {
                                        dataSourceConfig.dataErrorsRecord(issueBeans);
                                    }

                                    LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
                                    resultMap.put("customCode",properties.get("Customer Code"));
                                    resultMap.put("device",properties.get("Device Name"));
                                    resultMap.put("passBins",properties.get("Pass Bins"));
                                    try {
                                        dataToVtDB(rawdataInitBean,resultMap);
                                    } catch (Exception e) {
                                        continue;
                                    }
                                    try {
                                        datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),wafer,resultMap);
                                        datalogBackupAndRawDataDeal.rawdataDeal(dataSourceConfigBean.getRawdataPath(),rawFile,resultMap);
                                    } catch (Exception e) {
                                        continue;
                                    }
                                    FileUtils.forceDelete(wafer);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                           if (lot.listFiles().length==0)
                           {
                               FileUtils.deleteDirectory(lot);
                           }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    try {
                        FileUtils.forceDelete(lot);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void dataToVtDB(RawdataInitBean rawdataInitBean,HashMap<String,String> resultMap)
    {
        String[] passBins=resultMap.get("passBins").split(",");
        String customerCode=resultMap.get("customCode");
        String device=resultMap.get("device");
        String lotNum=resultMap.get("lot");
        String cpProcess=resultMap.get("cpStep");
        String waferId=resultMap.get("waferId");

        ArrayList<Integer> passBinsArray=new ArrayList<>();
        for (int i = 0; i < passBins.length; i++) {
            passBinsArray.add(Integer.valueOf(passBins[i]));
        }
        proberDataDAO.deleteSiteInforToBinInfoSummary(customerCode,device,lotNum,cpProcess,waferId);
        proberDataDAO.insertSiteInforToBinInfoSummary(customerCode,device,lotNum,cpProcess,waferId,rawdataInitBean.getSiteBinSum(),"P",passBinsArray);
        proberDataDAO.insertSiteInforToBinInfoSummary(customerCode,device,lotNum,cpProcess,waferId,rawdataInitBean.getSiteBinSum(),"F",passBinsArray);

        ArrayList<FailDieBean> failDies=new ArrayList<>();
        HashMap<String,String> testDieMap=rawdataInitBean.getTestDieMap();
        Set<String> coordinateSet=testDieMap.keySet();
        for (String coordinate : coordinateSet) {
            Integer softbin=Integer.valueOf(testDieMap.get(coordinate).substring(0,4).trim());
            if (!passBinsArray.contains(softbin))
            {
                Integer coordianteX=Integer.valueOf(coordinate.substring(0,4).trim());
                Integer coordianteY=Integer.valueOf(coordinate.substring(4).trim());
                FailDieBean failDieBean=new FailDieBean();
                failDieBean.setxCoordinate(coordianteX);
                failDieBean.setyCoordinate(coordianteY);
                failDieBean.setSiteId(0);
                failDieBean.setBinNumber(softbin);
                failDies.add(failDieBean);
            }
        }
        proberDataDAO.insertFailDieToBinInfo(customerCode,device,lotNum,cpProcess,waferId,failDies);
        BinWaferInforBean binWaferInforBean=new BinWaferInforBean();
        generateWaferInforBean.generate(rawdataInitBean,binWaferInforBean);
        proberDataDAO.insertWaferInforToBinWaferSummary(binWaferInforBean);
        if (!checkIfInforToMes.check(customerCode,device))
        {
            waferIdBinSummaryWrite.write(resultMap,rawdataInitBean);
        }
    }
    private void issueToDataBase(ArrayList<DataParseIssueBean> issueBeans)
    {
        dataSourceConfig.dataErrorsRecord(issueBeans);
    }
    private void dealException(DataSourceBean dataSourceConfigBean,File wafer,HashMap<String,String> resultMap,ArrayList<DataParseIssueBean> issueBeans,String exceptionMessage,String issueType,int level)
    {
        String path=datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),wafer,resultMap);
        DataParseIssueBean dataParseIssueBean=getDatabean(resultMap,issueType,level,path,exceptionMessage);
        issueBeans.add(dataParseIssueBean);
        dataSourceConfig.dataErrorsRecord(issueBeans);
        try {
            FileUtils.forceDelete(wafer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
