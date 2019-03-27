package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.MappingParseTools.*;
import com.vtest.it.RawdataGenerate.GenerateRawdata;
import com.vtest.it.RawdataGenerate.InitMesConfigToRawdataProperties;
import com.vtest.it.dao.probermapperdao.ProberDataDAO;
import com.vtest.it.dao.vtmesdao.VtMesConfigDAO;
import com.vtest.it.dao.vtmesdao.VtSiteYieldToMes;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.mesinfors.WaferIdBinSummaryWrite;
import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.LotInformationBean;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelPlatformMappingDeal extends PlatformMappingDeal{
    private GetDataSourceConfigDao dataSourceConfig;
    private TelOpusProberLotDatParse telOpusProberLotDatParse;
    private TimeCheck timeCheck;
    private InitMesConfigToRawdataProperties initMesConfigToRawdataProperties;
    private RawDataDeal rawDataDeal;
    private VtMesConfigDAO vtMesConfigDAO;
    private ProberDataDAO proberDataDAO;
    private GenerateWaferInforBean generateWaferInforBean;
    private GenerateRawdata generateRawdata;
    private CheckIfInforToMes checkIfInforToMes;
    private RawDataCheck rawDataCheck;
    private WaferIdBinSummaryWrite waferIdBinSummaryWrite;
    private TelProberMappingNormalParse telProberMappingNormalParse;
    private DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal;
    private TelOpusProberLotDaParse telOpusProberLotDaParse;
    private TelOpusProberMappingDaParse telOpusProberMappingDaParse;
    private TelProberMappingSmallDieParse telProberMappingSmallDieParse;
    private VtSiteYieldToMes vtSiteYieldToMes;

    @Autowired
    public void setVtSiteYieldToMes(VtSiteYieldToMes vtSiteYieldToMes) {
        this.vtSiteYieldToMes = vtSiteYieldToMes;
    }

    @Autowired
    public void setTelProberMappingSmallDieParse(TelProberMappingSmallDieParse telProberMappingSmallDieParse) {
        this.telProberMappingSmallDieParse = telProberMappingSmallDieParse;
    }
    @Autowired
    public void setTelOpusProberMappingDaParse(TelOpusProberMappingDaParse telOpusProberMappingDaParse) {
        this.telOpusProberMappingDaParse = telOpusProberMappingDaParse;
    }
    @Autowired
    public void setTelOpusProberLotDaParse(TelOpusProberLotDaParse telOpusProberLotDaParse) {
        this.telOpusProberLotDaParse = telOpusProberLotDaParse;
    }
    @Autowired
    public void setDatalogBackupAndRawDataDeal(DatalogBackupAndRawDataDeal datalogBackupAndRawDataDeal) {
        this.datalogBackupAndRawDataDeal = datalogBackupAndRawDataDeal;
    }
    @Autowired
    public void setTelProberMappingNormalParse(TelProberMappingNormalParse telProberMappingNormalParse) {
        this.telProberMappingNormalParse = telProberMappingNormalParse;
    }
    @Autowired
    public void setTelOpusProberLotDatParse(TelOpusProberLotDatParse telOpusProberLotDatParse) {
        this.telOpusProberLotDatParse = telOpusProberLotDatParse;
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
    public void deal()
    {
        DataSourceBean dataSourceConfigBean=dataSourceConfig.getConfig("TEL");
        File dataSource=new File(dataSourceConfigBean.getSourcePath());
        File[] files=dataSource.listFiles();
        for (File lot: files) {
            try {
                if (lot.isDirectory()&&lot.listFiles().length>0)
                {
                    if (timeCheck.check(lot,60))
                    {
                        String lotNum = lot.getName();
                        try {
                            LotInformationBean lotInformationBean = vtSiteYieldToMes.getLotInfor(lotNum);
                            if (lotInformationBean.getCustomerCode().equals("GCK")) {
                                File destDirectory = new File("/server212/VTESTFTP/GCK/PROBER/TEL/LCDMAP/" + lotNum);
                                if (!destDirectory.exists()) {
                                    destDirectory.mkdirs();
                                }
                                File[] sourceFile = lot.listFiles();
                                Pattern pattern = Pattern.compile("[0-9]{1}");
                                for (File file : sourceFile) {
                                    String sourceFileName = file.getName();
                                    Matcher matcher = pattern.matcher(sourceFileName.substring(sourceFileName.lastIndexOf(".") - 1, sourceFileName.lastIndexOf(".")));
                                    String destFileName = sourceFileName;
                                    if (matcher.find()) {
                                        destFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf(".") - 1) + "1" + sourceFileName.substring(sourceFileName.lastIndexOf("."));
                                    }
                                    FileUtils.copyFile(file, new File("/server212/VTESTFTP/GCK/PROBER/TEL/LCDMAP/" + lotNum + "/" + destFileName));
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        File destDir=new File(dataSourceConfigBean.getBackupSourcePath());
                        if (!destDir.exists())
                        {
                            destDir.mkdirs();
                        }
                        File[] mappings=lot.listFiles();
                        boolean flag=telBackupFile(mappings,dataSourceConfigBean.getBackupSourcePath()+"/"+lotNum);
                        HashMap<String, File> formAndWafcont=new HashMap<>();
                        ArrayList<File> waferIdRmpArray=new ArrayList<>();
                        ArrayList<File> waferIdDaArray=new ArrayList<>();
                        ArrayList<File> lotDaArray=new ArrayList<>();
                        ArrayList<File> waferIdDatArray=new ArrayList<>();
                        ArrayList<File> lotDatArray=new ArrayList<>();
                        initDatalog(mappings, formAndWafcont, waferIdRmpArray, waferIdDaArray, lotDaArray, waferIdDatArray, lotDatArray);
                        File WAFCONT=formAndWafcont.get("WAFCONT");
                        File FORM=formAndWafcont.get("FORM");
                        if (flag)
                        {
                            HashMap<String,String> resultMapLot=getResultMap("TEL");
                            for (File lotDa : lotDaArray) {
                                boolean delFlag=true;
                                String lotDaName=lotDa.getName();
                                HashMap<String, String> daResultMap= telOpusProberLotDaParse.get(lotDa);
                                String cp=daResultMap.get("cp");
                                String op=daResultMap.get("op");
                                resultMapLot.put("lot", lotNum);
                                resultMapLot.put("cpStep", cp);
                                String lotDaSuffix=lotDaName.substring(lotDaName.indexOf(".")-1, lotDaName.indexOf("."));
                                for (File waferIdDa : waferIdDaArray) {
                                    try {
                                        HashMap<String,String> resultMap=getResultMap("TEL");
                                        resultMap.put("lot", lotNum);
                                        resultMap.put("cpStep", cp);
                                        ArrayList<DataParseIssueBean> issueBeans=new ArrayList<>();
                                        String waferIdDaName=waferIdDa.getName();
                                        String waferIdDaSuffix=waferIdDaName.substring(waferIdDaName.indexOf(".")-1, waferIdDaName.indexOf("."));
                                        String waferId=waferIdDaName.substring(0,waferIdDaName.indexOf(".")-1);
                                        resultMap.put("waferId", waferId);
                                        if (waferIdDaSuffix.equals(lotDaSuffix)) {
                                            String patternName=waferId+waferIdDaSuffix;
                                            File rmpFile=null;
                                            for (File waferIdRmp : waferIdRmpArray) {
                                                if (waferIdRmp.getName().contains(patternName)) {
                                                    rmpFile=waferIdRmp;
                                                }
                                            }
                                            if (rmpFile==null) {
                                                dealException(dataSourceConfigBean,waferIdDa,resultMap,issueBeans,"there was no rmp file ","file_not_found",5);
                                                continue;
                                            }
                                            if (!checkCpProcess(cp))
                                            {
                                                dealException(dataSourceConfigBean,waferIdDa,resultMap,issueBeans,"there are error in cpProcess","cp_process",5);
                                                dealException(dataSourceConfigBean,rmpFile,resultMap,issueBeans,"there are error in cpProcess","cp_process",5);
                                                continue;
                                            }
                                            HashMap<String, String> waferIdDaResultMap= telOpusProberMappingDaParse.GetResult(waferIdDa);
                                            RawdataInitBean rawdataInitBean=new RawdataInitBean();
                                            try {
                                                telProberMappingSmallDieParse.Get(rmpFile,waferIdDaResultMap,rawdataInitBean,cp,op);
                                            } catch (Exception e) {
                                                dealException(dataSourceConfigBean,waferIdDa,resultMap,issueBeans,"there are error in file coding","code_error",5);
                                                dealException(dataSourceConfigBean,rmpFile,resultMap,issueBeans,"there are error in file coding","code_error",5);
                                                continue;
                                            }
                                            MesConfigBean mesConfigBean= vtMesConfigDAO.getBean(waferId,cp);
                                            if (null==mesConfigBean)
                                            {
                                                dealException(dataSourceConfigBean,waferIdDa,resultMap,issueBeans,"can't find this wafer in mes system","call_proc",5);
                                                dealException(dataSourceConfigBean,rmpFile,resultMap,issueBeans,"can't find this wafer in mes system","call_proc",5);
                                                continue;
                                            }
                                            initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean,mesConfigBean);
                                            try {
                                                rawDataDeal.Deal(rawdataInitBean);
                                            } catch (Exception e) {
                                                dealException(dataSourceConfigBean,waferIdDa,resultMap,issueBeans,e.getMessage(),"deal_bean",5);
                                                dealException(dataSourceConfigBean,rmpFile,resultMap,issueBeans,e.getMessage(),"deal_bean",5);
                                                continue;
                                            }
                                            File rawFile= generateRawdata.generate(rawdataInitBean);
                                            rawDataCheck.check(rawFile,issueBeans);
                                            for (DataParseIssueBean issueBean: issueBeans) {
                                                if (issueBean.getIssuLevel()==99)
                                                {
                                                    delFlag=false;
                                                    break;
                                                }
                                            }
                                            if (!delFlag)
                                            {
                                                continue;
                                            }
                                            if (issueBeans.size()>0)
                                            {
                                                dataSourceConfig.dataErrorsRecord(issueBeans);
                                            }
                                            LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
                                            resultMap.put("customCode",properties.get("Customer Code"));
                                            resultMap.put("device",properties.get("Device Name"));
                                            resultMap.put("passBins",properties.get("Pass Bins"));
                                            if(properties.get("Lot ID").equals(lotNum))
                                            {
                                                resultMapLot.put("customCode", properties.get("Customer Code"));
                                                resultMapLot.put("device", properties.get("Device Name"));
                                            }
                                            try {
                                                dataToVtDB(rawdataInitBean,resultMap);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                delFlag=false;
                                                continue;
                                            }
                                            try {
                                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),rmpFile,resultMap);
                                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),waferIdDa,resultMap);
                                                datalogBackupAndRawDataDeal.rawdataDeal(dataSourceConfigBean.getRawdataPath(),rawFile,resultMap);
                                            } catch (Exception e) {
                                                delFlag=false;
                                                continue;
                                            }
                                            FileUtils.forceDelete(waferIdDa);
                                            FileUtils.forceDelete(rmpFile);
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (delFlag)
                                {
                                    datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),lotDa,resultMapLot);
                                    FileUtils.forceDelete(lotDa);
                                }
                            }
                            if (WAFCONT!=null) {
                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),WAFCONT,resultMapLot);
                                FileUtils.forceDelete(WAFCONT);
                            }
                            if (FORM!=null) {
                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),FORM,resultMapLot);
                                FileUtils.forceDelete(FORM);
                            }
                        }else
                        {
                            HashMap<String,String> resultMapLot=getResultMap("TEL");
                            for (File lotDat : lotDatArray) {
                                boolean delFlag=true;
                                String lotDatName=lotDat.getName();
                                HashMap<String, String> datResultMap= telOpusProberLotDatParse.Get(lotDat);
                                String cp=datResultMap.get("cp");
                                String operator=datResultMap.get("op");
                                String lotDatSuffix=lotDatName.substring(lotDatName.indexOf(".")-1, lotDatName.indexOf("."));
                                resultMapLot.put("lot", lotNum);
                                resultMapLot.put("cpStep", cp);
                                for (File waferIdDat : waferIdDatArray)
                                {
                                    String waferIdDatName=waferIdDat.getName();
                                    String waferIdDatSuffix=waferIdDatName.substring(waferIdDatName.indexOf(".")-1, waferIdDatName.indexOf("."));
                                    if (waferIdDatSuffix.equals(lotDatSuffix)) {
                                        try {
                                            HashMap<String,String> resultMap=getResultMap("TEL");
                                            resultMap.put("lot", lotNum);
                                            resultMap.put("cpStep", cp);
                                            ArrayList<DataParseIssueBean> issueBeans=new ArrayList<>();
                                            String waferId=waferIdDatName.substring(0,waferIdDatName.lastIndexOf(".")-1);
                                            resultMap.put("waferId", waferId);
                                            if (!checkCpProcess(cp))
                                            {
                                                dealException(dataSourceConfigBean,waferIdDat,resultMap,issueBeans,"there are error in cpProcess","cp_process",5);
                                                continue;
                                            }
                                            RawdataInitBean rawdataInitBean=new RawdataInitBean();
                                            try {
                                                telProberMappingNormalParse.Get(waferIdDat,rawdataInitBean,operator,cp);
                                            } catch (Exception e) {
                                                dealException(dataSourceConfigBean,waferIdDat,resultMap,issueBeans,"there are error in file coding","code_error",5);
                                                continue;
                                            }
                                            MesConfigBean mesConfigBean= vtMesConfigDAO.getBean(waferId,cp);
                                            if (null==mesConfigBean)
                                            {
                                                dealException(dataSourceConfigBean,waferIdDat,resultMap,issueBeans,"can't find this wafer in mes system","call_proc",5);
                                                continue;
                                            }
                                            initMesConfigToRawdataProperties.initMesConfig(rawdataInitBean,mesConfigBean);
                                            try {
                                                rawDataDeal.Deal(rawdataInitBean);
                                            } catch (Exception e) {
                                                dealException(dataSourceConfigBean,waferIdDat,resultMap,issueBeans,"Rawdata deal Chains Fail","deal_bean",5);
                                                continue;
                                            }
                                            File rawFile= generateRawdata.generate(rawdataInitBean);
                                            rawDataCheck.check(rawFile,issueBeans);
                                            for (DataParseIssueBean issueBean: issueBeans) {
                                                if (issueBean.getIssuLevel()==99)
                                                {
                                                    delFlag=false;
                                                    break;
                                                }
                                            }
                                            if (!delFlag)
                                            {
                                                continue;
                                            }
                                            if (issueBeans.size()>0)
                                            {
                                                dataSourceConfig.dataErrorsRecord(issueBeans);
                                            }
                                            LinkedHashMap<String,String> properties=rawdataInitBean.getProperties();
                                            resultMap.put("customCode",properties.get("Customer Code"));
                                            resultMap.put("device",properties.get("Device Name"));
                                            resultMap.put("passBins",properties.get("Pass Bins"));
                                            if(properties.get("Lot ID").equals(lotNum))
                                            {
                                                resultMapLot.put("customCode", properties.get("Customer Code"));
                                                resultMapLot.put("device", properties.get("Device Name"));
                                            }
                                            try {
                                                dataToVtDB(rawdataInitBean,resultMap);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                delFlag=false;
                                                continue;
                                            }
                                            try {
                                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),waferIdDat,resultMap);
                                                datalogBackupAndRawDataDeal.rawdataDeal(dataSourceConfigBean.getRawdataPath(),rawFile,resultMap);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                delFlag=false;
                                                continue;
                                            }
                                            FileUtils.forceDelete(waferIdDat);
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                if (delFlag)
                                {
                                    datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),lotDat,resultMapLot);
                                    FileUtils.forceDelete(lotDat);
                                }
                            }
                            if (WAFCONT!=null) {
                                datalogBackupAndRawDataDeal.datalogBackup(dataSourceConfigBean.getBackupSourcePathByCp(),WAFCONT,resultMapLot);
                                FileUtils.forceDelete(WAFCONT);
                            }
                        }
                    }
                }else
                {
                    try {
                        FileUtils.forceDelete(lot);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (timeCheck.check(lot, 7200)) {
                    try {
                        FileUtils.deleteDirectory(lot);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    private void initDatalog(File[] mappings,HashMap<String, File> formAndWafcont,ArrayList<File> waferIdRmpArray,ArrayList<File> waferIdDaArray,ArrayList<File> lotDaArray,ArrayList<File> waferIdDatArray,ArrayList<File> lotDatArray)
    {
        for (File mapping : mappings) {
            String mappingName=mapping.getName();
            if (mappingName.equals("WAFCONT.DAT")) {
                formAndWafcont.put("WAFCONT", mapping);
                continue;
            }
            if (mappingName.contains("LOT")&&mappingName.endsWith(".DA")) {
                lotDaArray.add(mapping);
                continue;
            }
            if (mappingName.equals("FORM.DA")) {
                formAndWafcont.put("FORM", mapping);
                continue;
            }
            if (mappingName.endsWith(".RMP")) {
                waferIdRmpArray.add(mapping);
                continue;
            }
            if (mappingName.endsWith(".DA")) {
                waferIdDaArray.add(mapping);
                continue;
            }
            if (mappingName.endsWith(".DAT")&&mappingName.contains("LOT")) {
                lotDatArray.add(mapping);
                continue;
            }
            if (mappingName.endsWith(".DAT")) {
                waferIdDatArray.add(mapping);
            }
        }
    }
    private  boolean telBackupFile( File[] mappings,String backupPath)
    {
        boolean flag=false;
        ArrayList<File> mappingArray=new ArrayList<>();
        ArrayList<String> mappingNamesArray=new ArrayList<>();
        for (File mapping : mappings) {
            String fileName=mapping.getName();
            if (fileName.equals("FORM.DA")||fileName.equals("LOT1.DA")||fileName.equals("LOT2.DA")) {
                flag=true;
                continue;
            }else if (fileName.endsWith(".RMP")) {
                mappingArray.add(mapping);
                mappingNamesArray.add(fileName);
                continue;
            }else if (fileName.endsWith(".DA")) {
                mappingArray.add(mapping);
                mappingNamesArray.add(fileName);
                continue;
            }else if (fileName.endsWith(".DAT")&&!fileName.contains("WAFCONT")&&!fileName.contains("LOT")) {
                mappingArray.add(mapping);
                mappingNamesArray.add(fileName);
                continue;
            }
        }
        for (File mapping : mappingArray) {
            try {
                String mappingName=mapping.getName();
                String waferId=mappingName.substring(0, mappingName.lastIndexOf(".")-1);
                Integer time=Integer.valueOf(mappingName.substring(mappingName.lastIndexOf(".")-1, mappingName.lastIndexOf(".")));
                String suffix=mappingName.substring(mappingName.lastIndexOf("."));

                String retestFileName=waferId+(time+1)+suffix;
                if (!mappingNamesArray.contains(retestFileName)) {
                    String backFileName=waferId+"1"+suffix;
                    File mapDownFile=new File(backupPath+"/"+backFileName);
                    if (mapDownFile.exists()) {
                        mapDownFile.delete();
                    }
                    try {
                        FileUtils.copyFile(mapping, mapDownFile);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        boolean lot2BackupFlag=false;
        for (File mapping : mappings)
        {
            if (mapping.isFile()) {
                if (flag) {
                    if (mapping.getName().equals("LOT2.DA")) {
                        lot2BackupFlag=true;
                        File mapDownFile=new File(backupPath+"/LOT1.DA");
                        if (mapDownFile.exists()) {
                            mapDownFile.delete();
                        }
                        try {
                            FileUtils.copyFile(mapping, mapDownFile);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if (mapping.getName().equals("LOT1.DA")) {
                        if (!lot2BackupFlag) {
                            File mapDownFile=new File(backupPath+"/LOT1.DA");
                            if (mapDownFile.exists()) {
                                mapDownFile.delete();
                            }
                            try {
                                FileUtils.copyFile(mapping, mapDownFile);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }else if (mapping.getName().equals("FORM.DA")) {
                        File mapDownFile=new File(backupPath+"/FORM.DA");
                        if (mapDownFile.exists()) {
                            mapDownFile.delete();
                        }
                        try {
                            FileUtils.copyFile(mapping, mapDownFile);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if (mapping.getName().equals("WAFCONT.DAT")) {
                        File mapDownFile=new File(backupPath+"/WAFCONT.DAT");
                        if (mapDownFile.exists()) {
                            mapDownFile.delete();
                        }
                        try {
                            FileUtils.copyFile(mapping, mapDownFile);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }else {
                    if (mapping.getName().equals("LOT2.DAT"))
                    {
                        lot2BackupFlag=true;
                        File mapDownFile=new File(backupPath+"/LOT1.DAT");
                        if (mapDownFile.exists()) {
                            mapDownFile.delete();
                        }
                        try {
                            FileUtils.copyFile(mapping, mapDownFile);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else if (mapping.getName().equals("LOT1.DAT")) {
                        if (!lot2BackupFlag) {
                            File mapDownFile=new File(backupPath+"/LOT1.DAT");
                            if (mapDownFile.exists()) {
                                mapDownFile.delete();
                            }
                            try {
                                FileUtils.copyFile(mapping, mapDownFile);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }else {
                try {
                    FileUtils.forceDelete(mapping);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
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
        if (!rawdataInitBean.getSiteBinSum().keySet().isEmpty())
        {
            proberDataDAO.insertSiteInforToBinInfoSummary(customerCode,device,lotNum,cpProcess,waferId,rawdataInitBean.getSiteBinSum(),"P",passBinsArray);
            proberDataDAO.insertSiteInforToBinInfoSummary(customerCode,device,lotNum,cpProcess,waferId,rawdataInitBean.getSiteBinSum(),"F",passBinsArray);
        }

        BinWaferInforBean binWaferInforBean=new BinWaferInforBean();
        generateWaferInforBean.generate(rawdataInitBean,binWaferInforBean);
        proberDataDAO.insertWaferInforToBinWaferSummary(binWaferInforBean);
//        if (!checkIfInforToMes.check(customerCode,device))
//        {
//            waferIdBinSummaryWrite.write(resultMap,rawdataInitBean);
//        }
    }
}
