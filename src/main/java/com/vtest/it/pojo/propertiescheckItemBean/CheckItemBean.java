package com.vtest.it.pojo.propertiescheckItemBean;

import java.io.Serializable;

public class CheckItemBean implements Serializable {
    private String property;
    private boolean checkIsNa;
    private String equalsItem;
    private String customCode;
    private int level;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isCheckIsNa() {
        return checkIsNa;
    }

    public void setCheckIsNa(boolean checkIsNa) {
        this.checkIsNa = checkIsNa;
    }

    public String getEqualsItem() {
        return equalsItem;
    }

    public void setEqualsItem(String equalsItem) {
        this.equalsItem = equalsItem;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
