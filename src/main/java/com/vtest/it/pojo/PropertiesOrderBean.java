package com.vtest.it.pojo;

import java.io.Serializable;

public class PropertiesOrderBean implements Serializable {
    private Integer propertyOrder;
    private String  property;

    public Integer getPropertyOrder() {
        return propertyOrder;
    }

    public void setPropertyOrder(Integer propertyOrder) {
        this.propertyOrder = propertyOrder;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
