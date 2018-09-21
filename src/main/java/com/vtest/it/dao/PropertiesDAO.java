package com.vtest.it.dao;

import com.vtest.it.pojo.PropertiesOrderBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface PropertiesDAO {
    public LinkedList<PropertiesOrderBean> getOrder();
    public int updateConfig(@Param("list") LinkedList<PropertiesOrderBean> list);
    public int clearAllConfig();
}
