package com.adAIdas.web.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adAIdas.web.model.User;
import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

@RestController
public class UserController {

    @RequestMapping("/user/{id}")
    public User usuarioPorId(@PathVariable String id) throws IOException {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		
		User u = json.fromJson(User.class,
				FBManager.getJSONFromUrl("users/" + id + "/personal.json"));
        u.setId(id);
        
        return u;
    }
    
}
