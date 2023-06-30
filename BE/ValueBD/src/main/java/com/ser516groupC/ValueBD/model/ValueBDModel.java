package com.ser516groupC.ValueBD.model;

import java.util.List;

public class ValueBDModel {
    private double  totalBV;
    private List<ValueBDDailyValue> daily_values;
    
    public double getTotalBV() {
        return totalBV;
    }
    public void setTotalBV(double totalBV) {
        this.totalBV = totalBV;
    }
    public List<ValueBDDailyValue> getDaily_values() {
        return daily_values;
    }
    public void setDaily_values(List<ValueBDDailyValue> daily_values) {
        this.daily_values = daily_values;
    }

}
