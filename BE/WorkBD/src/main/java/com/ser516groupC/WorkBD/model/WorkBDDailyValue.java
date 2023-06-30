package com.ser516groupC.WorkBD.model;


public class WorkBDDailyValue {
   
    public String date;
    public double ideal_value;
    public double actual_value;
    
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public double getIdeal_value() {
        return ideal_value;
    }
    public void setIdeal_value(double ideal_value) {
        this.ideal_value = ideal_value;
    }
    public double getActual_value() {
        return actual_value;
    }
    public void setActual_value(double actual_value) {
        this.actual_value = actual_value;
    }
    

    
}