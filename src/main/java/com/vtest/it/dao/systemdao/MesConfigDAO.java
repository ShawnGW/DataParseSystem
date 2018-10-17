package com.vtest.it.dao.systemdao;

import com.vtest.it.pojo.MesItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesConfigDAO {
    public List<MesItem> getItems();
}
