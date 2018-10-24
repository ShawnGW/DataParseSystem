package com.vtest.it.dao.vtmesdao;

import com.vtest.it.pojo.SlotAndSequenceConfigBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VtMesSlotAndSequenceDAO {
    public SlotAndSequenceConfigBean getConfig(@Param("lot") String lot);
}
