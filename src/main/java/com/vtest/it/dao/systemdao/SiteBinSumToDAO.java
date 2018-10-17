package com.vtest.it.dao.systemdao;

import com.vtest.it.pojo.FailDieBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public interface SiteBinSumToDAO {
    public void SiteBinINS(@Param("customerCode")String customerCode, @Param("device")String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId, @Param("siteMap")HashMap<Integer,HashMap<Integer,Integer>> siteMap, @Param("testType")String testType);
    public int deleteSiteInfor(@Param("customerCode")String customerCode, @Param("device")String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId);
    public int failDieInsert(@Param("customerCode")String customerCode, @Param("device") String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId, @Param("failDieList")ArrayList<FailDieBean> failDieArray);
    public int failDieDelete(@Param("customerCode")String customerCode, @Param("device") String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId);
}
