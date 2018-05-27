package com.adAIdas.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adAIdas.web.model.Exercise;
import com.adAIdas.web.model.Work;
import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonSerializer;
import com.esotericsoftware.jsonbeans.JsonValue;
import com.esotericsoftware.jsonbeans.OutputType;

@RestController
public class ExerciseController {

    @RequestMapping("/exercises")
    public Exercise[] exercises() throws IOException {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		json.setSerializer(Exercise[].class, new SerializerExercise());
		
		String js = FBManager.getJSONFromUrl("exercises.json");
		Exercise[] exs = json.fromJson(Exercise[].class, js);
        
        return exs;
    }
    
	private class SerializerExercise implements JsonSerializer<Exercise[]>{

		@Override
		public void write(Json json, Exercise[] object, Class knownType) {
			// TODO Implementar cuando se demande
			
		}

		@Override
		public Exercise[] read(Json json, JsonValue jsonData, Class type) {
			Collection<Exercise> exers = new ArrayList<>();
			JsonValue v = jsonData.child();
			while(v != null) {
				String id = v.name();
				Work w;
				JsonValue child = v.child();
				v = v.next();
				if("work".equals(child.name())) {
					w = json.readValue(Work.class, child);
					Exercise e = new Exercise();
					e.setWork(w);
					exers.add(e);
				}
				
			}
			exers.forEach(e -> System.out.println(e));
			
			return exers.toArray(new Exercise[0]);
		}
		
	}
}
