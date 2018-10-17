package com.vtest.it.dao.systemdao;

import com.vtest.it.pojo.ComputerBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface VtComputerDAO {
    public ArrayList<ComputerBean> getComputers();
    public int updateComputer(@Param("computers") ArrayList<ComputerBean> computers);
}
