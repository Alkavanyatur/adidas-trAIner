package com.adAIdas.web.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adAIdas.web.model.Training;

@RestController
public class TrainingController {
	
	@DeleteMapping("/sessionDelete/{userId}")
	ResponseEntity<?> deleteSession(@PathVariable String userId) throws IOException {
		boolean borrado = FBManager.deleteSession(userId);

		return (borrado ? ResponseEntity.noContent() : ResponseEntity.notFound()).build();
	}
	
	@PostMapping("/sessionDone")
	ResponseEntity<?> sessionDone(@RequestBody Training input) throws IOException {
		addTraining(input);
		
		return deleteSession(input.getSession().getUser().getId());
	}

	private void addTraining(Training input) throws IOException {
		System.out.println("Adding training from user experience");
//		FBManager.addTrainingToUrl(input);//I finished hackathon here!! 
	}
}
