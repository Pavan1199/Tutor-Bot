package com.AUC.AUC.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.AUC.AUC.model.*;
import com.AUC.AUC.model.AUCDailyValue;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

@Service
public class InitServices {

    private String projName;
    private String sprintName;
    @Autowired
    TaigaRequests taigaReq = new TaigaRequests();

    AUCModel AUCData = new AUCModel();

    public void fetchWorkValues() throws JSONException, ParseException {
        double dailyidealSP=0;
        double totalSP = 0;
        JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
        JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
        String startDateStr = milestoneStatsJSON.getString("estimated_start");
        String endDateStr = milestoneStatsJSON.getString("estimated_finish");
        System.out.println(startDateStr+"--"+endDateStr);
        JSONArray day=milestoneStatsJSON.getJSONArray("days");
        HashMap<String, Double> dateSPMap = new HashMap<>();
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
            double storyPoint = Float.parseFloat(userStoryDetail.getString("userStoryPoints"));
            totalSP += storyPoint;


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


        }
        System.out.println("------------HASH MAP ---------------");
        System.out.println(Arrays.asList(dateSPMap));


        ++daydiff;
        dailyidealSP=(totalSP/daydiff);
        System.out.println("daily ideal value=="+dailyidealSP);
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
        List<AUCDailyValue> dVList = new ArrayList<>();
        int days=0;
        double workAUC=0;
        double runningSPSum = 0.0;
        double spideal=0;
        while(cDate.compareTo(cEndDate) <=0)
        {
            AUCDailyValue dailyValue = new AUCDailyValue();
            String dayStr = sdf.format(cDate.getTime());
            JSONObject idealdate= day.getJSONObject(days);
            //double idealSPValue=Float.parseFloat(idealdate.getString("optimal_points"));
            double openpoints=Float.parseFloat(idealdate.getString("open_points"));
            //openpoints=totalSP-openpoints;
            spideal=spideal+dailyidealSP;
            //Add daily story points to running sum
           if(dateSPMap.get(dayStr) != null) runningSPSum += dateSPMap.get(dayStr);

            if (spideal<=runningSPSum){
                workAUC+=(runningSPSum-spideal);
            }
            else if(runningSPSum<=spideal){
                workAUC+=(spideal-runningSPSum);
            }

            dailyValue.setDate(dayStr);
            dailyValue.setSpDone(runningSPSum);
            dailyValue.setSpideal(spideal);

            dVList.add(dailyValue);
            System.out.println("cvList ======"+dVList);
            days++;
            cDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println("AUC Calculated===="+workAUC);

        AUCData.setTotalSP(totalSP);
        AUCData.setIdealDailySP(dailyidealSP);
        AUCData.setworkAUC(workAUC);
        AUCData.setDailyValuesList(dVList);
    }
    public AUCModel fetchAUCResponse(String proj_name, String sprint_name) {

        this.projName = proj_name;
        this.sprintName = sprint_name;

        try {
            fetchWorkValues();
        } catch (JSONException | ParseException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        return AUCData;
    }
}
