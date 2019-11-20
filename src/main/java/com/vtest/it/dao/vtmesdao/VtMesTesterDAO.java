package com.vtest.it.dao.vtmesdao;

import com.vtest.it.pojo.testerLocationBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface VtMesTesterDAO {
    public ArrayList<testerLocationBean> getTesterLocation();
    public String getPassBin(@Param("waferId")String waferId);
    public void sendWaferMail(@Param("productId") String productId, @Param("lotId")String lotId, @Param("waferNo")String waferNo, @Param("testerId")String testerId);
}
