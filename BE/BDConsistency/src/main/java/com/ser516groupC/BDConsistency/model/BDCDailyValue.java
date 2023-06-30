package com.ser516groupC.BDConsistency.model;

public class BDCDailyValue {
   
    public String date;
    public double spDone;
    public double bvDone;

    public double getBvDone() {
        return bvDone;
    }

    public void setBvDone(double bvDone) {
        this.bvDone = bvDone;
    }

    public double getSpDone() {
        return spDone;
    }

    public void setSpDone(double spDone) {
        this.spDone = spDone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}