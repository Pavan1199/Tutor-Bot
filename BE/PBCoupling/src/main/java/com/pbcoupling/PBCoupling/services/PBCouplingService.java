package com.pbcoupling.PBCoupling.services;

import com.pbcoupling.PBCoupling.models.CouplingCV;
import com.pbcoupling.PBCoupling.models.PBCouplingDetails;
import com.pbcoupling.PBCoupling.models.TaigaMilestoneRequestModel;
import com.pbcoupling.PBCoupling.models.TaigaUserStory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PBCouplingService {

    @Autowired
    TaigaRequests taigaRequests;

    public List<PBCouplingDetails> getPBCouplingDetails(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        List<PBCouplingDetails> pbCouplingDetailsList = new ArrayList<>();

        List<TaigaUserStory> taigaUserStoryList = getProjectStoriesList(taigaMilestoneRequestModel);


        // FanOut details
        PBCouplingDetails pbCouplingDetailsFanOut = new PBCouplingDetails();
        pbCouplingDetailsFanOut.setType("FanOut");
        for(TaigaUserStory taigaUserStory: taigaUserStoryList) {
            Integer storyRefNumber = taigaUserStory.getRefNumber();
            List<String > usList = pbCouplingDetailsFanOut.getDependencyMap().get(taigaUserStory.getSubject());
            if(usList == null) {
                usList = new ArrayList<>();
                pbCouplingDetailsFanOut.getDependencyMap().put(taigaUserStory.getSubject(), usList);
            }
            for(TaigaUserStory taigaUserStory1: taigaUserStoryList) {
                String dependsOnString = taigaUserStory1.getCouplingCV().getDependsOn();
                if(dependsOnString!= null && dependsOnString.contains(String.valueOf(storyRefNumber))) {
                    pbCouplingDetailsFanOut.getDependencyMap().get(taigaUserStory.getSubject()).add(taigaUserStory1.getSubject());
                }
            }
        }
        pbCouplingDetailsList.add(pbCouplingDetailsFanOut);

        // FanIn details
        PBCouplingDetails pbCouplingDetailsFanIn = new PBCouplingDetails();
        pbCouplingDetailsFanIn.setType("FanIn");
        for(TaigaUserStory taigaUserStory: taigaUserStoryList) {
            List<String > usList = pbCouplingDetailsFanIn.getDependencyMap().get(taigaUserStory.getSubject());
            if(usList == null) {
                usList = new ArrayList<>();
                pbCouplingDetailsFanIn.getDependencyMap().put(taigaUserStory.getSubject(), usList);
            }
            String dependsOnString = taigaUserStory.getCouplingCV().getDependsOn();
            if(dependsOnString == null) continue;
            for(TaigaUserStory taigaUserStory1: taigaUserStoryList) {
                if(dependsOnString.contains(String.valueOf(taigaUserStory1.getRefNumber()))) {
                    pbCouplingDetailsFanIn.getDependencyMap().get(taigaUserStory.getSubject()).add(taigaUserStory1.getSubject());
                }
            }

        }
        pbCouplingDetailsList.add(pbCouplingDetailsFanIn);

        return pbCouplingDetailsList;
    }

    public TaigaUserStory getUSForRefNumber(Integer refNumber, List<TaigaUserStory> taigaUserStoryList) {
        for(TaigaUserStory taigaUserStory: taigaUserStoryList) {
            if(taigaUserStory.getRefNumber() == refNumber)
                return taigaUserStory;
        }
        return null;
    }

    public  List<TaigaUserStory> getProjectStoriesList(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        List<TaigaUserStory> taigaUserStoryList = new ArrayList<>();
        JSONArray taigaProjectStoriesJsonArray = new JSONArray(taigaRequests.getTaigaProjectStories(taigaMilestoneRequestModel));
        for(int i=0;i<taigaProjectStoriesJsonArray.length();i++) {
            TaigaUserStory taigaUserStory = new TaigaUserStory();
            taigaUserStory.setId(taigaProjectStoriesJsonArray.getJSONObject(i).getInt("id"));
            taigaUserStory.setSubject(taigaProjectStoriesJsonArray.getJSONObject(i).getString("subject"));
            taigaUserStory.setRefNumber(taigaProjectStoriesJsonArray.getJSONObject(i).getInt("refNumber"));
            CouplingCV couplingCV = new CouplingCV();
            JSONObject jsonObject = taigaProjectStoriesJsonArray.getJSONObject(i).getJSONObject("couplingCV");
            if(jsonObject.get("url") != null && !jsonObject.get("url").toString().equalsIgnoreCase("null"))
                couplingCV.setUrl(jsonObject.get("url").toString());
            if(jsonObject.get("dependsOn") != null && !jsonObject.get("dependsOn").toString().equalsIgnoreCase("null"))
                couplingCV.setDependsOn(jsonObject.get("dependsOn").toString());
            taigaUserStory.setCouplingCV(couplingCV);
            taigaUserStoryList.add(taigaUserStory);
        }
        return taigaUserStoryList;
    }

}
