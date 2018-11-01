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
import com.vtest.it.rawdatafterdeal.GenerateEquipmentInforBean;
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
public class TskPlatformMappingDeal{
    private GetDataSourceConfigDao dataSourceConfig;
    private TimeCheck timeCheck;
    private SlotModify slotModify;
    private TskProberMappingParse tskProberMappingParse;
    private InitMesConfigToRawdataProperties initMesConfigToRawdataProperties;
    private RawDataDeal rawDataDeal;
    private VtMesSlotAndSequenceDAO vtMesSlotAndSequenceDAO;
    private VtMesConfigDAO vtMesConfigDAO;
    private ProberDataDAO proberDataDAO;
    private GenerateEquipmentInforBean generateEquipmentInforBean;
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
    public void setGenerateEquipmentInforBean(GenerateEquipmentInforBean generateEquipmentInforBean) {
        this.generateEquipmentInforBean = generateEquipmentInforBean;
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
                            File[] wafers=lot.listFiles();
                            for (File wafer :wafers) {
                                try {
                                    ArrayList<DataParseIssueBean> issueBeans=new ArrayList<>();
                                    String waferId=wafer.getName().substring(4);
                                    SlotAndSequenceConfigBean slotAndSequenceConfigBean=vtMesSlotAndSequenceDAO.getConfig(lotNum);
                                    if (slotAndSequenceConfigBean.getReadType().equals("SLOT"));
                                    {
                                        waferId=slotModify.modify(slotAndSequenceConfigBean.getSequence(),waferId);
                                    }
                                    RawdataInitBean rawdataInitBean=new RawdataInitBean();
                                    tskProberMappingParse.Get(wafer,Integer.valueOf(slotAndSequenceConfigBean.getGpibBin()),rawdataInitBean);
                                    String cpProcess=rawdataInitBean.getDataProperties().get("CP Process");
                                    MesConfigBean mesConfigBean= vtMesConfigDAO.getBean(waferId,cpProcess);
                                    initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean,mesConfigBean);
                                    rawDataDeal.Deal(rawdataInitBean);
                                    File rawFile= generateRawdata.generate(rawdataInitBean);
                                    rawDataCheck.check(rawFile,issueBeans);

                                    LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
                                    HashMap<String,String> resultMap=new HashMap<>();
                                    resultMap.put("customCode",properties.get("Customer Code"));
                                    resultMap.put("device",properties.get("Device Name"));
                                    resultMap.put("passBins",properties.get("Pass Bins"));
                                    resultMap.put("lot", lotNum);
                                    resultMap.put("waferId", waferId);
                                    resultMap.put("cp", cpProcess);
                                    dataToVtDB(rawdataInitBean,resultMap);
                                    datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),wafer,resultMap);
                                    datalogBackupAndRawDataDeal.rawdataDeal(dataSourceConfigBean.getRawdataPath(),rawFile,resultMap);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            FileUtils.deleteDirectory(lot);
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
    public void dataToVtDB(RawdataInitBean rawdataInitBean,HashMap<String,String> resultMap)
    {
        String[] passBins=resultMap.get("passBins").split(",");
        String customerCode=resultMap.get("customCode");
        String device=resultMap.get("device");
        String lotNum=resultMap.get("lot");
        String cpProcess=resultMap.get("cp");
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
}
