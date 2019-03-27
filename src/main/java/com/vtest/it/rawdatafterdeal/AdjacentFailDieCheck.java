package com.vtest.it.rawdatafterdeal;

import com.vtest.it.adjacentFailDieCheck.AdjacentFailDieCheckTool;
import com.vtest.it.adjacentFailDieCheck.FailTypeCheck;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

public class AdjacentFailDieCheck extends AbstractRawDataAfterDeal {
    private AdjacentFailDieCheckTool adjacentFailDieCheckTool;
    private GenerateWaferInforBean generateWaferInforBean;
    private FailTypeCheck failTypeCheck;
    private GetDataSourceConfigDao getDataSourceConfigDao;

    @Autowired
    public void setAdjacentFailDieCheckTool(AdjacentFailDieCheckTool adjacentFailDieCheckTool) {
        this.adjacentFailDieCheckTool = adjacentFailDieCheckTool;
    }

    @Autowired
    public void setGenerateWaferInforBean(GenerateWaferInforBean generateWaferInforBean) {
        this.generateWaferInforBean = generateWaferInforBean;
    }

    @Autowired
    public void setFailTypeCheck(FailTypeCheck failTypeCheck) {
        this.failTypeCheck = failTypeCheck;
    }

    @Autowired
    public void setGetDataSourceConfigDao(GetDataSourceConfigDao getDataSourceConfigDao) {
        this.getDataSourceConfigDao = getDataSourceConfigDao;
    }

    @Override
    public void deal(RawdataInitBean rawdataInitBean) {
        try {
            LinkedHashMap<String, String> properties = rawdataInitBean.getProperties();
            String tester = properties.get("Tester ID");
            if (tester.equals("NA")) {
                return;
            }
            try {
                String endTime = properties.get("Test End Time").substring(0, 14);
                Date testEndTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(endTime);
                BinWaferInforBean dbOldTesterStatus = getDataSourceConfigDao.getTesterStatusSingle(tester);
                Date dbEendTime = dbOldTesterStatus.getEndTime();
                if (dbEendTime.getTime() > testEndTime.getTime()) {
                    return;
                }
            } catch (Exception e) {

            }
            ArrayList<String> passBins = new ArrayList<>();
            for (String bin : properties.get("Pass Bins").split(",")) {
                passBins.add(bin);
            }
            Integer TestDieRow = Integer.valueOf(properties.get("TestDieMaxY")) - Integer.valueOf(properties.get("TestDieMinY")) + 1;
            Integer TestDieCol = Integer.valueOf(properties.get("TestDieMaxX")) - Integer.valueOf(properties.get("TestDieMinX")) + 1;
            String[][] testDie = new String[TestDieRow][TestDieCol];
            Integer up = Integer.valueOf(properties.get("TestDieMinY"));
            Integer left = Integer.valueOf(properties.get("TestDieMinX"));
            HashMap<String, String> testDieMap = rawdataInitBean.getTestDieMap();
            Set<String> coordinateSet = testDieMap.keySet();
            for (String coordinate : coordinateSet) {
                Integer x = Integer.valueOf(coordinate.substring(0, 4).trim()) - left;
                Integer y = Integer.valueOf(coordinate.substring(4, 8).trim()) - up;
                String value = testDieMap.get(coordinate).substring(4, 8).trim();
                testDie[y][x] = value;
            }
            ArrayList<Set<String>> failDieChains = adjacentFailDieCheckTool.check(testDie, TestDieRow, TestDieCol, passBins);
            HashMap<String, Boolean> checkResult = new HashMap<>();
            for (Set<String> node : failDieChains) {
                if (checkResult.values().size() > 0 && !checkResult.values().contains(false)) {
                    break;
                }
                if (node.size() >= 5) {
                    if (node.size() >= 10) {
                        checkResult.put("10 interflow Fail", true);
                    }
                    if ((checkResult.containsKey("8 Neighborhood Fail") && !checkResult.get("8 Neighborhood Fail")) || !checkResult.containsKey("8 Neighborhood Fail")) {
                        boolean eightAdjacentFailDieCheckFlag = failTypeCheck.eightAdjacentFailDieCheck(node);
                        checkResult.put("8 Neighborhood Fail", eightAdjacentFailDieCheckFlag);
                    }
                    if ((checkResult.containsKey("4 Neighborhood Fail") && !checkResult.get("4 Neighborhood Fail")) || !checkResult.containsKey("4 Neighborhood Fail")) {
                        boolean fourAdjacentFailDieCheckFlag = failTypeCheck.fourAdjacentFailDieCheck(node);
                        checkResult.put("4 Neighborhood Fail", fourAdjacentFailDieCheckFlag);
                    }
                    if ((checkResult.containsKey("Y_Stretch Fail") && !checkResult.get("Y_Stretch Fail")) || !checkResult.containsKey("Y_Stretch Fail")) {
                        boolean xDirectionAdjacentFailDieCheckFlag = failTypeCheck.xDirectionAdjacentFailDieCheck(node);
                        checkResult.put("Y_Stretch Fail", xDirectionAdjacentFailDieCheckFlag);
                    }
                    if ((checkResult.containsKey("X_Stretch Fail") && !checkResult.get("X_Stretch Fail")) || !checkResult.containsKey("X_Stretch Fail")) {
                        boolean yDirectionAdjacentFailDieCheckFlag = failTypeCheck.yDirectionAdjacentFailDieCheck(node);
                        checkResult.put("X_Stretch Fail", yDirectionAdjacentFailDieCheckFlag);
                    }
                }
            }
            BinWaferInforBean binWaferInforBean = new BinWaferInforBean();
            generateWaferInforBean.generate(rawdataInitBean, binWaferInforBean);
            StringBuilder stringBuilder = new StringBuilder();
            for (String description : checkResult.keySet()) {
                if (checkResult.get(description)) {
                    stringBuilder.append(description + ";");
                }
            }
            if (stringBuilder.toString().length() == 0) {
                stringBuilder.append("normal");
            }
            binWaferInforBean.setCheckResult(stringBuilder.toString());
            getDataSourceConfigDao.insertWaferInforToBinWaferSummary(binWaferInforBean);
            getDataSourceConfigDao.waferFailTypeCheckOthers(properties.get("Wafer ID"), properties.get("CP Process"), tester);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
