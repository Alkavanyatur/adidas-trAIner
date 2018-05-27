package com.adAIdas.web.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adAIdas.web.model.Conditions;
import com.adAIdas.web.model.Exercise;
import com.adAIdas.web.model.Session;
import com.adAIdas.web.model.SessionConfiguration;
import com.adAIdas.web.model.Training;
import com.adAIdas.web.model.User;
import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

@RestController
public class SessionController {

    @RequestMapping("/session/{id}")
    public Session session(@PathVariable String id) throws IOException {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		
		User u = json.fromJson(User.class,
				FBManager.getJSONFromUrl("users/" + id + "/personal.json"));
		Conditions c = json.fromJson(Conditions.class, 
				FBManager.getJSONFromUrl("sessions/future/" + id + "/conditions.json"));
		Exercise e = json.fromJson(Exercise.class, 
				FBManager.getJSONFromUrl("sessions/future/" + id + "/exercise.json"));
        Session s = new Session(u, c, e);
        u.setId(id);
        
        return s;
    }
    
    @RequestMapping("/session/done/{id}")
    public Training[] sessionsDone(@PathVariable String id) throws IOException {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		
		User u = json.fromJson(User.class,
				FBManager.getJSONFromUrl("users/" + id + "/personal.json"));
		SessionConfiguration[] scs = json.fromJson(SessionConfiguration[].class, 
				FBManager.getJSONFromUrl("users/" + id + "/sessionsDone.json"));
		
		Training[] trainings = new Training[scs.length];
        u.setId(id);
        for (int i = 0; i < scs.length; i++) {
        	SessionConfiguration sc = scs[i];
			Session s = new Session(u, sc.getConditions(), sc.getExercise());
			trainings[i] = new Training(sc.getRating(), sc.getHr(), s);
		}
        
        return trainings;
    }
    
}
