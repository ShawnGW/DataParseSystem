package com.vtest.it.dao.vtmesdao;

import com.vtest.it.pojo.testerLocationBean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Repository
public interface VtMesTesterDAO {
    public ArrayList<testerLocationBean> getTesterLocation();
    public String getPassBin(@RequestParam("waferId")String waferId);
}
