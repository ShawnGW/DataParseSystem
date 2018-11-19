package com.vtest.it.dao.vtptmtmapperdao;

import com.vtest.it.pojo.systemyuser.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserValidate {
    public int  register(SystemUser user);
    public int  cancel(@Param("userName") String userName);
    public SystemUser validate(@Param("userName") String userName);
}
