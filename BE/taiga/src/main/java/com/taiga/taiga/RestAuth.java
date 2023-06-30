package com.taiga.taiga;

import com.taiga.taiga.services.TaigaAPIService;
import com.taiga.taiga.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;

@Service
public class RestAuth {

    public String authToken = null;

    @Autowired
    TaigaAPIService taigaAPIService;

    public String getAuthToken(String userName, String password) {
        if(authToken == null)
            getNewAuthToken(userName, password);
        return authToken;
    }

    public HttpStatus authenticateTaiga(String userName, String password, Integer responseCode) {
        if(authToken == null || responseCode == null || responseCode != 200)
            return getNewAuthToken(userName, password);
        return HttpStatus.OK;
    }

    private HttpStatus getNewAuthToken(String userName, String password) {
        System.out.println("Fetching New Taiga Auth Token....");
        try {
            String jsonInputString = "{\"username\": \"" + userName + "\" , \"password\": \"" + password + "\""
                    + ", \"type\": \""+ Utils.TAIGA_AUTH_TYPE +"\"}";
            HttpURLConnection connection = taigaAPIService.callPOSTAPI(Utils.TAIGA_AUTH_URL, jsonInputString, Utils.API_REQUEST_TYPE_POST, null);
            try(OutputStream outputStream= connection.getOutputStream()) {
                byte[] inputString = jsonInputString.getBytes(Utils.API_CHARACTER_SET_NAME);
                outputStream.write(inputString, 0, inputString.length);
            }
            if(connection.getResponseCode() == 200) {
                JSONObject responseJson = taigaAPIService.parseAPIResponse(connection);
                authToken = "Bearer " + (String) responseJson.get("auth_token");
                return HttpStatus.OK;
            }
        } catch (Exception e) {
            System.out.println("Error While Getting Taiga Auth Token");
            e.printStackTrace();
        }
        return HttpStatus.UNAUTHORIZED;
    }

}
