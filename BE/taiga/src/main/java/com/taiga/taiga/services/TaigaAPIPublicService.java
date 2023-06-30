package com.taiga.taiga.services;

import com.taiga.taiga.models.TaigaMilestoneRequestModel;
import com.taiga.taiga.utils.Utils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaigaAPIPublicService {
    public List<String> getTaigaProjectsList() {
        List<String> projectList = new ArrayList<>();
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        try {
            HttpURLConnection httpURLConnection;
            httpURLConnection = taigaAPIService.callPOSTAPI("https://api.taiga.io/api/v1/projects", null, Utils.API_REQUEST_TYPE_GET,null);
            if (httpURLConnection.getResponseCode() == 200) {
                JSONObject responseJson = taigaAPIService.parseAPIResponse(httpURLConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }

    public JSONObject getTaigaProjectDetail(TaigaMilestoneRequestModel taigaMilestoneRequestModel, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        JSONObject mileStoneDetailsJson = new JSONObject();
        try {
            HttpURLConnection httpURLConnection;
            String responseString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/projects/by_slug?slug="+taigaMilestoneRequestModel.getProjectName(), authToken);
            JSONObject responseJson = new JSONObject(responseString);
            String projectId = responseJson.get("id").toString();
            String responseString2 = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/milestones?project="+projectId, authToken);
            JSONArray response2Array = new JSONArray(responseString2);
            JSONObject sprintJson = new JSONObject();
            for(int i=0;i<response2Array.length();i++) {
                sprintJson = response2Array.getJSONObject(i);
                String sprintName = sprintJson.getString("name");
                if(sprintName.equalsIgnoreCase(taigaMilestoneRequestModel.getSprintName()))break;
            }
            JSONArray userStoriesArray = (JSONArray) sprintJson.get("user_stories");
            JSONObject userStory = (JSONObject) userStoriesArray.get(0);
            String mileStoneId = userStory.getString("milestone");
            String responseString3 = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/milestones/"+mileStoneId+"/stats", authToken);
            mileStoneDetailsJson = new JSONObject(responseString3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mileStoneDetailsJson;
    }

    public JSONArray getProjectUserStories(TaigaMilestoneRequestModel taigaMilestoneRequestModel, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        JSONArray userStoriesArray = new JSONArray();
        try {
            String responseString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/projects/by_slug?slug="+taigaMilestoneRequestModel.getProjectName(), authToken);
            JSONObject responseJson = new JSONObject(responseString);
            String projectId = responseJson.get("id").toString();
            String responseString2 = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/milestones?project="+projectId, authToken);
            JSONArray response2Array = new JSONArray(responseString2);
            JSONObject sprintJson = new JSONObject();
            for(int i=0;i<response2Array.length();i++) {
                sprintJson = response2Array.getJSONObject(i);
                String sprintName = sprintJson.getString("name");
                if(sprintName.equalsIgnoreCase(taigaMilestoneRequestModel.getSprintName()))break;
            }
            userStoriesArray = (JSONArray) sprintJson.get("user_stories");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userStoriesArray;
    }

    public String getCustomAttributeValue(Integer id, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        String customAttribute = "";
        try {
            String idString = id.toString();
            customAttribute = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/userstories/custom-attributes-values/"+idString, authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  customAttribute;
    }

    public String getUserStoryPoints(Integer id, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        String userStoryPoints = "0";
        try {
            String idString = id.toString();
            String responseString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/userstories/"+idString, authToken);
            JSONObject responseJson = new JSONObject(responseString);
            userStoryPoints = responseJson.getString("total_points");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  userStoryPoints;
    }

    public String getUserStoriesForAProject(Integer projectId, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        String userStoriesString = "";
        try {
            userStoriesString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/userstories?project="+projectId, authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userStoriesString;
    }

    public String getUserStoriesForASprint(Integer sprintId, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        String milestoneDetails = "";
        String userstoryDetails = "";
        try {
            milestoneDetails = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/milestones/"+sprintId, authToken);
            JSONObject responseJson = new JSONObject(milestoneDetails);
            userstoryDetails = responseJson.getString("user_stories");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userstoryDetails;
    }

    public String getCustomAttributesForProject(Integer projectId, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        String customAttributesString = "";
        try {
            customAttributesString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/userstory-custom-attributes?project="+projectId, authToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customAttributesString;
    }

    public JSONObject getProjectSprintJSON(TaigaMilestoneRequestModel taigaMilestoneRequestModel, String authToken) {
        TaigaAPIService taigaAPIService = new TaigaAPIService();
        JSONObject sprintDetailJSON = new JSONObject();
        try {
            
            String responseString = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/projects/by_slug?slug="+taigaMilestoneRequestModel.getProjectName(), authToken);
            JSONObject responseJson = new JSONObject(responseString);
            String projectId = responseJson.get("id").toString();
            String responseString2 = taigaAPIService.callGETAPI("https://api.taiga.io/api/v1/milestones?project="+projectId, authToken);
            JSONArray response2Array = new JSONArray(responseString2);
            JSONObject sprintJson = new JSONObject();
            for(int i=0;i<response2Array.length();i++) {
                sprintJson = response2Array.getJSONObject(i);
                String sprintName = sprintJson.getString("name");
                if(sprintName.equalsIgnoreCase(taigaMilestoneRequestModel.getSprintName()))break;
            }
            sprintDetailJSON = sprintJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprintDetailJSON;
    }
}
