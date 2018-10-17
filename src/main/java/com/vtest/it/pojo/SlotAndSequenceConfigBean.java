package com.vtest.it.pojo;

public class SlotAndSequenceConfigBean {
    private String readType;
    private String sequence;
    private String gpibBin;
    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getGpibBin() {
        return gpibBin;
    }

    public void setGpibBin(String gpibBin) {
        this.gpibBin = gpibBin;
    }
}
