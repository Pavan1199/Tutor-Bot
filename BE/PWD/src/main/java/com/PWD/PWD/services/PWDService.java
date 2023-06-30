package com.PWD.PWD.services;

import com.PWD.PWD.model.PWDDailyValue;
import com.PWD.PWD.model.PWDModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
@Service
public class PWDService {
   private String projName;
   private String sprintName;
   @Autowired
   TaigaRequest taigaReq = new TaigaRequest();
   PWDModel pwdModel=new PWDModel();


   private void fetchWorkValues() throws JSONException, ParseException {
      double dailyidealSP=0;
      double totalSP = 0;
      JSONObject milestoneStatsJSON = taigaReq.fetchMilestoneStats(projName, sprintName);
      JSONArray userStoryDetailArray = taigaReq.fetchMilestoneStoryDetails(projName, sprintName);
      String startDateStr = milestoneStatsJSON.getString("estimated_start");
      String endDateStr = milestoneStatsJSON.getString("estimated_finish");
      //System.out.println("milestoneStats---"+milestoneStatsJSON);
      //System.out.println("Milestone Story details---"+userStoryDetailArray);
      JSONArray day=milestoneStatsJSON.getJSONArray("days");
      HashMap<String, Double> dateSPMap = new HashMap<>();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date startdate=sdf.parse(milestoneStatsJSON.getString("estimated_start"));
      Date endddate=sdf.parse(milestoneStatsJSON.getString("estimated_finish"));
      long diffInMillies = Math.abs(endddate.getTime() - startdate.getTime());
      long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
      double daydiff=(double)diff;


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
      System.out.println("daydifferene"+daydiff);
      ++daydiff;
      dailyidealSP=(totalSP/daydiff);
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
      List<PWDDailyValue> dVList = new ArrayList<>();
      int days=0;
      double spideal=0;
      while(cDate.compareTo(cEndDate) <=0)
      {
         PWDDailyValue dailyValue = new PWDDailyValue();
         String dayStr = sdf.format(cDate.getTime());
         JSONObject idealdate= day.getJSONObject(days);
         //double idealSPValue=Float.parseFloat(idealdate.getString("optimal_points"));
         double openpoints=Float.parseFloat(idealdate.getString("open_points"));
         openpoints=totalSP-openpoints;
         spideal=spideal+dailyidealSP;

         dailyValue.setDate(dayStr);
         dailyValue.setSpDone(openpoints);
         dailyValue.setSpideal(spideal);

         dVList.add(dailyValue);
         days++;
         cDate.add(Calendar.DAY_OF_MONTH, 1);
      }


      pwdModel.setTotalSP(totalSP);
      pwdModel.setIdealDailySP(dailyidealSP);
      pwdModel.setDailyValuesList(dVList);
   }
   public PWDModel fetchPWDResponse(String proj_name, String sprint_name) {

      this.projName = proj_name;
      this.sprintName = sprint_name;
      try {
         fetchWorkValues();
      } catch (JSONException | ParseException e) {
         System.out.println("Exception while handling JSON in fetch work values");
      }
      return pwdModel;
   }
   }

