package es.trainer.trainer.data.model;

public class Session {
	User user;
	Conditions conditions;
	Exercise exercise;
	
	public User getUser() {
		return user;
	}
	public Conditions getConditions() {
		return conditions;
	}
	public Exercise getExercise() {
		return exercise;
	}
	
	public Session(User user, Conditions conditions, Exercise exercise) {
		super();
		this.user = user;
		this.conditions = conditions;
		this.exercise = exercise;
	}
	
	
//	long user;
//	String exercise;
	
//	public long getUser() {
//		return user;
//	}
//	public String getExercise() {
//		return exercise;
//	}
	
}
