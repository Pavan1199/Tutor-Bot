package com.ser516groupC.BDConsistency.model;

import java.util.List;

public class BDCModel {
    private double totalBV, totalSP, idealDailyBV, idealDailySP, burndownConsistency;
    private List<BDCDailyValue> dailyValuesList;

    public double getTotalBV() {
        return totalBV;
    }

    public void setTotalBV(double totalBV) {
        this.totalBV = totalBV;
    }

    public double getTotalSP() {
        return totalSP;
    }

    public void setTotalSP(double totalSP) {
        this.totalSP = totalSP;
    }

    public double getIdealDailyBV() {
        return idealDailyBV;
    }

    public void setIdealDailyBV(double idealDailyBV) {
        this.idealDailyBV = idealDailyBV;
    }

    public double getIdealDailySP() {
        return idealDailySP;
    }

    public void setIdealDailySP(double idealDailySP) {
        this.idealDailySP = idealDailySP;
    }

    public double getBurndownConsistency() {
        return burndownConsistency;
    }

    public void setBurndownConsistency(double burndownConsistency) {
        this.burndownConsistency = burndownConsistency;
    }

    public List<BDCDailyValue> getDailyValuesList() {
        return dailyValuesList;
    }

    public void setDailyValuesList(List<BDCDailyValue> dailyValuesList) {
        this.dailyValuesList = dailyValuesList;
    }
}
