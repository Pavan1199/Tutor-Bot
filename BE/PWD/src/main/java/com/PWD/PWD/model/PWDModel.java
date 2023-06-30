package com.PWD.PWD.model;

import java.util.List;

public class PWDModel {
    private double totalSP;
    private double idealDailySP;
    private double pWD;
    private List<PWDDailyValue> dailyValuesList;

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


    public List<PWDDailyValue> getDailyValuesList() {
        return dailyValuesList;
    }

    public void setDailyValuesList(List<PWDDailyValue> dailyValuesList) {
        this.dailyValuesList = dailyValuesList;
    }


}
