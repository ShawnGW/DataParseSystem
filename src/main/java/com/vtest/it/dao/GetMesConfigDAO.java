package com.vtest.it.dao;

import com.vtest.it.rawdataBean.RawdataPropertiesBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetMesConfigDAO {
    public RawdataPropertiesBean getConfig(@Param("waferId")String waferId,@Param("cp")String cp);
}
