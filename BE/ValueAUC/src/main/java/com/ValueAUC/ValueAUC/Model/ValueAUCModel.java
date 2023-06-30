package com.ValueAUC.ValueAUC.Model;
import java.util.List;

public class ValueAUCModel {
	private double totalBV, idealDailyBV, valueAUC;
    private List<ValueAUCDailyValue> dailyValuesList;

    public double getTotalBV() {
        return totalBV;
    }

    public void setTotalBV(double totalBV) {
        this.totalBV = totalBV;
    }

    public double getValueAUC() {
		return valueAUC;
	}

	public void setValueAUC(double valueAUC) {
		this.valueAUC = valueAUC;
	}

	public List<ValueAUCDailyValue> getDailyValuesList() {
		return dailyValuesList;
	}

	public void setDailyValuesList(List<ValueAUCDailyValue> dailyValuesList) {
		this.dailyValuesList = dailyValuesList;
	}

	public double getIdealDailyBV() {
        return idealDailyBV;
    }

    public void setIdealDailyBV(double idealDailyBV) {
        this.idealDailyBV = idealDailyBV;
    }

}
