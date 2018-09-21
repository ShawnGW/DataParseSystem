package com.vtest.it.pojo;

public class MesItem {
    private String item;
    private String mesItem;
    private String mesItemReplace;
    private boolean byCp;
    private boolean multiCp;
    private String defaultValue;
    private int inforLines;
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMesItem() {
        return mesItem;
    }

    public void setMesItem(String mesItem) {
        this.mesItem = mesItem;
    }

    public String getMesItemReplace() {
        return mesItemReplace;
    }

    public void setMesItemReplace(String mesItemReplace) {
        this.mesItemReplace = mesItemReplace;
    }

    public boolean isByCp() {
        return byCp;
    }

    public void setByCp(boolean byCp) {
        this.byCp = byCp;
    }

    public boolean isMultiCp() {
        return multiCp;
    }

    public void setMultiCp(boolean multiCp) {
        this.multiCp = multiCp;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getInforLines() {
        return inforLines;
    }

    public void setInforLines(int inforLines) {
        this.inforLines = inforLines;
    }
}
