package com.vtest.it.dao.vtmesdao;

import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public interface VtSiteYieldToMes {
    public void siteYieldToMes(HashMap<String,String> SiteInfor);
}
