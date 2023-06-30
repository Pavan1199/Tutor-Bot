package com.ser516groupC.BDConsistency.services;

import java.text.SimpleDateFormat;
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

import com.ser516groupC.BDConsistency.model.BDCDailyValue;
import com.ser516groupC.BDConsistency.model.BDCModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;


@Service
public class BDCServices {
    private String projName;
    private String sprintName;

    @Autowired
    TaigaRequests taigaReq;

    BDCModel BDCData = new BDCModel();

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
    
    public void fetchWorkValues() throws JSONException
    {
        JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
        JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
        

        HashMap<String, Double> dateSPMap = new HashMap<>();
        HashMap<String, Double> dateBVMap= new HashMap<>();
        
        double totalSP = 0;
        double totalBV = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(int i =0; i<userStoryDetailArray.length(); i++)
        {
            JSONObject userStoryDetail = userStoryDetailArray.getJSONObject(i);
            double storyPoint = Float.parseFloat(userStoryDetail.getString("userStoryPoints"));
            double bzValue = getBV(userStoryDetail.getString("businessValue"));
            totalSP += storyPoint;
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
            

            if(dateSPMap.get(finishedDate) == null)
            {
                dateSPMap.put(finishedDate, storyPoint);
            }
            else
            {
                dateSPMap.put(finishedDate, dateSPMap.get(finishedDate)+storyPoint);
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
        System.out.println(Arrays.asList(dateSPMap));
        System.out.println(Arrays.asList(dateBVMap));
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
        
        List<BDCDailyValue> dVList = new ArrayList<>();
        //until cdate.compareTo(cEndDate)<=0
        double runningBVSum = 0.0;
        double runningSPSum = 0.0;
        double totalBDC = 0.0;
        while(cDate.compareTo(cEndDate) <=0)
        {
            BDCDailyValue dailyValue = new BDCDailyValue();
            String dayStr = sdf.format(cDate.getTime());
            if(dateSPMap.get(dayStr) != null) runningSPSum += dateSPMap.get(dayStr);
            if(dateBVMap.get(dayStr) != null) runningBVSum += dateBVMap.get(dayStr);

            Double normSP = runningSPSum/totalSP;
            Double normBV = runningBVSum/totalBV;

            totalBDC += Math.abs(normSP-normBV);

            dailyValue.setDate(dayStr);
            dailyValue.setSpDone(normSP);
            dailyValue.setBvDone(normBV);

            dVList.add(dailyValue);

            cDate.add(Calendar.DAY_OF_MONTH, 1);  
        }     
        
        BDCData.setTotalSP(totalSP);
        BDCData.setTotalBV(totalBV);
        BDCData.setBurndownConsistency(totalBDC);
        BDCData.setDailyValuesList(dVList);
        
        //Work normalized -- 
    }
    public BDCModel fetchBDCResponse(String proj_name, String sprint_name) {

        this.projName = proj_name;
        this.sprintName = sprint_name;

        try {
            fetchWorkValues();
        } catch (JSONException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        
        return BDCData;
    }


}
