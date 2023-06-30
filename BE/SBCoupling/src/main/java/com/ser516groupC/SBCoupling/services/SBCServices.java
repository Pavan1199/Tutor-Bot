package com.ser516groupC.SBCoupling.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ser516groupC.SBCoupling.model.SBCEdges;
import com.ser516groupC.SBCoupling.model.SBCModel;
import com.ser516groupC.SBCoupling.model.SBCNodes;
@Service
public class SBCServices {
    
    private String projName;
    private String sprintName;

    @Autowired
    TaigaRequests taigaReq;

    SBCModel SBCData = new SBCModel();

    public void fetchSBCValues() throws JSONException {
        JSONArray sprintStoriesJSON = taigaReq.fetchSprintStories(projName, sprintName);

        HashMap<Integer, Integer> fanOutCountMap = new HashMap<>();
        HashMap<Integer, Boolean> sprintStoriesMap = new HashMap<>();
        HashMap<Integer, Integer> fanInCountMap = new HashMap<>();

        List<SBCEdges> edgesList = new ArrayList<SBCEdges>();
        List<SBCNodes> nodesList = new ArrayList<SBCNodes>();

        // Load all the sprint us in map
        for (int i = 0; i < sprintStoriesJSON.length(); i++) {
            JSONObject detail = sprintStoriesJSON.getJSONObject(i);
            sprintStoriesMap.put(detail.getInt("refNumber"), true);
        }

        for (int i = 0; i < sprintStoriesJSON.length(); i++) {

            int id = 0;

            JSONObject detail = sprintStoriesJSON.getJSONObject(i);
            JSONObject cv = detail.getJSONObject("couplingCV");

            id = detail.getInt("refNumber");

            String dependencyStr = cv.getString("dependsOn");

            String[] depsArray = dependencyStr.split("\\s*,\\s*");
            fanInCountMap.put(id, depsArray.length);

            for (String dep : depsArray) {
                int depOnId = Integer.parseInt(dep);
                if (sprintStoriesMap.get(depOnId) != null) {
                    // Add an edge from parent to child
                    SBCEdges edge = new SBCEdges();
                    edge.setFrom(depOnId);
                    edge.setTo(id);
                    edgesList.add(edge);

                    // Set fan-out count in map
                    if (fanOutCountMap.get(depOnId) == null) {
                        fanOutCountMap.put(depOnId, 1);
                    } else {
                        fanOutCountMap.put(depOnId, fanOutCountMap.get(depOnId) + 1);
                    }
                }

            }

        } // for each story in Sprint

        // Create nodes list
        Iterator iter = sprintStoriesMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Integer, Boolean> mapElement = (Map.Entry<Integer, Boolean>) iter.next();

            SBCNodes node = new SBCNodes();
            int fan_in = 0, fan_out = 0;
            int us_id = (int) mapElement.getKey();

            if (fanInCountMap.get(us_id) != null)
                fan_in = fanInCountMap.get(us_id);
            if (fanOutCountMap.get(us_id) != null)
                fan_out = fanOutCountMap.get(us_id);

            node.setId(us_id);
            node.setLabel("US" + us_id);
            node.setTitle("Fan in : " + fan_in + " Fan out : " + fan_out);

            nodesList.add(node);

        }

        SBCData.setEdges(edgesList);
        SBCData.setNodes(nodesList);

    }

    public SBCModel fetchSBResponse(String projectName, String sprintName) {
       
        this.projName = projectName;
        this.sprintName = sprintName;

        try {
            fetchSBCValues();
        } catch (JSONException e) {
            System.out.println("Exception while handling JSON in fetch work values");
        }
        
        return SBCData;
    }
}
