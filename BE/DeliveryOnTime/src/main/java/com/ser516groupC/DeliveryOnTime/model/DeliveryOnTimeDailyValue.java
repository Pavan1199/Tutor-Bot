package com.ser516groupC.DeliveryOnTime.model;


public class DeliveryOnTimeDailyValue {
	   
    public String date;
    public double runningBVSum;
    public double bvDone;

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
    public double getrunningBVSum() {
        return runningBVSum;
    }
    public void setrunningBVSum(double runningBVSum) {
        this.runningBVSum = runningBVSum;
    }
    

    
}
