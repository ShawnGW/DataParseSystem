package com.vtest.it.dao.probermapperdao;

import com.vtest.it.pojo.binwaferinfors.BinWaferInforBean;
import com.vtest.it.pojo.binwaferinfors.FailDieBean;
import com.vtest.it.pojo.equipment.EquipmentBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public interface ProberDataDAO {
    public int insertFailDieToBinInfo(@Param("customerCode")String customerCode, @Param("device") String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId, @Param("failDieList")ArrayList<FailDieBean> failDieArray);
    public int insertSiteInforToBinInfoSummary(@Param("customerCode")String customerCode, @Param("device") String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId, @Param("siteMap")HashMap<Integer,HashMap<Integer,Integer>> siteMap, @Param("testType")String testType,@Param("passBins") ArrayList<Integer> passBins);
    public int deleteSiteInforToBinInfoSummary(@Param("customerCode")String customerCode, @Param("device") String device, @Param("lot")String lot, @Param("cp")String cp, @Param("waferId")String waferId);
    public int insertEquipmentInforToeqCardSummary(EquipmentBean equipmentBean);
    public int insertWaferInforToBinWaferSummary(BinWaferInforBean binWaferInforBean);
}
