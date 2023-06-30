package com.ser516groupC.DeliveryOnTime.services;

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

import com.ser516groupC.DeliveryOnTime.model.DeliveryOnTimeDailyValue;
import com.ser516groupC.DeliveryOnTime.model.DeliveryOnTimeModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class DeliveryOnTimeServices {
    private String projName;
    private String sprintName;

    @Autowired
    TaigaRequests taigaReq;

    DeliveryOnTimeModel DeliveryOnTimeData = new DeliveryOnTimeModel();

    private double getBV(String bvString)
    {  
        System.out.println("BV String value is " + bvString);
        double retValue = 0.0;

        if(bvString.equalsIgnoreCase("x-small")) retValue = 1.0;
        else if(bvString.equalsIgnoreCase("small")) retValue = 2.0;
        else if(bvString.equalsIgnoreCase("medium")) retValue = 3.0;
        else if(bvString.equalsIgnoreCase("large")) retValue = 4.0;
        else if(bvString.equalsIgnoreCase("x-large")) retValue = 5.0;
        else if(bvString.equalsIgnoreCase("xx-large")) retValue = 6.0;

        return retValue;

    }
    
    public void fetchDeliveryOnTimeValues() throws JSONException
    {
        // Get Milestone stats and userstory details from Taiga MS
        JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
        JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
        
        String startDateStr = milestoneStatsJSON.getString("estimated_start");
        String endDateStr = milestoneStatsJSON.getString("estimated_finish");
        System.out.println(startDateStr+"--"+endDateStr);
        
        JSONArray day=milestoneStatsJSON.getJSONArray("days");
        //Store business value completed per date
        HashMap<String, Double> dateBVMap = new HashMap<>();
        
        double totalBV = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(int i =0; i<userStoryDetailArray.length(); i++)
        {
            //Get business value from json
            JSONObject userStoryDetail = userStoryDetailArray.getJSONObject(i);
            double bzValue = getBV(userStoryDetail.getString("businessValue"));
            totalBV += bzValue;

            //The date got is in Date+Time format. Convert to only date.
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
            
            //If the date is not in the hashmap insert into it with newly found storypoints
            if(dateBVMap.get(finishedDate) == null)
            {
                dateBVMap.put(finishedDate, bzValue);
            }
            else //If date already found, add the story points to existing points
            {
            	dateBVMap.put(finishedDate, dateBVMap.get(finishedDate)+ bzValue);
            }
        }
        
        System.out.println("------------HASH MAP ---------------");
        System.out.println(Arrays.asList(dateBVMap));


        Calendar cDate = Calendar.getInstance();
        Calendar cEndDate = Calendar.getInstance();

        try
        {
            cDate.setTime(sdf.parse(startDateStr));
            cEndDate.setTime(sdf.parse(endDateStr));
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        
        List<DeliveryOnTimeDailyValue> dVList = new ArrayList<>();
        double runningBVSum = 0.0;
        double deliveryOnTime = 0.0;
        int days=0;
        while(cDate.compareTo(cEndDate) <=0)
        {
        	DeliveryOnTimeDailyValue dailyValue = new DeliveryOnTimeDailyValue();
            
        	String dayStr = sdf.format(cDate.getTime());
            JSONObject idealdate= day.getJSONObject(days);
            
            double openpoints=Float.parseFloat(idealdate.getString("open_points"));
            
            dailyValue.setBvDone(openpoints);
            //Add daily story points to running sum
            if(dateBVMap.get(dayStr) != null) runningBVSum += dateBVMap.get(dayStr);

            //Add daily ideal sp to ideal running sum
            //runningIdealBV += dailyIdealWork;
            deliveryOnTime += runningBVSum/totalBV;
            dailyValue.setDate(dayStr);
            days++;
            //dailyValue.setIdeal_value(runningIdealBV);
            dailyValue.setrunningBVSum(runningBVSum);

            dVList.add(dailyValue);

            cDate.add(Calendar.DAY_OF_MONTH, 1); 
            
        }     
        
        
        DeliveryOnTimeData.setdeliveryOnTime(deliveryOnTime);
        DeliveryOnTimeData.setDaily_values(dVList);
        
        //Work normalized -- 
    }
    public DeliveryOnTimeModel fetchDeliveryOnTimeResponse(String proj_name, String sprint_name) {

        this.projName = proj_name;
        this.sprintName = sprint_name;

        try {
        	fetchDeliveryOnTimeValues();
        } catch (JSONException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        
        return DeliveryOnTimeData;
    }


}
