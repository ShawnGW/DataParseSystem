package com.vtest.it.rawdataBean;

import java.io.File;

public interface rawdataInitParse {
    public RawdataInitBean parse(File[] mapping, int gpibBin);
}
