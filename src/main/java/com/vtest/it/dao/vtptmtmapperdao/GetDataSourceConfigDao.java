package com.vtest.it.dao.vtptmtmapperdao;

import com.vtest.it.pojo.DataSourceBean;
import com.vtest.it.pojo.datainfortomes.DataInforToMesBean;
import com.vtest.it.pojo.propertiescheckItemBean.CheckItemBean;
import com.vtest.it.pojo.propertiescheckItemBean.DataParseIssueBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GetDataSourceConfigDao {
    public DataSourceBean getConfig(@Param("platform") String platform);
    public ArrayList<DataInforToMesBean> getList();
    public ArrayList<CheckItemBean> getCheckItemList();
    public int dataErrorsRecord(@Param("list")ArrayList<DataParseIssueBean> list);
}