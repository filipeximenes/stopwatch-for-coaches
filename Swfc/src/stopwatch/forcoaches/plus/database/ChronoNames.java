package stopwatch.forcoaches.plus.database;

public class ChronoNames {
	int id;
	String name;
	
	public ChronoNames(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
