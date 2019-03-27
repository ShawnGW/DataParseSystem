package com.vtest.it.dao.vtmesdao;

import com.vtest.it.pojo.LotInformationBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface VtSiteYieldToMes {
    public void siteYieldToMes(HashMap<String,String> SiteInfor);

    public LotInformationBean getLotInfor(@Param("lot") String lot);
}
