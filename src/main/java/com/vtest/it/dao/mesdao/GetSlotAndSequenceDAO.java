package com.vtest.it.dao.mesdao;

import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GetSlotAndSequenceDAO {
    public SlotAndSequenceConfigBean getConfig(@Param("lot") String lot);
}
