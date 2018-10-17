package com.vtest.it.rawdataBean;

import com.vtest.it.rawdatainformationBean.RawdataInitBean;

import java.io.File;

public interface rawdataInitParse {
    public RawdataInitBean parse(File[] mapping, int gpibBin);
}
