package com.pbcoupling.PBCoupling.models;

import java.io.Serializable;

public class CouplingCV implements Serializable {

    private int id;
    private String url;

    private String dependsOn;

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStoryRefNumber() {
        if(this.url == null) return -1;
        return Integer.valueOf(this.url.split("/us/")[1].split("\\\\?")[0]);
    }
}
