package com.adAIdas.web.model;

public class Training implements Ratingable {

	float rating;//de 0 a 1f
	int hr;
	Session session;
	
	public float getRating() {
		return rating;
	}
	public int getHr() {
		return hr;
	}
	public Session getSession() {
		return session;
	}
	
	public Training() {}
	
	public Training(float rating, int hr, Session session) {
		super();
		this.rating = rating;
		this.hr = hr;
		this.session = session;
	}
	
}
