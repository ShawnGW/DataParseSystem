package com.vtest.it.vtestDatalogDeal;

import com.vtest.it.testerDatalogParse.ChromaDatalogParse;
import com.vtest.it.testerDatalogParse.M7000DatalogParse;
import com.vtest.it.testerDatalogParse.V50DatalogParse;
import com.vtest.it.tools.RemoveBracket;
import com.vtest.it.tools.RemoveDoubleSpace;
import com.vtest.it.tools.TimeCheck;
import com.vtest.it.vtestinterface.DatalogParse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.HashMap;

public class StartDeal {
    private final  File V50DataSource=new File("/server212/Datalog/TempData/V50");
    private final  File M7000DataSource=new File("/server212/Datalog/TempData/M7000");
    private final  File T862DataSource=new File("/server212/Datalog/TempData/T862");
    private final  File ChromaDataSource=new File("/server212/Datalog/TempData/Chroma");
    private V50DatalogParse v50DatalogParse;
    private M7000DatalogParse m7000DatalogParse;
    private ChromaDatalogParse chromaDatalogParse;
    private DatalogDealParent datalogDealParent;
    private RemoveDoubleSpace removeDoubleSpace;
    private TimeCheck timeCheck;
    private TskPlatformMappingDeal tskPlatformMappingDeal;
    private TelPlatformMappingDeal telPlatformMappingDeal;
    private StdfPlatformMappingDeal stdfPlatformMappingDeal;

    @Autowired
    public void setStdfPlatformMappingDeal(StdfPlatformMappingDeal stdfPlatformMappingDeal) {
        this.stdfPlatformMappingDeal = stdfPlatformMappingDeal;
    }

    @Autowired
    public void setTelPlatformMappingDeal(TelPlatformMappingDeal telPlatformMappingDeal) {
        this.telPlatformMappingDeal = telPlatformMappingDeal;
    }

    @Autowired
    public void setTimeCheck(TimeCheck timeCheck) {
        this.timeCheck = timeCheck;
    }

    @Autowired
    public void setChromaDatalogParse(ChromaDatalogParse chromaDatalogParse) {
        this.chromaDatalogParse = chromaDatalogParse;
    }

    @Autowired
    public void setRemoveDoubleSpace(RemoveDoubleSpace removeDoubleSpace) {
        this.removeDoubleSpace = removeDoubleSpace;
    }

    @Autowired
    public void setV50DatalogParse(V50DatalogParse v50DatalogParse) {
        this.v50DatalogParse = v50DatalogParse;
    }
    @Autowired
    public void setDatalogDealParent(DatalogDealParent datalogDealParent) {
        this.datalogDealParent = datalogDealParent;
    }
    @Autowired
    public void setM7000DatalogParse(M7000DatalogParse m7000DatalogParse) {
        this.m7000DatalogParse = m7000DatalogParse;
    }
    @Autowired
    public void setTskPlatformMappingDeal(TskPlatformMappingDeal tskPlatformMappingDeal) {
        this.tskPlatformMappingDeal = tskPlatformMappingDeal;
    }
    public void tskPlatformDeal(){
        tskPlatformMappingDeal.deal();
    }
    public void telPlatformDeal()
    {
        telPlatformMappingDeal.deal();
    }
    public void stdfPlatformDeal()
    {
        stdfPlatformMappingDeal.deal();
    }
    public void v50Deal()
    {
        Deal(V50DataSource,v50DatalogParse,false);
    }
    public void m7000Deal()
    {
        Deal(M7000DataSource,m7000DatalogParse,true);
    }
    public void T862Deal()
    {
        Deal(T862DataSource,v50DatalogParse,true);
    }
    public void ChromaDeal()
    {
        Deal(ChromaDataSource,chromaDatalogParse,true);
    }
    public void Deal(File dataSource, DatalogParse parse,boolean dealStdf)
    {
        try {
            File[] files=dataSource.listFiles();
            for (int i = 0; i < files.length; i++) {
                try {
                    String fileName = RemoveBracket.remove(removeDoubleSpace.remove(files[i].getName()));
                    boolean dateCheckFlag=timeCheck.check(files[i],60);
                    if (dateCheckFlag)
                    {
                        if (fileName.startsWith("VTEST"))
                        {
                            HashMap<String,String> nameMap=parse.parseName(fileName);
                            datalogDealParent.datalogBackup(files[i],nameMap,dealStdf);
                        }else
                        {
                            files[i].delete();
                        }
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
