package com.vtest.it.dao.vtmesdao;

import com.vtest.it.pojo.testerLocationBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface VtMesTesterDAO {
    public ArrayList<testerLocationBean> getTesterLocation();

}
