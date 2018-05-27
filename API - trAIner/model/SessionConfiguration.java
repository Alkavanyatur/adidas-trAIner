package com.adAIdas.web.model;

public class SessionConfiguration {
	int hr;
	float rating;
	Conditions conditions;
	Exercise exercise;
	
	public int getHr() {
		return hr;
	}
	public float getRating() {
		return rating;
	}
	public Conditions getConditions() {
		return conditions;
	}
	public Exercise getExercise() {
		return exercise;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
}
