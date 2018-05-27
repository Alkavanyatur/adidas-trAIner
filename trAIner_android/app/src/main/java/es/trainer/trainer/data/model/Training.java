package es.trainer.trainer.data.model;

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
	
}
