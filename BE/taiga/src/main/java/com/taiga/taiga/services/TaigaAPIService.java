package com.taiga.taiga.services;

import com.taiga.taiga.RestAuth;
import com.taiga.taiga.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaigaAPIService {

    String responseString = null;

    public HttpURLConnection callPOSTAPI(String stringURL, String jsonInputString, String requestType, String authToken) throws IOException {
        URL url = new URL(stringURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setRequestProperty("Accept", "application/json");
        if(authToken != null )
            connection.setRequestProperty("Authorization", authToken);
        connection.setDoOutput(true);
        connection.setRequestMethod(requestType);
        try(OutputStream outputStream= connection.getOutputStream()) {
            byte[] inputString = jsonInputString.getBytes(Utils.API_CHARACTER_SET_NAME);
            outputStream.write(inputString, 0, inputString.length);
        }
        return connection;
    }

    public String callGETAPI(String stringURL, String authToken) throws IOException {
        URL url = new URL(stringURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", authToken);
        BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            responseString = inputLine;
        }
        return responseString;
    }

    public JSONObject parseAPIResponse(HttpURLConnection connection) throws JSONException {
        String responseText = null;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), Utils.API_CHARACTER_SET_NAME))) {
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
}
