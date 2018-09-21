package com.vtest.it.dao;

import com.vtest.it.pojo.MesItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesConfigDAO {
    public List<MesItem> getItems();
}
