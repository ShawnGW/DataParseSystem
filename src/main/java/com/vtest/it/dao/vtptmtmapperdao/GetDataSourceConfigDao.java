package com.vtest.it.dao.vtptmtmapperdao;

import com.vtest.it.pojo.DataSourceBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetDataSourceConfigDao {
    public DataSourceBean getConfig(@Param("platform") String platform);
}
