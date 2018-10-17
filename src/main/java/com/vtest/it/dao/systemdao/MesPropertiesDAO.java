package com.vtest.it.dao.systemdao;

import com.vtest.it.pojo.MesProperties;
import org.springframework.stereotype.Repository;

@Repository
public interface MesPropertiesDAO {
    public MesProperties getProperties();
    public int updateProperties(MesProperties mesProperties);
}
