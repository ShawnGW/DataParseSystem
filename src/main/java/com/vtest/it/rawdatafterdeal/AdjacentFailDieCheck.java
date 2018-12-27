package com.vtest.it.rawdatafterdeal;

import com.vtest.it.adjacentFailDieCheck.AdjacentFailDieCheckTool;
import com.vtest.it.adjacentFailDieCheck.FailTypeCheck;
import com.vtest.it.dao.vtptmtmapperdao.GetDataSourceConfigDao;
import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.rawdatainformationBean.RawdataInitBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

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
            ArrayList<String> passBins = new ArrayList<>();
            for (String bin : properties.get("Pass Bins").split(",")) {
                passBins.add(bin);
            }
            Integer TestDieRow = Integer.valueOf(properties.get("TestDiedown")) - Integer.valueOf(properties.get("TestDieup")) + 1;
            Integer TestDieCol = Integer.valueOf(properties.get("TestDieright")) - Integer.valueOf(properties.get("TestDieleft")) + 1;
            String[][] testDie = new String[TestDieRow][TestDieCol];
            Integer up = Integer.valueOf(properties.get("TestDieup"));
            Integer left = Integer.valueOf(properties.get("TestDieleft"));
            HashMap<String, String> testDieMap = rawdataInitBean.getTestDieMap();
            Set<String> coordinateSet = testDieMap.keySet();
            for (String coordinate : coordinateSet) {
                Integer x = Integer.valueOf(coordinate.substring(0, 4).trim()) - up;
                Integer y = Integer.valueOf(coordinate.substring(4, 8).trim()) - left;
                String value = testDieMap.get(coordinate).substring(4, 8).trim();
                testDie[x][y] = value;
            }
            ArrayList<Set<String>> failDieChains = adjacentFailDieCheckTool.check(testDie, TestDieRow, TestDieCol, passBins);
            HashMap<String, Boolean> checkResult = new HashMap<>();
            for (Set<String> node : failDieChains) {
                if (!checkResult.values().contains(false)) {
                    break;
                }
                if (node.size() >= 5) {
                    boolean eightAdjacentFailDieCheckFlag = failTypeCheck.eightAdjacentFailDieCheck(node);
                    boolean fourAdjacentFailDieCheckFlag = failTypeCheck.fourAdjacentFailDieCheck(node);
                    boolean xDirectionAdjacentFailDieCheckFlag = failTypeCheck.xDirectionAdjacentFailDieCheck(node);
                    boolean yDirectionAdjacentFailDieCheckFlag = failTypeCheck.yDirectionAdjacentFailDieCheck(node);
                    checkResult.put("8 Neighborhood Fail", eightAdjacentFailDieCheckFlag);
                    checkResult.put("4 Neighborhood Fail", fourAdjacentFailDieCheckFlag);
                    checkResult.put("Y_Stretch Fail", xDirectionAdjacentFailDieCheckFlag);
                    checkResult.put("X_Stretch Fail", yDirectionAdjacentFailDieCheckFlag);
                }
            }
            BinWaferInforBean binWaferInforBean=new BinWaferInforBean();
            generateWaferInforBean.generate(rawdataInitBean,binWaferInforBean);
            StringBuilder stringBuilder=new StringBuilder();
            for (String description:checkResult.keySet()) {
                if (checkResult.get(description))
                {
                    stringBuilder.append(description+";");
                }
            }
            binWaferInforBean.setCheckResult(stringBuilder.toString());
            getDataSourceConfigDao.insertWaferInforToBinWaferSummary(binWaferInforBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
