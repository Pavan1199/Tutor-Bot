package com.ValueAUC.ValueAUC.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ValueAUC.ValueAUC.Model.ValueAUCDailyValue;
import com.ValueAUC.ValueAUC.Model.ValueAUCModel;
import com.ValueAUC.ValueAUC.Model.AUCModel;
@Service
public class ValueAUCService {
    private String projName;
    private String sprintName;

    @Autowired
    TaigaRequests taigaReq;

    ValueAUCModel ValueAUC = new ValueAUCModel();

    private double getValueAUC(String valueAUC)
    {  
        System.out.println("Value AUC String value is " + valueAUC);
        double retValue = 0.0;

        if(valueAUC.equalsIgnoreCase("x-small")) retValue = 1.0;
        else if(valueAUC.equalsIgnoreCase("small")) retValue = 2.0;
        else if(valueAUC.equalsIgnoreCase("medium")) retValue = 3.0;
        else if(valueAUC.equalsIgnoreCase("large")) retValue = 4.0;
        else if(valueAUC.equalsIgnoreCase("x-large")) retValue = 5.0;
        else if(valueAUC.equalsIgnoreCase("xx-large")) retValue = 6.0;

        return retValue;

    }
    
    public void fetchWorkValues() throws JSONException, ParseException
    {
        JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
        JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
        
        String startDateStr = milestoneStatsJSON.getString("estimated_start");
        String endDateStr = milestoneStatsJSON.getString("estimated_finish");
        System.out.println(startDateStr+"--"+endDateStr);
        
        JSONArray day=milestoneStatsJSON.getJSONArray("days");
        
        HashMap<String, Double> dateBVMap= new HashMap<>();
        
        double totalBV = 0;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate=sdf.parse(milestoneStatsJSON.getString("estimated_start"));
        Date endddate=sdf.parse(milestoneStatsJSON.getString("estimated_finish"));
        long diffInMillies = Math.abs(endddate.getTime() - startdate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        double daydiff=(double)diff;
                System.out.println(daydiff);

        for(int i =0; i<userStoryDetailArray.length(); i++)
        {
            JSONObject userStoryDetail = userStoryDetailArray.getJSONObject(i);
            double bzValue = getValueAUC(userStoryDetail.getString("businessValue"));
            totalBV += bzValue;

            SimpleDateFormat sdfzulu = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            sdfzulu.setTimeZone(TimeZone.getTimeZone("UTC"));

            String finishedDateTime = userStoryDetail.getString("finishDate");
            String finishedDate = "";
            try
            {
                Date zuluDate = sdfzulu.parse(finishedDateTime);
                finishedDate = sdf.format(zuluDate);
            }
            catch(ParseException e)
            {
                e.printStackTrace();
            }
            

            if(dateBVMap.get(finishedDate) == null)
            {
                dateBVMap.put(finishedDate, bzValue);
            }
            else
            {
                dateBVMap.put(finishedDate, dateBVMap.get(finishedDate)+ bzValue);
            }
        }
        System.out.println("------------HASH MAP ---------------");
        System.out.println(Arrays.asList(dateBVMap));
        double dailyidealBV=0;
        
        dailyidealBV=(totalBV/daydiff);
        System.out.println("daily ideal value=="+dailyidealBV);

        
        
        Calendar cDate = Calendar.getInstance();
        Calendar cEndDate = Calendar.getInstance();
        
        long daysDiff = Duration.between(cEndDate.toInstant(), cDate.toInstant()).toDays();

        try
        {
            cDate.setTime(sdf.parse(startDateStr));
            cEndDate.setTime(sdf.parse(endDateStr));
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        
        List<ValueAUCDailyValue> dVList = new ArrayList<>();
        //until cdate.compareTo(cEndDate)<=0
        int days=0;
        double valueAUC=0;
        while(cDate.compareTo(cEndDate) <=0)
        {
            ValueAUCDailyValue dailyValue = new ValueAUCDailyValue();
            String dayStr = sdf.format(cDate.getTime());
            JSONObject idealdate= day.getJSONObject(days);
            double idealBVValue=Float.parseFloat(idealdate.getString("optimal_points"));
            double openpoints=Float.parseFloat(idealdate.getString("open_points"));

            if (idealBVValue<=openpoints){
            	valueAUC+=(openpoints-idealBVValue);
            }
            else if(openpoints<=idealBVValue){
            	valueAUC+=(idealBVValue-openpoints);
            }


            dailyValue.setDate(dayStr);
            dailyValue.setBvDone(openpoints);
            dailyValue.setBvIdeal(idealBVValue);

            dVList.add(dailyValue);
            System.out.println("cvList ======"+dVList);
            days++;
            cDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println("AUC Calculated===="+ValueAUC);

        ValueAUC.setTotalBV(totalBV);
        ValueAUC.setIdealDailyBV(dailyidealBV);
        ValueAUC.setValueAUC(valueAUC);
        ValueAUC.setDailyValuesList(dVList);
        
        //Work normalized -- 
    }
    public ValueAUCModel fetchAUCResponse(String proj_name, String sprint_name) throws ParseException {

        this.projName = proj_name;
        this.sprintName = sprint_name;

        try {
            fetchWorkValues();
        } catch (JSONException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        
        return ValueAUC;
    }
}
