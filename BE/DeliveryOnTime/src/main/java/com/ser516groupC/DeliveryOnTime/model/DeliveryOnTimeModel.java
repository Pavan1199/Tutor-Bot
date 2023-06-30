package com.ser516groupC.DeliveryOnTime.model;

import java.util.List;

import com.ser516groupC.DeliveryOnTime.model.DeliveryOnTimeDailyValue;

public class DeliveryOnTimeModel {
    private double deliveryOnTime;
    private List<DeliveryOnTimeDailyValue> daily_values;
    
    public double getdeliveryOnTime() {
        return deliveryOnTime;
    }
    public void setdeliveryOnTime(double deliveryOnTime) {
        this.deliveryOnTime = deliveryOnTime;
    }
    public List<DeliveryOnTimeDailyValue> getDaily_values() {
        return daily_values;
    }
    public void setDaily_values(List<DeliveryOnTimeDailyValue> daily_values) {
        this.daily_values = daily_values;
    }

}