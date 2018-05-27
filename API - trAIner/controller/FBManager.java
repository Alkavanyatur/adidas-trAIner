package com.adAIdas.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.adAIdas.web.model.SessionConfiguration;
import com.adAIdas.web.model.Training;
import com.esotericsoftware.jsonbeans.Json;

public class FBManager {
	static String siteUrl = "https://trainer-hack.firebaseio.com/";

    static String getJSONFromUrl(String path) throws IOException {
        URL url = new URL(siteUrl + path);
    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	connection.setRequestMethod("GET");
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
		in.close();
		connection.disconnect();
		
		return content.toString();
    }
    
    static boolean deleteFromUrl(String path) throws IOException {
        URL url = new URL(siteUrl + path);
    	HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	connection.setRequestMethod("DELETE");
    	connection.disconnect();
    	
    	return connection.getResponseCode() == 200;
    }
    
    static boolean deleteSession(String userId) throws IOException {
    	return deleteFromUrl("sessions/future/" + userId + ".json");
    }
    
    static boolean addTrainingToUrl(Training training) throws IOException {
        URL url = new URL(siteUrl + "users/" + training.getSession().getUser().getId() + "/sessionDone");
    	HttpURLConnection con = (HttpURLConnection) url.openConnection();
    	con.setDoOutput(true);
    	con.setDoInput(true);
    	con.setRequestProperty("Content-Type", "application/json");
    	con.setRequestProperty("Accept", "application/json");
    	con.setRequestMethod("POST");
    	
    	SessionConfiguration sc = new SessionConfiguration();
    	sc.setHr(training.getHr());
    	sc.setRating(training.getRating());
    	sc.setConditions(training.getSession().getConditions());
    	sc.setExercise(training.getSession().getExercise());
    	
    	Json json = new Json();
    	OutputStream os = con.getOutputStream();
    	os.write(json.toJson(sc).getBytes("UTF-8"));
    	os.close();
    	
    	con.disconnect();
    	
    	return con.getResponseCode() == 200;
    }
}
