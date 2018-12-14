package com.vtest.it.excelModel;

import com.vtest.it.dao.testermapperdao.TesterDataDAO;
import com.vtest.it.dao.vtmesdao.VtMesConfigDAO;
import com.vtest.it.pojo.MesConfigBean;
import com.vtest.it.pojo.binwaferinfors.waferYieldBean;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Service
public class SiteInforReport {
    private TesterDataDAO testerDataDAO;
    private VtMesConfigDAO vtMesConfigDAO;

    @Autowired
    public void setVtMesConfigDAO(VtMesConfigDAO vtMesConfigDAO) {
        this.vtMesConfigDAO = vtMesConfigDAO;
    }

    @Autowired
    public void setTesterDataDAO(TesterDataDAO testerDataDAO) {
        this.testerDataDAO = testerDataDAO;
    }

    public void write(String... information) throws IOException{
        HashMap<Integer, String> binDescriptions = new HashMap();
        ExcelInitModel model = new ExcelInitModel();
        ArrayList<Integer> osBins = new ArrayList<>();
        HashMap<String, String> infors = new HashMap<>();
        infors.put("customerCode", information[0]);
        infors.put("device", information[1]);
        infors.put("lot", information[2]);
        infors.put("cp", information[3]);
        infors.put("wafer", information[4]);
        ArrayList<waferYieldBean> siteInforsPrimary = testerDataDAO.getWaferBinSummaryUnifiedEntrance(information[0], information[1], information[2], information[3], information[4], "P");
        ArrayList<waferYieldBean> siteInforsRetest = testerDataDAO.getWaferBinSummaryUnifiedEntrance(information[0], information[1], information[2], information[3], information[4], "R");
        Set<String> waferIdsSetPrimary = new HashSet<>();
        Set<String> waferIdsSetRetest = new HashSet<>();
        ArrayList<BysiteAndTestProcessInfors> TotalSummaryAllSiteSum = new ArrayList<>();
        ArrayList<BysiteAndTestProcessInfors> TotalSummary = new ArrayList<>();
        ArrayList<BysiteAndTestProcessInfors> TotalSummaryReTestAllSiteSum = new ArrayList<>();
        ArrayList<BysiteAndTestProcessInfors> TotalSummaryReTest = new ArrayList<>();
        ArrayList<BysiteAndTestProcessInfors>[] resultArray = new ArrayList[4];
        resultArray[0] = TotalSummaryAllSiteSum;
        resultArray[1] = TotalSummary;
        resultArray[2] = TotalSummaryReTestAllSiteSum;
        resultArray[3] = TotalSummaryReTest;
        TreeSet<Integer> allBins = new TreeSet<>();
        for (waferYieldBean bean : siteInforsPrimary) {
            String waferId = bean.getWaferNo();
            Integer site = bean.getSiteId();
            if (waferIdsSetPrimary.contains(waferId + ":" + site)) {
                BysiteAndTestProcessInfors siteSummary = getBean(TotalSummary, waferId, site, "RP0");
                if (bean.getPassFail()) {
                    siteSummary.passBins = bean.getBinCount();
                }
                allBins.add(bean.getSoftBinNo());
                siteSummary.binSummary.put(bean.getSoftBinNo(), bean.getBinCount());
            } else {
                BysiteAndTestProcessInfors siteSummary = new BysiteAndTestProcessInfors();
                siteSummary.setRpProcess("RP0");
                siteSummary.setWaferId(waferId);
                siteSummary.setSiteNo(site + "");
                if (bean.getPassFail()) {
                    siteSummary.passBins = bean.getBinCount();
                }
                TreeMap<Integer, Integer> binSummary = new TreeMap<>();
                allBins.add(bean.getSoftBinNo());
                binSummary.put(bean.getSoftBinNo(), bean.getBinCount());
                siteSummary.setBinSummary(binSummary);
                TotalSummary.add(siteSummary);
                waferIdsSetPrimary.add(waferId + ":" + site);
            }
        }
        for (waferYieldBean bean : siteInforsRetest) {
            String waferId = bean.getWaferNo();
            Integer site = bean.getSiteId();
            if (waferIdsSetRetest.contains(waferId + ":" + site)) {
                BysiteAndTestProcessInfors siteSummary = getBean(TotalSummaryReTest, waferId, site, "RP1");
                siteSummary.binSummary.put(bean.getSoftBinNo(), bean.getBinCount());
                if (bean.getPassFail()) {
                    siteSummary.passBins = bean.getBinCount();
                }
                allBins.add(bean.getSoftBinNo());
            } else {
                BysiteAndTestProcessInfors siteSummary = new BysiteAndTestProcessInfors();
                siteSummary.setRpProcess("RP1");
                siteSummary.setPassBins(0);
                siteSummary.setWaferId(waferId);
                siteSummary.setSiteNo(site + "");
                if (bean.getPassFail()) {
                    siteSummary.passBins = bean.getBinCount();
                }
                allBins.add(bean.getSoftBinNo());
                TreeMap<Integer, Integer> binSummary = new TreeMap<>();
                binSummary.put(bean.getSoftBinNo(), bean.getBinCount());
                siteSummary.setBinSummary(binSummary);
                TotalSummaryReTest.add(siteSummary);
                waferIdsSetRetest.add(waferId + ":" + site);
            }
        }
        TreeSet<String> waferIds = new TreeSet<>();
        Set<String> checkSetPrimary = new HashSet<>();
        for (BysiteAndTestProcessInfors bean : TotalSummary) {
            if (osBins.size() == 0) {
                MesConfigBean mesConfigBean = vtMesConfigDAO.getBean(bean.waferId, infors.get("cp"));
                String[] osBinArray = mesConfigBean.getOsBins().split(",");
                for (String bin : osBinArray) {
                    osBins.add(Integer.valueOf(bin));
                }
            }
            if (binDescriptions.size() == 0) {
                try {
                    String[] descriptions;
                    String defines=vtMesConfigDAO.getBinDescription(bean.waferId, information[3]);
                    if (null==defines)
                    {
                         descriptions = "0:NA".split(";");
                    }else
                    {
                        descriptions = defines.split(";");
                    }
                    for (int i = 0; i < descriptions.length; i++) {
                        binDescriptions.put(Integer.valueOf(descriptions[i].split(":")[0]), descriptions[i].split(":")[1]);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            Integer osBinNumber = 0;
            TreeMap<Integer, Integer> binSummary = bean.binSummary;
            for (Map.Entry<Integer, Integer> entry : binSummary.entrySet()) {
                if (osBins.contains(entry.getKey())) {
                    osBinNumber += entry.getValue();
                }
            }
            bean.setOsBins(osBinNumber);
            waferIds.add(bean.waferId);
            if (checkSetPrimary.contains(bean.waferId)) {
                for (BysiteAndTestProcessInfors siteSummary : TotalSummaryAllSiteSum) {
                    if (siteSummary.waferId.equals(bean.waferId)) {
                        siteSummary.setPassBins(siteSummary.passBins + bean.passBins);
                        siteSummary.setOsBins(siteSummary.osBins + bean.osBins);
                        TreeMap<Integer, Integer> tempMap = new TreeMap<>();
                        for (Map.Entry<Integer, Integer> entry : siteSummary.binSummary.entrySet()) {
                            tempMap.put(entry.getKey(), entry.getValue());
                        }
                        for (Map.Entry<Integer, Integer> entry : bean.binSummary.entrySet()) {
                            if (!tempMap.containsKey(entry.getKey())) {
                                tempMap.put(entry.getKey(), entry.getValue());
                            } else {
                                tempMap.put(entry.getKey(), entry.getValue() + tempMap.get(entry.getKey()));
                            }
                        }
                        siteSummary.setBinSummary(tempMap);
                    }
                }
            } else {
                BysiteAndTestProcessInfors siteSummary = new BysiteAndTestProcessInfors();
                siteSummary.setRpProcess(bean.rpProcess);
                siteSummary.setPassBins(bean.passBins);
                siteSummary.setWaferId(bean.waferId);
                siteSummary.setSiteNo("All");
                siteSummary.setBinSummary(bean.binSummary);
                siteSummary.setOsBins(bean.osBins);
                TotalSummaryAllSiteSum.add(siteSummary);
            }
            checkSetPrimary.add(bean.waferId);
        }
        Set<String> checkSetRetest = new HashSet<>();
        for (BysiteAndTestProcessInfors bean : TotalSummaryReTest) {
            Integer osBinNumber = 0;
            TreeMap<Integer, Integer> binSummary = bean.binSummary;
            for (Map.Entry<Integer, Integer> entry : binSummary.entrySet()) {
                if (osBins.contains(entry.getKey())) {
                    osBinNumber += entry.getValue();
                }
            }
            bean.setOsBins(osBinNumber);
            waferIds.add(bean.waferId);
            if (checkSetRetest.contains(bean.waferId)) {
                for (BysiteAndTestProcessInfors siteSummary : TotalSummaryReTestAllSiteSum) {
                    if (siteSummary.waferId.equals(bean.waferId)) {
                        siteSummary.setPassBins(siteSummary.passBins + bean.passBins);
                        siteSummary.setOsBins(siteSummary.osBins + bean.osBins);
                        TreeMap<Integer, Integer> tempMap = new TreeMap<>();
                        for (Map.Entry<Integer, Integer> entry : siteSummary.binSummary.entrySet()) {
                            tempMap.put(entry.getKey(), entry.getValue());
                        }
                        for (Map.Entry<Integer, Integer> entry : bean.binSummary.entrySet()) {
                            if (!tempMap.containsKey(entry.getKey())) {
                                tempMap.put(entry.getKey(), entry.getValue());
                            } else {
                                tempMap.put(entry.getKey(), entry.getValue() + tempMap.get(entry.getKey()));
                            }
                        }
                        siteSummary.setBinSummary(tempMap);
                    }
                }
            } else {
                BysiteAndTestProcessInfors siteSummary = new BysiteAndTestProcessInfors();
                siteSummary.setRpProcess(bean.rpProcess);
                siteSummary.setPassBins(bean.passBins);
                siteSummary.setWaferId(bean.waferId);
                siteSummary.setSiteNo("All");
                siteSummary.setBinSummary(bean.binSummary);
                siteSummary.setOsBins(bean.osBins);
                TotalSummaryReTestAllSiteSum.add(siteSummary);
            }
            checkSetRetest.add(bean.waferId);
        }
        XSSFWorkbook workbook = model.workbook;
        XSSFSheet sheet = workbook.createSheet("Summary");
        for (int i = 0; i < 13; i++) {
            sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
        }
        sheet.createRow(0);
        sheet.createRow(1);
        for (int i = 0; i < 13; i++) {
            sheet.getRow(0).createCell(i).setCellStyle(model.Center_Style);
        }
        sheet.getRow(0).getCell(0).setCellValue("Device Name");
        sheet.getRow(0).getCell(1).setCellValue("Lot ID");
        sheet.getRow(0).getCell(2).setCellValue("Wafer ID");
        sheet.getRow(0).getCell(3).setCellValue("RP No");
        sheet.getRow(0).getCell(4).setCellValue("SiteNo");
        sheet.getRow(0).getCell(5).setCellValue("Total");
        sheet.getRow(0).getCell(6).setCellValue("PASS");
        sheet.getRow(0).getCell(7).setCellValue("Fail");
        sheet.getRow(0).getCell(8).setCellValue("Yield %");
        sheet.getRow(0).getCell(9).setCellValue("OS Yield %");
        sheet.getRow(0).getCell(10).setCellValue("Sit Gap");
        sheet.getRow(0).getCell(11).setCellValue("Retest rate");
        sheet.getRow(0).getCell(12).setCellValue("Recover rate");
        int startIndex = 13;
        for (Integer bin : allBins) {
            sheet.getRow(0).createCell(startIndex).setCellStyle(model.Center_Style);
            sheet.getRow(0).getCell(startIndex).setCellValue(bin);
            sheet.getRow(1).createCell(startIndex).setCellStyle(model.Center_Style);
            sheet.getRow(1).getCell(startIndex).setCellValue(binDescriptions.containsKey(bin)?binDescriptions.get(bin):"NA");
            startIndex++;
        }
        int startRow = 2;
        int gapStartindex=0;
        for (String waferId : waferIds) {
            for (int k = 0; k < resultArray.length; k++) {
                if (k==2||k==0)
                {
                    gapStartindex+=3;
                }
                for (BysiteAndTestProcessInfors bean : resultArray[k]) {
                    if (bean.waferId.equals(waferId)) {
                        sheet.createRow(startRow);
                        for (int i = 0; i < 13; i++) {
                            sheet.getRow(startRow).createCell(i).setCellStyle(model.Center_Style);
                        }
                        int totalDies = 0;
                        TreeMap<Integer, Integer> binSummary = bean.binSummary;
                        for (Map.Entry<Integer, Integer> entry : binSummary.entrySet()) {
                            totalDies += entry.getValue();
                        }
                        sheet.getRow(startRow).getCell(0).setCellValue(information[1]);
                        sheet.getRow(startRow).getCell(1).setCellValue(information[2]);
                        sheet.getRow(startRow).getCell(2).setCellValue(bean.waferId);
                        sheet.getRow(startRow).getCell(3).setCellValue(bean.rpProcess);
                        sheet.getRow(startRow).getCell(4).setCellValue(bean.siteNo.length() == 1 ? "Site" + bean.siteNo : bean.siteNo);
                        sheet.getRow(startRow).getCell(5).setCellValue(totalDies);
                        sheet.getRow(startRow).getCell(6).setCellValue(bean.passBins);
                        sheet.getRow(startRow).getCell(7).setCellFormula("F" + (startRow + 1) + "-G" + (startRow + 1));
                        sheet.getRow(startRow).getCell(8).setCellFormula("G" + (startRow + 1) + "/F" + (startRow + 1));
                        sheet.getRow(startRow).getCell(8).setCellStyle(model.Data_Style);
                        sheet.getRow(startRow).getCell(9).setCellStyle(model.Data_Style);
                        sheet.getRow(startRow).getCell(9).setCellValue(Double.parseDouble(String.format("%.6f", (double) bean.osBins / totalDies)));
                        if (bean.rpProcess.equals("RP1")) {
                            sheet.getRow(startRow).getCell(10).setCellStyle(model.Data_Style);
                            sheet.getRow(startRow).getCell(10).setCellFormula("ABS(I"+(1+gapStartindex)+"-I"+(2+gapStartindex)+")");
                            sheet.getRow(startRow).getCell(11).setCellStyle(model.Data_Style);
                            Integer grossDie = 0;
                            for (BysiteAndTestProcessInfors allPrimaryBean : TotalSummaryAllSiteSum) {
                                if (bean.waferId.equals(allPrimaryBean.waferId)) {
                                    for (Integer value : allPrimaryBean.binSummary.values()) {
                                        grossDie += value;
                                    }
                                }
                            }
                            sheet.getRow(startRow).getCell(11).setCellValue(Double.parseDouble(String.format("%.6f", (double) bean.passBins / grossDie)));
                            sheet.getRow(startRow).getCell(12).setCellStyle(model.Data_Style);
                            sheet.getRow(startRow).getCell(12).setCellFormula("G" + (startRow + 1) + "/F" + (startRow + 1));
                        } else {
                            sheet.getRow(startRow).getCell(10).setCellStyle(model.Data_Style);
                            sheet.getRow(startRow).getCell(10).setCellFormula("ABS(I"+(1+gapStartindex)+"-I"+(2+gapStartindex)+")");
                        }
                        int binStartindex = 13;
                        for (Integer bin : allBins) {
                            sheet.getRow(startRow).createCell(binStartindex).setCellStyle(model.Center_Style);
                            sheet.getRow(startRow).getCell(binStartindex).setCellValue(binSummary.containsKey(bin) ? binSummary.get(bin) : 0);
                            binStartindex++;
                        }
                        startRow++;
                    }
                }
            }
        }
        try {
            File directory=new File("/BysiteReportRelease/TestReportRelease/"+information[0]+"/"+information[1]+"/"+information[2]+"/"+information[3]);
            File directory1=new File("/BysiteReportRelease/MailReportRelease/"+information[0]+"/"+information[1]+"/"+information[2]+"/"+information[3]);
            File directory2=new File("/BysiteReport/"+information[0]+"/"+information[1]+"/"+information[2]+"/"+information[3]);
            if (directory.exists())
            {
                directory.mkdirs();
            }
            if (directory1.exists())
            {
                directory1.mkdirs();
            }
            if (directory2.exists())
            {
                directory2.mkdirs();
            }
            File srcFile=new File("/TempBySiteReport/"+information[2]+ "_BySiteSummaryReport.xlsx");
            workbook.write(new FileOutputStream(srcFile));
            FileUtils.copyFile(srcFile,new File(directory.getPath()+"/"+srcFile.getName()));
            FileUtils.copyFile(srcFile,new File(directory1.getPath()+"/"+srcFile.getName()));
            FileUtils.copyFile(srcFile,new File(directory2.getPath()+"/"+srcFile.getName()));
            FileUtils.forceDelete(srcFile);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BysiteAndTestProcessInfors getBean(ArrayList<BysiteAndTestProcessInfors> primarySummary, String waferId, Integer siteId, String type) {
        for (BysiteAndTestProcessInfors bean : primarySummary) {
            if (bean.waferId.equals(waferId) && bean.siteNo.equals(siteId.toString()) && bean.rpProcess.equals(type)) {
                return bean;
            }
        }
        return null;
    }

    private class BysiteAndTestProcessInfors implements Serializable {
        private String waferId;
        private String rpProcess;
        private String siteNo;
        private Integer passBins;
        private Integer osBins;
        private TreeMap<Integer, Integer> binSummary;

        public String getWaferId() {
            return waferId;
        }

        public void setWaferId(String waferId) {
            this.waferId = waferId;
        }

        public String getRpProcess() {
            return rpProcess;
        }

        public void setRpProcess(String rpProcess) {
            this.rpProcess = rpProcess;
        }

        public String getSiteNo() {
            return siteNo;
        }

        public void setSiteNo(String siteNo) {
            this.siteNo = siteNo;
        }

        public Integer getPassBins() {
            return passBins;
        }

        public void setPassBins(Integer passBins) {
            this.passBins = passBins;
        }

        public Integer getOsBins() {
            return osBins;
        }

        public void setOsBins(Integer osBins) {
            this.osBins = osBins;
        }

        public TreeMap<Integer, Integer> getBinSummary() {
            return binSummary;
        }

        public void setBinSummary(TreeMap<Integer, Integer> binSummary) {
            this.binSummary = binSummary;
        }
    }
}
