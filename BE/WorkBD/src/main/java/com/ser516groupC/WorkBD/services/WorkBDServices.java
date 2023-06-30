package com.ser516groupC.WorkBD.services;

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

import com.ser516groupC.WorkBD.model.WorkBDDailyValue;
import com.ser516groupC.WorkBD.model.WorkBDModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class WorkBDServices {
    private String projName;
    private String sprintName;

    @Autowired
    TaigaRequests taigaReq;

    WorkBDModel WorkBDData = new WorkBDModel();

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
    
    public void fetchWorkBDValues() throws JSONException
    {
        // Get Milestone stats and userstory details from Taiga MS
        JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
        JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
        
        //Store storypoints completed per date
        HashMap<String, Double> dateSPMap = new HashMap<>();
        
        double totalSP = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(int i =0; i<userStoryDetailArray.length(); i++)
        {
            //Get userstory points from json
            JSONObject userStoryDetail = userStoryDetailArray.getJSONObject(i);
            double storyPoint = Float.parseFloat(userStoryDetail.getString("userStoryPoints"));
            totalSP += storyPoint;

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
            if(dateSPMap.get(finishedDate) == null)
            {
                dateSPMap.put(finishedDate, storyPoint);
            }
            else //If date already found, add the story points to existing points
            {
                dateSPMap.put(finishedDate, dateSPMap.get(finishedDate)+storyPoint);
            }
        }
        
        System.out.println("------------HASH MAP ---------------");
        System.out.println(Arrays.asList(dateSPMap));

        //Find the start date and end date of sprint from Milestone stats
        String startDateStr = milestoneStatsJSON.getString("estimated_start");
        String endDateStr = milestoneStatsJSON.getString("estimated_finish");

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
        
        List<WorkBDDailyValue> dVList = new ArrayList<>();
        long noOfDays = Duration.between(cDate.toInstant(), cEndDate.toInstant()).toDays();
        long dailyIdealWork = (long)totalSP/noOfDays;
        //until cdate.compareTo(cEndDate)<=0
        double runningSPSum = 0.0;
        double runningIdealSP = 0.0;
        while(cDate.compareTo(cEndDate) <=0)
        {
            WorkBDDailyValue dailyValue = new WorkBDDailyValue();
            String dayStr = sdf.format(cDate.getTime());

            //Add daily story points to running sum
            if(dateSPMap.get(dayStr) != null) runningSPSum += dateSPMap.get(dayStr);

            //Add daily ideal sp to ideal running sum
            runningIdealSP += dailyIdealWork;

            dailyValue.setDate(dayStr);
            dailyValue.setIdeal_value(runningIdealSP);
            dailyValue.setActual_value(runningSPSum);

            dVList.add(dailyValue);

            cDate.add(Calendar.DAY_OF_MONTH, 1); 
            
        }     
        
        
        WorkBDData.setTotal_storypoints(totalSP);
        WorkBDData.setDaily_values(dVList);
        
        //Work normalized -- 
    }
    public WorkBDModel fetchWorkBDResponse(String proj_name, String sprint_name) {

        this.projName = proj_name;
        this.sprintName = sprint_name;

        try {
            fetchWorkBDValues();
        } catch (JSONException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        
        return WorkBDData;
    }


}
