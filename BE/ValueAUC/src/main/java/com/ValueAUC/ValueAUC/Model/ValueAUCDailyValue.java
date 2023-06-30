package com.ValueAUC.ValueAUC.Model;

public class ValueAUCDailyValue {
	public String date;
    public double bvDone;
    public double bvIdeal;

    public double getBvDone() {
        return bvDone;
    }

    public void setBvDone(double bvDone) {
        this.bvDone = bvDone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public double getBvIdeal(){
        return bvIdeal;
    }
    public void setBvIdeal(double bvIdeal){
        this.bvIdeal=bvIdeal;
    }
}
