package com.vtest.it.dao.mesdao;

import com.vtest.it.pojo.MesConfigBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetMesConfig {
    public MesConfigBean getBean(@Param("waferId") String waferId,@Param("cpProcess") String cpProcess);
}
