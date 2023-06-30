package com.AUC.AUC.model;

import java.util.List;

public class AUCModel {
    private double totalSP;
    private double idealDailySP;
    private double workAUC;
    private List<AUCDailyValue> dailyValuesList;

    public double getIdealDailySP() {
        return idealDailySP;
    }

    public void setIdealDailySP(double idealDailySP) {
        this.idealDailySP = idealDailySP;
    }


    public double getTotalSP() {
        return totalSP;
    }

    public void setTotalSP(double totalSP) {
        this.totalSP = totalSP;
    }


    public List<AUCDailyValue> getDailyValuesList() {
        return dailyValuesList;
    }

    public void setDailyValuesList(List<AUCDailyValue> dailyValuesList) {
        this.dailyValuesList = dailyValuesList;
    }

    public double getworkAUC(){
        return workAUC;
    }
    public void setworkAUC(double workAUC){
        this.workAUC=workAUC;
    }





}