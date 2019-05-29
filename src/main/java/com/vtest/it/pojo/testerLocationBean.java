package com.vtest.it.pojo;

import java.io.Serializable;

public class testerLocationBean implements Serializable {
    private String name;
    private Integer x;
    private Integer y;
    private String location;
    private String EQtype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEQtype() {
        return EQtype;
    }

    public void setEQtype(String EQtype) {
        this.EQtype = EQtype;
    }
}
