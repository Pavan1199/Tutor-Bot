package com.PWD.PWD.services;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class TaigaRequest {
    private String taigaSvcURL = "http://localhost:8080/taiga";
    public JSONArray parseAPIResponseAsArray(HttpURLConnection connection) throws JSONException {
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
        JSONArray responseJsonArray = new JSONArray(responseText);
        return responseJsonArray;
    }

    public JSONObject parseAPIResponse(HttpURLConnection connection) throws JSONException {
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
        JSONObject responseJson = new JSONObject(responseText);
        return responseJson;
    }

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

    public JSONObject fetchMilestoneStats(String projName, String sprintName)
    {
        JSONObject responseJson = null;
        try {
            String jsonInputString = "{\"projectName\": \"" + projName + "\" , \"sprintName\": \"" + sprintName + "\" }";
            HttpURLConnection connection = callPOSTAPI(taigaSvcURL+"/milestone-stats", jsonInputString, "POST", null);
            try(OutputStream outputStream= connection.getOutputStream()) {
                byte[] inputString = jsonInputString.getBytes("utf-8");
                outputStream.write(inputString, 0, inputString.length);
            }
            if(connection.getResponseCode() == 200) {
                //JsonNode jsonNode = objectMapper.readTree(connection.getResponseMessage());
                responseJson = parseAPIResponse(connection);
                System.out.println(responseJson.toString());
            }
        } catch (Exception e) {
            System.out.println("Error while fetching Milestone stats");
            e.printStackTrace();
        }
        return responseJson;
    }

    public JSONArray fetchMilestoneStoryDetails(String projName, String sprintName)
    {
        JSONArray responseJsonArray = null;
        try {
            String jsonInputString = "{\"projectName\": \"" + projName + "\" , \"sprintName\": \"" + sprintName + "\" }";
            HttpURLConnection connection = callPOSTAPI(taigaSvcURL+"/project-stories", jsonInputString, "POST", null);
            try(OutputStream outputStream= connection.getOutputStream()) {
                byte[] inputString = jsonInputString.getBytes("utf-8");
                outputStream.write(inputString, 0, inputString.length);
            }
            if(connection.getResponseCode() == 200) {
                //JsonNode jsonNode = objectMapper.readTree(connection.getResponseMessage());
                responseJsonArray = parseAPIResponseAsArray(connection);
                System.out.println(responseJsonArray.toString());
            }
        } catch (Exception e) {
            System.out.println("Error while fetching Milestone User Story details");
            e.printStackTrace();
        }
        return responseJsonArray;
    }

}
