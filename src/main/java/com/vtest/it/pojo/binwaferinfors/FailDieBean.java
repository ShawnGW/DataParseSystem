package com.vtest.it.pojo.binwaferinfors;

import java.io.Serializable;

public class FailDieBean implements Serializable {
    private Integer xCoordinate;
    private Integer yCoordinate;
    private Integer binNumber;
    private Integer siteId;

    public Integer getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Integer xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Integer getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Integer yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public Integer getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(Integer binNumber) {
        this.binNumber = binNumber;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }
}
