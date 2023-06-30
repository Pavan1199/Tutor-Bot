package com.ser516groupC.WorkBD.model;

import java.util.List;

public class WorkBDModel {
    private double  total_storypoints;
    private List<WorkBDDailyValue> daily_values;
    
    public double getTotal_storypoints() {
        return total_storypoints;
    }
    public void setTotal_storypoints(double total_storypoints) {
        this.total_storypoints = total_storypoints;
    }
    public List<WorkBDDailyValue> getDaily_values() {
        return daily_values;
    }
    public void setDaily_values(List<WorkBDDailyValue> daily_values) {
        this.daily_values = daily_values;
    }

}
