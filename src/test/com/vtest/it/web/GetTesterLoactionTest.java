package com.vtest.it.web;


import com.vtest.it.dao.vtmesdao.VtMesTesterDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:sqlServerDataBaseConfig.xml"})
public class GetTesterLoactionTest{
    @Autowired
    private VtMesTesterDAO vtMesTesterDAO;

    @Test
    public void test() {
        System.out.println(vtMesTesterDAO.getTesterLocation());
    }
}