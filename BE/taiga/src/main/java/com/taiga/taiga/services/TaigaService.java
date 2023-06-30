package com.taiga.taiga.services;

import com.taiga.taiga.RestAuth;
import com.taiga.taiga.models.*;
import com.taiga.taiga.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaigaService {

    @Value("${taiga.username}")
    private String taigaUsername;

    @Value("${taiga.password}")
    private String taigaPassword;

    @Autowired
    RestAuth restAuth;

    String authToken = new String();

    @Autowired
    TaigaAPIPublicService taigaAPIPublicService;

    public String getTaigaAuthToken() {
        return restAuth.getAuthToken(taigaUsername, taigaPassword);
    }

    public boolean authenticateTaiga(TaigaAuthModel taigaAuthModel) {
        HttpStatus httpStatus =  restAuth.authenticateTaiga(taigaAuthModel.getUsername(), taigaAuthModel.getPassword(), null);
        if(httpStatus == HttpStatus.OK) {
            authToken = restAuth.authToken;
            return true;
        }
        return false;
    }

    public List<TaigaProjectsSprintModel> taigaProjectList() {
        List<TaigaProjectsSprintModel> taigaProjectsSprintModelList = new ArrayList<>();
        TaigaProjectsSprintModel taigaProjectsSprintModel = new TaigaProjectsSprintModel();
        taigaProjectsSprintModel.setProjectName("dbiegan-sbs-bieganski");
        List<String > sprintNameList = new ArrayList<>();
        sprintNameList.add("Sprint 4");
        sprintNameList.add("Sprint 3");
        sprintNameList.add("Sprint 2");
        sprintNameList.add("Sprint 1");
        taigaProjectsSprintModel.setSprintName(sprintNameList);
        taigaProjectsSprintModelList.add(taigaProjectsSprintModel);
        return taigaProjectsSprintModelList;
    }

    public String taigaMilestoneStatsDetails(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
       JSONObject mileStoneStats =  taigaAPIPublicService.getTaigaProjectDetail(taigaMilestoneRequestModel, authToken);
       String response = mileStoneStats.toString();
       return response;
    }

    public List<TaigaUserStory> taigaProjectUserStoriesList(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        List<TaigaUserStory> taigaUserStoryList = new ArrayList<>();
        try {
            JSONArray userStoriesArray = taigaAPIPublicService.getProjectUserStories(taigaMilestoneRequestModel, authToken);
            for(int i=0;i<userStoriesArray.length();i++) {
                TaigaUserStory taigaUserStory = new TaigaUserStory();
                JSONObject userStoryJson = userStoriesArray.getJSONObject(i);

                Integer userStoryId = Integer.parseInt(userStoryJson.getString("id"));
                String subject = userStoryJson.getString("subject");
                String finishDateString = userStoryJson.getString("finish_date");

                taigaUserStory.setId(userStoryId);
                taigaUserStory.setSubject(subject);
                taigaUserStory.setFinishDate(finishDateString);
                taigaUserStoryList.add(taigaUserStory);

            }

            for(TaigaUserStory taigaUserStory: taigaUserStoryList) {
                Integer id = taigaUserStory.getId();
                String customAttribute = taigaAPIPublicService.getCustomAttributeValue(id, authToken);
                JSONObject customAttributeJson = new JSONObject(customAttribute);
                JSONObject attributeJson = customAttributeJson.getJSONObject("attributes_values");
                if(attributeJson.has("31169"))
                    taigaUserStory.setBusinessValue(attributeJson.getString("31169"));
                taigaUserStory.setUserStoryPoints(taigaAPIPublicService.getUserStoryPoints(id, authToken));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return taigaUserStoryList;
        
    }

    public List<TaigaUserStory > taigaAllProjectUserStoriesList(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        List<TaigaUserStory > taigaUserStoryList = new ArrayList<>();
        String projectName = taigaMilestoneRequestModel.getProjectName();
        Integer projectId = null;
        try {
            JSONArray userProjectsJsonArray = taigaAPIPublicService.getUserProjects(restAuth.userAuthDetail.getTaigaId(), authToken);
            for(int i=0;i<userProjectsJsonArray.length();i++) {
                JSONObject userProject = userProjectsJsonArray.getJSONObject(i);
                if(userProject.getString("slug").equalsIgnoreCase(projectName)) {
                    projectId = userProject.getInt("id");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(projectId == null)
            return taigaUserStoryList;


        try {
            String userStoriesString = taigaAPIPublicService.getUserStoriesForAProject(projectId, authToken);
            JSONArray userStoriesJsonArray = new JSONArray(userStoriesString);
            for(int i=0;i<userStoriesJsonArray.length();i++) {
                TaigaUserStory taigaUserStory = new TaigaUserStory();
                JSONObject userStory = userStoriesJsonArray.getJSONObject(i);
                taigaUserStory.setId(userStory.getInt("id"));
                taigaUserStory.setSubject(userStory.getString("subject"));
                taigaUserStory.setRefNumber(userStory.getInt("ref"));
                taigaUserStoryList.add(taigaUserStory);
            }

            taigaUserStoryList = taigaSetCouplingValuesForStories(projectId, taigaUserStoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taigaUserStoryList;
    }

    public List<TaigaUserStory > taigaGetSprintUserStories(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        //sprintId = 449782;
        List<TaigaUserStory > taigaUserStoryList = new ArrayList<>();
        ProjectSprintModel model = getProjectSprintIDs(taigaMilestoneRequestModel);
        System.out.println("ids are " + model.getSprintId() + ", " + model.getProjectId());
        try {
            String userStoriesString = taigaAPIPublicService.getUserStoriesForASprint(model.getSprintId(), authToken);
            //System.out.println(userStoriesString);

            JSONArray userStoriesJsonArray = new JSONArray(userStoriesString);
            for(int i=0;i<userStoriesJsonArray.length();i++) {
                TaigaUserStory taigaUserStory = new TaigaUserStory();
                JSONObject userStory = userStoriesJsonArray.getJSONObject(i);
                taigaUserStory.setId(userStory.getInt("id"));
                taigaUserStory.setRefNumber(userStory.getInt("ref"));
                taigaUserStoryList.add(taigaUserStory);
            }
            System.out.print(taigaUserStoryList.toString());
            taigaUserStoryList = taigaSetCouplingValuesForStories(model.getProjectId(), taigaUserStoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taigaUserStoryList;
    }

    public CouplingCV getCouplingCustomAttribute(Integer projectId) {
        CouplingCV couplingCV = new CouplingCV();
        try {
            String customAttributesString = taigaAPIPublicService.getCustomAttributesForProject(projectId, authToken);
            JSONArray customAttributeJsonArray = new JSONArray(customAttributesString);
            for(int i=0;i<customAttributeJsonArray.length();i++) {
                JSONObject customAttributeJsonObject = customAttributeJsonArray.getJSONObject(i);
                String customAttributeName = customAttributeJsonObject.getString("name");
                if(customAttributeName.equalsIgnoreCase(Utils.CUSTOM_ATTRIBUTE_COUPLING_NAME)) {
                    couplingCV.setId(customAttributeJsonObject.getInt("id"));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return couplingCV;
    }

    public List<TaigaUserStory > taigaSetCouplingValuesForStories(Integer projectId, List<TaigaUserStory > taigaUserStoryList) {
        String couplingAttributeId = String.valueOf(getCouplingCustomAttribute(projectId).getId());
        try {
            for(TaigaUserStory taigaUserStory: taigaUserStoryList) {
                CouplingCV couplingCV = new CouplingCV();
                couplingCV.setId(Integer.parseInt(couplingAttributeId));
                String couplingCustomValue = taigaAPIPublicService.getCustomAttributeValue(taigaUserStory.getId(), authToken);
                
                JSONObject couplingCustomValueJson = new JSONObject(couplingCustomValue);
                JSONObject attributesValuesJson = couplingCustomValueJson.getJSONObject("attributes_values");
                if(attributesValuesJson.has(couplingAttributeId)) {
                    String couplingAttributeUrl = attributesValuesJson.getString(couplingAttributeId);
                    couplingCV.setDependsOn(couplingAttributeUrl);
                }

                taigaUserStory.setCouplingCV(couplingCV);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taigaUserStoryList;
    }

    public ProjectSprintModel getProjectSprintIDs(TaigaMilestoneRequestModel taigaMilestoneRequestModel)
    {
        ProjectSprintModel model = new ProjectSprintModel();

        JSONObject sprintdetails = taigaAPIPublicService.getProjectSprintJSON(taigaMilestoneRequestModel, authToken);
        try {
            int projectId = sprintdetails.getInt("project");
            int sprintId = sprintdetails.getInt("id");

            model.setProjectId(projectId);
            model.setSprintId(sprintId);
            
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return model;
    }

}
