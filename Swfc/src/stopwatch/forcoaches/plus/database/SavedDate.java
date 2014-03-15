package stopwatch.forcoaches.plus.database;

public class SavedDate {
	long id;
	String date;
	
	public SavedDate(long id, String date) {
		this.id = id;
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	
	public long getId() {
		return id;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
