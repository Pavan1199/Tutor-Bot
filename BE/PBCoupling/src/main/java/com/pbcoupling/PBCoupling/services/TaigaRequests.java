package com.pbcoupling.PBCoupling.services;

import com.pbcoupling.PBCoupling.models.TaigaMilestoneRequestModel;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class TaigaRequests {

    @Value("${taiga.hostUrl}")
    private String taigaSvcURL;

    public HttpURLConnection callPOSTAPI(String stringURL, String jsonInputString, String requestType, String authToken) throws IOException {

        System.out.println("---------Req JSON-----------");
        System.out.println(jsonInputString);
        System.out.println("---------Req URL------------");
        System.out.println(stringURL);

        URL url = new URL(stringURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/json");
        if(authToken != null )
            connection.setRequestProperty("Authorization", authToken);
        connection.setDoOutput(true);
        connection.setRequestMethod(requestType);
        try(OutputStream outputStream= connection.getOutputStream()) {
            byte[] inputString = jsonInputString.getBytes("utf-8");
            outputStream.write(inputString, 0, inputString.length);
        }
        return connection;
    }

    public String parseAPIResponse(HttpURLConnection connection) throws JSONException {
        String responseText = null;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            responseText= String.valueOf(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseText;
    }

    public String getTaigaProjectStories(TaigaMilestoneRequestModel taigaMilestoneRequestModel) {
        String responseString = null;
        try {
            String jsonInputString = "{\"projectName\": \"" + taigaMilestoneRequestModel.getProjectName() + "\" , \"sprintName\": \"" + taigaMilestoneRequestModel.getSprintName() + "\" }";
            HttpURLConnection connection = callPOSTAPI(taigaSvcURL+"/project-all-stories", jsonInputString, "POST", null);
            try(OutputStream outputStream= connection.getOutputStream()) {
                byte[] inputString = jsonInputString.getBytes("utf-8");
                outputStream.write(inputString, 0, inputString.length);
            }
            if(connection.getResponseCode() == 200) {
                //JsonNode jsonNode = objectMapper.readTree(connection.getResponseMessage());
                responseString = parseAPIResponse(connection);
            }
        } catch (Exception e) {
            System.out.println("Error while fetching Milestone stats");
            e.printStackTrace();
        }
        return responseString;
    }
}
